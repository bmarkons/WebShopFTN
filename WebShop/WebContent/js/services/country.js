webShopApp.service('Country', function($http) { 
	this.getAll = function($scope) {
		$http.get("https://restcountries.eu/rest/v1/all").success(function(response){
			countries = [];
			for(var i = 0; i < response.length; i++){
				countries.push(response[i].name);
			}
			$scope.countries = countries;
		});
	}
});