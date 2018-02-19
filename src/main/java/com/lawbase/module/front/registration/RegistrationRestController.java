package com.lawbase.module.front.registration;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.PasswordException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserException;
import com.adhocmaster.mongo.user.UserService;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;
import util.restApi.RestSuccess;

@RestController
@RequestMapping( "front/rest/registration" )
public class RegistrationRestController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping( "/add" )
    public Serializable add(HttpSession httpSession,
            @RequestParam Map<String, String> params) throws RestBadDataException, RestInternalServerException, PasswordException, PersistenceException  {
			System.out.println("controller is printing in system console");     
			
			userService.registrationFromFormData( params );
			
			return new RestSuccess( RestSuccess.Codes.SAVE_DB );
			
			
    }

}
