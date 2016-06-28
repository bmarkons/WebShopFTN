
adminApp.controller('AddDelivererController', function($scope,$http){
	$http.get("/WebShop/rest/admin/getCountries").success( function(response) {
		$scope.countries = response;
	});
	$scope.delSearch = "";
	$scope.addDeliverer = function(){
		var deliverer = {
				name: $scope.name,
				description: $scope.description,
				countries: $scope.selectedCountries
		};
		$.ajax({
			type: 'PUT',
			url: '/WebShop/rest/deliverer/add',
			data: JSON.stringify(deliverer),
			dataType: 'json',
			contentType: "application/json",
		})
		.done(function(data){
			alert(data.msg);
		})
		.fail(function(data, status){
			alert(status);
		});
		window.location.href='/WebShop/admin.html#/deliverers';
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
});