package com.lawbase.role;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.sequence.SequenceDao;
import com.adhocmaster.mongo.user.MongoUserRepository;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserCreator;
import com.adhocmaster.user.role.Capability;
import com.adhocmaster.user.role.CapabilityAuthority;
import com.adhocmaster.user.role.CapabilityNotFoundException;
import com.adhocmaster.user.role.ExpirableCapabilityAuthority;
import com.adhocmaster.user.role.ExpirableCapabilityAuthorityService;
import com.adhocmaster.user.role.Role;
import com.adhocmaster.user.role.RoleCapabilitiesNotFoundException;
import com.adhocmaster.user.role.RoleNotFoundException;
import com.adhocmaster.user.role.UserExpirableCapabilityAuthorityProvider;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-farin.properties" )
public class UserExpirableCapabilityAuthorityProviderTest {

	@Autowired
	UserExpirableCapabilityAuthorityProvider userExpirableCapabilityAuthorityProvider;
	
	@Autowired
	ExpirableCapabilityAuthorityService expirableCapabilityAuthorityService; 
	
	
	@Autowired
    SequenceDao sequenceDao;
    
    @Autowired
    MongoUserRepository userRepository;
	
	@Test
	public void test() throws PersistenceException, CapabilityNotFoundException, RoleNotFoundException, RoleCapabilitiesNotFoundException {
		//fail("Not yet implemented");
		
		String name = "testName" + System.currentTimeMillis();
		String userName = "userName" + System.currentTimeMillis();
		
		
		UserCreator userCreator = new UserCreator( sequenceDao, userRepository, name, userName,  userName + "@test.com" );
		User user = userCreator.createAndPersist();
		
		user.setRole( Role.USER );
		
		
		Set<Capability> userCapabilities = new HashSet<Capability>();
		
		userCapabilities.add( Capability.ADD_CASE );
		userCapabilities.add( Capability.ADD_COURT );
		
		
		for( Capability capability: userCapabilities )
		{
			ExpirableCapabilityAuthority expirableCapabilityAuthority = new ExpirableCapabilityAuthority( user, capability );
			
			expirableCapabilityAuthorityService.save( expirableCapabilityAuthority );
			
		
		}
		
		Set<CapabilityAuthority> authorities = userExpirableCapabilityAuthorityProvider.getAuthorities( user );
		
		for( CapabilityAuthority authority: authorities )
		{
			System.out.println( "capability is:" + authority.getCapability().toString() );
		}
		
		assertTrue( authorities != null );
		
	}

}
