webShopApp.controller('StoresController', function($scope,$http,$mdDialog,$mdMedia,$mdToast,$rootScope, Dialog, Store) {
	$scope.activate = function(){
		$scope.getAllStores();
	}

	$scope.showReviews = function(ev, store){
		Dialog.showStoreReviews(ev, store);
	}

	$scope.getAllStores = function(){
		$http.get("/WebShop/rest/store/getAll").success( function(response) {
			if($scope.isSeller()){
				$scope.stores = [];
				angular.forEach(response,function(store){
					if($rootScope.user.username == store.seller){
						$scope.stores.push(store);
					}
				});
			}else{
				$scope.stores = response;
			}
		});
	};
	$scope.showDialog = function(ev,type,store) {
		var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && $scope.customFullscreen;
		var dialogController;
		if(type == 'new'){
			dialogController = addStoreDialogController;
		}else{
			dialogController = editStoreDialogController;
			$rootScope.editingStore = store;
		}
		$mdDialog.show({
			controller: dialogController,
			templateUrl: '/WebShop/partials/newStoreDialog.html',
			parent: angular.element(document.body),
			targetEvent: ev,
			clickOutsideToClose:true,
			fullscreen: useFullScreen
		})
		.then(function(msg) {
			$mdToast.show(
		    		//var pinTo = $scope.getToastPosition();
		    		$mdToast.simple()
		    		.textContent(msg)
		  		        //.position(pinTo )
		  		        .hideDelay(3000)
		  		        );
			$scope.getAllStores();
		}, function() {

		});
		$scope.$watch(function() {
			return $mdMedia('xs') || $mdMedia('sm');
		}, function(wantsFullScreen) {
			$scope.customFullscreen = (wantsFullScreen === true);
		});
	};
	$scope.remove = function(code){
		$http.delete("/WebShop/rest/store/remove/" + code).success(function(response){
			$mdToast.show(
			    		//var pinTo = $scope.getToastPosition();
			    		$mdToast.simple()
			    		.textContent(response.msg)
			  		        //.position(pinTo )
			  		        .hideDelay(3000)
			  		        );
			$scope.getAllStores();
		});
	};

	$scope.rateStore = function(rating, store){
		if(rating == 0)
			return;
		Store.rate(store, rating);
	};
	
	$scope.activate();
});

addStoreDialogController = function($scope, $mdDialog, $http, $rootScope, Country) {
	$scope.getAllSellers = function(){
		$http.get("/WebShop/rest/user/sellers").success( function(response) {
			$scope.sellers = response;
		});
	}
	$scope.getAllSellers();
	$scope.confirm = 'Add';
	$scope.disabled = false;
	Country.getAll($scope);
//		$http.get("/WebShop/rest/store/getAll").success( function(response) {
//			$scope.stores = response;
//		});
$scope.hide = function() {
	$mdDialog.hide();
};
$scope.cancel = function() {
	$mdDialog.cancel();
};
$scope.submit = function() {
	$scope.store.products = [];
	$.ajax({
		type: 'PUT',
		url: '/WebShop/rest/store/add',
		data: angular.toJson($scope.store),
		dataType: 'json',
		contentType: "application/json",
	})
	.done(function(data){
		$mdDialog.hide(data.msg);
	})
	.fail(function(data, status){
		$mdDialog.hide(status);
	});
};
};

editStoreDialogController = function($scope, $mdDialog, $http, $rootScope, Country) {
	$scope.getAllSellers = function(){
		$http.get("/WebShop/rest/user/sellers").success( function(response) {
			$scope.sellers = response;
		});
	}
	$scope.getAllSellers();
	$scope.confirm = 'Edit';
	$scope.disabled = true;
	Country.getAll($scope);
	$scope.store = angular.fromJson(angular.toJson($rootScope.editingStore));

	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.submit = function() {
		$.ajax({
			type: 'POST',
			url: '/WebShop/rest/store/update',
			data: angular.toJson($scope.store),
			dataType: 'json',
			contentType: "application/json",
		})
		.done(function(data){
			$mdDialog.hide(data.msg);
		})
		.fail(function(data, status){
			$mdDialog.hide(status);
		});
	};
};
