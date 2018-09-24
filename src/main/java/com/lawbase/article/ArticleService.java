package com.lawbase.article;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.service.RepositoryService;
import com.book.BookNode;
import com.book.exceptions.NoSuchNodeException;
import com.book.exceptions.NotALeafNodeException;
import com.book.simpleBook.Status;
import com.lawbase.act.ActManagementProjection;
import com.lawbase.journal.Journal;
import com.lawbase.journal.JournalRepository;
import com.mongo.media.MediaRepository;
import com.utility.form.FormValidationException;

import javassist.NotFoundException;

@Service
public class ArticleService extends RepositoryService<Article> {

    private static Logger logger = LoggerFactory.getLogger( ArticleService.class );
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private MediaRepository mediaRepository;
    
    @Autowired
    private ArticleManagementProjectionRepository articleManagementProjectionRepository;
    
  
    

    public ArticleService( ArticleRepository articleRepository, JournalRepository journalRepository, MediaRepository mediaRepository ) {
        
        super();
        this.articleRepository = articleRepository;
        this.journalRepository = journalRepository;
        this.mediaRepository = mediaRepository;
        
    }
    
    @Override
    public Article findOne( ObjectId id ) {
        
        return articleRepository.findOne( id );
        
    }

    @Override
    public Article save( Article book ) {

        return articleRepository.save(book);
        
    }


    @Override
    public void delete( Article book ) {

        articleRepository.delete( book );
        
    }




    @Override
    public Page<Article> findAll( Pageable pageable ) {

        return articleRepository.findAll(pageable);

    }
    
    public Article createFromMap( User user, Map<String, String> params ) throws PersistenceException {
        
        String title = params.get( "title" );
        
        if ( null == title )
            title = "Add a title";
        
        Article book = new Article( title, user );
        
        try {
            
        	save(book);
            
        } catch ( DataAccessException e ) {
            
            throw new PersistenceException( e.getMessage() );
            
        }
        
        logger.debug( book.toString() );
        return book;
        
    }
    
    public Article getArticleBook( ObjectId articleId ) throws FormValidationException, NotFoundException {

        if ( null == articleId )
            throw new FormValidationException( "article id null" );
        
        Article article = findOne( articleId );
        
        if ( null == article )
            throw new NotFoundException( "article not found" );
        
        return article;
        
    }
    
