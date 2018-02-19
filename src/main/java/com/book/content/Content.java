package com.book.content;

public interface Content {
    
    String getTitle();
    
    default String getBody() {
        
        return null;
        
    };
    
    default boolean isURL() {
        
        return false;
        
    }
    
    /*
    default String getURL() throws ContractViolationException {
        
        if ( getBody() == null )
            throw new ContractViolationException( "Both body and url cannot be defined as null" );
        
        return null;
        
    };
    */
    
    String getURL();

}
