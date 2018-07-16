package com.lawbase.module.admin.act;

import java.util.HashMap;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adhocmaster.controller.MvcUserController;
import com.adhocmaster.mongo.user.UserHelper;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;
import com.book.simpleBook.SimpleBook;
import com.lawbase.act.Act;
import com.lawbase.act.ActService;


@Controller
@RequestMapping( "/admin/act" )
public class AdminActController extends MvcUserController {
    
    private static final Logger logger = LoggerFactory.getLogger( AdminActController.class );
    
    
    private static final String viewRoot = "admin/act-";
    private static final String pathRoot = "/admin/act";
    
    
    @Autowired
    private ActService actService;

    @Autowired
    UserHelper userHelper;
    
    
    @Override
	protected void generateControllerPaths() {
		// TODO Auto-generated method stub
		
		controllerPaths = new HashMap<>();

        controllerPaths.put( "index", pathRoot );
        controllerPaths.put( "add", pathRoot + "/add" );
        controllerPaths.put( "edit", pathRoot + "/edit?id=" );
        controllerPaths.put( "delete", pathRoot + "/delete?id=" );
        controllerPaths.put( "manage", pathRoot + "/manage" );   
		
	}
    

    @GetMapping
    public String index(
            
            Model model, 
            HttpSession httpSession
            
            ) throws UserNotFoundInSessionException {
    	addUserInfoAttribute(model, httpSession);
        return manage(model, httpSession);
        
    }

    
    @RequestMapping( "/manage" )
    public String manage( 
            
            Model model, 
            HttpSession httpSession 
            
            ) throws UserNotFoundInSessionException {
    	
    	Page<Act> acts = actService.findAll( new PageRequest( 0, 5 ) );

		logger.debug( acts.toString() );

		model.addAttribute("books", acts);
		
        addUserInfoAttribute(model, httpSession);
		addCommonModelAttributes(model, "manage");

		return viewRoot + "index";

/*    	
    	List <Act> acts = actRepository.findAll();
        
        logger.debug( acts.toString() );
        
        model.addAttribute( "books", acts );
        addUserInfoAttribute(model, httpSession);
        model.addAttribute( "templatePart", "manage" );
        addCommonModelAttributes( model, "manage" );  
        
        return viewRoot + "index";
*/        
    }
    
    @RequestMapping("/add")
    public String add(
            
            Model model, 
            HttpSession httpSession 
            
            ) throws UserNotFoundInSessionException {

    	 addCommonModelAttributes( model, "add" );
    	 addUserInfoAttribute(model, httpSession);
         return viewRoot + "index";
        
    
    }
    
    @RequestMapping("/edit")
    public String edit(
            
            Model model, 
            HttpSession httpSession,
            @RequestParam ObjectId id
            
            ) throws UserNotFoundInSessionException {
    	
    	SimpleBook act = actService.findOne( id );
        model.addAttribute( "act", act );
        addCommonModelAttributes( model, "edit" );
        addUserInfoAttribute(model, httpSession);
        return viewRoot + "index";
        
    
    }
    

	

}
