package com.adhocmaster.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserHelper;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;

abstract public class MvcUserController extends MvcController {

	
    @Autowired
    protected UserHelper userHelper;
    
    protected User getUser( HttpSession httpSession ) throws UserNotFoundInSessionException {
        
        return userHelper.getFromSession( httpSession );
        
    }
    

}
