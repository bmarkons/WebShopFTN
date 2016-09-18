webShopApp.controller('DelivererEditController', function($scope, $http, Country, Deliverer){

	$scope.activate = function(){
		Country.getAll($scope);
		var deliverer = Deliverer.getEditingDeliverer();
		if(deliverer == null){
			window.location.href="/WebShop/webShop.jsp#/deliverers";
		}
		$scope.code = deliverer.code;
		$scope.name = deliverer.name;
		$scope.description = deliverer.description;
		$scope.countries = deliverer.countries;
		$scope.weightRatio = deliverer.weightRatio;
		$scope.dimensionRatio = deliverer.dimensionRatio;
		$scope.minPrice = deliverer.minPrice;
	}

	$scope.addDeliverer = function(){
		var deliverer = {
			code: $scope.code,
			name: $scope.name,
			description: $scope.description,
			countries: $scope.selectedCountries,
			weightRatio: $scope.weightRatio,
			dimensionRatio: $scope.dimensionRatio,
			minPrice: $scope.minPrice
		};
		$.ajax({
			type: 'POST',
			url: '/WebShop/rest/deliverer/update/' + deliverer.code,
			data: JSON.stringify(deliverer),
			dataType: 'json',
			contentType: "application/json",
		})
		.done(function(data){
			if(data.msg == "OK"){
				alert("Deliverer is updated.");
				window.location.href='/WebShop/webShop.jsp#/deliverers';
			}else{
				alert(data.msg);
			}
			Deliverer.cancelEditing();
		})
		.fail(function(data, status){
			alert(status);
		});
	};

	$scope.activate();
});