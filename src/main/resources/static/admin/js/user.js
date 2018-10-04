/**
 * version 1.0
 * @author Farin
 */

var user = {
	
	loadingOverlay : "#loadingOverlay",
	restPaths: {},
	
	print: function() {
		console.log("print hoise.");
	},
	add:	function( formData ){
		
		mApp.loadingOverlayAction().show();
		
		var url = this.restPaths.add;
		console.log( "the restpath in user:" + url );
		
		var data = $( formData ).serialize();
		
		mApp.callAjax( url, data, "post", this, "userAdded" );
		
		
	},
	
	update :	function ( formData ) {
		

		mApp.loadingOverlayAction().show();
		
		var url = this.restPaths.edit + "/" + userId;
		console.log( "the restpath in user:" + url );
		
		var data = $( formData ).serialize();
		
		if( data.indexOf("capabilities") >= 0 )
		{
			var firstOccurance = data.indexOf("&capabilities");
			
			data = data.replace( /&capabilities=/g, "," );
			
			var str1 = data.substring( 0, firstOccurance );
			var str2 = data.substring( firstOccurance, data.length );
						
			data = str1 + str2.replace( ",", "&capabilities=" );
			
		}
		
		mApp.callAjax( url, data, "post", this, "userAdded" );
		
	},
	
	userAdded : function ( user ) {
		

		mApp.loadingOverlayAction().hide();
		
		if( mApp.isResponseValid( user ) ) {

			mApp.showSuccessModal( JSON.stringify( user ) );
			
		} else {
			
			mApp.showErrorModal( JSON.stringify( user ) );
			
			
		}
		
	}
		
};

console.log( "2 setup restpaths will be called dedm dictionary" );
mApp.setUpRestPaths ( user, controllerPaths, "/admin/", "/admin/rest/" );

console.log( "5 Initiated dedm dictionary processor" );
console.log(user);

console.log( "A mysterious thing happens" );

console.log( "document.ready will be called from curtbook-add" );

