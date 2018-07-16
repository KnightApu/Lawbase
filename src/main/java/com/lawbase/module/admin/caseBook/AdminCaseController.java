package com.lawbase.module.admin.caseBook;

import java.util.HashMap;
import java.util.Map;

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
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;
import com.book.exceptions.NotSuchAuthorException;
import com.book.simpleBook.SimpleBook;
import com.book.simpleBook.SimpleMissingBook;
import com.lawbase.cases.Case;
import com.lawbase.cases.CaseService;

@Controller
@RequestMapping( "/admin/case" )
public class AdminCaseController extends MvcUserController {

    private static final Logger logger = LoggerFactory.getLogger( AdminCaseController.class );

    private static final String viewRoot = "admin/case-";
    private static final String pathRoot = "/admin/case";

    @Autowired
    private CaseService caseService;
    
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
    		
    		HttpSession httpSession,
            Model model, 
            @RequestParam Map<String, String> params
            
            ) throws UserNotFoundInSessionException {

        
    	Page<Case> casebooks = caseService.findAll( new PageRequest( 0, 5 ) );

		logger.debug( casebooks.toString() );

		model.addAttribute("casebooks", casebooks);
		
        addUserInfoAttribute(model, httpSession);
		addCommonModelAttributes(model, "index");

		return viewRoot + "index";
        
		
    }

    @GetMapping("/edit")
    public String edit(
    		
    		HttpSession httpSession,
            Model model, 
            @RequestParam ObjectId id
            
            ) throws UserNotFoundInSessionException, NotSuchAuthorException {

        User user = getUser( httpSession );
        
        SimpleBook caseBook = caseService.findOne( id );

        if( null == caseBook )
            caseBook = new SimpleMissingBook();
        
        model.addAttribute( "caseBook", caseBook );
        
        addUserInfoAttribute( model, httpSession);
        addCommonModelAttributes( model, "edit" );    
        
        return viewRoot + "index";
        
    }

}
