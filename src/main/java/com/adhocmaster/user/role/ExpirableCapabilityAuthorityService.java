package com.adhocmaster.user.role;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserService;
import com.utility.form.FormValidationException;

import javassist.NotFoundException;

@Service
public class ExpirableCapabilityAuthorityService {

	@Autowired
	ExpirableCapabilityAuthorityRepository expirableCapabilityAuthorityRepository;
	
	@Autowired
	UserService userService;
	
	
	public void save( ExpirableCapabilityAuthority expirableCapabilityAuthority )
	{
		expirableCapabilityAuthorityRepository.save( expirableCapabilityAuthority );
	}
	
	public Set<ExpirableCapabilityAuthority> getExpirableCapabilityAuthorities( User user )
	{
		return expirableCapabilityAuthorityRepository.findByUser( user );
	}
	
	public void updateFromFormData( ObjectId userId, Map<String, String> params ) throws PersistenceException
	{
		try { 
            
            updateCapabilities( userId, params );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
	}

	private void updateCapabilities( ObjectId userId, Map<String, String> params ) throws FormValidationException, NotFoundException, PersistenceException, CapabilityNotFoundException  {
		
		User user = userService.findOne( userId );
	       
        if ( StringUtils.isNotBlank( params.get( "capabilities" ) ) ) {
            
        	String capabilitiesFromParam = params.get( "capabilities" );
        	
        	String[] capabilityArray = capabilitiesFromParam.split( "," );
        	
        	Capability capability;
        	ExpirableCapabilityAuthority expirableCapabilityAuthority;
        	
        	for( String cap : capabilityArray  )
        	{
        		capability = Capability.findByName( cap );
        		
        		expirableCapabilityAuthority  = new ExpirableCapabilityAuthority( user, capability );
        		
        		save( expirableCapabilityAuthority );
        	
        	}            
            
        }
		
	}
	
}
