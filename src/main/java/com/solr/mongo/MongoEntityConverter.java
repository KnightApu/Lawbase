package com.solr.mongo;

import java.util.ArrayList;
import java.util.List;

import com.solr.core.SolrEntity;

public abstract class MongoEntityConverter<MongoEntity, S extends SolrEntity> {

    abstract public MongoEntity convertToM( S solrEntity );
    
    abstract public S convert( MongoEntity mongoEntity );
    
    public Iterable<MongoEntity> convertToM( List<S> solrEntity ) {
        
        // TODO define it
        return null;
        
    }
    
    @SuppressWarnings( "unchecked" )
    public Iterable<S> convert( List<MongoEntity> mongoEntities ) {

        List<S> solrEntities = new ArrayList<S>();
        
        if ( null == mongoEntities || mongoEntities.isEmpty())
            return ( Iterable<S> ) solrEntities.iterator();
        
        
        mongoEntities.forEach( cB -> solrEntities.add( convert( cB ) ) );
        
        return ( Iterable<S> ) solrEntities;
        
    }

}
