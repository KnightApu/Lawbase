package com.lawbase.module.front.caseBook;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adhocmaster.controller.DataTableResponseEntity;
import com.adhocmaster.controller.MongoRestController;
import com.adhocmaster.service.RepositoryService;
import com.lawbase.cases.Case;
import com.lawbase.cases.CaseManagementProjection;
import com.lawbase.cases.CaseService;
import com.solr.lawbase.search.cases.SolrCase;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;


@RestController
@RequestMapping( "/front/rest/case" )
public class FrontRestCaseController extends MongoRestController<Case> {

	private static final Logger logger = LoggerFactory.getLogger( FrontRestCaseController.class );
    
	
	@Autowired
    CaseService caseService;
    @Override
    protected RepositoryService<Case> getService() {

        return caseService;
    }
    
	@RequestMapping( "/" )
    public @ResponseBody DataTableResponseEntity<CaseManagementProjection> index(

            @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
            @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offSet,
            @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size

    		) throws RestInternalServerException {

        try {

        	// Page<CaseManagementProjection> casePage = caseService.findAllManagementProjection( offSet, size );
        	 
        	 Page<CaseManagementProjection> casePage = caseService.findAllManagementProjection( new PageRequest( 0, caseService.getSizeOfRepository() ));

        	 
        	 return new DataTableResponseEntity<CaseManagementProjection>( casePage, sEcho );
        	
        	
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
    }
	
	/**
     * 
     * @param field name of the field, nonblank. fieldname "all" if you want to search everything
     * @param value name of the value, nonblank
     * @param sEcho comes from datatable "sEcho"
     * @param offSet comes from datatable "iDisplayStart"
     * @param size comes from datatable "iDisplayLength"
     * @return
     * @throws RestInternalServerException, RestBadDataException
     */
    @RequestMapping( "/findByField")
    public DataTableResponseEntity<Case> findByField(

            @RequestParam( "field" ) String field,
            @RequestParam( "value" ) String value,
            @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
            @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offSet,
            @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size
            
            ) throws RestInternalServerException, RestBadDataException {
        
    	logger.info("Inside front Case rest controller/findByFieldMethod");

   	 	//Page<CaseManagementProjection> casePage = caseService.findAllManagementProjection( new PageRequest( 0, caseService.getSizeOfRepository() ));

    	
	 
   	 	//return new DataTableResponseEntity<CaseManagementProjection>( casePage, sEcho );

//    	Page<Case> casePage = caseService.findByfield( field, value, sEcho, offSet, size );
    	
//    	 return new DataTableResponseEntity<Case>( casePage, sEcho );
    	 
    	 if ( StringUtils.isBlank( field ) )
             throw new RestBadDataException( new Exception("field cannot be empty") );
         
         if ( StringUtils.isBlank( value ) )
             throw new RestBadDataException( new Exception("value cannot be empty") );

         try {

             int page = offSet / size;
                
             Page<Case> casePage = caseService.findByfield( field, value, sEcho, offSet, size );
             
         //    Page<Case> casePage2 = caseService.findByField( field, value, page, size );
             
             logger.info(casePage.getContent().toString());
             
             return new DataTableResponseEntity<Case>( casePage, sEcho );  
             
         } catch ( Exception e ) {
             
             throw new RestInternalServerException( e );
         }   
    	
       // return super.findByFieldForDataTable( field, value, sEcho, offSet, size );

    }

}
