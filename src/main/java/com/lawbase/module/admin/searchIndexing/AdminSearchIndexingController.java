package com.lawbase.module.admin.searchIndexing;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adhocmaster.controller.MvcUserController;

@Controller
@RequestMapping( "/admin/searchIndexing" )
public class AdminSearchIndexingController extends MvcUserController {

	private static final Logger logger = LoggerFactory.getLogger( AdminSearchIndexingController.class );

    private static final String viewRoot = "admin/searchIndexing-";
    private static final String pathRoot = "/admin/searchIndexing";
	
	@Override
	protected void generateControllerPaths() {

		controllerPaths = new HashMap<>();

        controllerPaths.put( "index", pathRoot );
        controllerPaths.put( "add", pathRoot + "/add" );
        controllerPaths.put( "edit", pathRoot + "/edit?id=" );
        controllerPaths.put( "delete", pathRoot + "/delete?id=" );
        controllerPaths.put( "manage", pathRoot + "/manage" ); 
		
	}
	
	@GetMapping( value = { "", "/", "/index" } )
    public String index(

            Model model, 
            @RequestParam Map<String, String> params
            
            ) {

        addCommonModelAttributes( model, "index" );  
        
        return viewRoot + "index";
        
    }

}
