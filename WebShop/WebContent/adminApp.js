var adminApp = angular.module("adminApp",['ngRoute']);
adminApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl: 'adminHome.html',
		controller: 'UserController'
	})
	.when('/users', {
		templateUrl: 'users.html',
		controller: 'UserController'
	})
	.when('/addUser', {
		templateUrl: 'addUser.html',
		controller: 'AddUserController'
	})
	.otherwise({
		redirectTo: '/'
	});
}]);

adminApp.controller('UserController', function($scope,$http) {
	$http.get("/WebShop/rest/user/getAll").success( function(response) {
		$scope.users = response;
	});
	$scope.userFilter = "";
	$scope.userTypeSelected = "user";
	$scope.userFilterFn = function(item){
		if((item.username.indexOf($scope.userFilter) > -1)){
			if($scope.userTypeSelected == "user"){
				return true;
			}else if(item.type == $scope.userTypeSelected){
				return true;
			}
			return false;
		}
	}
});

adminApp.controller('AddUserController', function($scope) {
	//$scope.message = "This page will be used to display all the students";
});

adminApp.controller('LoginController',function($scope){

});