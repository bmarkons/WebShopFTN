webShopApp.service('Review', function($http, $mdToast){
	var Review = this;

	Review.getAllForReviewed = function(reviewed, target){
		$http.get("/WebShop/rest/review/getAllForReviewed/" + target + "/" + reviewed.code).success(function(response){
			reviewed.reviews = response;
		});
	}

	Review.addReview = function(comment, reviewed, target){
		if(comment == null || comment == "") 
			return;

		$http.put("/WebShop/rest/review/" + target + "/add/" + reviewed.code, {comment: comment}).success(function(response){
			$mdToast.show(
				$mdToast.simple()
				.textContent(response.msg)
				.hideDelay(3000)
				);
			if(response.msg == "OK"){
				reviewed.reviews.push({code: response.code, date: response.date, comment: comment});
			}
		});
	}

	Review.editReview = function(review){
		$http.post("/WebShop/rest/review/update/" + review.code, review.editedComment).success(function(response){
			if(response.msg == "OK"){
				review.comment = review.editedComment;
			}
			delete review["editing"];
			delete review["editedComment"];
			$mdToast.show(
				$mdToast.simple()
				.textContent(response.msg)
				.hideDelay(3000)
				);
		});
	}

	Review.deleteReview = function(review, reviewed){
		$http.delete("/WebShop/rest/review/delete/" + review.code).success(function(response){
			if(response.msg == "OK"){
				for(var i = 0; i < reviewed.reviews.length; i++){
					if(reviewed.reviews[i].code == review.code){
						reviewed.reviews.splice(i,1);
					}
				}
			}
			$mdToast.show(
				$mdToast.simple()
				.textContent(response.msg)
				.hideDelay(3000)
				);
		});
	}

	Review.rate = function(review, rate){
		$http.post("/WebShop/rest/review/rate/" + review.code + "/" + rate, {}).success(function(response){
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