package com.solr.lawbase.search.framework;

import org.springframework.data.solr.core.SolrTemplate;

import com.solr.core.BaseSolrRepository;
import com.solr.core.SolrEntity;

public class SimpleBookSolrRepository<T extends SolrEntity> extends BaseSolrRepository<T, String>{

    public SimpleBookSolrRepository( SolrTemplate solrOperations, Class<T> entityClass ) {
        super( solrOperations, entityClass );
        // TODO Auto-generated constructor stub
    }
    
}
