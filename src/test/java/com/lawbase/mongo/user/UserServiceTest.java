package com.lawbase.mongo.user;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.PasswordException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserService;
import com.adhocmaster.user.role.RoleNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-apu.properties" )
public class UserServiceTest {

	@Autowired
	UserService userService;
	
	@Test
	public void test() throws RoleNotFoundException, PasswordException, PersistenceException {
		
		String userName = "hi1234" + System.currentTimeMillis();
		String role = "ADMIN";
		String password = "user";
		
		User user = userService.createUser( "name", userName, userName + "@test.com" , role, password );
		
		assertTrue( user != null);
		
		
	}
	
	@Test
	public void test2() throws PasswordException, PersistenceException {
		
		User user = userService.createUserfromRegistrationForm("Tamim Hassan"+ System.currentTimeMillis(),  "sasdsadakib123", "sakibur123@reve.com" ,  "5648",
				"23145" ,  "Teacher",  "BKSP");
		
		assertTrue( user != null);
	}

}
