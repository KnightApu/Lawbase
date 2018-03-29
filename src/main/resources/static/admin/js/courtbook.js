/**
 * version 1.0
 * @author Muktadir
 */

console.log( "1 before courtBookProcessor" );
var courtBookProcessor = {
		
	loadingOverlay : "#loadingOverlay",
	restPaths: {},
	
	add: function( title ) {
		
		
		 console.log( "6. inside courtprocessor add function - after pressing submit button this function is called." );
		var data = { "title" : title };
		console.log( "6. then this mApp.callAjax function is called which call the server rest api" );
		mApp.callAjax( this.restPaths.add, data, "post", this, "courtAdded" );
		
	},
	
	delete: function(id) {
		console.log(this.restPaths.delete+"/"+id);
		url = this.restPaths.delete+"/"+id;
		mApp.callAjax(url, "", "get", this, "deleted");
		
	},
	
	deleted: function( data ) {
		
		
		if( undefined != this.loadingOverlay ) {
			
			$( this.loadingOverlay ).css( "display", "none" );
		}
		
		if( mApp.isResponseValid( data) ) {
			
			mApp.showSuccessModal("Successfully Deleted");
			
		} else {
			
			mApp.showErrorModal( data );
			
            $( loadingSelector ).html( "failed to delete" );
			
		}
	},
	
	courtAdded: function ( courtBook ) {
		
		if( undefined != this.loadingOverlay ) {
			
			$( this.loadingOverlay ).css( "display", "none" );
		}
		
		if( mApp.isResponseValid(courtBook) ) {
			
			console.log( "successfully added");
			console.log( courtBook );
            $( loadingSelector ).html( "successfully added. redirecting in 1 second." );
            
            mApp.redirect( controllerPaths.edit + courtBook.id, 1000 );
			
		} else {
			
			mApp.showErrorModal( courtBook.message );
			
            $( loadingSelector ).html( "failed to add" );
			
		}
		
	},
	
	print: function () {
		console.log("js paise");
	},
	
	
	
	
}

/**
 * controllerPaths comes from global variable in the page this scripts was added to.
 */
console.log( "2 setup restpaths will be called courtBookProcessor" );
mApp.setUpRestPaths ( courtBookProcessor, controllerPaths, "/admin/", "/admin/rest/" );

console.log( "5 Initiated courtbook processor" );
console.log(courtBookProcessor);

console.log( "A mysterious thing happens" );

console.log( "document.ready will be called from curtbook-add" );

