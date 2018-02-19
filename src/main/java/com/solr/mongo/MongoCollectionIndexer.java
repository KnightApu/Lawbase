package com.solr.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.book.simpleBook.SimpleBook;
import com.book.simpleBook.SimpleBookRepository;
import com.book.simpleBook.Status;
import com.solr.core.BaseSolrRepository;
import com.solr.core.IndexStats;
import com.solr.core.SolrEntity;

public class MongoCollectionIndexer<Entity extends SimpleBook, T extends SolrEntity> {

    SimpleBookRepository<Entity> mongoRepo;
    BaseSolrRepository<T, String> solrRepo;
    
    MongoEntityConverter<Entity, T> entityConverter;
    
    IndexStats stats = new IndexStats();
    
    int recordsPerBatch = 100;
    int pageNo = 0;    
    int threadSleepTime = 5000;

    public MongoCollectionIndexer( 
            
            SimpleBookRepository<Entity> mongoRepo,
            BaseSolrRepository<T, String> solrRepo,
            MongoEntityConverter<Entity, T> entityConverter
            
            ) {
        
        super();
        this.mongoRepo = mongoRepo;
        this.solrRepo = solrRepo;
        this.entityConverter = entityConverter;
        
    }
    
    public MongoCollectionIndexer( 
            
            SimpleBookRepository<Entity> mongoRepo,
            BaseSolrRepository<T, String> solrRepo, 
            MongoEntityConverter<Entity, T> entityConverter,
            int recordsPerBatch, 
            int pageNo 
            
        ) {
        
        super();
        this.mongoRepo = mongoRepo;
        this.solrRepo = solrRepo;
        this.recordsPerBatch = recordsPerBatch;
        this.entityConverter = entityConverter;
        this.pageNo = pageNo;
        
    }

    
    public int getRecordsPerBatch() {
    
        return recordsPerBatch;
    }

    
    public void setRecordsPerBatch( int recordsPerBatch ) throws Exception {
        
        if ( threadSleepTime < 1 )
            throw new Exception( "invalid recordsPerBatch " + recordsPerBatch );    
    
        this.recordsPerBatch = recordsPerBatch;
    }

    
    public int getThreadSleepTime() {
    
        return threadSleepTime;
    }

    
    public void setThreadSleepTime( int threadSleepTime ) throws Exception {
        
        if ( threadSleepTime < 1000 )
            throw new Exception( "invalid threadSleepTime " + threadSleepTime );
    
        this.threadSleepTime = threadSleepTime;
    }

    /**
     * It sleeps for threadSleepTime after processing a batch. Only indexes public documents
     */

    public void index() {

        startStats();
        
        Page<Entity> page = mongoRepo.findByStatus( Status.PUBLIC, new PageRequest( pageNo, recordsPerBatch ) );
        
        if ( page.hasContent() ) {

            stats.setTotalRecords( page.getTotalElements() );
            
            IndexMongoDocs( page.getContent() );
            stats.incIndexRecords( page.getNumberOfElements() );
            
            while ( page.hasNext() ) {
                
                try {
                    
                    Thread.sleep( threadSleepTime );
                    
                } catch ( InterruptedException e ) {
                    
                    e.printStackTrace();
                    
                }
                
                page = mongoRepo.findByStatus( Status.PUBLIC, new PageRequest( ++pageNo, recordsPerBatch ) );       
                IndexMongoDocs( page.getContent() );   
                stats.incIndexRecords( page.getNumberOfElements() );  
                
            }
        }        
        
        stopStats();
        
    }

    private void stopStats() {
        
        stats.end();
        
    }

    private void startStats() {

        resetStats();
       
        stats.start();
        
    }

    @SuppressWarnings( "unchecked" )
    public void IndexMongoDocs( List<Entity> list ) {

        if ( null == list || list.isEmpty() )
            return;
        
        solrRepo.saveAll( entityConverter.convert( list ) );        
        
    }

    public void clearIndex() {

        solrRepo.deleteAll();
        resetStats();
        
    }

    public void rebuildIndex() {
        
        clearIndex();
        index();
        
    }   



    private void resetStats() {

        stats.reset();        
        
    }
    
    public IndexStats getIndexStats() {
        
        return stats;
        
    }


}
