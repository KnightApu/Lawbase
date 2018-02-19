package com.solr.lawbase.search.cases;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lawbase.cases.Case;
import com.lawbase.cases.CaseRepository;
import com.solr.lawbase.search.framework.BookIndexService;
import com.solr.mongo.MongoCollectionIndexer;

@Service
public class CaseIndexService extends BookIndexService<Case, SolrCase> {

    private static final Logger logger = LoggerFactory.getLogger( CaseIndexService.class );

    @Autowired
    CaseSolrRepository caseSolrRepository;

    @Autowired
    CaseRepository caseMongoRepository;
    
    @Autowired
    CaseEntityConverter entityConverter;


    
    MongoCollectionIndexer<Case, SolrCase> indexer;

    @Override
    public Logger getLogger() {

        return logger;

    }
    @Override
    public CaseSolrRepository getSolrRepository() {

        return caseSolrRepository;
    }

    @Override
    public CaseRepository getDataRepository() {

        return caseMongoRepository;
    }

    @Override
    public CaseEntityConverter getEntityConverter() {

        return entityConverter;
    }
    
    
    /**
     * Uses caseIndexExecutor to run.
     * @return
     * @throws Exception
     */
    @Async("caseIndexExecutor")
    public CompletableFuture<String> rebuildIndexAsync() throws Exception {
        
        return CompletableFuture.completedFuture( rebuildIndex() );
        
    }
    
    
}
