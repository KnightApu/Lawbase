package com.lawbase.module.admin.user;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adhocmaster.controller.DataTableResponseEntity;
import com.adhocmaster.controller.MongoRestController;
import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserService;
import com.adhocmaster.service.RepositoryService;
import com.adhocmaster.user.role.ExpirableCapabilityAuthorityService;
import com.utility.form.FormValidationException;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;

@RestController
@RequestMapping( "/admin/rest/user" )
public class AdminUserRestController extends MongoRestController<User> {

	@Autowired
	UserService userService;
	
	@Autowired
	ExpirableCapabilityAuthorityService expirableCapabilityAuthorityService; 
	
	
	@Override
	protected RepositoryService<User> getService() {
		
		return userService;
	}

	
	@RequestMapping( "/" )
    public @ResponseBody DataTableResponseEntity<User> index(

            @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
            @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offSet,
            @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size

    		) throws RestInternalServerException {

        try {

        	 //Page<User> userPage = userService.findAll( offSet, size );
        	 
        	 Page<User> userPage = userService.findAll( new PageRequest( 0, userService.getSizeOfRepository() ) );
        	 
        	 return new DataTableResponseEntity<User>( userPage, sEcho );
        	
        	
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
    }

	
    @PostMapping( "/add" )
    public User add(

            HttpSession httpSession,
            @RequestParam Map<String, String> params

    ) throws RestBadDataException, RestInternalServerException {

        try {

            User user = userHelper.getFromSession( httpSession );
            
            ObjectId userCreatorId = user.getId();
            
            return userService.addFromFormData( userCreatorId, params );
          
            
        } catch ( FormValidationException e ) {

            throw new RestBadDataException( e );

        } catch ( Exception e ) {

            throw new RestInternalServerException( e );

        }

    }

    /**
     * mapping path needs to follow string pattern as user.js uses it
     */
    @PostMapping( "edit/{id}" )
    public User update(
            
            @PathVariable ObjectId id,
            HttpSession httpSession,
            @RequestParam Map<String, String> params
            
            ) throws RestBadDataException, RestInternalServerException { 
                
        try {
        	
        	if ( StringUtils.isBlank( params.get( "updateType" ) ) )
                throw new PersistenceException( "no update type set" );
        	
        	if( params.get( "updateType" ).equals( "services" ) )
        	{
        		expirableCapabilityAuthorityService.updateFromFormData( id, params );
        	}
        	
            return userService.updateFromFormData( id, params);
            
           // return new RestSuccess( RestSuccess.Codes.SAVE_DB );
            
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
        
    }
	
}
