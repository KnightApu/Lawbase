package com.solr.lawbase.search.act;

import org.springframework.stereotype.Component;

import com.book.exceptions.NotSuchAuthorException;
import com.lawbase.act.Act;
import com.solr.mongo.MongoEntityConverter;

@Component
public class ActEntityConverter extends MongoEntityConverter<Act, SolrAct>{

    @Override
    public Act convertToM( SolrAct solrAct ) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SolrAct convert( Act act ) {

        if ( null == act )
            return null;
        
        SolrAct solrAct = new SolrAct();
        

        try {
            
            if ( null != act.getAuthor() )
                solrAct.setAuthorId( act.getAuthor().getId().toHexString() );
            
        } catch ( NotSuchAuthorException e ) {

            e.printStackTrace();
            
        }
        

        solrAct.setTitle( act.getTitle() );
        solrAct.setId( act.getStringId() );
        
        solrAct.setYear( act.getYear() );
        solrAct.setNumber( act.getNumber() );
        
        solrAct.setDescription( act.getDescription() );
        solrAct.setSections( act.getSections() );
        
        return solrAct;

        
    }


}
