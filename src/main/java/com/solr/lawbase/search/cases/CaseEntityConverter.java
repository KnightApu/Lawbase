package com.solr.lawbase.search.cases;

import org.springframework.stereotype.Component;

import com.book.exceptions.NotSuchAuthorException;
import com.lawbase.cases.Case;
import com.solr.mongo.MongoEntityConverter;

@Component
public class CaseEntityConverter extends MongoEntityConverter<Case, SolrCase>{

    @Override
    public Case convertToM( SolrCase solrEntity ) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SolrCase convert( Case mongoCase ) {

        if ( null == mongoCase )
            return null;
        
        SolrCase solrCase = new SolrCase();
        
        try {
            
            if ( null != mongoCase.getAuthor() )
                solrCase.setAuthorId( mongoCase.getAuthor().getId().toHexString() );
            
        } catch ( NotSuchAuthorException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        solrCase.setTitle( mongoCase.getTitle() );
        solrCase.setId( mongoCase.getStringId() );
        
        solrCase.firstParty = mongoCase.getFirstParty();
        solrCase.secondParty = mongoCase.getSecondParty();
        solrCase.courtBookTitle = mongoCase.getCourtBookTitle();

        solrCase.keywords = mongoCase.getKeywords();
        solrCase.sources = mongoCase.getSources();
        solrCase.subject = mongoCase.getSubject();
        solrCase.summary = mongoCase.getSummary();
        solrCase.transcript = mongoCase.getTranscript();
        solrCase.year = mongoCase.getYear();
        
        return solrCase;
    }

}
