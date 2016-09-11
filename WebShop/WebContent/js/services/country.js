webShopApp.service('Country', function($http) { 
	this.getAll = function($scope) {
		$http.get("/WebShop/rest/admin/getCountries").success(function(response){
			$scope.countries = response;
		});
	}
});