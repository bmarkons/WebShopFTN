purchaseDialogController = function ($scope, $rootScope, Deliverer, $mdDialog, ShoppingCart) {
	$scope.activate = function(){
		$scope.countries = [];
		for (var i = $rootScope.productsInCart.length - 1; i >= 0; i--) {
			index = countries.indexOf($rootScope.productsInCart[i].store.country);
			if( !(index > -1) ){
				countries.push($rootScope.productsInCart[i].store.country);
			}
		}
		$scope.countryGroups = [];
		for (var i = $scope.countries.length - 1; i >= 0; i--) {
			group = { country: $scope.countres[i], products: [], deliverers: [], delivererCode: null};
			
			for (var j = $rootScope.productsInCart.length - 1; j >= 0; j--) {
				if($rootScope.productsInCart[j].store.country == $scope.countries[i]){
					group.products.push($rootScope.productsInCart[j]);
				}
			};

			$scope.countryGroups.push(group);
		};
		Deliverer.getAll($scope);
		for (var i = $scope.deliverers.length - 1; i >= 0; i--) {
			for (var j = $scope.countryGroups.length - 1; j >= 0; j--) {
				if($scope.deliverers[i].countries.indexOf($scope.countryGroups[j].country) > -1){
					$scope.countryGroups[j].deliverers.push($scope.deliverers[i]);
				}
			};
		};
	}

	$scope.cancel = function(){
		$mdDialog.cancel();
	}

	$scope.purchase = function(){
		ShoppingCart.purchase($scope.delivererCode);
		$mdDialog.cancel();
	}

	$scope.activate();
}