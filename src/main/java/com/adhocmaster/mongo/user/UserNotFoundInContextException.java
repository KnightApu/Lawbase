package com.adhocmaster.mongo.user;


public class UserNotFoundInContextException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserNotFoundInContextException( String message ) {
        super( message );
    }
    
}
