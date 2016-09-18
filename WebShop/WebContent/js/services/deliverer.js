webShopApp.service('Deliverer', function($http){
	var Deliverer = this;
	var editing = null;

	Deliverer.setEditingDeliverer = function(deliverer){
		editing = deliverer;
	}

	Deliverer.getEditingDeliverer = function(){
		return editing;
	}

	Deliverer.cancelEditing = function(){
		editing = null;
	}

	Deliverer.getAll = function($scope){
		$http.get("/WebShop/rest/deliverer/getAll").success(function(response){
			$scope.deliverers = response;
		});
	}


});