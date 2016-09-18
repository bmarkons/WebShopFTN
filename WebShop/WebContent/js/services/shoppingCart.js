webShopApp.service('ShoppingCart', function($http, $rootScope, $mdToast){
	var ShoppingCart = this;
	$rootScope.productsInCart = [];

	ShoppingCart.add = function(product){
		$rootScope.productsInCart.push(product);
		$mdToast.show(
			$mdToast.simple()
			.textContent('Product ' + product.name + ' added to shopping cart.')
			.hideDelay(3000)
			);
	}

	ShoppingCart.remove = function(product){
		for (var i = $rootScope.productsInCart.length - 1; i >= 0; i--) {
			if($rootScope.productsInCart[i].code == product.code){
				$rootScope.productsInCart.splice(i, 1);
			}
		};
		$mdToast.show(
			$mdToast.simple()
			.textContent('Product ' + product.name + ' removed form shopping cart.')
			.hideDelay(3000)
			);

	}

	ShoppingCart.purchase = function(delivererCode){
		$http.put("/WebShop/rest/purchase/add/" + delivererCode, $rootScope.productsInCart).success(function(response){
			if(response.msg == "OK"){
				$mdToast.show(
					$mdToast.simple()
					.textContent('You made a purchase!')
					.hideDelay(3000)
					);
			}
			window.location.href='/WebShop/webShop.jsp#/purchases';
		});
		$rootScope.productsInCart = [];
	}

	ShoppingCart.check = function(){
		return "Heej dje ti korpa";
	}
});