package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.Date;

public abstract class Review implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2546190598781751535L;
	private Buyer buyer;
	private String code;
	private String comment;
	private Date date;
	private double rate;

	public Review(Buyer buyer, String code, String comment, Date date) {
		this.buyer = buyer;
		this.code = code;
		this.comment = comment;
		this.date = date;
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
		return this.rate;
	}

	
	/* SETTERS */
	public void setComment(String value) {
		this.comment = value;
	}

	public void setDate(Date value) {
		this.date = value;
	}

	public void setRate(double value) {
		this.rate = value;
	}

}
