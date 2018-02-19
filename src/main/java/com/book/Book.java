package com.book;

import org.bson.types.ObjectId;

import com.book.content.Content;
import com.book.exceptions.NoSuchNodeException;
import com.book.exceptions.NotALeafNodeException;
import com.book.exceptions.NotAParentNodeException;
import com.book.exceptions.NotSuchAuthorException;

public interface Book extends Content {

    String getStringId();
    ObjectId getId();
    BookTree getTree();
    
    BookNode getRootNode();
    
    Author getAuthor() throws NotSuchAuthorException;
    
    /**
     * Creates a new branch at the specified node. The new node is of parent type
     * @param parent must be a parent node
     * @param title
     * @throws NotAParentNodeException
     */
    BookNode addBranch( BookNode parent, String title ) throws NotAParentNodeException;
    /**
     * 
     * @param parent must be a parent node
     * @param title
     * @throws NotAParentNodeException
     */
    BookNode addLeaf( BookNode parent, String title ) throws NotAParentNodeException;
    
    /**
     * 
     * @param leaf a leaf node
     * @param content
     * @throws NotALeafNodeException
     */
    void addContent( BookNode leaf, Content content ) throws NotALeafNodeException;
    
    BookNode findNode( ObjectId id ) throws NoSuchNodeException;
    

}
