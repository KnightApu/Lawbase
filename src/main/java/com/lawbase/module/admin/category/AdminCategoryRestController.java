package com.lawbase.module.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adhocmaster.controller.DataTableResponseEntity;
import com.lawbase.taxonomy.Category;
import com.lawbase.taxonomy.CategoryRepository;
import com.lawbase.taxonomy.CategoryService;

import util.restApi.RestInternalServerException;

@RestController
@RequestMapping( "/admin/rest/category" )
public class AdminCategoryRestController {
	
	 @Autowired 
	 CategoryService categoryService;
	 @Autowired 
	 CategoryRepository categoryRepository;
	    
	
	 @RequestMapping( value = { "" } )
	    public DataTableResponseEntity<Category> index(
	    		
	    		 @RequestParam( "forEntity" ) String forEntity,
	             @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
	             @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offset,
	             @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size
	    		

	    )throws RestInternalServerException  {
		 	
		 try {

	            int page = offset / size;
	                    
	            List<Category> casePage =  categoryRepository.findRootCategoriesOnly( forEntity );
	            
	            return new DataTableResponseEntity<Category>( casePage.size(), casePage.size(), sEcho, casePage );
	            
	        } catch ( Exception e ) {

	            throw new RestInternalServerException( e );
	            
	        }
		 

	    }


}
