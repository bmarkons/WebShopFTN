package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonManagedReference;

@XmlRootElement
public class Store extends Reviewed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 638326813827023481L;
	private String address;
	private String code;
	private String country;
	private String email;
	private String name;
	private double rate;
	private String seller;
	private String telephone;
	private ArrayList<ActionSale> actionSales;
	private ArrayList<Product> products = new ArrayList<Product>();

	/* CUSTOM METHODS */
	public void addProduct(Product product) {
		products.add(product);
	}

	public void removeProduct(Product product) {
		products.remove(product);
	}

	// public void addPurchase(Purchase purchase) {
	// // TODO implement this operation
	// throw new UnsupportedOperationException("not implemented");
	// }

	public void addActionSale(ActionSale actionSale) {
		this.actionSales.add(actionSale);
	}

	public void removeActionSale(ActionSale actionSale) {
		this.actionSales.remove(actionSale);
	}

	/* GETTERS */
	public String getAddress() {
		return this.address;
	}

	public String getCode() {
		return this.code;
	}

	public String getCountry() {
		return this.country;
	}

	public String getEmail() {
		return this.email;
	}

	public String getName() {
		return this.name;
	}

	// public ArrayList<Purchase> getPurchase() {
	// return this.purchase;
	// }

	public double getRate() {
		return this.rate;
	}

	public String getSeller() {
		return this.seller;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public ArrayList<ActionSale> getActionSales() {
		return this.actionSales;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	/* SETTERS */
	public void setAddress(String value) {
		this.address = value;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public void setCountry(String value) {
		this.country = value;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setRate(double value) {
		this.rate = value;
	}

	public void setSeller(String sellerUsername) {
		this.seller = sellerUsername;
	}

	public void setTelephone(String value) {
		this.telephone = value;
	}

}
