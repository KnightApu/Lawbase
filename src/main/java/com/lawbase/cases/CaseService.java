package com.lawbase.cases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.adhocmaster.service.RepositoryService;
import com.book.BookNode;
import com.book.exceptions.NoSuchNodeException;
import com.book.exceptions.NotALeafNodeException;
import com.book.simpleBook.Status;
import com.lawbase.court.CourtBook;
import com.lawbase.court.CourtBookService;
import com.lawbase.taxonomy.Category;
import com.lawbase.taxonomy.CategoryRepository;
import com.mongo.media.Media;
import com.mongo.media.MediaRepository;
import com.utility.form.FormValidationException;

import io.reactivex.subjects.Subject;
import javassist.NotFoundException;

@Service
public class CaseService extends RepositoryService<Case> {

    private static Logger logger = LoggerFactory.getLogger( CaseService.class );
    
    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private CourtBookService courtBookService;
    @Autowired
    private MediaRepository mediaRepository;

	private CategoryRepository categoryRepository;

    @Autowired
    @Qualifier( "caseSavedPublisher")
    Subject<Case> caseSavedPublisher;

    @Autowired
    @Qualifier( "caseDeletedPublisher")
    Subject<Case> caseDeletedPublisher;
    


    public Case save( Case caseBook ) {

        Case book = caseRepository.save( caseBook );
        
        caseSavedPublisher.onNext( caseBook );
        
        return book;
        
    }
    
    public void delete( Case caseBook ) {
        
        caseRepository.delete( caseBook );
        caseDeletedPublisher.onNext( caseBook );
        
    }
    
    public void delete( ObjectId caseId ) throws FormValidationException, NotFoundException {
        
        Case caseBook = getCaseBook( caseId );
        delete( caseBook );
        
    }
    @Override
    public Case findOne( ObjectId id ) {

        return caseRepository.findOne( id );
    }
    
    public List<Case> findByCourtBookId(ObjectId id) {
    	
    	return caseRepository.findByCourtBookId(id);
    }

    @Override
    public Page<Case> findAll( Pageable pageable ) {

        return caseRepository.findAll( pageable );
    }

    public Case getCaseBook( ObjectId caseId ) throws FormValidationException, NotFoundException {

        if ( null == caseId )
            throw new FormValidationException( "case id null" );
        
        Case caseBook = findOne( caseId );
        
        if ( null == caseBook )
            throw new NotFoundException( "case not found" );
        
        return caseBook;
        
    }
    
    protected Case createFromMap( User user, Map<String, String> params ) throws PersistenceException {
        
        String title = params.get( "title" );
        
        if ( null == title )
            title = "Add a title";
        
        Case book = new Case( title, user );
        
        try {
            
            save( book );
            
        } catch ( DataAccessException e ) {
            
            throw new PersistenceException( e.getMessage() );
            
        }
        
        logger.debug( book.toString() );
        return book;
        
    }
    
    /**
     * 
     * @param courtbookId
     * @param user
     * @param params parentNodeId (leafNode which will hold the reference to this case book)
     * @return
     * @throws PersistenceException
     */
    
