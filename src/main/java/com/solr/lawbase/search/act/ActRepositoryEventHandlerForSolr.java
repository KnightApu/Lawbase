package com.solr.lawbase.search.act;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.lawbase.act.Act;
import com.lawbase.cases.Case;

import io.reactivex.subjects.Subject;

@Component
@RepositoryEventHandler( Case.class)
public class ActRepositoryEventHandlerForSolr {

    private static final Logger logger = LoggerFactory.getLogger( ActRepositoryEventHandlerForSolr.class );

    @Autowired
    @Qualifier( "actSavedPublisher" )
    Subject<Act> actSavedPublisher;

    @Autowired
    @Qualifier( "actDeletedPublisher" )
    Subject<Act> actDeletedPublisher;
    

    @Autowired
    ActSolrRepository actSolrRepository;
    @Autowired
    ActEntityConverter entityConverter;

    @PostConstruct
    public void attachSubscribers() {

        actSavedPublisher.subscribe( act -> handleAfterSave( act ) );
        actDeletedPublisher.subscribe( act -> handleAfterDelete( act ) );
        
    }

    @HandleAfterSave
    public void handleAfterSave( Act act ) {
        
        try {
            
            SolrAct solrAct = actSolrRepository.save( entityConverter.convert( act ) );
            
            System.out.println( "Solr act added " + solrAct );
            
        } catch ( Exception e ) {
            
            logger.error( e.getMessage() + " id: " + act.getId() );
            
        }
        
    }
    @HandleAfterDelete
    public void handleAfterDelete( Act act ) {
        
        try {
            
            actSolrRepository.deleteById( act.getStringId() );
            System.out.println( "Solr act deleted " + act );
            
            
        } catch ( Exception e ) {
            
            logger.error( e.getMessage() + " id: " + act.getId()  );
            
        }
        
    }
}
