package com.lawbase.module.admin.act;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adhocmaster.controller.MvcUserController;
import com.adhocmaster.mongo.user.UserHelper;
import com.book.simpleBook.SimpleBook;
import com.lawbase.act.Act;
import com.lawbase.act.ActRepository;
import com.lawbase.module.admin.caseBook.AdminCaseController;

@Controller
@RequestMapping( "/admin/act" )
public class AdminActController extends MvcUserController {
    
    private static final Logger logger = LoggerFactory.getLogger( AdminActController.class );
    
    
    private static final String viewRoot = "admin/act-";
    private static final String pathRoot = "/admin/act";
    @Autowired
    private ActRepository actRepository;
    
    @Autowired
    AdminCaseController adminCaseController;

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
    
    /**
     * 
     */
    @PostConstruct
    protected void init() {
        
        controllerPaths.put( "editCase", adminCaseController.getControllerPath( "edit" ) ); 
        // because contructor have no access to autowired elements which are added after construction
        
        
    }

    
    @GetMapping
    public String index(
            
            Model model, 
            HttpSession httpSession
            
            ) {
        
        return manage(model, httpSession);
        
    }
    
    @RequestMapping( "/manage" )
    public String manage( 
            
            Model model, 
            HttpSession httpSession 
            
            ) {
    	
    	List <Act> acts = actRepository.findAll();
        
        logger.debug( acts.toString() );
        
        model.addAttribute( "books", acts );
        
        model.addAttribute( "templatePart", "manage" );
        addCommonModelAttributes( model, "manage" );  
        
        return viewRoot + "index";
        
    }
    
    @RequestMapping("/add")
    public String add(
            
            Model model, 
            HttpSession httpSession 
            
            ) {

    	 addCommonModelAttributes( model, "add" );    
         return viewRoot + "index";
        
    
    }
    
    @RequestMapping("/edit")
    public String edit(
            
            Model model, 
            HttpSession httpSession,
            @RequestParam ObjectId id
            
            ) {
    	
    	SimpleBook act = actRepository.findOne( id );
        model.addAttribute( "act", act );
        addCommonModelAttributes( model, "edit" ); 
        return viewRoot + "index";
        
    
    }
    

	

}
