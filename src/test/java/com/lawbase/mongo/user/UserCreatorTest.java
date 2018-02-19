package com.lawbase.mongo.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.sequence.SequenceDao;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserCreator;
import com.adhocmaster.mongo.user.MongoUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-test.properties" )
public class UserCreatorTest {
    
    @Autowired
    SequenceDao sequenceDao;
    
    @Autowired
    MongoUserRepository userRepository;
    

    @Test
    public void test() {

        String name = " apu";
        String userName = "test-apu" + System.currentTimeMillis() ;
        UserCreator userCreator = new UserCreator( sequenceDao, userRepository, name, userName,  userName + "@test.com" );
        
        try {
            
            User user = userCreator.createAndPersist();
            assertNotNull( user );
            assertNotNull( user.getId() );
            assertThat( user.getNumericId() > 0 );
            
            User userFromDb = userRepository.findOne( user.getId() );
            
            assertNotNull( userFromDb );
            assertNotNull( userFromDb.getId() );
            assertThat( userFromDb.getNumericId() > 0 );
            
            userRepository.delete( user );
            userFromDb = userRepository.findOne( user.getId() );
            assertNull( userFromDb );         
            
            
        } catch ( PersistenceException e ) {
            
            e.printStackTrace();
            fail ( e.getMessage() );
            
        }
        
    }
    @Test
    public void testPersisted() {

        String name = "muktadir md";
        String userName = "test-muktadir" + System.currentTimeMillis() ;
        UserCreator userCreator = new UserCreator( sequenceDao, userRepository, name, userName,  userName + "@test.com" );
        
        userCreator.setEmail( userName + "@test.com" );
        
        try {
            
            User user = userCreator.createAndPersist();
            assertNotNull( user );
            assertNotNull( user.getId() );
            assertThat( user.getNumericId() > 0 );
            
            User userFromDb = userRepository.findOne( user.getId() );
            
            assertNotNull( userFromDb );
            assertNotNull( userFromDb.getId() );
            assertThat( userFromDb.getNumericId() > 0 );
                 
            
            
        } catch ( PersistenceException e ) {
            
            e.printStackTrace();
            fail ( e.getMessage() );
            
        }
        
    }

}
