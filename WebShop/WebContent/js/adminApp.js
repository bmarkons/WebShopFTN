var adminApp = angular.module("adminApp",['ngRoute']);

adminApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl: 'profile.html',
		controller: 'ProfileController'
	})
	.when('/users', {
		templateUrl: 'users.html',
		controller: 'UserController'
	})
	.when('/addUser', {
		templateUrl: 'addUser.html',
		controller: 'AddUserController'
	})
	.when('/deliverers', {
		templateUrl: 'deliverers.html',
		controller: 'DeliverersController'
	})
	.when('/addDeliverer', {
		templateUrl: 'addDeliverer.html',
		controller: 'AddDelivererController'
	})
	.otherwise({
		redirectTo: '/'
	});
}]);


//////////////////////////////
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
	$scope.removeUser = function(username){
		$http.delete("/WebShop/rest/user/remove/" + username).success(function(response){
			alert(response.msg);
			location.reload();
		});
	}
});

//////////////////////////////
adminApp.controller('AddUserController', function($scope,$http) {
	$http.get("/WebShop/rest/admin/getCountries").success( function(response) {
		$scope.countries = response;
	});
	$scope.addUser = function(){
		var user = {
			username: $scope.username,
			password: $scope.password,
			address: $scope.address,
			country: $scope.country,
			email: $scope.email,
			name: $scope.name,
			surname: $scope.surname,
			telephone: $scope.telephone,
			type: "seller"
		};
		register(false,user);
	}
});

//////////////////////////////
adminApp.controller('ProfileController', function($scope,$http) {
	$http.get("/WebShop/rest/admin/getCountries").success( function(response) {
		$scope.countries = response;
	});
	$scope.reset = function(){
		$http.get("/WebShop/rest/user/me").success( function(response) {
			$scope.user = response;
		});
	}
	$scope.updateUser = function(){
		$.ajax({
			type: 'POST',
			url: '/WebShop/rest/user/update',
			data: $scope.user,
			dataType: 'json'
		})
		.done(function(data){
			alert(data.msg);
		})
		.fail(function(data, status){
			alert(status);
		});
	};
	$scope.reset();
});

//////////////////////////////
adminApp.controller('DeliverersController', function($scope,$http) {
	$http.get("/WebShop/rest/deliverer/getAll").success( function(response) {
		$scope.deliverers = response;
	});
	
	$scope.removeDeliverer = function(code){
		$http.delete("/WebShop/rest/deliverer/remove/" + code).success(function(response){
			alert(response.msg);
			location.reload();
		});
	}
});

//////////////////////////////
adminApp.controller('AddDelivererController', function($scope,$http){
	$http.get("/WebShop/rest/admin/getCountries").success( function(response) {
		$scope.countries = response;
	});
	$scope.delSearch = "";
	$scope.addDeliverer = function(){
		var deliverer = {
				name: $scope.name,
				description: $scope.description,
				countries: $scope.selectedCountries
		};
		$.ajax({
			type: 'PUT',
			url: '/WebShop/rest/deliverer/add',
			data: JSON.stringify(deliverer),
			dataType: 'json',
			contentType: "application/json",
		})
		.done(function(data){
			alert(data.msg);
		})
		.fail(function(data, status){
			alert(status);
		});
		window.location.href='/WebShop/admin.html#/deliverers';
	};
	$scope.removeDeliverer = function(code){
		$.ajax({
			type: 'DELETE',
			url: '/WebShop/rest/deliverer/remove/' + code,
			dataType: 'json',
		})
		.done(function(data){
			if(data.msg == 'OK'){
				alert("Deliverer is successfuly removed.")
			}
		})
		.fail(function(data, status){
			alert(status);
		});
		window.location.href='/WebShop/admin.html#/deliverers';
	};
});