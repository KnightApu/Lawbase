package com.lawbase.module.front.user;

import java.security.Principal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserService;
import com.lawbase.controller.FrontCommonController;

@Controller
@RequestMapping("/front/profile")
public class UserProfileController extends FrontCommonController {
	
	private static final String viewRoot = "front/profile-";
    private static final String pathRoot = "/front/user/edit";
    
    @Override
    protected void generateControllerPaths() {
    	
     	//write a method in frontCommon Controller to take pathroot & viewroot as input & generate the controller path
        
    	controllerPaths = new HashMap<>();

        controllerPaths.put( "index", pathRoot );
        controllerPaths.put( "add", pathRoot + "/add" );
        controllerPaths.put( "view", pathRoot + "/view?id=" );
        controllerPaths.put( "delete", pathRoot + "/delete?id=" );
        controllerPaths.put( "manage", pathRoot + "/manage" );   
        
        
    }
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/")
    String index(Model model, Principal principal) {        
		
		String id = principal.getName();
		User user = userService.findById( id );
		
		model.addAttribute( "user", user );
		model.addAttribute("id", id);
		addCommonModelAttributes( model, "index" ); 
		addCommonFrontMenuAttributes(model);			 	
    	
        return viewRoot + "index";
        
    }

}
