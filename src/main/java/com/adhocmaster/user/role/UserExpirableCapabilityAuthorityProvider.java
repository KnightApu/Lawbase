package com.adhocmaster.user.role;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserService;

@Service
public class UserExpirableCapabilityAuthorityProvider implements CapabilityAuthorityProvider{

	@Autowired
	ExpirableCapabilityAuthorityService exipirableCapabilityAuthorityService;
	
	@Autowired
	UserService userService;
	
	Map<Role, Set<CapabilityAuthority>> roleAuthorities;
    Map<Role, Set<Capability>> roleCapabilities;
    
    public UserExpirableCapabilityAuthorityProvider() {

        roleAuthorities = new HashMap<Role, Set<CapabilityAuthority>>();
        loadAuthorities();        
        
    }

    private void loadAuthorities() {

        // **** USER ****
        Set<CapabilityAuthority> userCapabilities = new HashSet<CapabilityAuthority>();
        
        for ( Capability capability : StaticCapabilityProvider.userCapabilities ) {

            userCapabilities.add( new CapabilityAuthority( capability ) );
            
        }
        
        roleAuthorities.put(Role.USER, userCapabilities);
        
        // **** ADMIN ****
        Set<CapabilityAuthority> adminAuthorities = new HashSet<CapabilityAuthority>();
        
        for ( Capability capability : StaticCapabilityProvider.adminCapabilities ) {

            adminAuthorities.add( new CapabilityAuthority( capability ) );
            
        }
        
        roleAuthorities.put(Role.ADMIN, adminAuthorities);
        
        // **** ENTERPRISE ****
        Set<CapabilityAuthority> enterpriseAuthorities = new HashSet<CapabilityAuthority>();
        
        for ( Capability capability : StaticCapabilityProvider.enterpriseCapabilities ) {

        	enterpriseAuthorities.add( new CapabilityAuthority( capability ) );
            
        }
        
        roleAuthorities.put(Role.ENTERPRISE, enterpriseAuthorities);
        
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
	
	
	public Set<ExpirableCapabilityAuthority> getExpirableAuthorities( User user )
	{		
		return exipirableCapabilityAuthorityService.getExpirableCapabilityAuthorities( user );
	}

	
	public Set<ExpirableCapabilityAuthority> getExpirableAuthorities( String userName )
	{
		User user = userService.findByUserName( userName );
		
		return getExpirableAuthorities( user );
		
	}
	
	public Set<CapabilityAuthority> getAuthorities( User user ) throws CapabilityNotFoundException, RoleNotFoundException, RoleCapabilitiesNotFoundException
	{
		
		Set<ExpirableCapabilityAuthority> expirableAuthorities = getExpirableAuthorities( user );
		
		Set<CapabilityAuthority> capabilityAuthorities = new HashSet<CapabilityAuthority>();
		
		for( ExpirableCapabilityAuthority expirableAuthority : expirableAuthorities )
		{
			capabilityAuthorities.add( expirableAuthority.getCapabilityAuthority() );
		}
		
		Role role = user.getRole();
		
		capabilityAuthorities.addAll( getAuthorities( role ) );
		
		return capabilityAuthorities;
		
		
	}
	
	public Set<Capability> getCapabilities( User user ) throws RoleNotFoundException, RoleCapabilitiesNotFoundException
	{
		
		Set<ExpirableCapabilityAuthority> expirableAuthorities = getExpirableAuthorities( user );
		
		Set<Capability> capabilities = new HashSet<Capability>();
		
		for( ExpirableCapabilityAuthority expirableAuthority : expirableAuthorities )
		{
			capabilities.add( expirableAuthority.getCapability() );
		}
		
		Role role = user.getRole();
		
		capabilities.addAll( getCapabilities( role ) );
		
		return capabilities;
		
		
	}
	
	
}
