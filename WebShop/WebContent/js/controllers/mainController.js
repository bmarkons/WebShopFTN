webShopApp.controller('MainController', function ($scope, $timeout, $mdSidenav, $log, $rootScope, Auth) {
	$scope.toggleSideNav = function() {
		$mdSidenav('left').toggle();
	};
	$scope.isAdmin = function(){
		//return Auth.isAdmin();
		return true;
	};
	$scope.isBuyer = function(){
		//return Auth.isBuyer();
		return true;
	};
	$scope.isSeller = function(){
		//return Auth.isSeller();
		return true;
	};
});