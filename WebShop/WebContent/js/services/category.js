webShopApp.service('Category', function($http){
	this.getAll = function($scope){
		$http.get("/WebShop/rest/category/getAll").success(function(response) {
			$scope.categories = response;
		});
	}
});