package com.lawbase.module.front.caseBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.book.simpleBook.SimpleBook;
import com.book.simpleBook.SimpleMissingBook;
import com.lawbase.cases.Case;
import com.lawbase.cases.CaseRepository;
import com.lawbase.controller.FrontCommonController;
import com.lawbase.module.admin.caseBook.AdminCaseController;

@Controller
@RequestMapping( "/front/case" )
public class FrontCaseController extends FrontCommonController {

	private static final Logger logger = LoggerFactory.getLogger( AdminCaseController.class );

    private static final String viewRoot = "front/case-";
    private static final String pathRoot = "/front/case";

    @Autowired
    private CaseRepository caseRepository;
    
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
    
    @GetMapping( value = { "", "/", "/index" } )
    public String index(

            Model model, 
            @RequestParam Map<String, String> params
            
            ) {

        // TODO make is paginated
        
        List<Case> books = caseRepository.findAll();
        
        model.addAttribute( "books", books );
        
        addCommonModelAttributes( model, "index" ); 
        
        addCommonFrontMenuAttributes(model);
        
        return viewRoot + "index";
        
    }

    @GetMapping("/view")
    public String edit(

            Model model, 
            @RequestParam ObjectId id
            
            ) {

        
        SimpleBook caseBook = caseRepository.findOne( id );
        
        if( null == caseBook )
            caseBook = new SimpleMissingBook();
        
        model.addAttribute( "caseBook", caseBook );
        addCommonModelAttributes( model, "view" );    
        
        addCommonFrontMenuAttributes(model);
        
        return viewRoot + "view";
        
    }
}
