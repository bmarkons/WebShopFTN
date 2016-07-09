var adminApp = angular.module("adminApp",['ngRoute','ngMaterial','ngMessages', 'material.svgAssetsCache','mdColorPicker','lfNgMdFileInput']);

adminApp.service('Countries', function($http) {
	   
   this.getAll = function() {
	   return  $http.get("/WebShop/rest/admin/getCountries");
   }

});

adminApp.service('Auth', function($rootScope,$http){
	this.getUser = function(){
		return $http.get("/WebShop/rest/user/me");
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
		controller: 'ProductsController'
	})
	.otherwise({
		redirectTo: '/'
	});
}]);

adminApp.run(['$rootScope', '$location', 'Auth', function ($rootScope, $location, Auth) {
    $rootScope.$on('$routeChangeStart', function (event) {
    	Auth.getUser().success( function(response) {
    		if(response == null){
    			location.href = '/WebShop/HomeServlet';
    		}
    		$rootScope.user = response;
    	});
    });
}]);

adminApp.controller('MenuController', function($scope) {
	$scope.currentNavItem = 'page1';
});
