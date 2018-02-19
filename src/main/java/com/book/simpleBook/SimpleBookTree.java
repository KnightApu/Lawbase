package com.book.simpleBook;

import java.util.List;

import org.bson.types.ObjectId;

import com.book.BookNode;
import com.book.BookTree;

public class SimpleBookTree implements BookTree {
    
    BookNode root;

    @Override
    public BookNode getRoot() {

        return root;
        
    }

    public SimpleBookTree( BookNode root ) {
        
        this.root = root;
        
    }

    @Override
    public String toString() {

        return "SimpleBookTree [root=" + root + "]";
    }

    @Override
    public BookNode find( ObjectId id ) {

        return find( root, id );
                
    }
    
    private BookNode find( BookNode currentNode, ObjectId id ) {
        
        if( id.equals( currentNode.getId() ) )
            return currentNode;
        
        BookNode tmp = null;
        
        List<BookNode> children = currentNode.getChildren();
        
        if ( null == children )
            return null;
        
        for ( BookNode child : children ) {

            tmp = find( child, id );
            if( null != tmp )
                return tmp;
            
        }
        
        return null;
        
    }
    
    



}
