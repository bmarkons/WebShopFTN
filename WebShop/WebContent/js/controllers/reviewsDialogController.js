reviewsDialogController = function ($scope, $mdDialog, reviewed, target, Review) {
	$scope.activate = function(){
		$scope.reviewed = reviewed;
		$scope.inputVisibility = false;
	}

	$scope.addReview = function(){
		Review.addReview($scope.comment, reviewed, target)
		$scope.inputVisibility = false;
		$scope.comment = "";
	}

	$scope.editReview = function(review){
		Review.editReview(review);
	}

	$scope.deleteReview = function(review){
		Review.deleteReview(review, reviewed);
	}

	$scope.cancel = function() {
		$mdDialog.cancel();
	};

	$scope.activate();
};