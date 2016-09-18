webShopApp.controller('PurchasesController', function($scope, Purchase){
	$scope.activate = function(){
		Purchase.getAll($scope);
	}

	$scope.activate();
});