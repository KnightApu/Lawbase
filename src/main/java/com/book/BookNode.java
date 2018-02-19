package com.book;

import java.util.List;

import org.bson.types.ObjectId;

import com.book.content.Content;
import com.book.exceptions.NotALeafNodeException;
import com.book.exceptions.NotAParentNodeException;

public interface BookNode {
    
    public String getStringId();
    public ObjectId getId();
    public ObjectId getParentId();
    public void setParentId( ObjectId parentId );
    /**
     * If Leaf node better render title of the content
     * @return
     */
    public String getTitle();
    
    public boolean isLeaf();
    
    /**
     * 
     * @return null if not a parent node
     */
    public List<BookNode> getChildren();
    
    public void addNode( BookNode child ) throws NotAParentNodeException;
    
    /**
     * 
     * @return null if no content exists or not a leaf
     */
    public Content getContent();
    public void addContent( Content content ) throws NotALeafNodeException;
    

}
