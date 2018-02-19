package com.lawbase.module.admin.caseBook;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adhocmaster.controller.MongoRestController;
import com.adhocmaster.service.RepositoryService;
import com.lawbase.cases.Case;
import com.lawbase.cases.CaseService;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;

@RestController
@RequestMapping( "/admin/rest/case" )
public class AdminRestCaseController extends MongoRestController<Case> {

    @Autowired
    CaseService caseService;
    @Override
    protected RepositoryService<Case> getService() {

        return caseService;
    }
    
    

    @PostMapping( "edit/{id}" )
    public Case update(
            
            @PathVariable ObjectId id,
            HttpSession httpSession,
            @RequestParam Map<String, String> params
            
            ) throws RestBadDataException, RestInternalServerException { 
                
        try {

        	return caseService.updateFromFormData( id, params);
            
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
        
    }
    /*
    @PostMapping( "edit/{id}" )
    public Serializable update(
            
            @PathVariable ObjectId id,
            HttpSession httpSession,
            @RequestParam Map<String, String> params,
            @RequestParam("categoryList") List<String> categoryStringIds
            
            ) throws RestBadDataException, RestInternalServerException { 
        
        
        for( int i = 0; i < categoryStringIds.size(); i++ )
        {
        	System.out.println("category:" + categoryStringIds.get(i));
        }
        
        try {

            //caseService.updateFromFormData( id, params );
            
            caseService.updateFromFormData( id, params, categoryStringIds );
            
            return new RestSuccess( RestSuccess.Codes.SAVE_DB );
            
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
        
    }
    */

    

}
