package ftn.ra122013.webshop.beans;

import java.util.ArrayList;

public abstract class Reviewed {
	private ArrayList<Review> reviews = new ArrayList<Review>();

	public ArrayList<Review> getReviews() {
		return this.reviews;
	}

	public void addReview(Review review) {
		reviews.add(review);
	}

	public void removeReview(String code) {
		for (Review review : reviews) {
			if (review.getCode().equals(code)) {
				reviews.remove(review);
			}
		}
	}
}
