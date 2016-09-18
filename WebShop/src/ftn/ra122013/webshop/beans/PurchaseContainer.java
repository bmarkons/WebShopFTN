package ftn.ra122013.webshop.beans;

import java.util.ArrayList;

public class PurchaseContainer {
	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public ArrayList<String> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<String> countries) {
		this.countries = countries;
	}

	public ArrayList<String> getDelivererCodes() {
		return delivererCodes;
	}

	public void setDelivererCodes(ArrayList<String> delivererCodes) {
		this.delivererCodes = delivererCodes;
	}

	private ArrayList<Product> products;
	private ArrayList<String> countries;
	private ArrayList<String> delivererCodes;

}
