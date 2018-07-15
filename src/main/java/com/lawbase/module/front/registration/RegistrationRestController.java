package com.lawbase.module.front.registration;


import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.PasswordException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserService;


@RestController
@RequestMapping( "public/rest/registration" )
public class RegistrationRestController {
	
	private static final Logger logger = LoggerFactory.getLogger( RegistrationRestController.class );
	
	@Autowired
	UserService userService;
	
	@PostMapping( "/add" )
    public User add(HttpSession httpSession,
            @RequestParam Map<String, String> params) throws PasswordException, PersistenceException  {
			
			User user =  new User();
			try {
				
				user = userService.registrationFromFormData( params );
				
			} catch (PasswordException e) {
				
				logger.debug(e.toString());
				
			} catch (PersistenceException e) {
				
				logger.debug(e.toString());
			}
			
			
			return user;
			
			
    }

}
