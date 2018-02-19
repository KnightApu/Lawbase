package com.adhocmaster.context;

import org.springframework.security.core.context.SecurityContext;

public interface SecurityContextFacade {
    
    void isContextAvailable();
    
    SecurityContext getContext();

    void setContext(SecurityContext securityContext);
    
    void refreshContext();

}
