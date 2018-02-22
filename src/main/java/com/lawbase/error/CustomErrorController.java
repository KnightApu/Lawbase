package com.lawbase.error;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lawbase.controller.FrontCommonController;


@Controller
@RequestMapping( "/error" )
public class CustomErrorController extends FrontCommonController  implements ErrorController {
	
	private static final Logger logger = LoggerFactory.getLogger( CustomErrorController.class );
	

	private static final String PATH = "front/error-index";

    private static final String viewRoot = "front/error-";
    private static final String pathRoot = "/front/error";

    @Autowired
    private ErrorAttributes errorAttributes;
    
    @Override
    protected void generateControllerPaths() {

        controllerPaths = new HashMap<>();

        controllerPaths.put( "index", pathRoot );
        controllerPaths.put( "add", pathRoot + "/add" );
        controllerPaths.put( "view", pathRoot + "/view?id=" );
        controllerPaths.put( "delete", pathRoot + "/delete?id=" );
        controllerPaths.put( "manage", pathRoot + "/manage" );   
        
        
    }
    

    @Override
    public String getErrorPath() {
    	
    	logger.debug( "the error path" + PATH );
        return PATH;
    
    }
    
    @GetMapping( value = { "", "/", "/index" } )
    public String index(

            Model model, 
            @RequestParam Map<String, String> params,            
            HttpServletRequest request            
            
            ) {

    	
    	Map<String,Object> errors = getErrorAttributes( request ); 
    	
    	logger.info( errors.toString() );
    	
    	String errorMessage = "Something unexpected happened!";
    	
    	if ( StringUtils.isNotBlank( params.get( "message" ) ) ) {
    	
    		errorMessage =  params.get( "message" );
    		 
    	}        
    	
        addCommonModelAttributes( model, "index" );  
        
        addCommonFrontMenuAttributes( model );        
        
        addCommonFrontErrorMessage( model, errorMessage, errors  );
        
        return viewRoot + "index";
        
    }
    
    
    @GetMapping( value = { "/accessDenied" } )
    public String accessDenied(

            Model model, 
            @RequestParam Map<String, String> params,
            HttpServletRequest request
            
            ) {

    	Map<String,Object> errors = getErrorAttributes( request ); 
        
    	logger.info( errors.toString() );
    	
        addCommonModelAttributes( model, "index" );  
        
        addCommonFrontMenuAttributes( model );
        
        addCommonFrontErrorMessage( model, "Access Denied", errors );
        
        return viewRoot + "index";
        
    }
    
    protected Map<String, Object> getErrorAttributes(  HttpServletRequest request ){
    	
    	RequestAttributes requestAttributes = new ServletRequestAttributes(request);
    	
    	Map<String,Object> errors = errorAttributes.getErrorAttributes(requestAttributes, true );
    	
    	return errors;
    	
    }
    

	/*
	 * adds the error Attributes to the error page
	 */
	protected void addCommonFrontErrorMessage( Model model, String errorMessage, Map<String, Object> errors ){
		
		model.addAttribute( "errorMessage", errorMessage );
		
		String time = errors.get( "timestamp" ).toString();		
		String status = errors.get( "status" ).toString();
		String error = errors.get( "error" ).toString();		
		String message = errors.get( "message" ).toString();
		
		if( time != null | time.equals( "" ) ) 
		{
			model.addAttribute( "time" , time );
		}
		
		if( status != null | status.equals( "" ) ) 
		{
			model.addAttribute( "status" , status );
		}
		
		if( error != null | error.equals( "" ) ) 
		{
			model.addAttribute( "error" , error );
		}
		
		if( message != null | message.equals( "" ) ) 
		{
			model.addAttribute( "message" , message );
		}
		
	}
    
}
