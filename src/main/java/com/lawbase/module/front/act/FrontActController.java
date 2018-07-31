package com.lawbase.module.front.act;

import java.util.HashMap;
import java.util.Map;

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

import com.book.simpleBook.SimpleBook;
import com.book.simpleBook.SimpleMissingBook;
import com.lawbase.act.Act;
import com.lawbase.act.ActService;
import com.lawbase.controller.FrontCommonController;

@Controller
@RequestMapping( "/front/act" )
public class FrontActController extends FrontCommonController {

	private static final Logger logger = LoggerFactory.getLogger( FrontActController.class );

    private static final String viewRoot = "front/act-";
    private static final String pathRoot = "/front/act";

    @Autowired
    private ActService actService;
    
    @Override
    protected void generateControllerPaths() {

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

        
    	Page<Act> acts = actService.findAll( new PageRequest( 0, 5 ) );
        
        model.addAttribute( "acts", acts );
        
        addCommonModelAttributes( model, "index" );  
        
        addCommonFrontMenuAttributes(model);
        
        return viewRoot + "index";
        
    }

    @GetMapping("/view")
    public String edit(

            Model model, 
            @RequestParam ObjectId id
            
            ) {

        
        SimpleBook actBook = actService.findOne( id );
        
        if( null == actBook )
            actBook = new SimpleMissingBook();
        
        model.addAttribute( "actBook", actBook );
        addCommonModelAttributes( model, "view" );    
        
        addCommonFrontMenuAttributes(model);
        
        return viewRoot + "view";
        
    }
}
