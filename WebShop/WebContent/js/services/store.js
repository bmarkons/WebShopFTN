webShopApp.service('Store', function($http, Auth, $mdToast){
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

	this.rate = function(store, rate){
		$http.post("/WebShop/rest/store/rate/" + store.code + "/" + rate, {}).success(function(response){
			if(response.msg == "OK"){
				$mdToast.show(
					$mdToast.simple()
					.textContent('Thanks for rating!')
					.hideDelay(3000)
					);
			}
		});
	}
});
