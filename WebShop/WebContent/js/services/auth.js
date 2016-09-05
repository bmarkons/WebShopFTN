webShopApp.service('Auth', function($rootScope, $http){
	this.getUser = function(){
		return $http.get("/WebShop/rest/user/me");
	}
	this.userRole = function(){
		return $rootScope.user.type;
	}
	this.userInfo = function(){
		return $rootScope.user;
	}
	this.isAdmin = function (){
		return $rootScope.user.type == "admin";
	}
	this.isSeller = function ($scope){
		return $rootScope.user.type == "seller";
	}
	this.isBuyer = function($scope){
		return $rootScope.user.type == "buyer";
	}
});