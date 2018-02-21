package com.lawbase.module.front.registration;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lawbase.controller.FrontCommonController;

@Controller
@RequestMapping("/registration")
public class RegistrationController extends FrontCommonController {
	
	private static final String viewRoot = "front/registration-";
    private static final String pathRoot = "/registration";
    
    @Override
    protected void generateControllerPaths() {
    	
     	
        
    	controllerPaths = new HashMap<>();

        controllerPaths.put( "index", pathRoot );
        controllerPaths.put( "add", pathRoot + "/add" );
        controllerPaths.put( "view", pathRoot + "/view?id=" );
        controllerPaths.put( "delete", pathRoot + "/delete?id=" );
        controllerPaths.put( "manage", pathRoot + "/manage" );   
        
        
    }
	
	@RequestMapping("")
    String index(Model model) {
		
		addCommonModelAttributes( model, "index" ); 
        addCommonFrontMenuAttributes(model);
    	
    	return viewRoot + "index";
        
    }

}
