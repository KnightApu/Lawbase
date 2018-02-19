package com.lawbase.citation;

import org.bson.types.ObjectId;

import com.book.content.UrlContent;

public class Citation extends UrlContent {

    String type = "";
    ObjectId id = null;
    
    public Citation( String title, String url ) {
        super( title, url );
    }

    public Citation( String title, String url, String type, ObjectId id ) {
        
        super( title, url );
        this.type = type;
        this.id = id;
        
    }

    
    public String getType() {
    
        return type;
    }

    
    public void setType( String type ) {
    
        this.type = type;
    }

    
    public ObjectId getId() {
    
        return id;
    }

    
    public void setId( ObjectId id ) {
    
        this.id = id;
    }
    
    

}
