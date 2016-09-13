webShopApp.controller('MainController', function ($scope, $timeout, $mdSidenav, $log, $rootScope, Auth) {
	$scope.toggleSideNav = function() {
		$mdSidenav('left').toggle();
	};
	$scope.isAdmin = function(){
		return Auth.isAdmin();
	};
	$scope.isBuyer = function(){
		return Auth.isBuyer();
	};
	$scope.isSeller = function(){
		return Auth.isSeller();
	};
});