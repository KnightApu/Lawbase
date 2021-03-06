var user = {

	loadingOverlay : "#loadingOverlay",
	restPaths : {},

	print : function() {
		console.log("printing in user js");
	},

	add : function(formData) {

		console.log("inside add function");

		mApp.loadingOverlayAction().show();

		var url = this.restPaths.add;

		console.log("the restpath in user:" + url);

		var data = $(formData).serialize();

		console.log(data);

		mApp.callAjax(url, data, "post", this, "userAdded");

	},

	update : function(formData, userId) {

		mApp.loadingOverlayAction().show();

		var url = this.restPaths.edit + "/" + userId;
		console.log("the restpath in user:" + url);

		var data = $(formData).serialize();

		console.log(data);

		mApp.callAjax(url, data, "post", this, "userProfileUpdate");

	},

	userProfileUpdate : function(user) {
		
		console.log(user);
		location.reload();
		console.log("refreshing");
	},

	userAdded : function(user) {

		console.log("user added e call asche.");
		mApp.loadingOverlayAction().hide();

		console.log(user);
		if (mApp.isResponseValid(user)) {

			mApp.showSuccessModal(user.message);
			mApp.redirect("/registration/pricingTable", 100);

		} else {

			mApp.showErrorModal(user.message);

		}

	}

};

console.log("2 setup restpaths will be called dedm dictionary");
//mApp.setUpRestPaths ( user, controllerPaths, "/admin/", "/admin/rest/" );

console.log("5 Initiated dedm dictionary processor");
console.log(user);

console.log("A mysterious thing happens");

console.log("document.ready will be called from curtbook-add");
