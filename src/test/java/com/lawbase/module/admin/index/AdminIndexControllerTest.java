package com.lawbase.module.admin.index;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.context.SecurityContext;

import com.adhocmaster.context.SecurityContextFacade;


public class AdminIndexControllerTest {


    private AdminIndexController controller;
    private SecurityContextFacade mockSecurityContextFacade;
    private SecurityContext mockSecurityContext;

    @Before
    public void setUp() throws Exception {
        
      mockSecurityContextFacade = mock(SecurityContextFacade.class);
      mockSecurityContext = mock(SecurityContext.class);
      stub(mockSecurityContextFacade.getContext()).toReturn(mockSecurityContext);
      controller = new AdminIndexController(mockSecurityContextFacade);
      
    }

    @Test
    public void testDoSomething() {
      controller.doSomething();
      verify(mockSecurityContextFacade).getContext();
    }


}
