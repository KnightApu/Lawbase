package com.adhocmaster.mongo.auth;

import org.springframework.security.core.AuthenticationException;

public class MongoAuthenticationException extends AuthenticationException {

    public MongoAuthenticationException( String msg ) {
        super( msg );
    }

    public MongoAuthenticationException( String msg, Throwable t ) {
        super( msg, t );
    }
    
    

}
