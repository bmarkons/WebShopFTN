purchaseDialogController = function ($scope, $rootScope, Deliverer, $mdDialog, ShoppingCart) {
	$scope.activate = function(){
		Deliverer.getAll($scope);
		var productsInCart = $rootScope.productsInCart;
		var countries = [];
		for (var i = productsInCart.length - 1; i >= 0; i--) {
			countries.push(productsInCart[i].store.country);
		};
		countries = $.unique(countries);
		$scope.groups = [];
		for (var i = countries.length - 1; i >= 0; i--) {
			var group = { country: countries[i], delivererCode: null };
			$scope.groups.push(group);
		};
	}

	$scope.cancel = function(){
		$mdDialog.cancel();
	}

	$scope.purchase = function(){
		ShoppingCart.purchase($scope.groups);
		$mdDialog.cancel();
	}

	$scope.delByCountry = function(country) {
		return function(deliverer) {
			return deliverer.country != country;
		}
	}

	$scope.prodByCountry = function(country) {
		return function(product) {
			return product.store.country != country;
		}
	}

	$scope.activate();
}