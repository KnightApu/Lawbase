package com.solr.lawbase.search.cases;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;

import com.solr.lawbase.search.framework.SimpleBookSolrRepository;

/***
 * Bean created manually in config file
 * @author muktadir
 *
 */
public class CaseSolrRepository extends SimpleBookSolrRepository<SolrCase>{


    public CaseSolrRepository( @Qualifier( "caseSolrTemplate" ) SolrTemplate caseSolrTemplate ) {
        
        super( caseSolrTemplate, SolrCase.class );
        
    }

    public Page<SolrCase> findByFirstParty( String firstParty, int page, int size ) {
        
        return findByField( "firstParty", firstParty, page, size );
        
    }
    public Page<SolrCase> findBySecondParty( String secondParty, int page, int size ) {
        
        return findByField( "secondParty", secondParty, page, size );
        
    }
    public Page<SolrCase> findByCourtBookTitle( String courtBookTitle, int page, int size ) {
        
        return findByField( "courtBookTitle", courtBookTitle, page, size );
        
    }
    public Page<SolrCase> findBySources( String sources, int page, int size ) {
        
        return findByField( "sources", sources, page, size );
        
    }
    public Page<SolrCase> findByKeywords( String keywords, int page, int size ) {
        
        return findByField( "keywords", keywords, page, size );
        
    }
    public Page<SolrCase> findBySubject( String subject, int page, int size ) {
        
        return findByField( "subject", subject, page, size );
        
    }
    public Page<SolrCase> findBySummary( String summary, int page, int size ) {
        
        return findByField( "summary", summary, page, size );
        
    }
    public Page<SolrCase> findByTranscript( String transcript, int page, int size ) {
        
        return findByField( "transcript", transcript, page, size );
        
    }
    
}
