package com.lawbase.cases;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.book.content.Content;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CaseRefContent implements Content {

    @DBRef(lazy = true)
    @JsonIgnore
    Case caseBook;
    
       
    @JsonProperty( "caseBook" )
    public CaseProjectionForCourtBook getProjectionForCourtBook() {
        
        // TODO bad smell
        if ( null == caseBook )
            return null;
        
        return new CaseProjectionForCourtBook( caseBook.getId(), caseBook.getTitle() );
        
    }
    
    public CaseRefContent() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    public CaseRefContent( Case caseBook ) {
        super();
        this.caseBook = caseBook;
    }


    public Case getCaseBook() {
    
        return caseBook;
    }

    
    public void setCaseBook( Case caseBook ) {
    
        this.caseBook = caseBook;
    }

    @Override
    public String getTitle() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isURL() {
        
        return true;
        
    }
    @Override
    public String getURL() {

        // TODO Auto-generated method stub
        return null;
    }

}
