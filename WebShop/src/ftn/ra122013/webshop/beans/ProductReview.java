package ftn.ra122013.webshop.beans;

import java.util.Date;

public class ProductReview extends Review {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2800846178576753731L;

	private Product product;

	public ProductReview(Buyer buyer, String code, String comment, Date date, Product product) {
		super(buyer, code, comment, date);
		this.product = product;
	}

	@Override
	public Reviewed getReviewed() {
		return product;
	}

}
