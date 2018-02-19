package com.lawbase.module.admin.court;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.lawbase.cases.Case;
import com.lawbase.cases.CaseService;
import com.lawbase.court.CourtBook;
import com.lawbase.court.CourtBookService;
import com.lawbase.module.admin.book.RestHelper;

import util.restApi.RestBadDataException;
import util.restApi.RestInternalServerException;

@RestController
@RequestMapping( "/admin/rest/courtbook" )
public class AdminCourtBookRestController extends MongoRestController<CourtBook> {

    @Autowired
    protected UserHelper userHelper;
    
    @Autowired
    private RestHelper<CourtBook> restHelper;

    
    @Autowired
    CaseService caseService;
    @Autowired
    private CourtBookService courtBookService;

    @Override
    protected RepositoryService<CourtBook> getService() {

        return courtBookService;
    }
    
    @RequestMapping( "/" )
    public @ResponseBody DataTableResponseEntity<CourtBook> index(

            @RequestParam( value = "sEcho", required = false, defaultValue = "1" ) int sEcho,
            @RequestParam( value = "iDisplayStart", required = false, defaultValue = "0" ) int offSet,
            @RequestParam( value = "iDisplayLength", required = false, defaultValue = "10" ) int size

    		) throws RestInternalServerException {

        try {

        	 Page<CourtBook> courtPage = courtBookService.findAll( offSet, size );
        	 
        	 return new DataTableResponseEntity<CourtBook>( courtPage, sEcho );
        	
        	
        } catch ( Exception e ) {

            throw new RestInternalServerException( e );
            
        }
    }

    @RequestMapping( "/add" )
    public CourtBook add(

            HttpSession httpSession,
            @RequestParam Map<String, String> params

    ) throws RestBadDataException, RestInternalServerException {

        try {

            User user = userHelper.getFromSession( httpSession );
            return courtBookService.createFromMap( user, params );

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

        return restHelper.addNode( courtBookService, id, params );

    }

    /**
     * adds a new case to a node of a courtbook tree.
     * 
     * @param id
     * @param httpSession
     * @param params
     * @return
     * @throws RestBadDataException
     * @throws RestInternalServerException
     */
    @PostMapping( "/edit/{id}/addCase" )
    public Case addCase(

            @PathVariable ObjectId id,
            HttpSession httpSession,
            @RequestParam Map<String, String> params

    ) throws RestBadDataException, RestInternalServerException {

        User user;
        try {

            user = userHelper.getFromSession( httpSession );

            // add the casebook title as a leaf node in the courtbook node tree
            params.put( "leaf", "true" );

            BookNode leafNode = restHelper.addNode( courtBookService, id, params );

            // TODO this is code smell

            params.put( "parentNodeId", leafNode.getStringId() );

            Case caseBook = caseService.createFromCourtbook( id,
                    user, params );

            return caseBook;

        } catch ( UserNotFoundInSessionException e ) {

            throw new RestInternalServerException( new Exception( "user session not found" ) );

        } catch ( Exception e ) {

            throw new RestInternalServerException( e );

        }

    }

}
