
adminApp.controller('ProductsController', function($scope, $http, $mdDialog,$mdMedia, $mdToast, $rootScope, Product) {
	//Init
	Product.getAll($scope);
	
	$scope.removeImage = function(product, image){
		Product.removeImage(product, image);
	};
	
	$scope.remove = function(product){
		Product.remove(product, $scope);
	};
	$scope.showDialog = function(ev, type, product) {
		var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))
		&& $scope.customFullscreen;
		var dialogController;
		dialogTemplate = '/WebShop/adminPartials/newProductDialog.html';
		if (type == 'new') {
			dialogController = addProductDialogController;
		} else if(type == 'image'){
			dialogController = addImageDialogController;
			$rootScope.editingProduct = product;
			dialogTemplate = '/WebShop/adminPartials/imageDialog.html'
		}else {
			dialogController = editProductDialogController;
			$rootScope.editingProduct = product;
		}
		$mdDialog.show({
			controller : dialogController,
			templateUrl : dialogTemplate,
			parent : angular.element(document.body),
			targetEvent : ev,
			clickOutsideToClose : true,
			fullscreen : useFullScreen
		}).then(function(msg) {
			$mdToast.show(
				$mdToast.simple().textContent(msg).hideDelay(3000));
			Product.getAll($scope);
		}, function() {

		});
		$scope.$watch(function() {
			return $mdMedia('xs') || $mdMedia('sm');
		}, function(wantsFullScreen) {
			$scope.customFullscreen = (wantsFullScreen === true);
		});
	};
});

addProductDialogController = function($scope, $mdDialog, $http, $rootScope, Countries, Auth, Product, Category) {
	Category.getAll($scope);
	$scope.confirm = 'Add';
	$scope.disabled = false;
	Countries.getAll().success( function(response) {
		$scope.countries = response;
	});
	$http.get("/WebShop/rest/store/getAll").success(function(response) {
		if(Auth.userRole() == "seller"){
			$scope.stores = [];
			angular.forEach(response, function(store){
				if($rootScope.user.username == store.seller){
					$scope.stores.push(store);
				}
			});
		}else{
			$scope.stores = response;
		}
	});
	
	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.add = function() {
		$scope.newproduct.store = $scope.code;
		Product.add($scope.newproduct).done(function(data) {
			if (data.msg == 'OK' && ($scope.imagefiles.length > 0)) {
				Product.uploadImages($imagefiles, data.code).then(function(response) {
					$mdDialog.hide(response.data.msg + "[images upload]");
				}, function(err) {
					$mdDialog.hide("error [images upload]");
				});

				Product.setVideo($scope.videoUrl, data.code).then(function(response) {
					$mdDialog.hide(response.data.msg + "[video url set]");
				}, function(err) {
					$mdDialog.hide("error [video url set]");
				});
			}else{
				$mdDialog.hide(data.msg + " [adding product]");
			}
		}).fail(function(data, status) {
			$mdDialog.hide(status + "[adding product]");
		});
	};
};

editProductDialogController = function($scope, $mdDialog, $http, $rootScope, Countries) {
	$scope.confirm = 'Edit';
	$scope.newproduct = angular.fromJson(angular
		.toJson($rootScope.editingProduct));
	$http.get("/WebShop/rest/category/getAll").success(function(response) {
		$scope.categories = response;
	});
	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.add = function() {
		$.ajax({
			type : 'POST',
			url : '/WebShop/rest/product/update/' + $scope.newproduct.store.code,
			data : angular.toJson($scope.newproduct),
			dataType : 'json',
			contentType : "application/json",
		}).done(function(data) {
			$mdDialog.hide(data.msg);
		}).fail(function(data, status) {
			$mdDialog.hide(status);
		});
	};
};

addImageDialogController = function($scope, $mdDialog, $rootScope, Product) {
	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.add = function() {
		Product.uploadImages($scope.imagefiles, $rootScope.editingProduct.code).then(function(response) {
			$mdDialog.hide(response.data.msg);
		}, function(err) {
			$mdDialog.hide(err);
		});
	};
};