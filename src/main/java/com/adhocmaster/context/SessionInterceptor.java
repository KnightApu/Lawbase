package com.adhocmaster.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserHelper;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {
    

    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Autowired
    public SecurityContextFacade securityContextFacade;
    @Autowired
    private UserHelper userHelper;
    
    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
            throws Exception {

//        logger.debug( "into session interceptor" );
//        logger.debug( userHelper.toString() );
//        logger.debug( securityContextFacade.toString() );
        
        HttpSession httpSession = request.getSession();
        
        SecurityContext securityContext = securityContextFacade.getContext();

//        logger.debug( "Facade " + securityContext.toString() );
//        logger.debug( "Facade " + SecurityContextHolder.getContext().toString() );
        
        Authentication auth = securityContext.getAuthentication();
        
        if ( null != auth ) {

            Object principal = securityContext.getAuthentication().getPrincipal();
            
            if ( null != principal ) {

                User user = userHelper.getSetFromSession( httpSession, principal );
                
                /**
                if ( null != user )
                    logger.debug( "User session created:" + user.toString() );
                else
                    logger.debug( "User session creation failed" );
                **/
            }
            
        }
        return super.preHandle( request, response, handler );
        
    }
    
    
    
    
    
}
