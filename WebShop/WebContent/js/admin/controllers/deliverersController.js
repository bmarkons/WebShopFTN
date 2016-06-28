
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