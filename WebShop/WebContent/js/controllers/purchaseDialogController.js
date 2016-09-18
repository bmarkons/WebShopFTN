purchaseDialogController = function ($scope, Deliverer, $mdDialog, ShoppingCart) {
	$scope.activate = function(){
		Deliverer.getAll($scope);
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