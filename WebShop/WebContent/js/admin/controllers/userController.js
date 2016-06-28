
adminApp.controller('UserController', function($scope,$http) {
	$http.get("/WebShop/rest/user/getAll").success( function(response) {
		$scope.users = response;
	});
	$scope.userFilter = "";
	$scope.userTypeSelected = "user";
	$scope.userFilterFn = function(item){
		if((item.username.indexOf($scope.userFilter) > -1)){
			if($scope.userTypeSelected == "user"){
				return true;
			}else if(item.type == $scope.userTypeSelected){
				return true;
			}
			return false;
		}
	}
	$scope.removeUser = function(username){
		$http.delete("/WebShop/rest/user/remove/" + username).success(function(response){
			alert(response.msg);
			location.reload();
		});
	}
});