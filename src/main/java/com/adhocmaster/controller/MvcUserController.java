package com.adhocmaster.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserHelper;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;

abstract public class MvcUserController extends MvcController {

	
    @Autowired
    protected UserHelper userHelper;
    
    protected User getUser( HttpSession httpSession ) throws UserNotFoundInSessionException {
        
        return userHelper.getFromSession( httpSession );
        
    }
    
    protected void addUserInfoAttribute(Model model, HttpSession httpSession) throws UserNotFoundInSessionException {
    	
    	model.addAttribute("userName", getUser(httpSession).getName());
    	
    }
    

}
