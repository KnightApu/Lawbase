package com.lawbase.module.front.user;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserService;
import com.lawbase.controller.FrontCommonController;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;

@Controller
@RequestMapping("/front/user")
public class UserProfileController extends FrontCommonController {
	
	private static final String viewRoot = "front/user-";
    private static final String pathRoot = "/front/user";
    
    @Override
    protected void generateControllerPaths() {
    	
     	//write a method in frontCommon Controller to take pathroot & viewroot as input & generate the controller path
        
    	controllerPaths = new HashMap<>();

        controllerPaths.put( "index", pathRoot );
        controllerPaths.put( "add", pathRoot + "/add" );
        controllerPaths.put( "view", pathRoot + "/view?id=" );
        controllerPaths.put( "delete", pathRoot + "/delete?id=" );
        controllerPaths.put( "manage", pathRoot + "/manage" ); 
        controllerPaths.put( "edit", pathRoot + "/edit?id=" );
        
        
    }
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/edit")
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
