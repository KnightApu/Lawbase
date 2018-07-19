package com.adhocmaster.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lawbase.error.CustomErrorController;

abstract public class MvcController {

    protected Map<String, String> controllerPaths;
    
    private static final Logger logger = LoggerFactory.getLogger( CustomErrorController.class );
	
    
    public MvcController() {

        generateControllerPaths();
        
    }

    /**
     * Every page has a set of common attributes.
     * templatePart : the part of master page which is specific to the request path. Like a feature can have a master page and sub pages are linked through templatePart
     * controllerPaths: All the related path to the current path. Like add/edit/remove paths.
     * currentPath: current path of the page.
     * @param model
     * @param methodName template names are named exactly as methodName. So, if there is a listing method, both the methodName and templatePart name should be "listing"
     */
    protected void addCommonModelAttributes( Model model, String methodName ) {

        model.addAttribute( "templatePart", methodName );
        model.addAttribute( "currentPath", getControllerPath( methodName ) );
        model.addAttribute( "controllerPaths", controllerPaths );
        
    }
    
    /**
     * 
     * @param methodName
     * @return request path associated with the method
     */
    public final String getControllerPath( String methodName ) {
        
        return controllerPaths.get( methodName );
        
    }
    
    
    @ExceptionHandler(Exception.class)
	public String handleError( HttpServletRequest req, Exception ex, Model model) {
	    
	    model.addAttribute( "exception", ex );
	    
	    String errorControllerMapping = CustomErrorController.class.getAnnotation( RequestMapping.class ).value() [ 0 ];
        
        logger.debug( "the error controller url " + errorControllerMapping );
	    
	    return "forward:" + errorControllerMapping;
    
    }
    
    abstract protected void generateControllerPaths();

}
