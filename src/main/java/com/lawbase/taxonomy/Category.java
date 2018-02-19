package com.lawbase.taxonomy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.book.Author;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lawbase.act.Act;
import com.lawbase.cases.Case;

@Document
public class Category {

    @DBRef
    private Author author;
    
    @Id
    private ObjectId id;
    private String name = "";
    
    @DBRef
    @JsonBackReference
    private Category parent = null;
    
    @DBRef
    @JsonManagedReference
    private List<Category> children = Collections.synchronizedList( new ArrayList<Category>() );
    
    private String forEntity = "";
    
    @DBRef
    public List<Case> caseList;
    
    @DBRef
    public List<Act> actList; 
    
    public Category() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Category( String name, Category parent, String forEntity ) {
        
        super();
        this.name = name;
        this.parent = parent;
        this.forEntity = forEntity;
        
    }
    

    
    public Category( Author author, String name, Category parent, String forEntity ) {
        super();
        this.author = author;
        this.name = name;
        this.parent = parent;
        this.forEntity = forEntity;
    }

    public ObjectId getId() {
    
        return id;
    }

    
    public void setId( ObjectId id ) {
    
        this.id = id;
    }
    
    public void generateId() {
        
        this.id = new ObjectId();
        
    }

    
    public String getName() {
    
        return name;
    }

    
    public void setName( String name ) {
    
        this.name = name;
    }

    
    public Category getParent() {
    
        return parent;
    }

    
    public void setParent( Category parent ) {
    
        this.parent = parent;
    }

    
    public String getForEntity() {
    
        return forEntity;
    }

    
    public void setForEntity( String forEntity ) {
    
        this.forEntity = forEntity;
    }

    
    public void setChildren( List<Category> children ) {
    
        this.children = children;
    }

    public List<Category> getChildren()  {

        if ( null ==  children )
            createChildrenList();
        
        return children;
        
    }

    private synchronized void createChildrenList() {
        
        if ( null ==  children)
            children =  Collections.synchronizedList( new ArrayList<Category>() );
        
    }

    public void addChild( Category child ) {

        getChildren().add( child );
        
    }
    
    public void removeChild( Category child ) {

        getChildren().remove( child );
        
    }

    
    public Author getAuthor() {
    
        return author;
    }

    
    public void setAuthor( Author author ) {
    
        this.author = author;
    }
    
    public void setCaseList( List<Case> caseList ) {
    	
    	this.caseList = caseList;
    }
    
    public List<Case> getCaseList() {
    	
    	return caseList;
    	
    }
    
    public void setActList( List<Act> actList ) {
    	
    	this.actList = actList;
    }
    
    public List<Act> getActList() {
    	
    	return actList;
    	
    }

    @Override
    public String toString() {

        return "Category [author=" + author + ", id=" + id + ", name=" + name + ", parent =" + ( (parent == null) ? "null" : parent.getName() ) + ", children="
                + children + ", forEntity=" + forEntity + "]";
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {

        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Category other = ( Category ) obj;
        if ( id == null ) {
            if ( other.id != null )
                return false;
        } else if ( !id.equals( other.id ) )
            return false;
        return true;
    }
    
    
    
    
}
