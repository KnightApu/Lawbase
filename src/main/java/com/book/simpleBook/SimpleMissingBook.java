package com.book.simpleBook;

import org.bson.types.ObjectId;

import com.book.Author;
import com.lawbase.cases.Case;

public class SimpleMissingBook extends Case {

    public SimpleMissingBook( String title, Author author ) {
        super( title, author );
        // TODO Auto-generated constructor stub
    }
    
    public SimpleMissingBook() {
        
        super( "Not found", null );
        
    }

    @Override
    public ObjectId getId() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getStringId() {

        // TODO Auto-generated method stub
        return "";
    }
    
    
    
    

}
