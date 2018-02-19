package com.solr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawbase.act.Act;
import com.lawbase.act.ActRepository;
import com.lawbase.cases.Case;
import com.lawbase.cases.CaseRepository;
import com.solr.lawbase.search.act.ActEntityConverter;
import com.solr.lawbase.search.act.ActSolrRepository;
import com.solr.lawbase.search.act.SolrAct;
import com.solr.lawbase.search.cases.CaseEntityConverter;
import com.solr.lawbase.search.cases.CaseSolrRepository;
import com.solr.lawbase.search.cases.SolrCase;
import com.solr.mongo.MongoCollectionIndexer;


@RunWith(SpringRunner.class)
@SpringBootTest( classes = com.lawbase.LawbaseApplication.class )
@TestPropertySource( locations = "classpath:application-muktadir.properties" )
public class MongoCollectionIndexerTest {

    @Autowired
    CaseSolrRepository caseSolrRepository;

    @Autowired
    CaseRepository caseMongoRepository;
    @Autowired
    CaseEntityConverter caseEntityConverter;
    
    @Autowired
    ActSolrRepository actSolrRepository;

    @Autowired
    ActRepository actMongoRepository;
    @Autowired
    ActEntityConverter actEntityConverter;

    @Test
    public void index() {

        new MongoCollectionIndexer<Case, SolrCase>( caseMongoRepository, caseSolrRepository, caseEntityConverter ).index();
        new MongoCollectionIndexer<Act, SolrAct>( actMongoRepository, actSolrRepository, actEntityConverter ).index();

    }
    @Test
    public void clearIndex() {

        new MongoCollectionIndexer<Case, SolrCase>( caseMongoRepository, caseSolrRepository, caseEntityConverter ).clearIndex();
        new MongoCollectionIndexer<Act, SolrAct>( actMongoRepository, actSolrRepository, actEntityConverter ).clearIndex();

    }
    @Test
    public void rebuildIndex() {

        new MongoCollectionIndexer<Case, SolrCase>( caseMongoRepository, caseSolrRepository, caseEntityConverter ).rebuildIndex();
        new MongoCollectionIndexer<Act, SolrAct>( actMongoRepository, actSolrRepository, actEntityConverter ).rebuildIndex();

    }

}
