package com.lawbase.cases;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class CaseProjectionForCourtBook {

    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId id;

    private String title;

    
    public CaseProjectionForCourtBook( ObjectId id, String title ) {
        super();
        this.id = id;
        this.title = title;
    }


    public ObjectId getId() {
    
        return id;
    }

    
    public void setId( ObjectId id ) {
    
        this.id = id;
    }

    
    public String getTitle() {
    
        return title;
    }

    
    public void setTitle( String title ) {
    
        this.title = title;
    }
    
    
}
