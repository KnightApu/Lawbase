package com.adhocmaster.user.role;

import java.io.Serializable;

import org.springframework.security.core.GrantedAuthority;

public class CapabilityAuthority implements GrantedAuthority, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Capability capability;
    
    

    public CapabilityAuthority( Capability capability ) {
        super();
        this.capability = capability;
    }

    

    
    public Capability getCapability() {
    
        return capability;
    }



    @Override
    public String getAuthority() {

        return capability.name();

    }




    @Override
    public String toString() {

        return "CapabilityAuthority [capability=" + capability + "]";
    }
    
    

}
