package com.book.simpleBook;

import com.book.content.Content;

public class TextContent implements Content {

    private String title;
    private String body;
    
    public String getTitle() {
    
        return title;
    }
    
    public void setTitle( String title ) {
    
        this.title = title;
    }
    
    public String getBody() {
    
        return body;
    }
    
    public void setBody( String body ) {
    
        this.body = body;
    }

    public TextContent( String title, String body ) {
        super();
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {

        return "TextContent [title=" + title + ", body=" + body + "]";
    }

    @Override
    public String getURL() {

        return null;
    }
    
    
    

}
