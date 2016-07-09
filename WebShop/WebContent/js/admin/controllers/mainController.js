
adminApp.controller('MainController', function ($scope, $timeout, $mdSidenav, $log, $rootScope) {
	$scope.toggleSideNav = function() {
	    $mdSidenav('left').toggle();
	};
	$scope.isAdmin = function(){
		var user = $rootScope.user;
		if(user != null){
			var type = $rootScope.user.type;
			if(type == 'admin'){
				return true;
			}
		}else{
			return false;
		}
	};
	$scope.isBuyer = function(){
		var user = $rootScope.user;
		if(user != null){
			var type = $rootScope.user.type;
			if(type == 'buyer'){
				return true;
			}
		}else{
			return false;
		}
	};
	$scope.isSeller = function(){
		var user = $rootScope.user;
		if(user != null){
			var type = $rootScope.user.type;
			if(type == 'seller'){
				return true;
			}
		}else{
			return false;
		}
	};
});