package ftn.ra122013.webshop.beans;

import java.util.Date;

public class StoreReview extends Review {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6165248447345250600L;
	private Store store;
	
	public StoreReview(Buyer buyer, String code, String comment, Date date, Store store) {
		super(buyer, code, comment, date);
		this.store = store;
	}

	@Override
	public Reviewed getReviewed() {
		return store;
	}

}
