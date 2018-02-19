package com.lawbase.court;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;

public class CourtBookFactory {
    
    private static Logger logger = LoggerFactory.getLogger( CourtBookFactory.class );
    
    private CourtBookRepository courtRepository;
    
    
    
    public CourtBookFactory( CourtBookRepository courtRepository ) {

        this.courtRepository = courtRepository;
        
    }



    public CourtBook createFromMap( User user, Map<String, String> params ) throws PersistenceException {
        
        String title = params.get( "title" );
        
        if ( null == title )
            title = "Add a title";
        
        CourtBook courtBook = new CourtBook( title, user );
        
        try {
            
            courtRepository.save( courtBook );
            
        } catch ( DataAccessException e ) {
            
            throw new PersistenceException( e.getMessage() );
            
        }
        
        logger.debug( courtBook.toString() );
        return courtBook;
        
    }
    
    
    public List<CourtBook> getCourtBooks(){
    	
    	List<CourtBook> courtBooks = courtRepository.findAll();
    	
    	return courtBooks;
    	
    }

}
