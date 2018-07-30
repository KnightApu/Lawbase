package com.lawbase.module.admin.act;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.adhocmaster.controller.DataTableResponseEntity;
import com.adhocmaster.controller.MongoRestController;
import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserHelper;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;
import com.adhocmaster.service.RepositoryService;
import com.lawbase.act.Act;
import com.lawbase.act.ActManagementProjection;
import com.lawbase.act.ActService;

import util.restApi.RestBadDataException;
import util.restApi.RestError;
import util.restApi.RestInternalServerException;

@RestController
@RequestMapping( "/admin/rest/act" )
public class AdminActRestController extends MongoRestController<Act> {
	
	
	@Autowired
    protected UserHelper userHelper;
    @Autowired
    private ActService actService;
    
    
    
    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler( RestBadDataException.class )
    @ResponseBody RestError
    handleBadRequest( HttpServletRequest request, Exception ex ) {
        
        Throwable cause = ex.getCause();
        return new RestError( cause.getClass().getCanonicalName(), cause.getMessage() );
        
    }
    

    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    @ExceptionHandler( RestInternalServerException.class )
    @ResponseBody RestError
    handleInternalServerError( HttpServletRequest request, Exception ex ) {
        
        Throwable cause = ex.getCause();
        return new RestError( cause.getClass().getCanonicalName(), cause.getMessage() );
        
    }
    
    @Override
    protected RepositoryService<Act> getService() {

        return actService;
    }
    
    @RequestMapping( "/" )
    public DataTableResponseEntity<ActManagementProjection> index(

            @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
            @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offSet,
            @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size

    		) throws RestInternalServerException {

        try {

        	 Page<ActManagementProjection> actPage = actService.findAllManagementProjection( offSet, size );
        	 
        	 return new DataTableResponseEntity<ActManagementProjection>( actPage, sEcho );
        	
        	
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
    }
    
    
	
    @RequestMapping( "/add" )
    public Act add(

            HttpSession httpSession,
            @RequestParam Map<String, String> params
            
            
            ) throws RestBadDataException, RestInternalServerException {

        try {
            
            User user = userHelper.getFromSession( httpSession );
            return actService.createFromMap( user, params );
            
        } catch ( UserNotFoundInSessionException e ) {

            throw new RestBadDataException( e );
            
        } catch ( PersistenceException e ) {

            throw new RestInternalServerException( e );
            
        }

        
    }
    
    
    
    @PostMapping( "edit/{id}" )
    public Act update(
            
            @PathVariable ObjectId id,
            HttpSession httpSession,
            @RequestParam Map<String, String> params
            
            ) throws RestBadDataException, RestInternalServerException { 
        
        
        try {

            actService.updateFromFormData(id, params);
            return actService.getAct(id);
            
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
        
    }
    
}
