package com.solr.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.SolrTransactionSynchronizationAdapterBuilder;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

public class BaseSolrRepository<T, ID>{

    private static final String DEFAULT_ID_FIELD = "id";
    public static final int SEARCH_TIME_OUT = 5000; // miliseconds

    private final SolrOperations solrOperations;
    private String idFieldName = DEFAULT_ID_FIELD;
    private final Class<T> entityClass;
    private final String solrCollectionName;

    

    /**
     * @param solrOperations must not be null
     * @param entityClass
     */
    public BaseSolrRepository(SolrTemplate solrOperations, Class<T> entityClass) {
        
        this.solrOperations = solrOperations;
        this.entityClass = entityClass;
        this.solrCollectionName = solrOperations.getSolrCore();
        
    }

    /**
     * default SEARCH_TIME_OUT is applied. If you need to change timeout please use the other method which allows overriding default search timeout.\n
     * findByField( int timeout, String fieldName, String FieldValue, int page, int size )
     * @param fieldName
     * @param FieldValue
     * @param page
     * @param size
     * @return
     */
    public Page<T> findByField( String fieldName, String FieldValue, int page, int size ) {
        
        return findByField( SEARCH_TIME_OUT, fieldName, FieldValue, page, size );
        
    }
    /**
     * 
     * @param timeout 0 means unlimited time. given in miliseconds.
     * @param fieldName
     * @param FieldValue
     * @param page
     * @param size
     * @return
     */
    public Page<T> findByField( int timeout, String fieldName, String FieldValue, int page, int size ) {
        
        Pageable pageable = new SolrPageRequest( page, size );

        //Criteria criteria = new Criteria( fieldName ).is( FieldValue );
        Criteria criteria = new Criteria( fieldName ).expression( "(" + FieldValue + ")" );
        
        Query query = new SimpleQuery( criteria );
        
        query.setPageRequest( pageable );
        
        query.setTimeAllowed( timeout );
        
        SolrOperations solrOperations = getSolrOperations();
        Class<T> entityClass = getEntityClass();
        
        return solrOperations.query(  query, entityClass );
        
    }

    public T findById(ID id) {
        return getSolrOperations().queryForObject(solrCollectionName,
                new SimpleQuery(new Criteria(this.idFieldName).is(id)), getEntityClass());
    }

    public Iterable<T> findAll() {
        int itemCount = (int) this.count();
        if (itemCount == 0) {
            return new PageImpl<>(Collections.<T> emptyList());
        }
        return this.findAll(new SolrPageRequest(0, itemCount));
    }

    public Page<T> findAll(Pageable pageable) {
        return getSolrOperations().queryForPage(solrCollectionName,
                new SimpleQuery(new Criteria(Criteria.WILDCARD).expression(Criteria.WILDCARD)).setPageRequest(pageable),
                getEntityClass());
    }

    public Iterable<T> findAll(Sort sort) {
        int itemCount = (int) this.count();
        if (itemCount == 0) {
            return new PageImpl<>(Collections.<T> emptyList());
        }
        return getSolrOperations().queryForPage(solrCollectionName,
                new SimpleQuery(new Criteria(Criteria.WILDCARD).expression(Criteria.WILDCARD))
                        .setPageRequest(new SolrPageRequest(0, itemCount)).addSort(sort),
                getEntityClass());
    }

    public Iterable<T> findAllById(Iterable<ID> ids) {
        org.springframework.data.solr.core.query.Query query = new SimpleQuery(new Criteria(this.idFieldName).in(ids));
        query.setPageRequest(new SolrPageRequest(0, (int) count(query)));

        return getSolrOperations().queryForPage(solrCollectionName, query, getEntityClass());
    }

    public long count() {
        return count(new SimpleQuery(new Criteria(Criteria.WILDCARD).expression(Criteria.WILDCARD)));
    }

    protected long count(org.springframework.data.solr.core.query.Query query) {
        org.springframework.data.solr.core.query.Query countQuery = SimpleQuery.fromQuery(query);
        return getSolrOperations().count(solrCollectionName, countQuery);
    }

    public <S extends T> S save(S entity) {

        registerTransactionSynchronisationIfSynchronisationActive();
        this.solrOperations.saveBean(solrCollectionName, entity);
        commitIfTransactionSynchronisationIsInactive();
        return entity;
    }


    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {


        if (!(entities instanceof Collection<?>)) {
            throw new InvalidDataAccessApiUsageException("Entities have to be inside a collection");
        }

        registerTransactionSynchronisationIfSynchronisationActive();
        this.solrOperations.saveBeans(solrCollectionName, (Collection<? extends T>) entities);
        commitIfTransactionSynchronisationIsInactive();
        return entities;
    }


    public void deleteById(ID id) {

        registerTransactionSynchronisationIfSynchronisationActive();
        this.solrOperations.deleteById(solrCollectionName, id.toString());
        commitIfTransactionSynchronisationIsInactive();
    }

    public void delete(T entity) {

        deleteAll(Collections.singletonList(entity));
    }

    public void deleteAll(Iterable<? extends T> entities) {

        ArrayList<String> idsToDelete = new ArrayList<>();
        for (T entity : entities) {
            idsToDelete.add(extractIdFromBean(entity).toString());
        }

        registerTransactionSynchronisationIfSynchronisationActive();
        this.solrOperations.deleteById(solrCollectionName, idsToDelete);
        commitIfTransactionSynchronisationIsInactive();
    }

    
    public void deleteAll() {
        registerTransactionSynchronisationIfSynchronisationActive();
        this.solrOperations.delete(solrCollectionName,
                new SimpleFilterQuery(new Criteria(Criteria.WILDCARD).expression(Criteria.WILDCARD)));
        commitIfTransactionSynchronisationIsInactive();
    }

    public final String getIdFieldName() {
        return idFieldName;
    }

    public Class<T> getEntityClass() {

        if (!isEntityClassSet()) {
            throw new InvalidDataAccessApiUsageException("Unable to resolve EntityClass. Please use according setter!");
        }
        return entityClass;
    }

    private boolean isEntityClassSet() {
        return entityClass != null;
    }

    public final SolrOperations getSolrOperations() {
        return solrOperations;
    }

    private Object extractIdFromBean(T entity) {

        SolrInputDocument solrInputDocument = this.solrOperations.convertBeanToSolrInputDocument(entity);
        return extractIdFromSolrInputDocument(solrInputDocument);
        
    }

    private String extractIdFromSolrInputDocument(SolrInputDocument solrInputDocument) {
        Assert.notNull(solrInputDocument.getField(idFieldName),
                "Unable to find field '" + idFieldName + "' in SolrDocument.");
        Assert.notNull(solrInputDocument.getField(idFieldName).getValue(), "ID must not be 'null'.");

        return solrInputDocument.getField(idFieldName).getValue().toString();
    }

    private void registerTransactionSynchronisationIfSynchronisationActive() {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            registerTransactionSynchronisationAdapter();
        }
    }

    private void registerTransactionSynchronisationAdapter() {
        TransactionSynchronizationManager.registerSynchronization(SolrTransactionSynchronizationAdapterBuilder
                .forOperations(this.solrOperations).withDefaultBehaviour());
    }

    private void commitIfTransactionSynchronisationIsInactive() {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            this.solrOperations.commit(solrCollectionName);
        }
    }
    
}