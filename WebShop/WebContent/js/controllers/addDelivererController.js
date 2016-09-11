webShopApp.controller('AddDelivererController', function($scope,$http){
	$http.get("/WebShop/rest/admin/getCountries").success( function(response) {
		$scope.countries = response;
	});
	$scope.addDeliverer = function(){
		var deliverer = {
				name: $scope.name,
				description: $scope.description,
				countries: $scope.selectedCountries,
				tariffs: $scope.tariffs
		};
		$.ajax({
			type: 'PUT',
			url: '/WebShop/rest/deliverer/add',
			data: JSON.stringify(deliverer),
			dataType: 'json',
			contentType: "application/json",
		})
		.done(function(data){
			window.location.href='/WebShop/admin.html#/deliverers';
			if(data.msg == "OK"){
				alert("New deliverer is added.");
			}else{
				alert(data.msg);
			}
		})
		.fail(function(data, status){
			alert(status);
		});
	};
	$scope.removeDeliverer = function(code){
		$.ajax({
			type: 'DELETE',
			url: '/WebShop/rest/deliverer/remove/' + code,
			dataType: 'json',
		})
		.done(function(data){
			if(data.msg == 'OK'){
				alert("Deliverer is successfuly removed.")
			}
		})
		.fail(function(data, status){
			alert(status);
		});
		window.location.href='/WebShop/admin.html#/deliverers';
	};

	$scope.addTariff = function (){
		var tariff = {
				minDimension: $scope.newtariff.minDimension,
				maxDimension: $scope.newtariff.maxDimension,
				minWeight: $scope.newtariff.minWeight,
				maxWeight: $scope.newtariff.maxWeight,
				price: $scope.newtariff.price
		}
		$scope.tariffs.push(tariff);
		$scope.adding = false;
		$scope.newtariff={
				minDimension: '',
				maxDimension: '',
				minWeight: '',
				maxWeight: '',
				price: ''
		}
	}
	$scope.removeTariff = function(tariff){
		index = $scope.tariffs.indexOf(tariff);
		$scope.tariffs.splice(index,1);
	};
	$scope.delSearch = "";
	$scope.name = "";
	$scope.description = "";
	$scope.selectedCountries = [];
	$scope.tariffs = [];
});