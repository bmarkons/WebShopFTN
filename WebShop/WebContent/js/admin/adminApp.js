var adminApp = angular.module("adminApp",['ngRoute']);

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
	.otherwise({
		redirectTo: '/'
	});
}]);
