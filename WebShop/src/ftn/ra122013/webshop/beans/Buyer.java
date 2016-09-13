package ftn.ra122013.webshop.beans;

import java.util.ArrayList;
import java.util.HashMap;

public class Buyer extends User {

	public Buyer(String username, String password, String address, String country, String email, String name,
			String surname, String telephone) {
		super(username, password, address, country, email, name, surname, telephone);
		setType("buyer");
	}

	private static final long serialVersionUID = -7363816313248923679L;

	//private ArrayList<Purchase> purchases;

	private ArrayList<Product> wishList;

	/* CUSTOM METHODS */

//	public void addPurchase(Purchase purchase) {
//		purchases.add(purchase);
//	}

	public void addToWishList(Product product) {
		this.wishList.add(product);
	}

	public void removeFromWishList(Product product) {
		this.wishList.remove(product);
	}

	/* GETTERS */
//	public ArrayList<Purchase> getPurchases() {
//		return this.purchases;
//	}


	public ArrayList<Product> getWishList() {
		return this.wishList;
	}

}
