

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