    public Case createFromCourtbook( ObjectId courtbookId, User user, Map<String, String> params ) throws PersistenceException {

        Case caseBook = createFromMap( user, params );
        
        try {
            
            CaseRefContent caseRefContent = new CaseRefContent( caseBook );

            CourtBook courtBook = courtBookService.findOne( courtbookId );

            if ( null == courtBook )
                throw new Exception( "court book not found");
            
            BookNode leaftNode = courtBook.findNode( new ObjectId( params.get( "parentNodeId" ) ) );
            
            if ( null == leaftNode )
                throw new NoSuchNodeException();
            
            courtBook.addContent( leaftNode, caseRefContent );
            
            courtBookService.save( courtBook ); //attached reference
            
            caseBook.populateCourtInformation( courtBook, leaftNode );
            
            save( caseBook );
            
            return caseBook;
            

        } catch ( NoSuchNodeException e ) {

            caseRepository.delete( caseBook );
            
            throw new PersistenceException( "parent node not found" );
            
        } catch ( NotALeafNodeException e ) {
            
            caseRepository.delete( caseBook );

            throw new PersistenceException( "parent node is not a leaf" );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
        
    }


    /**
     * 
     * @param caseId
     * @param params updateType: basicInformation, digest, transcript
     * @throws PersistenceException
     */
    public Case updateFromFormData( ObjectId caseId, Map<String, String> params ) throws PersistenceException {
        
        if ( StringUtils.isBlank( params.get( "updateType" ) ) )
            throw new PersistenceException( "no update type set" );
        
        Case casebook = caseRepository.findOne( caseId ); 
        
        switch( params.get( "updateType" ) ) {

            case "basicInformation":
                
                try { 
                    
                    updateBasicInformation( caseId, params );
                    
                } catch ( Exception e ) {

                    throw new PersistenceException( e.getMessage() );
                    
                }
                
                break;
            case "digest":
                
                try { 
                    
                    updateDigest( caseId, params );
                    
                } catch ( Exception e ) {

                    throw new PersistenceException( e.getMessage() );
                    
                }
                
                break;
            case "transcript":
                
                try { 
                    
                    updateTranscript( caseId, params );
                    
                } catch ( Exception e ) {

                    throw new PersistenceException( e.getMessage() );
                    
                }
                
                break;
               
            case "transcriptMedia":
            	
            	try{
            		
            		updateTranscriptMedia( caseId, params );
            		
            	}catch ( Exception e ) {
            		
            		
            		throw new PersistenceException( e.getMessage() );
            	}
            	
            	break;
            	
            case "reference":
            	
            	try{
            		
            		updateReference( caseId, params );
            		
            	}catch ( Exception e ) {
            		
            		
            		throw new PersistenceException( e.getMessage() );
            	}
            	
            	break;	
        }
        
        return casebook;
        
    }

    public void updateFromFormData( ObjectId caseId, Map<String, String> params, List<String> categoryStringids ) throws PersistenceException, FormValidationException, NotFoundException {
    

        if ( StringUtils.isBlank( params.get( "updateType" ) ) )
            throw new PersistenceException( "no update type set" );
        
        if( params.get( "updateType" ).equals( "category" ) ) {
            
        	System.out.println("categoryUpdate");
            updateFromFormCategoryList( caseId, categoryStringids );
            
        } else {

            updateFromFormData( caseId, params );
            
        }
        
    }
    
    private void updateFromFormCategoryList( ObjectId caseId, List<String> categoryStringIds ) throws FormValidationException, NotFoundException {

        // TODO Auto-generated method stub
    	Case caseBook = getCaseBook( caseId );
    	
    	for(int i = 0; i < categoryStringIds.size(); i++)
    	{
    		Category tempCategory = categoryRepository.findById( new ObjectId(categoryStringIds.get(i)) );
    		System.out.println("hi tempCategory"+tempCategory.getName());
    	}
        
        
    }

    /**
     * 
     * @param caseId
     * @param params firstParty, secondParty, sources, keywords
     * @throws FormValidationException
     * @throws NotFoundException
     * @throws PersistenceException
     */
    public void updateBasicInformation( ObjectId caseId, Map<String, String> params ) throws FormValidationException, NotFoundException, PersistenceException {
                
        Case caseBook = getCaseBook( caseId );
       
        if ( StringUtils.isNotBlank( params.get( "firstParty" ) ) ) {
            
            caseBook.setFirstParty( params.get( "firstParty" ) );
            
            String currentFirstParty = params.get( "firstParty" );
            String prevSecondParty = caseBook.getSecondParty();
            
            String newTitle = currentFirstParty + " vs " + prevSecondParty;
            
            caseBook.setTitle(newTitle);
            
        }
        if ( StringUtils.isNotBlank( params.get( "secondParty" ) ) ) {
            
            caseBook.setSecondParty( params.get( "secondParty" ) );
            
            String currentSecondParty = params.get( "secondParty" );
            String prevFirstParty = caseBook.getFirstParty();
            
            String newTitle = prevFirstParty + " vs " + currentSecondParty;
            
            caseBook.setTitle(newTitle);
            
        }
        if ( StringUtils.isNotBlank( params.get( "sources" ) ) ) {
            
            String[] sources = StringUtils.split( params.get( "sources" ), ',' );
            caseBook.setSources( Arrays.asList( sources ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "keywords" ) ) ) {

            String[] keywords = StringUtils.split( params.get( "keywords" ), ',' );
            caseBook.setKeywords( Arrays.asList( keywords ) );
            
            
        }        
        if ( StringUtils.isNotBlank( params.get( "treatment" ) ) ) {
        	
        	String tempTreatment = params.get( "treatment" );
        	
        	CaseTreatment treatment = CaseTreatment.valueOf(tempTreatment);
        	
        	caseBook.setTreatment( treatment );
                        
        }
        
        if ( StringUtils.isNotBlank( params.get( "status" ) ) ) {
            
        	String tempStatus = params.get( "status" );
        	
        	Status status = Status.valueOf( tempStatus );
        	
        	caseBook.setStatus( status );
            
        }

        
        try {

            save( caseBook );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
          
    }
    

    /**
     * 
     * @param caseId
     * @param params caseNo, caseDate, judge, counsels, subject, summary, _abstract, mentionedIn, followed, affirmed, appliedIn, appliedBy, disposition
     * @throws FormValidationException
     * @throws NotFoundException
     * @throws PersistenceException
     * @throws ParseException
     */
    public void updateDigest( ObjectId caseId, Map<String, String> params ) throws FormValidationException, NotFoundException, PersistenceException, ParseException {
        
        SimpleDateFormat df = new SimpleDateFormat( "dd-mm-yyyy" );
        
        Case caseBook = getCaseBook( caseId );

        if ( StringUtils.isNotBlank( params.get( "caseNo" ) ) ) {
            
            caseBook.setCaseNo( params.get( "caseNo" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "caseDate" ) ) ) {
            
            caseBook.setCaseDate( df.parse( params.get( "caseDate" ) ) );
            
        }

        if ( StringUtils.isNotBlank( params.get( "judge" ) ) ) {
            
            caseBook.setJudge( params.get( "judge" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "counsels" ) ) ) {
            
            caseBook.setCounsels( params.get( "counsels" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "subject" ) ) ) {
            
            caseBook.setSubject( params.get( "subject" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "summary" ) ) ) {
            
            caseBook.setSummary( params.get( "summary" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "_abstract" ) ) ) {
            
            caseBook.set_abstract( params.get( "_abstract" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "mentionedIn" ) ) ) {
            
            caseBook.setMentionedIn( params.get( "mentionedIn" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "followed" ) ) ) {
            
            caseBook.setFollowed( params.get( "followed" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "affirmed" ) ) ) {
            
            caseBook.setAffirmed( params.get( "affirmed" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "appliedIn" ) ) ) {
            
            caseBook.setAppliedIn( df.parse( params.get( "appliedIn" ) ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "appliedBy" ) ) ) {
            
            caseBook.setAppliedBy( params.get( "appliedBy" ) );
            
        }
        if ( StringUtils.isNotBlank( params.get( "disposition" ) ) ) {
            
            caseBook.setDisposition( params.get( "disposition" ) );
            
        }
        try {

            save( caseBook );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
          
    }

    
    
    private void updateReference( ObjectId caseId, Map<String, String> params ) throws FormValidationException, NotFoundException, PersistenceException {

        Case caseBook = getCaseBook( caseId );
        
        if ( StringUtils.isNotBlank( params.get( "reference" ) ) ) {
            
            caseBook.setReference( params.get( "reference" ) );
            
        }
        try {

            save( caseBook );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
        
    }
    
    /**
     * 
     * @param caseId
     * @param params transcript
     * @throws FormValidationException
     * @throws NotFoundException
     * @throws PersistenceException
     */
    private void updateTranscript( ObjectId caseId, Map<String, String> params ) throws FormValidationException, NotFoundException, PersistenceException {

        Case caseBook = getCaseBook( caseId );
        
        if ( StringUtils.isNotBlank( params.get( "transcript" ) ) ) {
            
            caseBook.setTranscript( params.get( "transcript" ) );
            
        }
        try {

            save( caseBook );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }
        
    }
    
    private void updateTranscriptMedia( ObjectId caseId, Map<String, String> params) throws FormValidationException, NotFoundException, PersistenceException {
    	
    	//System.out.println("hello in update transcript media");
    	
    	
    	
    	//params.forEach((k,v)-> System.out.println(k+", "+v));
    	
    	Case caseBook = getCaseBook( caseId );
    	
    	//System.out.println("caseBook"+caseBook.toString());
    	
    	
        if ( StringUtils.isNotBlank( params.get( "mediaId" ) ) ) {
            
            String mediaIdText = ( params.get( "mediaId" ) );
            
            long mediaId = Long.parseLong( mediaIdText );
            
            Media media = mediaRepository.findById(mediaId);
            
            List<Media> mediaSet = caseBook.getMediaSet();
            
            if(mediaSet == null)
            {
            	mediaSet = new ArrayList<Media>();
            	//System.out.println( "media Set is null" );
            	
            }
            
            mediaSet.add( media );
            
            caseBook.setMediaSet( mediaSet );
            
            logger.debug( "the media" + media.toString() );
            
        }
        try {

            save( caseBook );
            
        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
            
        }

    	
    }
    
    public CaseProjectionForCourtBook getProjectionForCourtBook( Case caseBook ) {
        
        if ( null == caseBook )
            return null;
        
        return new CaseProjectionForCourtBook( caseBook.getId(), caseBook.getTitle() );
        
        
    }

}
