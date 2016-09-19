webShopApp.controller('ProfileController', function($scope, $http, Country) {
	Country.getAll($scope);
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