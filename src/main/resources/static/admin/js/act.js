/**
 * version 1.0
 * @author Apu
 */


var actProcessor = {
		
	loadingOverlay : "#loadingOverlay",
	restPaths: {},
	
	add: function( title, number) {
		
		
		 console.log( "6. inside actprocessor add function - after pressing submit button this function is called." );
		var data = { "title" : title, "number" : number};
		
		console.log( "6. then this mApp.callAjax function is called which call the server rest api" );
		console.log(this.restPaths.add);
		console.log(data);
		mApp.callAjax( this.restPaths.add, data, "post", this, "actAdded" );
		
	},
	
	updateBasicInformation: function( actTitle, actNumber, actPublishedDate, sources, treatment, status,  actId) {
		var data = {"title": actTitle, "number" : actNumber, "publishedDate" : actPublishedDate, "actDescription": sources, "treatment": treatment, "status":status, "updateType" : "basicInfo" };
		console.log(this.restPaths.edit);
		console.log(data);
		mApp.callAjax( this.restPaths.edit+"/"+actId, data, "post", this, "actBasicInfoUpdated" );
		 
	},
	
	updateSection: function( sections, actId) {
		var data = { "sections" : sections, "updateType" : "sectionInfo"};
		console.log(this.restPaths.edit);
		console.log(data);
		mApp.callAjax( this.restPaths.edit+"/"+actId, data, "post", this, "actSectionUpdated" );
		 
	},
	
	updateMedia: function(formId) {
		mApp.loadingOverlayAction().show();
		
		var form = $( formId )[0]; 

		var data = new FormData(form);
	 
		var otherData=$(formId).serialize();
		
		//alert("in update MEDIA"+data+" HEY "+JSON.stringify(data)+ " HEY2222 "+otherData);
				
		var file = data;

		console.log( data );
		
		mApp.uploadMedia(file,otherData, this, "updateMediaLink"); 
		
	},
	
	
	updateMediaLink:function(data){
		
		mApp.loadingOverlayAction().show();
		
		var url = this.restPaths.edit + "/" + actId;
		
		//alert("in UPDATE LINK"+data+" HEY "+JSON.stringify(data));
		
		console.log( data );
		
		mApp.callAjax( url, data, "post", this, "actAdded" );
		
		
	},
	
	actAdded: function ( act ) {		
		
		console.log( "success call back function paise");
		
		if( undefined != this.loadingOverlay ) {
			
			$( this.loadingOverlay ).css( "display", "none" );
		}
		
		if( mApp.isResponseValid(act) ) {
			
			console.log( "successfully added");
			alert( "Successfully updated! Please refresh to see the change");
			console.log( act );
            $( loadingSelector ).html( "successfully added. redirecting in 1 second." );
            console.log(controllerPaths.edit);
            mApp.redirect( controllerPaths.edit + act.id, 1000 );
           
			
		} else {
			
			mApp.showErrorModal( act.message );
			alert( "failed to add");
			
            $( loadingSelector ).html( "failed to add" );
			
		}
		
	},
	
	
	actBasicInfoUpdated: function (act) {
		
		if( undefined != this.loadingOverlay ) {
			
			$( this.loadingOverlay ).css( "display", "none" );
		}
		
		if( mApp.isResponseValid(act) ) {
			
			 $( loadingSelectorBasic ).fadeIn("1000");
			 $( loadingSelectorBasic ).text( "successfully updated" );
			 $( loadingSelectorBasic ).fadeOut("1000");
			
			 
			
           
			
		} else {
			
			mApp.showErrorModal( act.message );
			
			$( loadingSelectorBasic ).fadeIn("1000");
            $( loadingSelectorBasic ).html( "failed to update" );
            $( loadingSelectorBasic ).fadeOut("1000");
            
			
		}
		
		
		
	},
	
	actSectionUpdated: function (act) {
		
		if( undefined != this.loadingOverlay ) {
			
			$( this.loadingOverlay ).css( "display", "none" );
		}
		
		if( mApp.isResponseValid(act) ) {
			
			
			 $( loadingSelectorSection ).fadeIn("1000");
			 $( loadingSelectorSection ).text( "successfully updated" );
			 $( loadingSelectorSection ).fadeOut("1000");
			
           
			
		} else {
			
			mApp.showErrorModal( act.message );
			
			$( loadingSelectorSection ).fadeIn("1000");
            $( loadingSelectorSection ).html( "failed to update" );
            $( loadingSelectorSection ).fadeOut("1000");
            
			
		}
		
	},
	
	print: function () {
		console.log("app.js paise");
	},
	
	
	
	
}
/**
 * controllerPaths comes from global variable in the page this scripts was added to.
 */


console.log( "2 setup restpaths will be called courtBookProcessor" );
mApp.setUpRestPaths ( actProcessor, controllerPaths, "/admin/", "/admin/rest/" );



console.log( "5 Initiated courtbook processor" );
console.log(actProcessor);

console.log( "A mysterious thing happens" );

console.log( "document.ready will be called from curtbook-add" );