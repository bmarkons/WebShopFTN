package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public abstract class Review implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546190598781751535L;
	private Buyer buyer;
	private String code;
	private String comment;
	private Date date;
	private HashMap<String, Integer> rates = new HashMap<String, Integer>();

	public void rate(int rate, Buyer buyer) {
		rates.put(buyer.getUsername(), rate);
	}
	
	public abstract Reviewed getReviewed();
	
	/* GETTERS */
	public Buyer getBuyer() {
		return this.buyer;
	}

	public String getCode() {
		return this.code;
	}

	public String getComment() {
		return this.comment;
	}

	public Date getDate() {
		return this.date;
	}

	public double getRate() {
		if (rates.size() == 0)
			return 0;
		int sum = 0;
		for (int rate : rates.values()) {
			sum += rate;
		}
		return sum / rates.size();
	}

	
	/* SETTERS */
	public void setComment(String value) {
		this.comment = value;
	}

	public void setDate(Date value) {
		this.date = value;
	}

	public void setCode(String code){
		this.code = code;
	}
	
	public void setBuyer(Buyer buyer){
		this.buyer = buyer;
	}

}
