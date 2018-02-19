package com.book.simpleBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.book.BookNode;
import com.book.content.Content;
import com.book.exceptions.NotALeafNodeException;
import com.book.exceptions.NotAParentNodeException;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class SimpleBookNode implements BookNode {

    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId id;
    
    private ObjectId parentId = null;
    
    private String title = "";
    private boolean leaf = false;
    private List<BookNode> children = Collections.synchronizedList( new ArrayList<BookNode>() );
    private Content content = null;
    
    private Status status = Status.DRAFT;
    private int order = 10000;

    
    
    public ObjectId getId() {
    
        return id;
    }

    @Override
    public String getStringId() {

        return id.toHexString();
        
    }
    
    public void setId( ObjectId id ) {
    
        this.id = id;
    }

    public void setLeaf( boolean leaf ) {
    
        this.leaf = leaf;
    }
    
    public void setChildren( List<BookNode> children ) {
    
        this.children = children;
    }

    public void setContent( Content content ) {
    
        this.content = content;
    }

    
    public SimpleBookNode() {

        this.id = new ObjectId();
        
    }


    public SimpleBookNode( boolean leaf, String title ) {

        this.id = new ObjectId();
        this.title = title;
        this.leaf = leaf;
    }

    @Override
    public String getTitle() {

        return title;
        
    }
    
    public void setTitle( String title ) {
    
        this.title = title;
    }
    @Override
    public boolean isLeaf() {

        return leaf;
        
    }

    @Override
    public List<BookNode> getChildren() {

        if ( isLeaf() )
            return null;
        
        if ( null ==  children )
            createChildrenList();
        
        return children;
        
    }

    private synchronized void createChildrenList() {
        
        if ( null ==  children)
            children =  Collections.synchronizedList( new ArrayList<BookNode>() );
        
    }
    
    @Override
    public Content getContent() {

        if ( ! isLeaf() )
            return null;
        
        return content;
        
    }


    @Override
    public void addNode( BookNode child ) throws NotAParentNodeException {

        child.setParentId( id );
        getChildren().add( child );
        
    }


    @Override
    public void addContent( Content content ) throws NotALeafNodeException {

        if ( ! isLeaf() )
            throw new NotALeafNodeException();
        
        this.content = content;
        
    }

    
    public Status getStatus() {
    
        return status;
    }

    
    public void setStatus( Status status ) {
    
        this.status = status;
    }

    
    public int getOrder() {
    
        return order;
    }

    
    public void setOrder( int order ) {
    
        this.order = order;
    }


    @Override
    public String toString() {

        return "SimpleBookNode [id=" + id + ", title=" + title + ", leaf=" + leaf + ", children=" + children
                + ", content=" + content + ", status=" + status + ", order=" + order + "]";
    }

    @Override
    public ObjectId getParentId() {

        return parentId;
        
    }


    @Override
    public void setParentId( ObjectId parentId ) {
    
        this.parentId = parentId;
    }
    



    
    
    

}
