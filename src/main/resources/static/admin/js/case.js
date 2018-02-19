function ConvertToCaseBook( book ) {
	
	book.updateMedia = function ( formId ) {
		
		mApp.loadingOverlayAction().show();
		
		var form = $( formId )[0]; 

		var data = new FormData(form);
	
		var otherData=$(formId).serialize();
		
		//alert("in update MEDIA"+data+" HEY "+JSON.stringify(data)+ " HEY2222 "+otherData);
				
		var file = data;

		console.log( data );
		
		mApp.uploadMedia(file,otherData, this, "updateMediaLink");   
	};
	
	
	
	book.updateMediaLink=function(data){
		
		mApp.loadingOverlayAction().show();
		
		var url = this.bookProcessor.restPaths.edit + "/" + this.id;
		
		//alert("in UPDATE LINK"+data+" HEY "+JSON.stringify(data));
		
		console.log( data );
		
		mApp.callAjax( url, data, "post", this, "caseAdded" );
		
		
	};
	
	
	book.update = function ( formId ) {
		

		mApp.loadingOverlayAction().show();
		
		var url = this.bookProcessor.restPaths.edit + "/" + this.id;
		var data = $( formId ).serialize();
		
		//alert("in UPDATE"+data+" HEY "+JSON.stringify(data));
		
		console.log( data );
		
		mApp.callAjax( url, data, "post", this, "caseAdded" );
		
	};
	
	book.caseAdded = function ( caseBook ) {
		

		mApp.loadingOverlayAction().hide();
		
		console.log( caseBook );
		if( mApp.isResponseValid( caseBook ) ) {

			mApp.showSuccessModal( JSON.stringify( caseBook ) );
			//alert("Successfully updated! Please refresh to see the change")
			
		} else {
			
			mApp.showErrorModal( JSON.stringify( caseBook ) );
			
			
		}
		
	};

	book.loaded = function ( obj ) {

		mApp.loadingOverlayAction().hide();
		
		if( mApp.isResponseValid( obj ) ) {
			
			
			
		} else {
			
			mApp.showErrorModal( obj.message );
						
		}
		
		
	};
}