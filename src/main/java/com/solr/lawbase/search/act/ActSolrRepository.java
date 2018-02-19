package com.solr.lawbase.search.act;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.solr.core.SolrTemplate;

import com.solr.lawbase.search.framework.SimpleBookSolrRepository;

public class ActSolrRepository  extends SimpleBookSolrRepository<SolrAct>{


    public ActSolrRepository( @Qualifier( "actSolrTemplate" ) SolrTemplate actSolrTemplate ) {
        
        super( actSolrTemplate, SolrAct.class );
        
    }

}
