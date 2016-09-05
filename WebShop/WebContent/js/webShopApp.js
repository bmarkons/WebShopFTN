var webShopApp = angular.module("webShopApp",['ngRoute','ngMaterial','ngMessages', 'material.svgAssetsCache','mdColorPicker','lfNgMdFileInput','jkAngularRatingStars', 'ui.tree']);

webShopApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl: '/WebShop/partials/profile.html',
		controller: 'ProfileController'
	})
	.when('/users', {
		templateUrl: '/WebShop/partials/users.html',
		controller: 'UserController'
	})
	.when('/addUser', {
		templateUrl: '/WebShop/partials/addUser.html',
		controller: 'AddUserController'
	})
	.when('/deliverers', {
		templateUrl: '/WebShop/partials/deliverers.html',
		controller: 'DeliverersController'
	})
	.when('/addDeliverer', {
		templateUrl: '/WebShop/partials/addDeliverer.html',
		controller: 'AddDelivererController',
		resolve: {
			user: function (Auth) {
				return Auth.getUser();
			}
		}
	})
	.when('/categories', {
		templateUrl: '/WebShop/partials/categories.html',
		controller: 'CategoriesController',
		resolve: {
			user: function (Auth) {
				return Auth.getUser();
			}
		}
	})
	.when('/stores', {
		templateUrl: '/WebShop/partials/stores.html',
		controller: 'StoresController',
		resolve: {
			user: function (Auth) {
				return Auth.getUser();
			}
		}
	})
	.when('/products', {
		templateUrl: '/WebShop/partials/products.html',
		controller: 'ProductsController',
		resolve: {
			user: function (Auth) {
				return Auth.getUser();
			}
		}
	})
	.otherwise({
		redirectTo: '/'
	});
}]);

webShopApp.run(['$rootScope', '$location', 'Auth', function ($rootScope, $location, Auth) {
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