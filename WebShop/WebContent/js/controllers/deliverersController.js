webShopApp.controller('DeliverersController', function($scope,$http) {
	$scope.getAllDeliverers = function(){
		$http.get("/WebShop/rest/deliverer/getAll").success( function(response) {
			$scope.deliverers = response;
			angular.forEach($scope.deliverers, function(value,key){
				value.editing = false;
				if(value.tariffs == null){
					value.tariffs = [];
				}
			});
		});
	};
	$scope.removeDeliverer = function(code){
		$http.delete("/WebShop/rest/deliverer/remove/" + code).success(function(response){
			alert(response.msg);
			location.reload();
		});
	}
	$scope.saveChanges = function(deliverer){
//		delete deliverer.editing;
//		deliverer = {
//				code: deliverer.code,
//				name: deliverer.name,
//				description: deliverer.description,
//				countries: deliverer.countries
//		};
		$.ajax({
			type: 'POST',
			url: '/WebShop/rest/deliverer/update',
			data: angular.toJson(deliverer.changed),
			dataType: 'json',
			contentType: "application/json",
		})
		.done(function(data){
			if(data.msg == "OK"){
				alert("Deliverer '" + deliverer.code + "' has been updated.")
				$scope.getAllDeliverers();
			}else{
				alert(data.msg);
			}
		})
		.fail(function(data, status){
			alert(status);
		});
	}
$scope.newTariff = function(deliverer){
	var newtariff = {
		minDimension: '',
		maxDimension: '',
		minWeight: '',
		maxWeight: '',
		price: ''
	}
	deliverer.changed.tariffs.push(newtariff);
};
$scope.removeTariff = function(tariff, deliverer){
	var tariffs = deliverer.changed.tariffs;
	angular.forEach(tariffs,function(value,key){
		if(angular.equals(value,tariff)){
			index = tariffs.indexOf(value);
			tariffs.splice(value,1);
		}
	});
};
$http.get("/WebShop/rest/admin/getCountries").success( function(response) {
	$scope.countries = response;
});
$scope.getAllDeliverers();
});