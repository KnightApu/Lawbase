package com.book.simpleBook;

import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.book.Author;
import com.book.Book;
import com.book.BookNode;
import com.book.BookTree;
import com.book.content.Content;
import com.book.exceptions.NotALeafNodeException;
import com.book.exceptions.NotAParentNodeException;
import com.book.exceptions.NotSuchAuthorException;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import com.lawbase.taxonomy.Category;

public abstract class SimpleBook implements Book {
    
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId id;
    
    @DBRef
    private Author author;
    
    @Field
    private String title;
    private BookTree tree;
    
    private Status status = Status.DRAFT;
    
    /** auditing **/
    
    @Version
    private long version;
    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date dateLastModified;

    @CreatedBy
    @DBRef
    private Author createdBy;
    
    @LastModifiedBy
    @DBRef
    private Author lastModifiedBy;

    
    @DBRef
    private List<Category> categoryList;
    
    @Override
    public String getTitle() {

        return title;

    }

    public void setTitle( String title ) {
    
        this.title = title;
    }
    
    @Override
    public String getURL() {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getStringId() {

        return id.toHexString();
        
    }

    @Override
    public ObjectId getId() {

        return id;
        
    }
    public void setId( ObjectId newId ) {

        id = newId;
        
    }
    public void setId( String newId ) {

        id = new ObjectId( newId );
        
    }
    
    public List<Category> getCategoryList() {
    	
    	return categoryList;
    }
    
    public void setCategoryList(List<Category> categoryList) {
    	
    	this.categoryList = categoryList;
    }

    @Override
    public BookTree getTree() {

        if ( null == tree )
            createTree();
        
        return tree;
        
    }
    
    private synchronized void createTree() {
        
        if ( null ==  tree ) 
            tree = new SimpleBookTree( new SimpleBookNode( false, title ) );
        
    }

    public SimpleBook( String title, Author author ) {

        this.title = title;
        this.author = author;
        createTree();
        
    }

    @Override
    public String toString() {

        return "SimpleBook [id=" + id + ", title=" + title + ", tree=" + tree + "]";
    }

    /**
     * wrapper method to manipulate nodes from book object
     */
    @Override
    public BookNode addBranch( BookNode parent, String title ) throws NotAParentNodeException {

        BookNode node = new SimpleBookNode( false, title );
        parent.addNode( node );
        return node;
        
    }

    /**
     * wrapper method to manipulate nodes from book object
     */
    @Override
    public BookNode addLeaf( BookNode parent, String title ) throws NotAParentNodeException {

        BookNode node = new SimpleBookNode( true, title );
        parent.addNode( node );
        return node;
        
    }
    
    /**
     * wrapper method to manipulate nodes from book object
     */
    @Override
    public void addContent( BookNode leaf, Content content ) throws NotALeafNodeException {

        leaf.addContent( content );
        
    }

    @Override
    public BookNode getRootNode() {

        return getTree().getRoot();

    }
    @Override
    public BookNode findNode( ObjectId id ) {

        return getTree().find( id );

    }


    @Override
    public Author getAuthor() throws NotSuchAuthorException {

        return author;

    }
    
    public void setAuthor( Author author ) {
    
        this.author = author;
    }

    public Status getStatus() {
    
        return status;
    }

    
    public void setStatus( Status status ) {
    
        this.status = status;
    }
    
    

}
