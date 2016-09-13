package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Reviewed implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3315955519261127626L;
	private ArrayList<Review> reviews = new ArrayList<Review>();

	public ArrayList<Review> getReviews() {
		return this.reviews;
	}

	public void addReview(Review review) {
		reviews.add(review);
	}

	public void removeReview(String code) {
		for (Object review : reviews.toArray()) {
			if (((Review) review).getCode().equals(code)) {
				reviews.remove(review);
			}
		}
	}
}
