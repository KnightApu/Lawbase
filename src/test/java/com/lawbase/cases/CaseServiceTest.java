package com.lawbase.cases;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.mongo.PersistenceException;
import com.solr.lawbase.search.act.ActSolrRepository;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-muktadir.properties" )
public class CaseServiceTest {

    @Autowired
    CaseService caseService;
    
    @Autowired
    ActSolrRepository actSolrRepository;
    
    @Autowired
    @Qualifier( "caseSavedPublisher")
    Subject<Case> caseSavedPublisher;

    @Autowired
    @Qualifier( "caseDeletedPublisher")
    Subject<Case> caseDeletedPublisher;
    
    boolean gotSaved = false;
    boolean gotDeleted = false;
    
    @Test
    public void testAdd() throws PersistenceException, InterruptedException {
        
        attachSubscribers();
        
        Map<String, String> params = new HashMap<String, String>();
        
        params.put( "title", "Test event add" );
        
        Case caseBook = caseService.createFromMap( null, params );
        
        caseService.delete( caseBook );

        Thread.sleep(1000);
        
        assertTrue( gotSaved );
        assertTrue( gotDeleted );
        

    }
    
    public void attachSubscribers() {

        gotSaved = false;
        gotDeleted = false;
        
        caseSavedPublisher.subscribe( new Consumer<Case>() {


            @Override
            public void accept( Case t ) throws Exception {

                System.out.println( "Case saved " + t );
                
                gotSaved = true;
                
            }
        } );

        caseDeletedPublisher.subscribe( new Consumer<Case>() {


            @Override
            public void accept( Case t ) throws Exception {

                System.out.println( "Case deleted " + t );
                gotDeleted = true;
                
            }
        } );
        
        
    }

}
