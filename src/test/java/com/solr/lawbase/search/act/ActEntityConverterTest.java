package com.solr.lawbase.search.act;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.lawbase.act.Act;


public class ActEntityConverterTest {

    ActEntityConverter converter = new ActEntityConverter();
    
    @Test
    public void test() {


        Act act = new Act( "asdf", null, "3245asdf" );
        
        act.setId( new ObjectId() );

        act.setDescription( "Where is my description" );
        act.setSections( "Sections" );
        
        SolrAct sAct = converter.convert( act );
        
        System.out.println( sAct );
    }

}
