webShopApp.service('Dialog', function($mdMedia, $mdDialog, $rootScope){

	var showDialog = function(ev, controller, templateUrl, locals){
		var customFullscreen;
		$rootScope.$watch(function() {
			return $mdMedia('xs') || $mdMedia('sm');
		}, function(wantsFullScreen) {
			customFullscreen = (wantsFullScreen === true);
		});

		return $mdDialog.show({
			controller : controller,
			templateUrl : templateUrl,
			parent : angular.element(document.body),
			targetEvent : ev,
			locals: locals,
			clickOutsideToClose : true,
			fullscreen : ($mdMedia('sm') || $mdMedia('xs')) && customFullscreen
		});
	}

	this.showAddProduct = function(ev){
		return showDialog(ev, productAddController, '/WebShop/partials/productNew.html');
	}

	this.showEditProduct = function(ev){
		return showDialog(ev, productEditController, '/WebShop/partials/productEdit.html');
	}

	this.showAddImage = function (ev){
		return showDialog(ev, productImageController, '/WebShop/partials/productImage.html');
	}

	this.showStoreReviews = function (ev, reviewed){
		return showDialog(ev, reviewsDialogController, '/WebShop/partials/reviewsDialog.html', { reviewed: reviewed, target: 'store'});
	}

	this.showProductReviews = function (ev, reviewed){
		return showDialog(ev, reviewsDialogController, '/WebShop/partials/reviewsDialog.html', { reviewed: reviewed, target: 'product'});
	}
});