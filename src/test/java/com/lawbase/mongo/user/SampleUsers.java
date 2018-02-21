package com.lawbase.mongo.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.sequence.SequenceDao;
import com.adhocmaster.mongo.user.MongoUserRepository;
import com.adhocmaster.mongo.user.UserCreator;
import com.adhocmaster.user.role.Role;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-muktadir.properties" )
public class SampleUsers {

    @Autowired
    SequenceDao sequenceDao;
    
    @Autowired
    MongoUserRepository userRepository;
    
    @Test
    public void createSampleUsers() throws PersistenceException {

        String name;
        String userName;
        String email;
        UserCreator userCreator;
        
        for ( int i = 0; i < 10; ++i ) {
            
            name = "test test";
            userName = "test" + System.currentTimeMillis();
            email = userName + "@test.com";
            
            userCreator = new UserCreator( sequenceDao, userRepository, name, userName, email );
            //userCreator.setRole(Role.USER);
            
            userCreator.createAndPersist();
            
        }

    }

}
