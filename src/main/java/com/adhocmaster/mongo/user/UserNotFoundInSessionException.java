package com.adhocmaster.mongo.user;


public class UserNotFoundInSessionException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserNotFoundInSessionException( String message ) {
        super( message );
    }
    
}
