webShopApp.controller('ProfileController', function($scope,$http) {
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