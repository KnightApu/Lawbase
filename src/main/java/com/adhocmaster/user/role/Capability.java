package com.adhocmaster.user.role;


public enum Capability {
    
	INDIVIDUAL,
    ROLE_INDIVIDUAL,
    ADMIN,
    ROLE_ADMIN,
    ENTERPRISE,
    ROLE_ENTERPRISE,

    READ_CASE,
    ADD_CASE,
    EDIT_CASE,
    MANAGE_CASE,
    
    READ_ACT,
    ADD_ACT,
    EDIT_ACT,
    MANAGE_ACT,
    

    READ_COURT,
    ADD_COURT,
    EDIT_COURT,
    MANAGE_COURT;
    
    public static Capability findByName( String name ) throws CapabilityNotFoundException {
        

        if ( null == name ) {
            
            throw new CapabilityNotFoundException( "Null input" );
        }
        
        for ( Capability capability : Capability.values() ) {
            
            if ( capability.name().equalsIgnoreCase( name ) ) {
                
                return capability;
                
            }
            
        }
        
        throw new CapabilityNotFoundException( name + " capability not found" );
        
    }

}
