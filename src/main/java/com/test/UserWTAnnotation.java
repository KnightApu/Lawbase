package com.test;

import java.io.Serializable;

import org.bson.types.ObjectId;

public class UserWTAnnotation implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private ObjectId id;
    public UserWTAnnotation( String name, ObjectId id ) {
        super();
        this.name = name;
        this.id = id;
    }
    public UserWTAnnotation() {
        super();
        // TODO Auto-generated constructor stub
    }
    public String getName() {
    
        return name;
    }
    
    public void setName( String name ) {
    
        this.name = name;
    }

    public ObjectId getId() {
    
        return id;
    }
    
    public void setId( ObjectId id ) {
    
        this.id = id;
    }
    @Override
    public String toString() {

        return "UserWTAnnotation [name=" + name + ", id=" + id + "]";
    }
    
    

}
