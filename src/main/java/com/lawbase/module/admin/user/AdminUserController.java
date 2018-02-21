package com.lawbase.module.admin.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adhocmaster.controller.MvcController;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserService;
import com.adhocmaster.user.role.CapabilityAuthority;
import com.adhocmaster.user.role.CapabilityNotFoundException;
import com.adhocmaster.user.role.RoleCapabilitiesNotFoundException;
import com.adhocmaster.user.role.RoleNotFoundException;
import com.adhocmaster.user.role.UserExpirableCapabilityAuthorityProvider;
import com.lawbase.module.admin.journal.AdminJournalController;

@Controller
@RequestMapping( "/admin/user" )
public class AdminUserController extends MvcController {

	private static final Logger logger = LoggerFactory.getLogger( AdminJournalController.class );
    
    private static final String viewRoot = "admin/user-";
    private static final String pathRoot = "/admin/user";
	
    
    @Autowired
    UserService userService;
    
    @Autowired
    UserExpirableCapabilityAuthorityProvider userExpirableAuthorityProvider;
    
    
	@Override
	protected void generateControllerPaths() {
		 controllerPaths = new HashMap<>();

		 controllerPaths.put( "index", pathRoot );
	     controllerPaths.put( "add", pathRoot + "/add" );
	     controllerPaths.put( "edit", pathRoot + "/edit?id=" );
	     controllerPaths.put( "delete", pathRoot + "/delete?id=" );
	     controllerPaths.put( "manage", pathRoot + "/manage" );   
	     controllerPaths.put( "error", pathRoot + "/error" ); 
	        
		
	}
	
	 @GetMapping( value = { "", "/", "/index" } )
	 public String index(

	            Model model, 
	            @RequestParam Map<String, String> params
	            
	            ) {
		 
		 	
		 Page<User> users = userService.findAll( new PageRequest( 1, 5 ) );
		 
		 logger.debug( users.toString() );
		    
	     model.addAttribute( "users", users );
	        
	     addCommonModelAttributes( model, "index" );  
	        
	     return viewRoot + "index";
	        
	 }
	 
	 @GetMapping("/edit")
	 public String edit(

	            Model model, 
	            @RequestParam ObjectId id
	            
	            ) throws CapabilityNotFoundException, RoleNotFoundException, RoleCapabilitiesNotFoundException {

	        
		 User user = userService.findOne( id );
	       
		 
	     if( null == user )
	     {
	    	 addCommonModelAttributes( model, "error" ); 
	    	 model.addAttribute( "message", "The user is null" );
			 
	    	 return viewRoot + "index";
	    	 
	     }	
	     
	     Set<CapabilityAuthority> authorities = userExpirableAuthorityProvider.getAuthorities( user );
	    
	     model.addAttribute( "user", user );
	     model.addAttribute( "authorities", authorities );
	     
	     addCommonModelAttributes( model, "edit" );    
	        
	     return viewRoot + "index";
	        
	 }
	 
	 	
	 @GetMapping("/add")
	 public String add(

	            Model model
	            
	            ) {

		 addCommonModelAttributes( model, "add" );        
	     
		 return viewRoot + "index";
	    	 
	        
	 }

	
}