function ConvertToCourtBook( book ) {
	
	console.log( "6. into convert to courtbook" );
	book.addCase = function( volumeId, caseName ) {

		mApp.loadingOverlayAction().show();
		
		var url = this.bookProcessor.restPaths.edit + "/" + this.id + "/addCase";
		var data = {
				
			'parentNodeId' 	: volumeId,
			'title'			: caseName
			
		};
		
		console.log( data );
		
		mApp.callAjax( url, data, "post", this, "caseAdded" );
		
		
	};

	book.caseAdded = function ( caseBook ) {

		mApp.loadingOverlayAction().hide();

		console.log( caseBook );
		if( mApp.isResponseValid( caseBook ) ) {
			
			//this.renderCases();
			alert( 'Case added. please reload ' );
			
		} else {
			
			mApp.showErrorModal( caseBook.message );
			
			
		}
		
	};
	
	book.addYear = function ( year ) {
		
		if ( mApp.isInt( year ) ) {
			
			this.bookProcessor.addNodeWithCB( this.getRootNode().id, false, year, this, "yearAdded" );
			
		} else {
			
			mApp.showErrorModal( year + " not valid" );
			
		}
		
	};

	book.yearAdded = function ( bookNode ) {
		
		mApp.loadingOverlayAction().hide();
		
		console.log( bookNode );
		if( mApp.isResponseValid( bookNode ) ) {
			
			this.getRootNode().children.push( bookNode );
			this.renderYears();
			
		} else {
			
			mApp.showErrorModal( bookNode.message );
			
			
		}
		
	};
	

	
	book.addVolume = function ( yearId, volume ) {

		this.bookProcessor.addNodeWithCB( yearId, false, volume, this, "volumeAdded" );		
		
	};
	
	book.volumeAdded = function ( bookNode ) {
		
		mApp.loadingOverlayAction().hide();
		
		console.log( bookNode );
		if( mApp.isResponseValid( bookNode ) ) {
			
			var parentNode = this.findNode( bookNode.parentId );
			
			console.log( "year node ");
			console.log( parentNode );
			
			if( false === parentNode ) {

				mApp.showErrorModal( "year not found for" + bookNode.title );
				return;
			}
			
			parentNode.children.push( bookNode );
			
			this.renderVolumes( parentNode.id );
			
		} else {
			
			mApp.showErrorModal( bookNode.message );
			
			
		}
		
	};

	/** render methods **/
	book.renderYears = function() {

		mApp.loadingOverlayAction().show();
		
		var yearNodes = this.getRootNode().children;

		$( this.env.yearList ).empty();
		$( this.env.yearSelector ).empty();
		
		yearNodes.forEach( function( yearNode ) {
			
			var html = "<li><a href='javascript:void(0)' class='" + this.env.switchYearList.replace(".", "") + "' data-id='" + yearNode.id + "'>" + yearNode.title + "</a></li>";
			$( this.env.yearList ).prepend( html );
			
			var option = "<option value='" + yearNode.id + "'>" + yearNode.title + "</option>;"
			$( this.env.yearSelector ).prepend( option );
			
		});
		

		mApp.loadingOverlayAction().hide();
		
		//this.bindEvents();
		
	};

	book.renderVolumes = function ( yearId ){

		mApp.loadingOverlayAction().show();
		
		var yearNode = this.findNode( yearId );
		
		console.log( "rendering volumes" );
		console.log( yearNode );
		
		var volumeNodes = yearNode.children;
		
		$( this.env.volumeList ).empty();
		$( this.env.volumeSelector ).empty();
		
		
		volumeNodes.forEach( function( volumeNode ) {
			
			var html = "<li><a href='javascript:void(0)' class='" + this.env.switchVolumeList.replace(".", "") + "' data-id='" + volumeNode.id + "'>" + volumeNode.title + "</a></li>";
			$( this.env.volumeList ).prepend( html );
			
			//console.log( "this.env.volumeSelector: " + this.env.volumeSelector );
			var option = "<option value='" + volumeNode.id + "'>" + yearNode.title + " - " + volumeNode.title + "</option>;"
			$( this.env.volumeSelector ).prepend( option );
			
		});

		//$( this.env.volumeList ).parent().prepend( "<h5> Year: " + yearNode.title + "</h5>" );
		
		mApp.loadingOverlayAction().hide();
		
		//this.bindEvents();
	};
	
	book.getCaseFromReference = function( caseRefNode ) {
		
		if ( null != caseRefNode.content )
			return caseRefNode.content.caseBook;
		
		return null;
		
	};

	book.renderCases = function ( volumeId ){

		mApp.loadingOverlayAction().show();
		
		var volumeNode = this.findNode( volumeId );
		
		console.log( "rendering case references" );
		console.log( volumeNode );
		
		var caseRefNodes = volumeNode.children;
		
		$( this.env.caseList ).empty();
		
		caseRefNodes.forEach( function( caseRefNode ) {
			
			var caseBook = book.getCaseFromReference( caseRefNode );
			console.log(caseBook);
			if ( null ==  caseBook )
				return;
			
			var editLink = controllerPaths.editCase + caseBook.id;
			
			console.log(editLink);
			
			var html = "<li><a href='" + editLink + "' class='' data-id='" + caseBook.id + "'>" + caseRefNode.title + "</a></li>";
			$( this.env.caseList ).prepend( html );
			
			
		});

		//$( this.env.volumeList ).parent().prepend( "<h5> Year: " + yearNode.title + "</h5>" );
		
		mApp.loadingOverlayAction().hide();
		
		//this.bindEvents();
	};
	

	/** view manipulators **/

	book.onYearAnchorSelected = function ( anchorObj ) {
		
		var id = $( anchorObj ).data( "id" );
		console.log( id );

		$( this.env.yearList ).find("li").removeClass("selected");
		$( anchorObj ).parent().addClass("selected");
		this.renderVolumes( id );
		
		
		
	}
	
	book.onVolumeAnchorSelected = function ( volObj ) {

		var id = $( volObj ).data( "id" );
		console.log( id );

		$( this.env.volumeList ).find("li").removeClass("selected");
		$( volObj ).parent().addClass("selected");
		this.renderCases( id );
		
	}

	book.loadFirstYearVolumes = function() {
		
		var years =  this.getRootNode().children;
		
		if ( years.length < 1 )
			return;
		
		
		this.renderVolumes( years[0].id );
		
	};
	

	
	book.bindEvents = function() {
		
		// year switchers
		var holder = this;

		// bind yearList anchors
		$( this.env.yearList ).on( "click", "a", function( event ) {

			event.preventDefault();
			event.stopPropagation();
			//alert( "hi" );
			holder.onYearAnchorSelected( this );
			
		});

		// bind volumeList anchors
		// volume switchers
		$( this.env.volumeList ).on( "click", "a", function( event ) {
			
			console.log( this );
			
			event.preventDefault();
			event.stopPropagation();
			holder.onVolumeAnchorSelected( this );
			
		});
		
		$( this.env.yearSelector ).change( function ( event ) {

			event.preventDefault();
			event.stopPropagation();
			//alert( $(this).val() );
			
			var yearId = $( this ).val();
			var yearObj = $( holder.env.yearList ).find("[data-id='" + yearId + "']");
			
			holder.onYearAnchorSelected( yearObj );
			
		});
		
		$( this.env.caseModal ).on( "shown.bs.modal", function() {
			
			var selectedYear = $( holder.env.yearList ).find(".selected");
			
			if ( undefined == selectedYear )
				return;
			
			var yearObj = $(selectedYear[0]).find('a');
			
			var yearId = $( yearObj ).attr( 'data-id' );
			
			//alert( yearId );
			$( holder.env.yearSelector ).val( yearId );
			
		} );
		
	};
	

	book.loaded = function ( obj ) {

		mApp.loadingOverlayAction().hide();
		
		if( mApp.isResponseValid( obj ) ) {
			
			this.obj = obj;
			
			this.renderYears();
			
			this.loadFirstYearVolumes();
			
			this.bindEvents();
			
			
		} else {
			
			mApp.showErrorModal( obj.message );
						
		}
		
		
	};
	
}
