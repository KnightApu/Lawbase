package com.adhocmaster.user.role;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;

public class StaticCapabilityAuthorityProvider implements CapabilityAuthorityProvider {
    


    Map<Role, Set<CapabilityAuthority>> roleAuthorities;
    Map<Role, Set<Capability>> roleCapabilities;
    
    
    public StaticCapabilityAuthorityProvider() {

        roleAuthorities = new HashMap<Role, Set<CapabilityAuthority>>();
        loadAuthorities();        
        
    }

    private void loadAuthorities() {

        // **** INDIVIDUAL ****
        Set<CapabilityAuthority> userCapabilities = new HashSet<CapabilityAuthority>();
        
        for ( Capability capability : StaticCapabilityProvider.userCapabilities ) {

            userCapabilities.add( new CapabilityAuthority( capability ) );
            
        }
        
        roleAuthorities.put( Role.INDIVIDUAL, userCapabilities);
        
        // **** ADMIN ****
        Set<CapabilityAuthority> adminAuthorities = new HashSet<CapabilityAuthority>();
        
        for ( Capability capability : StaticCapabilityProvider.adminCapabilities ) {

            adminAuthorities.add( new CapabilityAuthority( capability ) );
            
        }
        
        roleAuthorities.put( Role.ADMIN, adminAuthorities);
        
     // **** ENTERPRISE ****
        Set<CapabilityAuthority> enterpriseAuthorities = new HashSet<CapabilityAuthority>();
        
        for ( Capability capability : StaticCapabilityProvider.enterpriseCapabilities ) {

        	enterpriseAuthorities.add( new CapabilityAuthority( capability ) );
            
        }
        
        roleAuthorities.put( Role.ENTERPRISE, enterpriseAuthorities);
        
    }

    @Override
    public Set<CapabilityAuthority> getAuthorities( Role role )
            throws RoleNotFoundException, RoleCapabilitiesNotFoundException {

        if ( null == role )
            throw new RoleNotFoundException( "Null input" );
        
        Set<CapabilityAuthority> authorities = this.roleAuthorities.get( role );
        
        if ( null == authorities )
            throw new RoleCapabilitiesNotFoundException( role.name() );
        
        return authorities;
    }

    @Override
    public Set<CapabilityAuthority> getAuthorities( String roleName )
            throws RoleNotFoundException, RoleCapabilitiesNotFoundException {

        Role role = Role.findByName( roleName );
        
        return getAuthorities( role );
        
    }
    

    @Override
    public Set<Capability> getCapabilities( Role role )
            throws RoleNotFoundException, RoleCapabilitiesNotFoundException {

        throw new RuntimeErrorException( null, "Not implemented yet" );
    }
    

    @Override
    public Set<Capability> getCapabilities( String roleName )
            throws RoleNotFoundException, RoleCapabilitiesNotFoundException {

        throw new RuntimeErrorException( null, "Not implemented yet" );
    }


}
