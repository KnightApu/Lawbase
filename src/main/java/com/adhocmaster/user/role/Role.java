package com.adhocmaster.user.role;

public enum Role {
    
    INDIVIDUAL,
    ADMIN,
    ENTERPRISE;
    
    
    public static Role findByName( String name ) throws RoleNotFoundException {
        
        if ( null == name ) {
            
            throw new RoleNotFoundException( "Null input" );
        }
        
        for ( Role role : Role.values() ) {
            
            if ( role.name().equals( name ) ) {
                
                return role;
                
            }
            
        }
        
        throw new RoleNotFoundException( name + " role not found" );
        
    }
    

}
