package com.adhocmaster.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import com.book.simpleBook.SimpleBook;
import com.solr.core.BaseSolrRepository;
import com.solr.core.IndexStats;
import com.solr.core.SolrEntity;
import com.solr.lawbase.search.cases.CaseSearchRestController;
import com.solr.lawbase.search.framework.BookIndexService;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;
import util.restApi.RestSuccess;

public abstract class SolrRestController<MongoEntity extends SimpleBook, T extends SolrEntity> extends RestController {

    /**
     * What doesn't break you, makes you.
     */
	
	private static final Logger logger = LoggerFactory.getLogger( CaseSearchRestController.class );

    abstract protected BaseSolrRepository<T, String> getRepository();
    abstract protected BookIndexService<MongoEntity, T> getIndexService();
    abstract protected void callRebuildIndexAsync() throws Exception;

    public boolean IsMaintenanceMode() {
        
        return getIndexService().IsMaintenanceMode();
        
    }

    /**
     * 
     * @param field name of the field, nonblank. fieldname "all" if you want to search everything
     * @param value name of the value, nonblank
     * @param sEcho
     * @param offset
     * @param size
     * @return
     * @throws RestInternalServerException
     * @throws RestBadDataException
     */
    protected DataTableResponseEntity<T> findByFieldForDataTable (

            String field,
            String value,
            int sEcho,
            int offset,
            int size
            
            ) throws RestInternalServerException, RestBadDataException {
        

        if ( IsMaintenanceMode() )
            throw new RestInternalServerException( new Exception( "search engine in maintenance mode." ) );

        if ( StringUtils.isBlank( field ) )
            throw new RestBadDataException( new Exception("field cannot be empty") );
        
        if ( StringUtils.isBlank( value ) )
            throw new RestBadDataException( new Exception("value cannot be empty") );

        try {

            int page = offset / size;
                    
            Page<T> casePage = getRepository().findByField( field, value, page, size );
            
            logger.info(casePage.getContent().toString());
            
            return new DataTableResponseEntity<T>( casePage, sEcho );  
            
        } catch ( Exception e ) {
            
            throw new RestInternalServerException( e );
        }        
        

    }
    
    /**
     * 
     * @param params contains the parameters passed and corresponding values of each parameter, paramater 'courtBookTitle' can not be blank
     * @param sEcho
     * @param offset
     * @param size
     * @return
     * @throws RestInternalServerException
     * @throws RestBadDataException
     */
    protected DataTableResponseEntity<T> findByMultipleFieldsForDataTable (

            Map<String, String> params,
            int sEcho,
            int offset,
            int size
            
            ) throws RestInternalServerException, RestBadDataException {
        

        if ( IsMaintenanceMode() )
            throw new RestInternalServerException( new Exception( "search engine in maintenance mode." ) );

        if ( StringUtils.isBlank( params.get( "courtBookTitle" ) ) )
            throw new RestBadDataException( new Exception("court book title cannot be empty") );

        try {

            int page = offset / size;
                    
            Page<T> casePage = getRepository().findByMultipleFields( params, page, size );
            
            logger.info(casePage.getContent().toString());
            
            return new DataTableResponseEntity<T>( casePage, sEcho );  
            
        } catch ( Exception e ) {
            
            throw new RestInternalServerException( e );
        }        
        

    }


    protected RestSuccess rebuildIndex() throws RestInternalServerException {
        
        try {
            
            if ( IsMaintenanceMode() )
                throw new RestInternalServerException( new Exception( "Another index is already running. Check status." ) );
            
            callRebuildIndexAsync();
            
            return new RestSuccess( RestSuccess.Codes.INDEXING );
            
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
        
    }
    protected IndexStats statsIndex() throws RestInternalServerException {
        
        try {
            
            return getIndexService().getIndexStats();
            
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
        
    }
}
