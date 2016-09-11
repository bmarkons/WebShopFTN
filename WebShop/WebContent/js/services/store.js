webShopApp.service('Store', function($http, Auth){
	this.getAll = function($scope){
		$http.get("/WebShop/rest/store/getAll").success(function(response) {
			$scope.stores = response;
		});
	}

	this.getAllForUser = function($scope){
		$http.get("/WebShop/rest/store/getAll").success(function(response) {
			if(Auth.isSeller()){
				$scope.stores = [];
				angular.forEach(response, function(store){
					if(Auth.userInfo().username == store.seller){
						$scope.stores.push(store);
					}
				});
			}else{
				$scope.stores = response;
			}
		});
	}
});
