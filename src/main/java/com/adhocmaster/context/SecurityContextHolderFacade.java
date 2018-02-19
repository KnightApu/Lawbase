package com.adhocmaster.context;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHolderFacade implements SecurityContextFacade {

    public SecurityContext getContext() {

        return SecurityContextHolder.getContext();
        
    }

    public void setContext( SecurityContext securityContext ) {

        SecurityContextHolder.setContext( securityContext );
        
    }

    @Override
    public void refreshContext() {

        
    }

    @Override
    public void isContextAvailable() {

        // TODO Auto-generated method stub
        
    }

}
