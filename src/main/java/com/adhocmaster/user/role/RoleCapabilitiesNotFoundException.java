package com.adhocmaster.user.role;


public class RoleCapabilitiesNotFoundException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RoleCapabilitiesNotFoundException( String message ) {
        
        super( message + "( RoleCapabilitiesNotFoundException )" );
        
    }
    
}
