webShopApp.controller('AddDelivererController', function($scope, $http, Country){

	$scope.activate = function(){
		$scope.delSearch = "";
		Country.getAll($scope);
	}

	$scope.addDeliverer = function(){
		var deliverer = {
			name: $scope.name,
			description: $scope.description,
			countries: $scope.selectedCountries,
			weightRatio: $scope.weightRatio,
			dimensionRatio: $scope.dimensionRatio,
			minPrice: $scope.minPrice
		};
		$.ajax({
			type: 'PUT',
			url: '/WebShop/rest/deliverer/add',
			data: JSON.stringify(deliverer),
			dataType: 'json',
			contentType: "application/json",
		})
		.done(function(data){
			if(data.msg == "OK"){
				alert("New deliverer is added.");
				window.location.href='/WebShop/webShop.jsp#/deliverers';
			}else{
				alert(data.msg);
			}
		})
		.fail(function(data, status){
			alert(status);
		});
	};

	$scope.activate();
});