webShopApp.service('Product', function($http, $mdToast, $mdDialog){

	var Product = this;

	Product.editingProduct = null;

	Product.setEditingProduct = function (product){
		Product.editingProduct = product;
	}

	Product.getEditingProduct = function (){
		return Product.editingProduct;
	}

	Product.cancelEditingProduct = function (){
		Product.editingProduct = null;
	}

	Product.getAll = function ($scope){
		$http.get("/WebShop/rest/product/getAll").success(function(response){
			$scope.products = response;
			angular.forEach(response, function(product){
				$http.get("/WebShop/rest/product/getmedia/" + product.code).success(function(media){
					product.images = media.images;
					product.video = media.video;
				})
			});
		});
	}

	Product.getAllForUser = function($scope){
		$http.get("/WebShop/rest/product/getAll").success(function(response){
			if(Auth.isAdmin()){
				$scope.products = response;
			}else if(Auth.isSeller){
				$scope.products = [];
				for(var i = response.length - 1; i >= 0; i--) {
					if(response[i].username == Auth.userInfo.username){
						$scope.products.push(response[i]);
					}
				};
			}
			angular.forEach($scope.products, function(product){
				$http.get("/WebShop/rest/product/getmedia/" + product.code).success(function(media){
					product.images = media.images;
					product.video = media.video;
				})
			});
		});
	}

	Product.add = function(product, $scope){
		$.ajax({
			type : 'PUT',
			url : '/WebShop/rest/product/add/' + product.store,
			data : angular.toJson(product),
			dataType : 'json',
			contentType : 'application/json',
		}).done(function(data) {
			if (data.msg == 'OK' && ($scope.imagefiles.length > 0)) {
				Product.uploadImages($scope.imagefiles, data.code).then(function(response) {
					$mdDialog.hide(response.data.msg + "[images upload]");
				}, function(err) {
					$mdDialog.hide("error [images upload]");
				});

				// Product.setVideo(product.videoUrl, data.code).then(function(response) {
				// 	$mdDialog.hide(response.data.msg + "[video url set]");
				// }, function(err) {
				// 	$mdDialog.hide("error [video url set]");
				// });
	}else{
		$mdDialog.hide(data.msg + " [adding product]");
	}
}).fail(function(data, status) {
	$mdDialog.hide(status + "[adding product]");
});
}

Product.remove = function(product, $scope){
	$http.delete("/WebShop/rest/product/remove/" + product.code).success(function(response){
		$mdToast.show(
			$mdToast.simple()
			.textContent(response.msg)
			.hideDelay(3000)
			);

		if($scope){
			for(var i = 0; i < $scope.products.length; i++){
				if(product.code == $scope.products[i].code){
					$scope.products.splice(i,1);
				}
			}
		}
	});
}

Product.update = function(product){
	delete product["images"];
	delete product["reviews"];
	$.ajax({
		type : 'POST',
		url : '/WebShop/rest/product/update/' + product.store,
		data : angular.toJson(product),
		dataType : 'json',
		contentType : 'application/json',
	}).done(function(data) {
		$mdDialog.hide(data.msg);
	}).fail(function(data, status) {
		$mdDialog.hide(status);
	});
}

Product.uploadImages = function(imageFiles, product){
	var formData = new FormData();
	formData.append('code', product.code);
	angular.forEach(imageFiles, function(imageFile) {
		formData.append('imagefiles', imageFile.lfFile);
	});
	return $http.post('/WebShop/rest/product/uploadImages', formData, {
		transformRequest : angular.identity,
		headers : {
			'Content-Type' : undefined
		}
	});
}
Product.setVideo = function(videoUrl, productCode){
	var formData = new FormData();
	formData.append('code', productCode);
	formData.append('videoUrl', videoUrl);

	return $http.post('/WebShop/rest/product/setVideoUrl', formData, {
		transformRequest : angular.identity,
		headers : {
			'Content-Type' : undefined
		}
	});
}
Product.removeImage = function(product, image){
	$http.delete("/WebShop/rest/product/removeimg/" + product.code + "/" + image.name).success(function(response){
		$mdToast.show(
			$mdToast.simple()
			.textContent(response.msg)
			.hideDelay(3000)
			);
		for(var i = product.images.length - 1; i >= 0; i--) {
			if(product.images[i].name == image.name){
				product.images.splice(i,1);
				return false;
			}
		};
	});
}

Product.rate = function(product, rate){
	$http.post("/WebShop/rest/product/rate/" + product.code + "/" + rate, {}).success(function(response){
		if(response.msg == "OK"){
			$mdToast.show(
				$mdToast.simple()
				.textContent('Thanks for rating!')
				.hideDelay(3000)
				);
		}
	});
}

});