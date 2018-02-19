package com.solr.lawbase.search.cases;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.lawbase.cases.Case;


public class CaseEntityConverterTest {

    CaseEntityConverter converter = new CaseEntityConverter();
    
    @Test
    public void test() {


        Case caseBook = new Case( "asdf", null );
        
        caseBook.setId( new ObjectId() );
        
        SolrCase sCase = converter.convert( caseBook );
        
        System.out.println( sCase );
        
    }

}
