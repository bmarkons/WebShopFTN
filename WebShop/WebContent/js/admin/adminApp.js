var adminApp = angular.module("adminApp",['ngRoute','ngMaterial','ngMessages', 'material.svgAssetsCache','mdColorPicker','lfNgMdFileInput','jkAngularRatingStars', 'ui.tree']);

adminApp.service('Dialog', function())

adminApp.service('Countries', function($http) { 
	this.getAll = function() {
		return  $http.get("/WebShop/rest/admin/getCountries");
	}
});

adminApp.service('Category', function($http){
	this.getAll = function($scope){
		$http.get("/WebShop/rest/category/getAll").success(function(response) {
			$scope.categories = response;
		});
	}
});

adminApp.service('Product', function($http, $mdToast){
	this.getAll = function ($scope){
		$http.get("/WebShop/rest/product/getAll").success(function(response){
			$scope.products = response;
			angular.forEach(response, function(product){
				$http.get("/WebShop/rest/product/getmedia/" + product.code).success(function(media){
					product.images = media.images;
					product.video = media.video;
				})
			});
		});
	}

	this.getAllForUser = function($scope){
		$http.get("/WebShop/rest/product/getAll").success(function(response){
			if(Auth.isAdmin()){
				$scope.products = response;
			}else if(Auth.isSeller){
				$scope.products = [];
				for(var i = response.length - 1; i >= 0; i--) {
					if(response[i].username == Auth.userInfo.username){
						$scope.products.push(response[i]);
					}
				};
			}
			angular.forEach($scope.products, function(product){
				$http.get("/WebShop/rest/product/getmedia/" + product.code).success(function(media){
					product.images = media.images;
					product.video = media.video;
				})
			});
		});
	}

	this.add = function(product){
		return $.ajax({
			type : 'PUT',
			url : '/WebShop/rest/product/add/' + product.store,
			data : angular.toJson(product),
			dataType : 'json',
			contentType : "application/json",
		});
	}

	this.remove = function(product, $scope){
		$http.delete("/WebShop/rest/product/remove/" + product.code).success(function(response){
			$mdToast.show(
				$mdToast.simple()
				.textContent(response.msg)
				.hideDelay(3000)
				);
			if($scope){
				for(var i = 0; i < $scope.products.length; i++){
					if(product.code == $scope.products[i].code){
						$scope.products.splice(i,1);
					}
				}
			}
		});
	}

	this.uploadImages = function(imageFiles, productCode){
		var formData = new FormData();
		formData.append('code', productCode);
		angular.forEach(imageFiles, function(imageFile) {
			formData.append('imagefiles', imageFile.lfFile);
		});
		return $http.post('/WebShop/rest/product/uploadImages', formData, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		});
	}
	this.setVideo = function(videoUrl, productCode){
		var formData = new FormData();
		formData.append('code', product);
		formData.append('videoUrl', videoUrl);
		
		return $http.post('/WebShop/rest/product/setVideoUrl', formData, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		});
	}
	this.removeImage = function(product, image){
		$http.delete("/WebShop/rest/product/removeimg/" + product.code + "/" + image.name).success(function(response){
			$mdToast.show(
				$mdToast.simple()
				.textContent(response.msg)
				.hideDelay(3000)
				);
			for(var i = product.images.length - 1; i >= 0; i--) {
				if(product.images[i].name == image.name){
					product.images.splice(i,1);
					return false;
				}
			};
		});
	}
});

adminApp.service('Auth', function($rootScope, $rootScope, $http){
	this.getUser = function(){
		return $http.get("/WebShop/rest/user/me");
	}
	this.userRole = function(){
		return $rootScope.user.type;
	}
	this.userInfo = function(){
		return $rootScope.user;
	}
	this.isAdmin = function (){
		return $rootScope.user.type == "admin";
	}
	this.isSeller = function (){
		return $rootScope.user.type == "seller";
	}
	this.isBuyer = function(){
		return $rootScope.user.type == "buyer";
	}
});

adminApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl: '/WebShop/adminPartials/profile.html',
		controller: 'ProfileController'
	})
	.when('/users', {
		templateUrl: '/WebShop/adminPartials/users.html',
		controller: 'UserController'
	})
	.when('/addUser', {
		templateUrl: '/WebShop/adminPartials/addUser.html',
		controller: 'AddUserController'
	})
	.when('/deliverers', {
		templateUrl: '/WebShop/adminPartials/deliverers.html',
		controller: 'DeliverersController'
	})
	.when('/addDeliverer', {
		templateUrl: '/WebShop/adminPartials/addDeliverer.html',
		controller: 'AddDelivererController'
	})
	.when('/categories', {
		templateUrl: '/WebShop/adminPartials/categories.html',
		controller: 'CategoriesController'
	})
	.when('/stores', {
		templateUrl: '/WebShop/adminPartials/stores.html',
		controller: 'StoresController'
	})
	.when('/products', {
		templateUrl: '/WebShop/adminPartials/products.html',
		controller: 'ProductsController',
	})
	.otherwise({
		redirectTo: '/'
	});
}]);

adminApp.run(['$rootScope', '$location', 'Auth', function ($rootScope, $location, Auth) {
	$rootScope.$on('$routeChangeStart', function (event, next, current) {
		Auth.getUser().success( function(response) {
			if(response == null){
				location.href = '/WebShop/HomeServlet';
			}else{
				$rootScope.user = response;
			}
		});
	});
}]);

adminApp.controller('MenuController', function($scope) {
	$scope.currentNavItem = 'page1';
});
