package com.lawbase.module.admin.journal;

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
import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.mongo.user.UserHelper;
import com.adhocmaster.mongo.user.UserNotFoundInSessionException;
import com.adhocmaster.service.RepositoryService;
import com.book.BookNode;
import com.lawbase.article.Article;
import com.lawbase.article.ArticleService;
import com.lawbase.journal.Journal;
import com.lawbase.journal.JournalFactory;
import com.lawbase.journal.JournalManagementProjection;
import com.lawbase.journal.JournalRepository;
import com.lawbase.journal.JournalService;
import com.lawbase.module.admin.book.RestHelper;


import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;


@RestController
@RequestMapping( "/admin/rest/journal" )
public class AdminJournalRestController extends MongoRestController<Journal>{
	
	@Autowired
    protected UserHelper userHelper;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private RestHelper<Journal> restHelper;
    @Autowired
    JournalService journalService;
    
    @Autowired
    ArticleService articleService;

    @Override
    protected RepositoryService<Journal> getService() {

        return journalService;
        
    }
	
	@RequestMapping( "/" )
    public @ResponseBody DataTableResponseEntity<JournalManagementProjection> index(

            @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
            @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offSet,
            @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size

    		) throws RestInternalServerException {

        try {
        	        	 
        	 //Page<JournalManagementProjection> journalPage = journalService.findAllManagementProjection( offSet, size );
        	 
        	 Page<JournalManagementProjection> journalPage = journalService.findAllManagementProjection( new PageRequest( 0, journalService.getSizeOfRepository() ) );
        	 
        	 return new DataTableResponseEntity<JournalManagementProjection>( journalPage, sEcho );
        	
        	
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
    }
	
	 @RequestMapping( "/add" )
	    public Journal add(

	            HttpSession httpSession,
	            @RequestParam Map<String, String> params

	    ) throws RestBadDataException, RestInternalServerException {

	        try {

	            User user = userHelper.getFromSession( httpSession );
	            return new JournalFactory( journalRepository ).createFromMap( user, params );

	        } catch ( UserNotFoundInSessionException e ) {

	            throw new RestBadDataException( e );

	        } catch ( PersistenceException e ) {

	            throw new RestInternalServerException( e );

	        }

	    }
	 
	 /**
	     * mapping path needs to follow string pattern as book.js uses it
	     */
	    @PostMapping( "/edit/{id}/addNode" )
	    public BookNode addNode(

	            @PathVariable ObjectId id,
	            HttpSession httpSession,
	            @RequestParam Map<String, String> params

	    ) throws RestBadDataException, RestInternalServerException {

	        return restHelper.addNode( journalService, id, params );

	    }

	    /**
	     * adds a new case to a node of a journal tree.
	     * 
	     * @param id
	     * @param httpSession
	     * @param params
	     * @return
	     * @throws RestBadDataException
	     * @throws RestInternalServerException
	     */
	    
	    
	    
	    //"/edit/{id}/addCase"
	    //add article is not added yet
	    
	    @PostMapping( "/edit/{id}/addArticle" )
	    public Article addArticle(

	            @PathVariable ObjectId id,
	            HttpSession httpSession,
	            @RequestParam Map<String, String> params

	    ) throws RestBadDataException, RestInternalServerException {

	        User user;
	        try {

	            user = userHelper.getFromSession( httpSession );

	            // add the article title as a leaf node in the article node tree
	            params.put( "leaf", "true" );

	            BookNode leafNode = restHelper.addNode( journalService, id, params );

	            // TODO this is code smell

	            params.put( "parentNodeId", leafNode.getStringId() );

	            Article article = articleService.createFromJournal( id,
	                    user, params );

	            return article;

	        } catch ( UserNotFoundInSessionException e ) {

	            throw new RestInternalServerException( new Exception( "user session not found" ) );

	        } catch ( Exception e ) {

	            throw new RestInternalServerException( e );

	        }

	    }


}