    /**
     * 
     * @param courtbookId
     * @param user
     * @param params parentNodeId (leafNode which will hold the reference to this case book)
     * @return
     * @throws PersistenceException
     */
    public Article createFromJournal( ObjectId journalId, User user, Map<String, String> params ) throws PersistenceException {

        Article article = createFromMap( user, params );
        
        try {
            
            ArticleRefContent articleRefContent = new ArticleRefContent( article );

            Journal journal = journalRepository.findOne( journalId );

            if ( null == journal )
                throw new Exception( "journal not found");
            
            BookNode leaftNode = journal.findNode( new ObjectId( params.get( "parentNodeId" ) ) );
            
            if ( null == leaftNode )
                throw new NoSuchNodeException();
            
            journal.addContent( leaftNode, articleRefContent );
            
            //TODO no repository access
            journalRepository.save( journal ); //attached reference
            
            article.populateJournalInformation( journal, leaftNode );
            
            save( article );
            
            return article;
            

        } catch ( NoSuchNodeException e ) {

            delete( article );
            
            throw new PersistenceException( "parent node not found" );
            
        } catch ( NotALeafNodeException e ) {
            
            delete( article );

            throw new PersistenceException( "parent node is not a leaf" );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
        
    }

    /**
     * 
     * @param articleId
     * @param params updateType: basicInformation, digest, transcript
     * @return
     * @throws PersistenceException
     */
    public Article updateFromFormData( ObjectId articleId, Map<String, String> params ) throws PersistenceException {
        
        
    	if ( StringUtils.isBlank( params.get( "updateType" ) ) )
            throw new PersistenceException( "no update type set" );
    	
    	Article article = articleRepository.findOne( articleId );
        
        switch( params.get( "updateType" ) ) {
        	case "article":
                try { 
                    
                    updateBasicInformation( articleId, params );
                    
                } catch ( Exception e ) {
                	System.out.println("exception khaise");
                    throw new PersistenceException( e.getMessage() );
                    
                }         
            
        }
        
        return article;
        
    }
        
    
    public void updateFromFormData( ObjectId articleId, Map<String, String> params, List<String> categoryStringids ) throws PersistenceException {
    

        if ( StringUtils.isBlank( params.get( "updateType" ) ) )
            throw new PersistenceException( "no update type set" );
        
        if( params.get( "updateType" ).equals( "category" ) ) {
            
            updateFromFormCategoryList( articleId, categoryStringids );
            
        } else {

            updateFromFormData( articleId, params );
            
        }
        
    }
    
    private void updateFromFormCategoryList( ObjectId articleId, List<String> categoryStringids ) {

        // TODO Auto-generated method stub
        
    }

    /**
     * 
     * @param caseId
     * @param params firstParty, secondParty, sources, keywords
     * @throws FormValidationException
     * @throws NotFoundException
     * @throws PersistenceException
     */
    public void updateBasicInformation( ObjectId articleId, Map<String, String> params ) throws FormValidationException, NotFoundException, PersistenceException {
	   
        Article articleBook = getArticleBook( articleId );
        
        if ( StringUtils.isNotBlank( params.get( "title" ) ) ) {
            
			articleBook.setTitle( params.get( "title" ) );
            
        }
        
        if ( StringUtils.isNotBlank( params.get( "year" ) ) ) {
            
        	articleBook.setYear( Integer.parseInt(params.get( "year" )) );
             
        }
        
        if ( StringUtils.isNotBlank( params.get( "volume" ) ) ) {
            
            
        	articleBook.setVolume(params.get( "volume" ) );
            
        }
        
        if ( StringUtils.isNotBlank( params.get( "issue" ) ) ) {

        	articleBook.setIssueNo(params.get( "issue" ) );
        }     
        
        if ( StringUtils.isNotBlank( params.get( "externalAuthor" ) ) ) {

        	articleBook.setExternalAuthor(params.get( "externalAuthor" ) );
        } 
        
        if ( StringUtils.isNotBlank( params.get( "description" ) ) ) {

        	articleBook.setExternalAuthor(params.get( "description" ) );
        } 
        
        if ( StringUtils.isNotBlank( params.get( "article" ) ) ) {

        	articleBook.setDescription(params.get( "article" ) );
        } 
        
        if ( StringUtils.isNotBlank( params.get( "searchTerms" ) ) ) {

        	articleBook.setSearchTerms(params.get( "searchTerms" ));

        }
        
        if ( StringUtils.isNotBlank( params.get( "subject" ) ) ) {

        	articleBook.setSubject(params.get( "subject" ));

        }
        
        if ( StringUtils.isNotBlank( params.get( "status" ) ) ) {
            
        	String tempStatus = params.get( "status" );
        	logger.debug( "Status article::::" + tempStatus );
        	
        	Status status = Status.valueOf(tempStatus);
        	articleBook.setStatus( status );
            
        }
        
        
        
        try {

            save( articleBook );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
          
    }
    

    /**
     * 
     * @param articleId
     * @param params -testing-
     * @throws FormValidationException
     * @throws NotFoundException
     * @throws PersistenceException
     * @throws ParseException
     */
    public void updateDigest( ObjectId articleId, Map<String, String> params ) throws FormValidationException, NotFoundException, PersistenceException, ParseException {
        
        SimpleDateFormat df = new SimpleDateFormat( "dd-mm-yyyy" );
        
        Article article = getArticleBook( articleId );

        
        try {

            save( article );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
          
    }

    
    
    private void updateReference( ObjectId caseId, Map<String, String> params ) throws FormValidationException, NotFoundException, PersistenceException {

        Article article = getArticleBook( caseId );
        
        
        try {

            save( article );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
        
    }
    
    /**
     * 
     * @param articleId
     * @param params transcript
     * @throws FormValidationException
     * @throws NotFoundException
     * @throws PersistenceException
     */
    private void updateTranscript( ObjectId articleId, Map<String, String> params ) throws FormValidationException, NotFoundException, PersistenceException {

        Article article = getArticleBook( articleId );
        
        
        try {

            save( article );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
        
    }
    
    private void updateTranscriptMedia( ObjectId articleId, Map<String, String> params) throws FormValidationException, NotFoundException, PersistenceException {
    	
    	//System.out.println("hello in update transcript media");
    	
    	
    	
    	//params.forEach((k,v)-> System.out.println(k+", "+v));
    	
    	Article article = getArticleBook( articleId );
    	
    	//System.out.println("caseBook"+caseBook.toString());
    	
        try {

            save( article );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }

    	
    }
    
    public Page<ArticleManagementProjection> findAllManagementProjection( Pageable pageable ) {

        return articleManagementProjectionRepository.findAll( pageable );

    }
    
    public Page<ArticleManagementProjection> findAllManagementProjection( int offSet, int size ) {

        int page = offSet / size;
        
        PageRequest pageRequest = new PageRequest( page, size );
        
        return findAllManagementProjection( pageRequest );
        
}

	public int getSizeOfRepository() {
		
		return (int) articleRepository.count();
		
	}
    
    
}
