package com.adhocmaster.mongo.user;

import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.adhocmaster.context.SecurityContextFacade;

public class UserHelper {

    private static final Logger logger = LoggerFactory.getLogger(UserHelper.class);
    
    private MongoUserRepository userRepository;
    public SecurityContextFacade securityContextFacade;
    
    public UserHelper( MongoUserRepository userRepository, SecurityContextFacade securityContextFacade ) {

        this.userRepository = userRepository;
        this.securityContextFacade = securityContextFacade;
        
    }

    public ObjectId getIdFromPrincipal( Object Principal ) {
        
        String data = Principal.toString();
        
        if ( ObjectId.isValid( data ) )
            return new ObjectId( data );
        
        return null;
        
    }

    /**
     * gets user object from session and creates one into session if not already available
     * @param httpSession
     * @param principal
     * @return
     */
    public User getSetFromSession( HttpSession httpSession, Object principal ) {
        

        User user = ( User ) httpSession.getAttribute( "user" );
        
        if ( null == user ) {
            
            ObjectId id = getIdFromPrincipal( principal );
            
            if ( null == id )
                return null;
            
            user = userRepository.findOne( id );
            
            httpSession.setAttribute( "user", user );
            
            System.out.println( user );
            
        }
        
        return user;
                
    }
    
    /**
     * 
     * @param httpSession
     * @return gets user object from session
     * @throws UserNotFoundInSessionException
     */
    public User getFromSession( HttpSession httpSession ) throws UserNotFoundInSessionException {
        
        User user = ( User ) httpSession.getAttribute( "user" );
        
        if ( null == user ) {
            
            throw new UserNotFoundInSessionException( "User not found in session" );
            
        }
        
        return user;
                
    }
    
    /**
     * 
     * @return user from repository by id from principal in auth
     * @throws UserNotFoundInContextException
     */
    public User getFromContextAuth() throws UserNotFoundInContextException {

        SecurityContext securityContext = securityContextFacade.getContext();

        logger.debug( "Facade " + securityContext.toString() );
        logger.debug( "Facade " + SecurityContextHolder.getContext().toString() );
        
        Authentication auth = securityContext.getAuthentication();
        
        User user = null;
        
        if ( null != auth ) {

            Object principal = securityContext.getAuthentication().getPrincipal();
            
            if ( null != principal ) {

                user = userRepository.findOne( getIdFromPrincipal( principal ) );                 
                
            }
            
        }

        if ( null == user ) {
            
            throw new UserNotFoundInContextException( "User not found in context" );
            
        }
        
        return user;
                
        
    }
    
    

}
