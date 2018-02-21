package com.adhocmaster.mongo.auth;


import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;


import com.adhocmaster.mongo.user.PasswordException;
import com.adhocmaster.mongo.user.PasswordHelper;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserService;
import com.adhocmaster.user.role.CapabilityAuthority;
import com.adhocmaster.user.role.CapabilityAuthorityProvider;
import com.adhocmaster.user.role.CapabilityNotFoundException;
import com.adhocmaster.user.role.Role;
import com.adhocmaster.user.role.RoleCapabilitiesNotFoundException;
import com.adhocmaster.user.role.RoleNotFoundException;
import com.adhocmaster.user.role.UserExpirableCapabilityAuthorityProvider;

@Component
public class MongoAuthenticationProvider implements AuthenticationProvider {
    
    private static final Logger logger = LoggerFactory.getLogger( MongoAuthenticationProvider.class );
    
    
    @Autowired
    UserService userService;
    
    //i edited
//    @Autowired
//    CapabilityAuthorityProvider authorityProvider;
    
    @Autowired
    UserExpirableCapabilityAuthorityProvider userAuthorityProvider;
    
    

    /**
     * @return dont null if the provider cannot validate the authentication. Another provider will be tried by Spring ProviderManager
     */
    @Override
    public Authentication authenticate( Authentication authentication ) throws AuthenticationException {
        

        //logger.debug( "In mongoAuthentication provider: " + authentication.isAuthenticated());

        if ( authentication.isAuthenticated() )
            return authentication;
                
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        System.out.println(userName + "---------"+ password  );
        
        User user = userService.findByUserName( userName );
        
        if ( null == user ) {
            
            //logger.debug( userName + " user not found." );
        	System.out.println(userName + "----user not found-----"+ password  );
        	
            throw new MongoAuthenticationException( "User not found" );
            
        }

        try {
            
            if ( isPasswordCorrect( user, password )) {
                
                //createSession( user );
            	if( user.getRole() == Role.findByName( "ENTERPRISE" ) )
            	{
            		final WebAuthenticationDetails details = ( WebAuthenticationDetails ) authentication.getDetails();
                    
            		String remoteIpAddress = details.getRemoteAddress();
                   
                    if( remoteIpAddress.equals( user.getIpAddress() ))
                    {
                    	logger.debug( "\nremote ip:" + remoteIpAddress + " stored ip:" + user.getIpAddress() + " matched" );
                    	
                    	return getAuthToken( user );
                    	
                    }
                    
                    throw new MongoAuthenticationException( "Incorrect location" );
            		            		
            	}
                
                return getAuthToken( user );
                
            }
            
            System.out.println(userName + "---password vul------"+ password  );

            logger.debug( userName + " Incorrect password." );
            throw new MongoAuthenticationException( "Incorrect password" );
            
        } catch ( PasswordException e ) {
        	
        	System.out.println(userName + "---password exception------"+ password  );
            logger.debug( "Password exception: " + e.getMessage() );
            throw new MongoAuthenticationException( "Password exception: " + e.getMessage() );
            
        } catch (RoleNotFoundException e) {
			// TODO Auto-generated catch block
        	System.out.println(userName + "---role vul------"+ password  );
        	logger.debug( "Role exception: " + e.getMessage() );
        	 throw new MongoAuthenticationException( "Role exception: " + e.getMessage() );
		}


    }


    private boolean isPasswordCorrect( User user, String password ) throws PasswordException {
        
        String existingPasswordHash = user.getPasswordHash();
        
        return PasswordHelper.check( password, existingPasswordHash );
        
    }

//    private void createSession( User user ) {
//        
//        //logger.debug( "session class is " + sessionRepository.getClass().getCanonicalName() );
////        Session session = this.sessionRepository.createSession();
////        session.setAttribute( "user", user );
//        
//        
//    }
    
    private Authentication getAuthToken( User user ) {

        try {
            
        	//i edited
            //Set<CapabilityAuthority> authorities = authorityProvider.getAuthorities( user.getRole() );
        	Set<CapabilityAuthority> authorities = userAuthorityProvider.getAuthorities( user );
                    	
        	
            //System.out.println( authorities );
            
            Authentication auth = new UsernamePasswordAuthenticationToken( user.getId(), user.getPasswordHash(), authorities );
            
            //System.out.println( auth.isAuthenticated() );
            
            return auth;
            
        } catch ( RoleNotFoundException | RoleCapabilitiesNotFoundException | CapabilityNotFoundException e ) {

            throw new MongoAuthenticationException( e.getMessage(), e );
            
        }
        
    }
    /**
     * Checks if authenticationClazz has MongoAuthentication class contract
     */
    @Override
    public boolean supports( Class<?> authenticationClazz ) {

        return true;
        // this class is fed a UsernamePasswordAuthenticationToken!!!
        /*
        System.out.println( "In mongoAuthentication provider support" );
        
        System.out.println( authenticationClazz.getCanonicalName() );
        
        if( MongoAuthentication.class.isAssignableFrom( authenticationClazz ) )
            return true;
        
        return false;
        */

    }
    

}
