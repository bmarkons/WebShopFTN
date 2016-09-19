webShopApp.controller('ProductsController', function($scope, $http, $mdDialog, $mdMedia, $mdToast, $rootScope, Dialog, Product, user, ShoppingCart) {
	//Init;
	Product.getAllForUser($scope);
	
	$scope.showReviews = function(ev, product){
		Dialog.showProductReviews(ev, product);
	}

	$scope.removeImage = function(product, image){
		Product.removeImage(product, image);
	};
	
	$scope.removeProduct = function(product){
		Product.remove(product, $scope);
	};
	
	$scope.addNewProduct = function (ev){
		Dialog.showAddProduct(ev).then(function(msg) {
			$mdToast.show($mdToast.simple().textContent(msg).hideDelay(3000));
			Product.getAll($scope);
		});
	};

	$scope.editProduct = function (ev, product){
		if(!($rootScope.user.type == 'admin') && !($rootScope.user.username == product.store.seller))
			return;
		Product.setEditingProduct(product);
		Dialog.showEditProduct(ev).then(function(msg) {
			$mdToast.show($mdToast.simple().textContent(msg).hideDelay(3000));
			Product.cancelEditingProduct();
			Product.getAll($scope);
		});
	};

	$scope.addImage = function (ev, product){
		Product.setEditingProduct(product);
		Dialog.showAddImage(ev).then(function(msg) {
			$mdToast.show($mdToast.simple().textContent(msg).hideDelay(3000));
			Product.cancelEditingProduct();
			Product.getAll($scope);
		});
	};

	$scope.rateProduct = function (rating, product){
		if(rating == 0)
			return;
		Product.rate(product, rating);
	}

	$scope.addToCart = function (product){
		ShoppingCart.add(product);
	}
});

productAddController = function($scope, $mdDialog, Country, Auth, Product, Category, Store) {
	//Init
	$scope.activate = function(){
		Category.getAll($scope);
		Country.getAll($scope);
		Store.getAllForUser($scope);
	}

	$scope.add = function() {
		Product.add($scope.newproduct, $scope);
	};

	$scope.cancel = function() {
		$mdDialog.cancel();
	};

	$scope.activate();
};

productEditController = function($scope, $mdDialog, $http, $rootScope, Country, Product, Category) {
	$scope.activate = function(){
		$scope.product = angular.fromJson(angular.toJson(Product.getEditingProduct()));
		Category.getAll($scope);
		Country.getAll($scope);
	}

	$scope.hide = function() {
		$mdDialog.hide();
	};

	$scope.cancel = function() {
		$mdDialog.cancel();
	};

	$scope.saveChanges = function() {
		Product.update($scope.product);
	};

	$scope.activate();
};

productImageController = function($scope, $mdDialog, $rootScope, Product) {
	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.add = function() {
		Product.uploadImages($scope.imagefiles, Product.getEditingProduct()).then(function(response) {
			$mdDialog.hide(response.data.msg);
		}, function(err) {
			$mdDialog.hide(err);
		});
	};
};