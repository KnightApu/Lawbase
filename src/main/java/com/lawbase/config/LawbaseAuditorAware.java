package com.lawbase.config;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.adhocmaster.context.SecurityContextFacade;
import com.adhocmaster.mongo.user.MongoUserRepository;
import com.adhocmaster.mongo.user.User;


public class LawbaseAuditorAware implements AuditorAware<User> {

    private static final Logger logger = LoggerFactory.getLogger( LawbaseAuditorAware.class );
    private final SecurityContextFacade securityContextFacade;
    private final MongoUserRepository userRepository;

    public LawbaseAuditorAware( SecurityContextFacade securityContextFacade, MongoUserRepository userRepository ) {
        
        this.securityContextFacade = securityContextFacade;
        this.userRepository = userRepository;
        
    }
    
    @Override
    public User getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if ( null == authentication || ! authentication.isAuthenticated() ) {
            
            return null;
            
        }
        
        String userId = securityContextFacade.getContext().getAuthentication().getPrincipal().toString();
        
        logger.debug( "Got userId:" + userId );
        
        return userRepository.findOne( new ObjectId( userId ) );
        
        // TODO read from session
//        return null;
        //return ( User ) authentication.getPrincipal();

    }

}
