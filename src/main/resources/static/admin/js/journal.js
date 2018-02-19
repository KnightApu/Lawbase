/**
 * version 1.0
 * @author Muktadir
 */

console.log( "1 before journal processor" );
var journalProcessor = {
		
	loadingOverlay : "#loadingOverlay",
	restPaths: {},
	
	add: function( title ) {
		
		
		 console.log( "6. inside courtprocessor add function - after pressing submit button this function is called." );
		var data = { "title" : title };
		console.log( "6. then this mApp.callAjax function is called which call the server rest api" );
		mApp.callAjax( this.restPaths.add, data, "post", this, "journalAdded" );
		
	},
	
	journalAdded: function ( journal ) {
		
		if( undefined != this.loadingOverlay ) {
			
			$( this.loadingOverlay ).css( "display", "none" );
		}
		
		if( mApp.isResponseValid(journal) ) {
			
			console.log( "successfully added");
			console.log( journal );
            $( loadingSelector ).html( "successfully added. redirecting in 1 second." );
            
            mApp.redirect( controllerPaths.edit + journal.id, 1000 );
			
		} else {
			
			mApp.showErrorModal( journal.message );
			
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
mApp.setUpRestPaths ( journalProcessor, controllerPaths, "/admin/", "/admin/rest/" );

console.log( "5 Initiated courtbook processor" );
console.log(journalProcessor);

console.log( "A mysterious thing happens" );

console.log( "document.ready will be called from curtbook-add" );

function ConvertToJournal( book ) {
	
	console.log( "6. into convert to courtbook" );
	
	book.addArticle = function( volumeId, articleName ) {

		mApp.loadingOverlayAction().show();
		console.log("book.addArticle eo call paise");
		
		var url = this.bookProcessor.restPaths.edit + "/" + this.id + "/addArticle";
		var data = {
				
			'parentNodeId' 	: volumeId,
			'title'			: articleName
			
		};
		
		console.log( data );
		
		mApp.callAjax( url, data, "post", this, "articleAdded" );
		
		
	};

	book.articleAdded = function ( article ) {

		mApp.loadingOverlayAction().hide();

		console.log( article );
		if( mApp.isResponseValid( article ) ) {
			
			//this.renderCases();
			alert( 'Article added. please reload ' );
			
		} else {
			
			mApp.showErrorModal( article.message );
			
			
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
	
	book.getArticleFromReference = function( articleRefNode ) {
		
		console.log("get article from ref e call ase?");
		if ( null != articleRefNode.content ) {
			
			console.log("get article from ref e call ase 2?");
			
			return articleRefNode.content.articleBook;
			
		}	
		
		return null;
		
	};

	book.renderArticles = function ( volumeId ){

		mApp.loadingOverlayAction().show();
		
		var volumeNode = this.findNode( volumeId );
		
		console.log( "rendering article references" );
		
		var articleRefNodes = volumeNode.children;
		console.log(articleRefNodes);
		$( this.env.articleList ).empty();
		
		articleRefNodes.forEach( function( articleRefNode ) {
			
			var article = book.getArticleFromReference( articleRefNode );
			if ( null ==  article )
				return;
			
			var editLink = controllerPaths.editArticle + article.id;
			
			console.log(editLink);
			
			var html = "<li><a href='" + editLink + "' class='' data-id='" + article.id + "'>" + articleRefNode.title + "</a></li>";
			$( this.env.articleList ).prepend( html );
			
			
		});

		//$( this.env.volumeList ).parent().prepend( "<h5> Year: " + yearNode.title + "</h5>" );
		
		mApp.loadingOverlayAction().hide();
		
		//this.bindEvents();
	};
	

	/** view manipulators **/

	book.onYearAnchorSelected = function ( anchorObj ) {
		
		console.log("year selector paise");
		var id = $( anchorObj ).data( "id" );
		console.log( id );

		$( this.env.yearList ).find("li").removeClass("selected");
		$( anchorObj ).parent().addClass("selected");
		this.renderVolumes( id );
		
		
		
	}
	
	book.onVolumeAnchorSelected = function ( volObj ) {
		
		console.log("volume selector paise");
		var id = $( volObj ).data( "id" );
		console.log( id );

		$( this.env.volumeList ).find("li").removeClass("selected");
		$( volObj ).parent().addClass("selected");
		this.renderArticles( id );
		
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
		
		$( this.env.articleModal ).on( "shown.bs.modal", function() {
			
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


