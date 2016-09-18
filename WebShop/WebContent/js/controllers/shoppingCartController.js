webShopApp.controller('ShoppingCartController', function($scope, ShoppingCart, Dialog) {
	$scope.removeFromCart = function(product){
		ShoppingCart.remove(product);
	}

	$scope.goToPurchase = function(ev){
		Dialog.showPurchase(ev);
	}
});