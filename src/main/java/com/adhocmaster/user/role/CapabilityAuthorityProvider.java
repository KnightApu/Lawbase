package com.adhocmaster.user.role;

import java.util.Set;


public interface CapabilityAuthorityProvider {

    public Set<CapabilityAuthority> getAuthorities( Role role ) throws RoleNotFoundException, RoleCapabilitiesNotFoundException;
    
    public Set<CapabilityAuthority> getAuthorities( String roleName ) throws RoleNotFoundException, RoleCapabilitiesNotFoundException;
    
    public Set<Capability> getCapabilities( Role role ) throws RoleNotFoundException, RoleCapabilitiesNotFoundException;
    
    public Set<Capability> getCapabilities( String roleName ) throws RoleNotFoundException, RoleCapabilitiesNotFoundException;
    
    
    
}
