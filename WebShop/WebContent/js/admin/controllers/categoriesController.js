
adminApp.controller('CategoriesController', function($scope,$http,$mdDialog,$mdMedia,$mdToast,$rootScope) {
	$scope.getAllCategories = function(){
		$http.get("/WebShop/rest/category/getAll").success( function(response) {
			$scope.categories = response;
		});
	};
	$scope.showDialog = function(ev,type,category) {
	    var useFullScreen = ($mdMedia('sm') || $mdMedia('xs'))  && $scope.customFullscreen;
	    if(type == 'new'){
	    	dialogController = addDialogController;
	    }else{
	    	dialogController = editDialogController;
	    	$rootScope.editingCategory = category;
	    	//editCategoryService.setEditing(category);
	    }
	    $mdDialog.show({
	      controller: dialogController,
	      templateUrl: '/WebShop/adminPartials/newCategoryDialog.html',
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
	    	$scope.getAllCategories();
	    }, function() {
	    
	    });
	    $scope.$watch(function() {
	      return $mdMedia('xs') || $mdMedia('sm');
	    }, function(wantsFullScreen) {
	      $scope.customFullscreen = (wantsFullScreen === true);
	    });
	};
	$scope.remove = function(name){
		$http.delete("/WebShop/rest/category/remove/" + name).success(function(response){
			$mdToast.show(
		    		//var pinTo = $scope.getToastPosition();
		  		      $mdToast.simple()
		  		        .textContent(response.msg)
		  		        //.position(pinTo )
		  		        .hideDelay(3000)
		  		    );
			$scope.getAllCategories();
		});
	};
	$scope.edit = function(category){
		
	};
	$scope.getAllCategories();
});

addDialogController = function($scope, $mdDialog, $http, $rootScope) {
	$scope.confirm = 'Add';
	$scope.disabled = false;
	$http.get("/WebShop/rest/category/getAll").success( function(response) {
		$scope.categories = response;
	});
	$scope.hide = function() {
	    $mdDialog.hide();
	  };
	  $scope.cancel = function() {
	    $mdDialog.cancel();
	  };
	  $scope.add = function() {
		  $scope.newcategory.children = [];
		  $.ajax({
				type: 'PUT',
				url: '/WebShop/rest/category/add',
				data: angular.toJson($scope.newcategory),
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

editDialogController = function($scope, $mdDialog, $http, $rootScope) {
	$scope.confirm = 'Save changes';
	$scope.disabled = true;
	$scope.newcategory = angular.fromJson(angular.toJson($rootScope.editingCategory));
	$http.get("/WebShop/rest/category/getAll").success( function(response) {
		$scope.categories = response;
	});
	$scope.hide = function() {
	    $mdDialog.hide();
	};
	$scope.cancel = function() {
	    $mdDialog.cancel();
	};
	$scope.add = function() {
		  $.ajax({
				type: 'POST',
				url: '/WebShop/rest/category/update',
				data: angular.toJson($scope.newcategory),
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