package com.lawbase.mongo.user;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.mongo.user.MongoUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-muktadir.properties" )
public class FixUserRoles {

    @Autowired
    MongoUserRepository userRepository;
    
}
