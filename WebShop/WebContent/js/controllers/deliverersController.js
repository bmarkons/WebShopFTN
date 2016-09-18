webShopApp.controller('DeliverersController', function($scope, $http, Country, Deliverer) {
	$scope.activate = function(){
		Country.getAll($scope);
		$scope.getAllDeliverers();
	}

	$scope.getAllDeliverers = function(){
		$http.get("/WebShop/rest/deliverer/getAll").success( function(response) {
			$scope.deliverers = response;
			angular.forEach($scope.deliverers, function(value,key){
				value.editing = false;
				if(value.tariffs == null){
					value.tariffs = [];
				}
			});
		});
	};
	$scope.removeDeliverer = function(code){
		$http.delete("/WebShop/rest/deliverer/remove/" + code).success(function(response){
			alert(response.msg);
			location.reload();
		});
	}

	$scope.editDeliverer = function(deliverer){
		Deliverer.setEditingDeliverer(deliverer);
		window.location.href = '/WebShop/webShop.jsp#/editDeliverer'
	}

	$scope.activate();
});