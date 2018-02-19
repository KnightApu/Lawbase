package com.book.simpleBook.author;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.book.Author;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class SimpleAuthor implements Author, Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;

    public SimpleAuthor( String name ) {

        this.name = name;
        
    }
    

    @Override
    public String getName() {

        return name;
    }
    
    
    public void setName( String name ) {
    
        this.name = name;
    }

    @Override
    public ObjectId getId() {

        return id;
        
    }
    
    public void setId( String hexString ) {

        this.id = new ObjectId( hexString );
        
    }
    
    public String getStringId() {

        return id.toHexString();
        
    }

}
