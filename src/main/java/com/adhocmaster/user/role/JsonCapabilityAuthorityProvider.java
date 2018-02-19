package com.adhocmaster.user.role;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Component;

@Component
public class JsonCapabilityAuthorityProvider implements CapabilityAuthorityProvider {
    
    public static final String resourceLocation = "capabilities.json";

    Map<Role, Set<CapabilityAuthority>> roleAuthorities;
    Map<Role, Set<Capability>> roleCapabilities;
    
    
    public JsonCapabilityAuthorityProvider() {

        roleAuthorities = new HashMap<Role, Set<CapabilityAuthority>>();
        roleCapabilities = new HashMap<Role, Set<Capability>>();
        loadAuthorities();        
        
    }

    private void loadAuthorities() {

        try {
            
            Map<Role, List<Capability>> jsonRoleCapabilities = JSONRoleParser.getRoleCapabilities( resourceLocation );
            
            for( Role role : jsonRoleCapabilities.keySet() ) {

                Set<CapabilityAuthority> authorities = new HashSet<CapabilityAuthority>();
                Set<Capability> capabilites = new HashSet<Capability>();
                
                for( Capability capability : jsonRoleCapabilities.get( role ) ) {
                    
                    capabilites.add( capability );
                    authorities.add( new CapabilityAuthority( capability ) );
                    
                }
                
                // also add role to authoriy
                authorities.add( new CapabilityAuthority( Capability.findByName( role.name() ) ) );
                authorities.add( new CapabilityAuthority( Capability.findByName( "ROLE_" + role.name() ) ) );
                
                capabilites.add( Capability.findByName( role.name() )  );
                capabilites.add( Capability.findByName( "ROLE_" + role.name() )  );

                roleAuthorities.put( role, authorities );
                roleCapabilities.put( role, capabilites );
            
            }
            
        } catch ( Exception e ) {

            e.printStackTrace();
            throw new RuntimeErrorException( new Error( "Authorities loading failed" + e.getMessage() )  );
            
        }
        
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

        if ( null == role )
            throw new RoleNotFoundException( "Null input" );
        
        Set<Capability> capabilities = this.roleCapabilities.get( role );
        
        if ( null == capabilities )
            throw new RoleCapabilitiesNotFoundException( role.name() );
        
        return capabilities;
    }
    

    @Override
    public Set<Capability> getCapabilities( String roleName )
            throws RoleNotFoundException, RoleCapabilitiesNotFoundException {

        Role role = Role.findByName( roleName );
        
        return getCapabilities( role );
    }

}
