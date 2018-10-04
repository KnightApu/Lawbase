package com.lawbase.module.admin.article;

import java.util.Map;

import javax.servlet.http.HttpSession;

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
import com.adhocmaster.service.RepositoryService;
import com.lawbase.article.Article;
import com.lawbase.article.ArticleManagementProjection;
import com.lawbase.article.ArticleService;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;

@RestController
@RequestMapping( "/admin/rest/article" )
public class AdminRestArticleController extends MongoRestController<Article> {


    @Autowired
    ArticleService articleService;


    @Override
    protected RepositoryService<Article> getService() {

        return articleService;
    }
    
	@RequestMapping( "/" )
    public @ResponseBody DataTableResponseEntity<ArticleManagementProjection> index(

            @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
            @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offSet,
            @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size

    		) throws RestInternalServerException {

        try {

        	// Page<ArticleManagementProjection> articlePage = articleService.findAllManagementProjection( offSet, size );
        	 
        	 Page<ArticleManagementProjection> articlePage = articleService.findAllManagementProjection( new PageRequest( 0, articleService.getSizeOfRepository() ));
        	 
        	 return new DataTableResponseEntity<ArticleManagementProjection>( articlePage, sEcho );
        	
        	
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
    }
	

    @PostMapping( "edit/{id}" )
    public Article update(
            
            @PathVariable ObjectId id,
            HttpSession httpSession,
            @RequestParam Map<String, String> params
            
            ) throws RestBadDataException, RestInternalServerException { 
                
        try {
        	
            return articleService.updateFromFormData( id, params);
            
            //System.out.println("update form date call hoise");
            //return new RestSuccess( RestSuccess.Codes.SAVE_DB );
            
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
        
    }    
    

}
