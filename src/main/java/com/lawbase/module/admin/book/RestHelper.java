package com.lawbase.module.admin.book;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.adhocmaster.service.RepositoryService;
import com.book.BookNode;
import com.book.exceptions.NoSuchNodeException;
import com.book.exceptions.NotAParentNodeException;
import com.book.simpleBook.SimpleBook;

import util.restApi.RestInternalServerException;

@Component
public class RestHelper<T extends SimpleBook> {

    /**
     * We need book id to save the tree.
     * @param repositoryService
     * @param id of the book
     * @param params  parentNodeId, leaf, title
     * @return new node
     * @throws RestInternalServerException
     */
    
    public BookNode addNode( RepositoryService<T> repositoryService, ObjectId id, Map<String, String> params ) throws RestInternalServerException {
        

        try{

            T simpleBook = repositoryService.findOne( id );

            if ( null == simpleBook )
                throw new Exception( "court book not found");
            
            BookNode parentNode = simpleBook.findNode( new ObjectId( params.get( "parentNodeId" ) ) );
            
            if ( null == parentNode )
                throw new NoSuchNodeException();
            
            Boolean isLeaf = Boolean.valueOf( params.get( "leaf" ) );
            String title = params.get( "title" );
            
            BookNode node = null;
            
            if ( isLeaf )
                node = simpleBook.addLeaf( parentNode, title );
            else
                node = simpleBook.addBranch( parentNode, title );
            
            repositoryService.save( simpleBook );
            
            return node;
            
        } catch ( NoSuchNodeException e ) {

            throw new RestInternalServerException( new Exception( "parent node not found") );
            
        } catch ( NotAParentNodeException e ) {

            throw new RestInternalServerException( new Exception( "branch is not a parent") );
            
        } catch ( Exception e ) {
            
            throw new RestInternalServerException( e );
            
        }
        
    }

    public BookNode addContent( RepositoryService<T> repositoryService, ObjectId id, Map<String, String> params ) {

        return null;
        
    }
    
    /**
     * 
     * @param repositoryService
     * @param id
     * @param params parentNodeId
     * @return parentNode
     * @throws RestInternalServerException
     */
    
    public BookNode getParentNode( RepositoryService<T> repositoryService, ObjectId id, Map<String, String> params ) throws RestInternalServerException {
        
        try{

            T simpleBook = repositoryService.findOne( id );

            if ( null == simpleBook )
                throw new Exception( "court book not found");
            
            BookNode parent = simpleBook.findNode( new ObjectId( params.get( "parentNodeId" ) ) );
            
            if ( null == parent )
                throw new NoSuchNodeException();
                        
            return parent;
            
        } catch ( NoSuchNodeException e ) {

            throw new RestInternalServerException( new Exception( "parent node not found") );
                        
        } catch ( Exception e ) {
            
            throw new RestInternalServerException( e );
            
        }
        
    }
    
}
