package com.lawbase.role;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.user.role.CapabilityAuthority;
import com.adhocmaster.user.role.Role;
import com.adhocmaster.user.role.RoleCapabilitiesNotFoundException;
import com.adhocmaster.user.role.RoleNotFoundException;
import com.adhocmaster.user.role.StaticCapabilityAuthorityProvider;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=com.adhocmaster.user.role.StaticCapabilityAuthorityProvider.class)
@TestPropertySource( locations = "classpath:application-farin.properties" )
public class StaticCapabilityAuthorityProviderTest {
    
    @Autowired
    StaticCapabilityAuthorityProvider staticCapabilityAuthorityProvider;

    @Test
    public void test() throws RoleNotFoundException, RoleCapabilitiesNotFoundException {

        Set<CapabilityAuthority> capabilityAuthorities = staticCapabilityAuthorityProvider.getAuthorities( "SUPER_ADMIN" );
        Set<CapabilityAuthority> roleCapabilityAuthorities = staticCapabilityAuthorityProvider.getAuthorities( Role.SUPER_ADMIN );
        
        assertTrue( capabilityAuthorities.equals( roleCapabilityAuthorities ) );
        
        System.out.println( capabilityAuthorities );

    }

}
