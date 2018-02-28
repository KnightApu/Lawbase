package com.adhocmaster.user.role;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StaticCapabilityProvider implements CapabilityProvider {

    protected static Capability[] userCapabilities = new Capability[] {

            Capability.INDIVIDUAL,
            Capability.ROLE_INDIVIDUAL,
            Capability.READ_CASE,
            Capability.READ_ACT,
            Capability.READ_COURT
            
        };
    
    protected static Capability[] adminCapabilities = new Capability[] {

            Capability.ADMIN,
            Capability.MANAGE_CASE,
            Capability.MANAGE_ACT,
            Capability.MANAGE_COURT
                
        };
    
    protected static Capability[] enterpriseCapabilities = new Capability[] {

            Capability.ENTERPRISE,
            Capability.ROLE_ENTERPRISE,
            Capability.READ_CASE
            
        };
    
    Map<Role, Set<Capability>> capabilities;


    public StaticCapabilityProvider() {

        capabilities = new HashMap<Role, Set<Capability>>();
        
        loadRoleCapabilities();
        
        
    }
    
    private void loadRoleCapabilities() {

        // INDIVIDUAL
        Set<Capability> userCapabilitiesSet = new HashSet<Capability>();
        
        userCapabilitiesSet.addAll( Arrays.asList( userCapabilities ) );
               
        capabilities.put( Role.INDIVIDUAL, userCapabilitiesSet );
        
        // ADMIN
        Set<Capability> adminCapabilitiesSet = new HashSet<Capability>();
        
        adminCapabilitiesSet.addAll( Arrays.asList( adminCapabilities ) );
               
        capabilities.put( Role.ADMIN, adminCapabilitiesSet );
        
        // ENTERPRISE
        Set<Capability> enterpriseCapabilitiesSet = new HashSet<Capability>();
        
        enterpriseCapabilitiesSet.addAll( Arrays.asList( adminCapabilities ) );
               
        capabilities.put( Role.ENTERPRISE, enterpriseCapabilitiesSet );
        
    }
    

    @Override
    public Set<Capability> getCapabilities( String roleName ) throws RoleNotFoundException, RoleCapabilitiesNotFoundException {

        Role role = Role.findByName( roleName );
        
        return getCapabilities( role );
        

    }
    
    @Override
    public Set<Capability> getCapabilities( Role role ) throws RoleNotFoundException, RoleCapabilitiesNotFoundException {

        if ( null == role )
            throw new RoleNotFoundException( "Null input" );
        
        Set<Capability> roleCapabilities = this.capabilities.get( role );
        
        if ( null == roleCapabilities )
            throw new RoleCapabilitiesNotFoundException( role.name() );
        
        return roleCapabilities;

    }

}
