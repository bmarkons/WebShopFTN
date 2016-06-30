var adminApp = angular.module("adminApp",['ngRoute','ngMaterial','ngMessages', 'material.svgAssetsCache']);

//app.service('editCategoryService', function() {
//	  var editing;
//
//	  var setEditing = function(category) {
//	      editing = category;
//	  };
//
//	  var getEditing = function(){
//	      return editing;
//	  };
//
//	  return {
//	    setEditing: setEditing,
//	    getEditing: getEditing
//	  };
//
//	});

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
	.otherwise({
		redirectTo: '/'
	});
}]);

adminApp.controller('MenuController', function($scope) {
	$scope.currentNavItem = 'page1';
});
