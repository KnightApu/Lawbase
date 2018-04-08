package com.adhocmaster.user.role;


public enum Capability {
    
	INDIVIDUAL,
    ROLE_INDIVIDUAL,
    ADMIN,
    ROLE_ADMIN,
    ENTERPRISE,
    ROLE_ENTERPRISE,
    SUPER_ADMIN,
    ROLE_SUPER_ADMIN,
    EDITOR,
    ROLE_EDITOR,

    READ_CASE,
    ADD_CASE,
    EDIT_CASE,
    MANAGE_CASE,
    
    READ_ACT,
    ADD_ACT,
    EDIT_ACT,
    MANAGE_ACT,
    
    READ_JOURNAL,
    ADD_JOURNAL,
    EDIT_JOURNAL,
    MANAGE_JOURNAL,
    
    READ_ARTICLE,
    ADD_ARTICLE,
    EDIT_ARTICLE,
    MANAGE_ARTICLE,
    
    READ_USER,
    ADD_USER,
    EDIT_USER,
    MANAGE_USER,
    
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
