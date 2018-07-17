package com.lawbase.module.admin.journal;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
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
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;
import com.book.simpleBook.SimpleBook;
import com.book.simpleBook.SimpleMissingBook;
import com.lawbase.journal.Journal;
import com.lawbase.journal.JournalService;
import com.lawbase.module.admin.article.AdminArticleController;

@Controller
@RequestMapping( "/admin/journal" )
public class AdminJournalController extends MvcUserController{
	
	private static final Logger logger = LoggerFactory.getLogger( AdminJournalController.class );
    
    private static final String viewRoot = "admin/journal-";
    private static final String pathRoot = "/admin/journal";
    
    @Autowired
    private JournalService journalService;
    
    
    @Autowired
    AdminArticleController adminArticleController;
    
    //AdminArticleController will be there;
    
    
    @Override
    protected void generateControllerPaths() {

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
        
        controllerPaths.put( "editArticle", adminArticleController.getControllerPath( "edit" ) ); 
        // because contructor have no access to autowired elements which are added after construction
        
        
    }


    @GetMapping( value = { "", "/", "/index" } )
    public String index(
    		
    		HttpSession httpSession,
            Model model, 
             @RequestParam Map<String, String> params
            
            ) throws UserNotFoundInSessionException {

        Page<Journal> journal = journalService.findAll( new PageRequest ( 1, 5 ) );
      
        logger.debug( journal.toString() );
        
        model.addAttribute( "books", journal );
        

        addUserInfoAttribute( model, httpSession );
        addCommonModelAttributes( model, "index" );  
        
        return viewRoot + "index";
        
        
    }
    
    @GetMapping("/add")
    public String add(

    		HttpSession httpSession,
            Model model
            
            ) throws UserNotFoundInSessionException {

    	addUserInfoAttribute( model, httpSession );
        addCommonModelAttributes( model, "add" );        
        
        return viewRoot + "index";
    	 
        
    }
    
    
    
    
    @GetMapping("/edit")
    public String edit(

            Model model, 
            @RequestParam ObjectId id
            
            ) {

        
        SimpleBook journal = journalService.findOne( id );
        
        if( null == journal )
            journal = new SimpleMissingBook();
        
        model.addAttribute( "journal", journal );
        addCommonModelAttributes( model, "edit" );        
        
        return viewRoot + "index";
        
    }
}
