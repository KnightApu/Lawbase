package com.lawbase.court;

import java.util.Map;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.service.RepositoryService;
import com.utility.form.FormValidationException;

import javassist.NotFoundException;

@Service
public class CourtBookService extends RepositoryService<CourtBook> {

    private static final Logger logger = LoggerFactory.getLogger( CourtBookService.class );

    @Autowired
    private CourtBookRepository courtBookRepository;
    @Autowired
    private CourtBookSearchRepository courtBookSearchRepository;
    
    @Cacheable( cacheNames = "courtBooksById", key = "#id", sync = true )
    public CourtBook findOne( ObjectId id ) {

        logger.debug( "findOne NOT served from cache, so wait for 2 sec" + id.toHexString() );
        
        CourtBook courtBook = courtBookRepository.findOne( id );
        
        return courtBook;
        
    }
    
    @Caching( 
            
            evict = { @CacheEvict( cacheNames = "courtBookAll", allEntries = true ) },
            put = { @CachePut( cacheNames = "courtBooksById", key = "#result.id" ) }
            
    )
    public CourtBook save( CourtBook courtBook ) {

        logger.debug( "Save should evict cache courtBookAll, courtBooksById" + courtBook.getId() );
        return courtBookRepository.save( courtBook );
        
    }

    @Caching( 
            evict = { 
                    
                @CacheEvict( cacheNames = "courtBookAll", allEntries = true ),
                @CacheEvict( value = "courtBooksById", key = "#courtBook.id" )   
                
            }            
    )
    public void delete( CourtBook courtBook ) {

        logger.debug( "delete should evict cache" + courtBook.getId() );
        courtBookRepository.delete( courtBook );
        
    }
    
    public void delete( ObjectId id ) throws FormValidationException, NotFoundException {
        
        CourtBook courtBook = getBook( id );
        delete( courtBook );
        
    }
    
    public CourtBook getBook( ObjectId id ) throws FormValidationException, NotFoundException {

        if ( null == id )
            throw new FormValidationException( "case id null" );
        
        CourtBook courtBook = findOne( id );
        
        if ( null == courtBook )
            throw new NotFoundException( "case not found" );
        
        return courtBook;
        
    }
    
    @Cacheable( cacheNames = "courtBookAll", key="{ #pageable.pageNumber, #pageable.pageSize }" )
    @Override
    public Page<CourtBook> findAll( Pageable pageable ) {

        return courtBookRepository.findAll( pageable );
        
    }

	public Page<CourtBookSearchProjection> findAllSearchProjections(int offSet, int size) {

        int page = offSet / size;
        PageRequest pageRequest = new PageRequest(page, size);
        return findAllSearchProjections( pageRequest );
        
	}
    public Page<CourtBookSearchProjection> findAllSearchProjections( Pageable pageable ) {

        return courtBookSearchRepository.findAll( pageable );
        
    }
    
    public Page<CourtBook> findByTitleStartingWith( String title, Pageable pageable ) {

        return courtBookRepository.findByTitleStartingWith( title, pageable );
        
    }

    public CourtBook createFromMap( User user, Map<String, String> params ) throws PersistenceException {
        
        String title = params.get( "title" );
        
        if ( null == title )
            title = "Add a title";
        
        CourtBook courtBook = new CourtBook( title, user );
        
        try {
            
            courtBookRepository.save( courtBook );
            
        } catch ( DataAccessException e ) {
            
            throw new PersistenceException( e.getMessage() );
            
        }
        
        logger.debug( courtBook.toString() );
        return courtBook;
        
    }

	public int getSizeOfRepository() {
		
		return (int) courtBookRepository.count();
	
	}

}
