function BookProcessor ( bookId, loadingSelector ) {

	console.log("inside the bookprocessor");
	this.restPaths = {};
	this.loadingSelector = loadingSelector;
	this.bookId = bookId;
	this.book = {};
	
	this.setBook = function ( book ) {
		
		this.book = book;
	};
	
	this.addNode = function( parentNodeId, leaf, title ) {

		mApp.loadingOverlayAction().show();
		
		var url = this.restPaths.edit + "/" + this.bookId + "/addNode";
		var data = {
				
			'parentNodeId' 	: parentNodeId,
			'leaf'			: leaf,
			'title'			: title
			
		};
		
		console.log( data );
		
		mApp.callAjax( url, data, "post", this, "nodeAdded" );
		
	};

	this.addNodeWithCB = function( parentNodeId, leaf, title, object, callback ) {

		mApp.loadingOverlayAction().show();
		
		var url = this.restPaths.edit + "/" + this.bookId + "/addNode";
		var data = {
				
			'parentNodeId' 	: parentNodeId,
			'leaf'			: leaf,
			'title'			: title
			
		};
		
		console.log( data );
		
		mApp.callAjax( url, data, "post", object, callback );
		
	};
	this.nodeAdded = function( bookNode ) {

		mApp.loadingOverlayAction().hide();
		
		console.log( bookNode );
		if( mApp.isResponseValid(bookNode) ) {
			
			
		} else {
			
			mApp.showErrorModal( bookNode.message );
			
			
		}
		
	} 
	
	this.getBook = function( bookId, obj, callback ) {

		mApp.loadingOverlayAction().show();


		var url = this.restPaths.index + "/" + bookId;
		mApp.callAjax( url, {}, "get", obj, callback );
	
		
	}
	
	this.delayedGetBook  = function( bookId, obj, callback ) {

		mApp.loadingOverlayAction().show();

		var holder = this;
		
        setTimeout( function() {
        	
        	holder.getBook( bookId, obj, callback )
        	
        }, 5000);	
		
	}
	
}

// ends BookTreeProcessor


/** Book class **/

/**
 * env holds the dom environment variables.
 * loads the book from server on initialization.
 * This is a abstract class. extending classes must define "loaded" method.
 */

function Book ( bookId, bookProcessor, env ) {
	
	
	this.bookProcessor = bookProcessor;
	this.id  = bookId;
	this.env = env;
	this.obj = {}; // save the book object here.
	
	this.getRootNode = function() {
		
		return this.obj.rootNode;
		
	};
	
	this.findNodeFromCurrentNode = function ( currentNode, nodeId ) {

		if( currentNode.id == nodeId ) {

			console.log(" matched node ");
			console.log( currentNode );
			return currentNode;
			
		}

        var tmp;
        
        var children = currentNode.children;
        
        if ( null == children || children.length < 1 )
            return false;
        
        
        for( var i = 0; i < children.length; ++i ) {

            tmp = this.findNodeFromCurrentNode( children[i], nodeId );
            if( false !== tmp )
                return tmp;
        	
        }
        return false;
	};

	
	this.findNode = function ( nodeId ) {
		
		var node =  this.findNodeFromCurrentNode( this.getRootNode(), nodeId );
		
//		console.log( "findNode: " );
//		console.log( node );
		
		return node;
		
	};
	
	
	
	/** initializers **/
	this.load = function( bootId ) {
		
		bookProcessor.getBook( bookId, this, 'loaded' );
		
	};
	
	
	
	this.get = function ( key ) {
		
		if ( undefined != obj.key ) {
			
			return object.key;
			
		} else {
			
			mApp.showErrorModal( key + " not found in book" );
			
		}
		
	};
	/** Load the book **/
	var holder = this;
	setTimeout( function() {

		holder.load( holder.id );
		holder.bookProcessor.setBook( holder );
		
	}, 500 );
}