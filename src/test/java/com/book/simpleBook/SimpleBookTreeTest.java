package com.book.simpleBook;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.book.BookNode;
import com.book.BookTree;
import com.book.exceptions.NotAParentNodeException;


public class SimpleBookTreeTest {

    
    
    @Test
    public void test() {
        getRoot();
    }
    public BookNode getRoot() {

        SimpleBookNode root = new SimpleBookNode( false, "title" );
        
        SimpleBookNode l1n1 = new SimpleBookNode( true, "title" ); // a leaf node
        l1n1.setContent( new TextContent( "title", "body" ) );
        
        if ( null != root.getContent() )
            fail();
        
        if ( null != l1n1.getChildren() )
            fail();
        

        SimpleBookNode l1n2 = new SimpleBookNode( false, "title" ); // a parent node
        
        SimpleBookNode l1n2l2n1 = new SimpleBookNode( false, "title" ); // a parent node

        SimpleBookNode l1n2l2n2 = new SimpleBookNode( true, "find me" ); // a leaf node
        l1n2l2n2.setContent( new TextContent( "title level 2", "body" ) );
        
        SimpleBookNode l1n2l2n3 = new SimpleBookNode( true, "title" ); // a leaf node
        l1n2l2n3.setContent( new TextContent( "title level 22", "body" ) );
        
        
        try {

            root.addNode( l1n1 );
            root.addNode( l1n2 );
            l1n2.addNode( l1n2l2n1 );
            l1n2.addNode( l1n2l2n2 );
            l1n2.addNode( l1n2l2n3 );

            assertEquals( root.getChildren().size(), 2 );
            assertEquals( l1n2.getChildren().size(), 3 );
            assertEquals( l1n2l2n1.getChildren().size(), 0 );
            
        } catch ( NotAParentNodeException e ) {
            
            fail( "parent threw leaf NotAParentNodeException" );
            
        }

        BookTree bookTree = new SimpleBookTree( root );
        
        BookNode found = bookTree.find( l1n2l2n2.getId() );
        
        assertNotNull( found );
        assertTrue( l1n2l2n2.getTitle().equals( found.getTitle() ) );
        
        System.out.println( found );
        
        assertNull( bookTree.find( new ObjectId() ) );
                
        return root;
    }

}
