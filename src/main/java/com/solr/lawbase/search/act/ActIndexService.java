package com.solr.lawbase.search.act;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lawbase.act.Act;
import com.lawbase.act.ActRepository;
import com.solr.lawbase.search.framework.BookIndexService;
import com.solr.mongo.MongoCollectionIndexer;

@Service
public class ActIndexService extends BookIndexService<Act, SolrAct> {

    private static final Logger logger = LoggerFactory.getLogger( ActIndexService.class );

    MongoCollectionIndexer<Act, SolrAct> indexer;
    

    @Autowired
    ActSolrRepository actSolrRepository;

    @Autowired
    ActRepository actMongoRepository;
    
    @Autowired
    ActEntityConverter entityConverter;
    
    @Override
    public Logger getLogger() {

        return logger;
    }

    @Override
    public ActSolrRepository getSolrRepository() {

        return actSolrRepository;
    }

    @Override
    public ActRepository getDataRepository() {

        return actMongoRepository;
    }

    @Override
    public ActEntityConverter getEntityConverter() {

        return entityConverter;
    }
    
    /**
     * Uses actIndexExecutor to run.
     * @return
     * @throws Exception
     */
    @Async("actIndexExecutor")
    public CompletableFuture<String> rebuildIndexAsync() throws Exception {
        
        return CompletableFuture.completedFuture( rebuildIndex() );
        
    }
    

}
