package com.lawbase.module.admin.article;

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
import com.lawbase.article.Article;
import com.lawbase.article.ArticleRepository;
import com.lawbase.article.ArticleService;
import com.lawbase.journal.JournalRepository;
import com.mongo.media.MediaRepository;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;

@RestController
@RequestMapping( "/admin/rest/article" )
public class AdminRestArticleController extends MongoRestController<Article> {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    ArticleService articleService;



    @Override
    protected RepositoryService<Article> getService() {

        return articleService;
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
