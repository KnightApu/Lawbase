

var searchIndexingProcessor = {
	
	loadingOverlay : "#loadingOverlay",
	restPaths: {},
	
	rebuild:	function( componentName ){
		
		mApp.loadingOverlayAction().show();
		
		var url = "/search-engine/" + componentName + "/rebuildIndex";
		console.log( "the restpath in indexingRebuild:" + url );
		
		var data = "";
		
		console.log( data );
		
		mApp.callAjax( url, data, "post", this, "done" );
		
		
	},
	
	checkStatus:	function( componentName ){
		
		mApp.loadingOverlayAction().show();
		
		var url = "/search-engine/" + componentName + "/statsIndex";
		console.log( "the restpath in indexingStat:" + url );
		
		var data = "";
		
		console.log( data );
		
		mApp.callAjax( url, data, "post", this, "done" );
		
		
	},
	
	done : function ( response ) {
		

		mApp.loadingOverlayAction().hide();
		
		console.log( response );
		
		if( mApp.isResponseValid( response ) ) {
			
			if( response.message != null )
			{
				mApp.showSuccessModal( response.message );
			}
			else
			{
				mApp.showSuccessModal( JSON.stringify( response ) );
			}
			
			
		} else {
			
			mApp.showErrorModal( user.message );
			
			
		}
		
	}
		
};

console.log( "2 setup restpaths will be called indexing processor" );
//mApp.setUpRestPaths ( user, controllerPaths, "/admin/", "/admin/rest/" );

console.log( "5 Initiated indexing processor" );
//console.log(user);

console.log( "A mysterious thing happens" );

console.log( "document.ready will be called from indexing-index" );

