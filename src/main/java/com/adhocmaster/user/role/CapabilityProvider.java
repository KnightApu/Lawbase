package com.adhocmaster.user.role;

import java.util.Set;

public interface CapabilityProvider {
    

    public Set<Capability> getCapabilities( Role role ) throws RoleNotFoundException, RoleCapabilitiesNotFoundException;
    
    public Set<Capability> getCapabilities( String role ) throws RoleNotFoundException, RoleCapabilitiesNotFoundException;
    

}
