package com.lawbase.act;

import static org.junit.Assert.assertTrue;

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

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-muktadir.properties" )
public class ActServiceTest {

    @Autowired
    ActService actService;
    
    @Autowired
    @Qualifier( "actSavedPublisher" )
    Subject<Act> actSavedPublisher;

    @Autowired
    @Qualifier( "actDeletedPublisher" )
    Subject<Act> actDeletedPublisher;

    boolean gotSaved = false;
    boolean gotDeleted = false;

    public void attachSubscribers() {

        gotSaved = false;
        gotDeleted = false;
        
        actSavedPublisher.subscribe( new Consumer<Act>() {


            @Override
            public void accept( Act t ) throws Exception {

                System.out.println( "Act saved " + t );
                
                gotSaved = true;
                
            }
        } );

        actDeletedPublisher.subscribe( new Consumer<Act>() {


            @Override
            public void accept( Act t ) throws Exception {

                System.out.println( "Act deleted " + t );
                gotDeleted = true;
                
            }
        } );
        
        
    }
    @Test
    public void test() throws PersistenceException, InterruptedException {

        attachSubscribers();
        
        Map<String, String> params = new HashMap<String, String>();
        
        params.put( "title", "Test event add" );
        
        Act caseBook = actService.createFromMap( null, params );
        
        actService.delete( caseBook );

        Thread.sleep(1000);
        
        assertTrue( gotSaved );
        assertTrue( gotDeleted );
        
    }
    
    @Test
    public void testDate() throws PersistenceException, InterruptedException {

       ActRepository actRepository = null;
       Act act = (Act) actRepository.findByTitle("agseg");
       
     
    	
        
        assertTrue( gotSaved );
        assertTrue( gotDeleted );
        
    }

}
