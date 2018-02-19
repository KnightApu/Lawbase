package com.book.simpleBook;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.book.content.Content;
import com.fasterxml.jackson.annotation.JsonBackReference;

public class SimpleBookContent implements Content {

    @DBRef
    @JsonBackReference
    private SimpleBook simpleBook;
    
    
    @Override
    public String getTitle() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isURL() {
        
        return false;
        
    }
    
    @Override
    public String getURL() {
        
        return null;
        
    };
}
