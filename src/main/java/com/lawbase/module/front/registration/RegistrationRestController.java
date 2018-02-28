package com.lawbase.module.front.registration;


import java.util.Map;
import javax.servlet.http.HttpSession;
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
	
	@Autowired
	UserService userService;
	
	@PostMapping( "/add" )
    public User add(HttpSession httpSession,
            @RequestParam Map<String, String> params) throws PasswordException, PersistenceException  {
		
			System.out.println("controller is printing in system console");     
			
			User user = userService.registrationFromFormData( params );
			
			System.out.println(user.getName());
			
			return user;
			
			
    }

}
