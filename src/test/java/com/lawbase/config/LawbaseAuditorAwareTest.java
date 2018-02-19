package com.lawbase.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.adhocmaster.context.SecurityContextFacade;
import com.adhocmaster.mongo.user.MongoUserRepository;
import com.adhocmaster.mongo.user.User;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource( locations = "classpath:application-test.properties" )
public class LawbaseAuditorAwareTest {

    private String testUsername = "test1482922679289";
    private SecurityContextFacade mockSecurityContextFacade;
    private SecurityContext mockSecurityContext;
    

    @Autowired
    private MongoUserRepository userRepository;
    
    @Autowired
    AuditorAware<User> auditorAware;
    AuditorAware<User> mockAuditorAware;

    @Before
    public void setUp() throws Exception {
        
      mockSecurityContextFacade = mock(SecurityContextFacade.class);
      mockSecurityContext = mock(SecurityContext.class);
      stub(mockSecurityContextFacade.getContext()).toReturn(mockSecurityContext);
      
      //stub( auditorAware.getCurrentAuditor() ).toReturn( userRepository.findByUserName( testUsername ) );
      
      
      
    }
    
    @Test
    public void test() {

        System.out.println( auditorAware );
        System.out.println( auditorAware.getCurrentAuditor() );

    }

}
