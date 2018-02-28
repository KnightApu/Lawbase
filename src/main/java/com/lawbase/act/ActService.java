package com.lawbase.act;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.adhocmaster.mongo.PersistenceException;
import com.adhocmaster.mongo.user.User;
import com.book.simpleBook.Status;
import com.mongo.media.Media;
import com.mongo.media.MediaRepository;
import com.utility.form.FormValidationException;
import io.reactivex.subjects.Subject;
import javassist.NotFoundException;

@Service
public class ActService {

    private static Logger logger = LoggerFactory.getLogger( ActService.class );

    @Autowired
    private ActRepository actRepository;

    private MediaRepository mediaRepository;

    @Autowired
    @Qualifier( "actSavedPublisher" )
    Subject<Act> actSavedPublisher;

    @Autowired
    @Qualifier( "actDeletedPublisher" )
    Subject<Act> actDeletedPublisher;

    public ActService( ActRepository actRepository, MediaRepository mediaRepository ) {

        this.actRepository = actRepository;
        this.mediaRepository = mediaRepository;

    }

    public void save( Act act ) {

        actRepository.save( act );
        actSavedPublisher.onNext( act );

    }

    public void delete( Act act ) {

        actRepository.delete( act );
        actDeletedPublisher.onNext( act );

    }

    public void delete( ObjectId actId ) throws FormValidationException, NotFoundException {

        Act act = getAct( actId );
        delete( act );

    }

    public Act getAct( ObjectId actId ) throws FormValidationException, NotFoundException {

        if ( null == actId )
            throw new FormValidationException( "act id null" );

        Act act = actRepository.findOne( actId );

        if ( null == act )
            throw new NotFoundException( "act not found" );

        return act;

    }

    public Act createFromMap( User user, Map<String, String> params ) throws PersistenceException {

        String title = params.get( "title" );
        String number = params.get( "number" );

        if ( null == title )
            title = "Add a title";

        Act act = new Act( title, user, number );

        try {

            save( act );

        } catch ( DataAccessException e ) {

            throw new PersistenceException( e.getMessage() );

        }

        logger.debug( act.toString() );
        return act;

    }

    public void updateFromFormData( ObjectId actId, Map<String, String> params ) throws PersistenceException {

        if ( StringUtils.isBlank( params.get( "updateType" ) ) )
            throw new PersistenceException( "no update type set" );

        switch ( params.get( "updateType" ) ) {

            case "basicInfo" :

                try {
                    
                    updateBasicInformation( actId, params );

                } catch ( Exception e ) {

                    throw new PersistenceException( e.getMessage() );

                }

                break;

            case "sectionInfo" :

                try {

                    updateSection( actId, params );

                } catch ( Exception e ) {

                    throw new PersistenceException( e.getMessage() );

                }

                break;

            case "actMedia" :

                try {

                    updateMedia( actId, params );

                } catch ( Exception e ) {

                    throw new PersistenceException( e.getMessage() );

                }

                break;

        }

    }

    private void updateMedia( ObjectId actId, Map<String, String> params )
            throws FormValidationException, NotFoundException, PersistenceException {

        Act act = getAct( actId );

        if ( StringUtils.isNotBlank( params.get( "mediaId" ) ) ) {

            String mediaIdText = ( params.get( "mediaId" ) );

            Long mediaId = Long.parseLong( mediaIdText );

            Media media = mediaRepository.findById( mediaId );

            List<Media> mediaSet = act.getMediaSet();

            if ( mediaSet == null ) {
                
                mediaSet = new ArrayList<Media>();

            }

            mediaSet.add( media );

            act.setMediaSet( mediaSet );


        }

        try {

            save( act );

        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
        }

    }

    private void updateSection( ObjectId actId, Map<String, String> params )
            throws FormValidationException, NotFoundException, PersistenceException {

        Act act = getAct( actId );
        if ( StringUtils.isNotBlank( params.get( "sections" ) ) ) {

            act.setSections( params.get( "sections" ) );

        }

        try {

            save( act );

        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
        }

    }

    public void updateBasicInformation( ObjectId actId, Map<String, String> params )
            throws FormValidationException, NotFoundException, PersistenceException, ParseException {
        // TODO Auto-generated method stub

        Act act = getAct( actId );

        if ( StringUtils.isNotBlank( params.get( "title" ) ) ) {

            act.setTitle( params.get( "title" ) );

        }
        if ( StringUtils.isNotBlank( params.get( "number" ) ) ) {

            act.setNumber( params.get( "number" ) );

        }

        SimpleDateFormat df = new SimpleDateFormat( "dd-mm-yyyy" );
        if ( StringUtils.isNotBlank( params.get( "publishedDate" ) ) ) {

            act.setDatePublished( df.parse( params.get( "publishedDate" ) ) );
            String dateStringFormat = params.get("publishedDate").toString();
            int yearExtractorFromDate = Integer.parseInt(dateStringFormat.substring(6, 10));
            act.setYear(yearExtractorFromDate);
            
            logger.debug( "act year:" + act.getYear() );

        }

        if ( StringUtils.isNotBlank( params.get( "actDescription" ) ) ) {

            act.setDescription( params.get( "actDescription" ) );

        }
        
        if ( StringUtils.isNotBlank( params.get( "searchTerms" ) ) ) {

            act.setSearchTerms(params.get( "searchTerms" ));

        }
        
        if ( StringUtils.isNotBlank( params.get( "subject" ) ) ) {

            act.setSubject(params.get( "subject" ));

        }
        
        if ( StringUtils.isNotBlank( params.get( "status" ) ) ) {

        	Status status = Status.valueOf(  params.get( "status" ) );
            act.setStatus( status );

        }

        if ( StringUtils.isNotBlank( params.get( "treatment" ) ) ) {

            String tempAct = ( params.get( "treatment" ) );

            ActTreatment treatment = ActTreatment.valueOf( tempAct );
            act.setTreatment( treatment );

        }
        if ( StringUtils.isNotBlank( params.get( "status" ) ) ) {
            
        	String tempStatus = params.get( "status" );
        	logger.debug( "Status Act::::" + tempStatus );
        	
        	Status status = Status.valueOf(tempStatus);
        	act.setStatus( status );
            
        }

        

        try {

            save( act );

        } catch ( Exception e ) {

            throw new PersistenceException( e.getMessage() );
        }

    }

}
