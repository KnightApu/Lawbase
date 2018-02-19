package com.book.content;


public class UrlContent implements Content {
    
    String title;
    String URL;

    
    
    public void setTitle( String title ) {
    
        this.title = title;
    }



    
    public void setURL( String uRL ) {
    
        URL = uRL;
    }



    public UrlContent( String title, String url ) {
        super();
        this.title = title;
        this.URL = url;
    }
    
    

    @Override
    public String getTitle() {

        // TODO Auto-generated method stub
        return title;
    }

    @Override
    public String getURL() {

        // TODO Auto-generated method stub
        return URL;
    }

}
