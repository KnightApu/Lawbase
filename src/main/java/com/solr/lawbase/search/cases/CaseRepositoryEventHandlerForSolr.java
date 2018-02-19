package com.solr.lawbase.search.cases;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.lawbase.cases.Case;

import io.reactivex.subjects.Subject;

@Component
@RepositoryEventHandler( Case.class)
public class CaseRepositoryEventHandlerForSolr {

    private static final Logger logger = LoggerFactory.getLogger( CaseRepositoryEventHandlerForSolr.class );

    @Autowired
    @Qualifier( "caseSavedPublisher")
    Subject<Case> caseSavedPublisher;

    @Autowired
    @Qualifier( "caseDeletedPublisher")
    Subject<Case> caseDeletedPublisher;
    
    @Autowired
    CaseSolrRepository caseSolrRepository;
    @Autowired
    CaseEntityConverter entityConverter;
    
    @PostConstruct
    public void attachSubscribers() {

        caseSavedPublisher.subscribe( caseBook -> handleAfterSave( caseBook ) );
        caseDeletedPublisher.subscribe( caseBook -> handleAfterDelete( caseBook ) );
        
    }

    @HandleAfterSave
    public void handleAfterSave( Case caseBook ) {
        
        try {
            
            SolrCase solrCase = caseSolrRepository.save( entityConverter.convert( caseBook ) );
            
            System.out.println( "Solr case added " + solrCase );
            
        } catch ( Exception e ) {
            
            logger.error( e.getMessage() + " id: " + caseBook.getId() );
            
        }
        
    }
    @HandleAfterDelete
    public void handleAfterDelete( Case caseBook ) {
        
        try {
            
            caseSolrRepository.deleteById( caseBook.getStringId() );
            System.out.println( "Solr case deleted " + caseBook );
            
            
        } catch ( Exception e ) {
            
            logger.error( e.getMessage() + " id: " + caseBook.getId()  );
            
        }
        
    }
}
