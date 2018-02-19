package com.lawbase.journal;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.lawbase.court.CourtBookFactory;

public class JournalFactory {
	 private static Logger logger = LoggerFactory.getLogger( CourtBookFactory.class );
	    
	    private JournalRepository journalRepository;
	    
	    
	    
	    public JournalFactory( JournalRepository journalRepository ) {

	        this.journalRepository = journalRepository;
	        
	    }



	    public Journal createFromMap( User user, Map<String, String> params ) throws PersistenceException {
	        
	        String title = params.get( "title" );
	        
	        if ( null == title )
	            title = "Add a title";
	        
	        Journal journal = new Journal( title, user );
	        
	        try {
	            
	            journalRepository.save( journal );
	            
	        } catch ( DataAccessException e ) {
	            
	            throw new PersistenceException( e.getMessage() );
	            
	        }
	        
	        logger.debug( journal.toString() );
	        return journal;
	        
	    }

}
