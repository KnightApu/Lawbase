package com.solr.lawbase.search.framework;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.book.simpleBook.SimpleBook;
import com.book.simpleBook.SimpleBookRepository;
import com.solr.core.IndexStats;
import com.solr.core.SolrEntity;
import com.solr.mongo.MongoCollectionIndexer;
import com.solr.mongo.MongoEntityConverter;

public abstract class BookIndexService<MongoEntity extends SimpleBook, T extends SolrEntity> {

    protected final String COMPLETED = "COMPLETED";
    protected final String ERROR = "ERROR";

    private boolean IsIndexRunning = false;

    @Value("${solr.indexer.batch}")
    protected int recordsPerBatch = 1;
    
    @Value("${solr.indexer.sleep}")
    protected int threadSleepTime = 2000;

    MongoCollectionIndexer<MongoEntity, T> indexer;

    abstract public Logger getLogger();
    abstract public SimpleBookSolrRepository<T> getSolrRepository();
    abstract public SimpleBookRepository<MongoEntity> getDataRepository();
    abstract public MongoEntityConverter<MongoEntity, T> getEntityConverter();
    

    public boolean IsIndexReady() {
        
        return !IsIndexRunning;
        
    }

    public boolean IsMaintenanceMode() {
        
        return IsIndexRunning;
        
    }
    
    public boolean IsIndexRunning() {
        
        return IsIndexRunning;
        
    }
    
    public void IndexStarted() {
        
        getLogger().debug( "Index started" );
        IsIndexRunning = true;
        
    }
    
    public void IndexStopped() {

        getLogger().debug( "Index stopped" );
        IsIndexRunning = false;
        
    }
    
    public IndexStats getIndexStats() {
        
        if ( null == indexer )
            return new IndexStats();
        
        IndexStats s = indexer.getIndexStats();
        
        if ( null == s )
            return new IndexStats();
        
        return s;
        
    }
    

    protected void startIndexing() throws Exception {

        indexer = new MongoCollectionIndexer<MongoEntity, T>( getDataRepository(), getSolrRepository(), getEntityConverter() );
        
        indexer.setRecordsPerBatch( recordsPerBatch );
        indexer.setThreadSleepTime( threadSleepTime );
        
        IndexStarted();
        indexer.rebuildIndex();
        IndexStopped();
        
    }
    
    protected String rebuildIndex() throws Exception {
                
        try {

            if ( IsIndexRunning() )
                throw new Exception( "Index engine already running" );
            
            startIndexing();
            
            return COMPLETED;
            
        } catch ( Exception e ) {

            getLogger().error( e.getMessage() );
            return ERROR + ": " + e.getMessage();
            
        }
    }

}
