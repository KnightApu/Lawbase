

var topnav = {
	
	print: function() {
		console.log("topnav js file print hoise");
		},
	
	logout:	function( formData ){
		
	
		
		var params = $( formData ).serialize();
		
		$.ajax({
    		type: "post",
    		url: "/logout",
    		data: params,
    		dataType: 'json',
    		contentType: "application/json; charset=utf-8",
    		traditional: 'true',
    		async: false,
    		success: function (data) {
    			
    			console.log(" logout is successful");
    			
    			
    			
    		},
    		
    		error: function (jqXHR, textStatus, errorThrown) {
    			
    			console.log(jqXHR);
    			console.log("logout is unsuccessful");
    			console.log(textStatus);
    			console.log(errorThrown);
    			window.location.replace("/");
    			
    		}
    	});
		
		
		
		
	},
	
	

}