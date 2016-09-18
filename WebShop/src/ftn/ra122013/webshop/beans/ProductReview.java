package ftn.ra122013.webshop.beans;

import java.util.Date;

public class ProductReview extends Review {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2800846178576753731L;

	private Product product;

	@Override
	public Reviewed getReviewed() {
		return product;
	}

}
