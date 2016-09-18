webShopApp.service('Purchase', function($http){
	this.getAll = function($scope){
		$http.get("/WebShop/rest/purchase/getAll").success(function(response) {
			$scope.purchases = response;
		});
	}
});