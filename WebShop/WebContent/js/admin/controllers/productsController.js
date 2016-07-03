
adminApp.controller('ProductsController', function($scope, $http, $mdDialog,$mdMedia, $mdToast, $rootScope) {
	$scope.getAllProducts = function(){
		$http.get("/WebShop/rest/product/getAll").success(function(response) {
			$scope.products = response;
		});
	};
	$scope.remove = function(code){
		$http.delete("/WebShop/rest/product/remove/" + code).success(function(response){
			$mdToast.show(
		    		//var pinTo = $scope.getToastPosition();
		  		      $mdToast.simple()
		  		        .textContent(response.msg)
		  		        //.position(pinTo )
		  		        .hideDelay(3000)
		  		    );
			$scope.getAllProducts();
		});
	};
	$scope.showDialog = function(ev, type, product) {
		var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))
				&& $scope.customFullscreen;
		var dialogController;
		if (type == 'new') {
			dialogController = addProductDialogController;
		} else {
			dialogController = editProductDialogController;
			$rootScope.editingProduct = product;
		}
		$mdDialog.show({
			controller : dialogController,
			templateUrl : '/WebShop/adminPartials/newProductDialog.html',
			parent : angular.element(document.body),
			targetEvent : ev,
			clickOutsideToClose : true,
			fullscreen : useFullScreen
		}).then(function(msg) {
			$mdToast.show(
			// var pinTo = $scope.getToastPosition();
			$mdToast.simple().textContent(msg)
			// .position(pinTo )
			.hideDelay(3000));
			$scope.getAllProducts();
		}, function() {

		});
		$scope.$watch(function() {
			return $mdMedia('xs') || $mdMedia('sm');
		}, function(wantsFullScreen) {
			$scope.customFullscreen = (wantsFullScreen === true);
		});
	};
	$scope.getAllProducts();
});

addProductDialogController = function($scope, $mdDialog, $http, $rootScope, Countries) {
	$scope.confirm = 'Add';
	$scope.disabled = false;
	$http.get("/WebShop/rest/category/getAll").success(function(response) {
		$scope.categories = response;
	});
	Countries.getAll().success( function(response) {
		$scope.countries = response;
	});
	$http.get("/WebShop/rest/store/getAll").success(function(response) {
		$scope.stores = response;
	});
	
	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.add = function() {
		$scope.newproduct.store = $scope.code;
		$.ajax({
			type : 'PUT',
			url : '/WebShop/rest/product/add/' + $scope.code,
			data : angular.toJson($scope.newproduct),
			dataType : 'json',
			contentType : "application/json",
		}).done(function(data) {
			if (data.msg == 'OK' && ($scope.imagefiles.length > 0 || $scope.videofile.length > 0)) {
				var formData = new FormData();
				angular.forEach($scope.imagefiles, function(obj) {
					formData.append('imagefiles', obj.lfFile);
				});
				angular.forEach($scope.videofile, function(obj) {
					formData.append('videofile', obj.lfFile);
				});
				$http.post('/WebShop/rest/product/upload', formData, {
					transformRequest : angular.identity,
					headers : {
						'Content-Type' : undefined
					}
				}).then(function(data) {
					$mdDialog.hide(data.msg + "[upload]");
				}, function(err) {
					$mdDialog.hide("error [upload]");
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