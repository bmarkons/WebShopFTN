package ftn.ra122013.webshop.beans;

import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;

public class Purchase implements Serializable {

	private static final long serialVersionUID = -6321855647657608950L;

	private Buyer buyer;
	private String code;
	private Deliverer deliverer;
	private ArrayList<Product> products;
	private Store store;
	private double totalPrice;
	private String complaint;

	/* GETTERS */
	public Buyer getBuyer() {
		return this.buyer;
	}

	public String getCode() {
		return this.code;
	}

	public Deliverer getDeliverer() {
		return this.deliverer;
	}

	public ArrayList<Product> getProducts() {
		return this.products;
	}

	public Store getStore() {
		return this.store;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public String getComplaint() {
		return complaint;
	}

	/* SETTERS */
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public void setBuyer(Buyer value) {
		this.buyer = value;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public void setDeliverer(Deliverer value) {
		this.deliverer = value;
	}

	public void setStore(Store value) {
		this.store = value;
	}

	public void setTotalPrice(double value) {
		this.totalPrice = value;
	}

	public void setComplaint(String complaint) {
		this.complaint = complaint;
	}
}
