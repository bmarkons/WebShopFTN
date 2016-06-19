package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Product extends Reviewed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8404789795298641087L;
	private Category category;
	private String code;
	private String color;
	private String dimension;
	private String name;
	private String originCountry;
	private ArrayList picture;
	private double priceUnit;
	private String producer;
	private Purchase purchase;
	private double quantity;
	private double rate;
	private String video;
	private double weight;

	/* GETTERS */
	public Category getCategory() {
		return this.category;
	}

	public String getCode() {
		return this.code;
	}

	public String getColor() {
		return this.color;
	}

	public String getDimension() {
		return this.dimension;
	}

	public String getName() {
		return this.name;
	}

	public String getOriginCountry() {
		return this.originCountry;
	}

	public ArrayList getPicture() {
		return this.picture;
	}

	public double getPriceUnit() {
		return this.priceUnit;
	}

	public String getProducer() {
		return this.producer;
	}

	public Purchase getPurchase() {
		return this.purchase;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public double getRate() {
		return this.rate;
	}

	public String getVideo() {
		return this.video;
	}

	public double getWeight() {
		return this.weight;
	}

	/* SETTERS */
	public void setCategory(Category value) {
		this.category = value;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public void setColor(String value) {
		this.color = value;
	}

	public void setDimension(String value) {
		this.dimension = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setOriginCountry(String value) {
		this.originCountry = value;
	}

	public void setPicture(ArrayList value) {
		this.picture = value;
	}

	public void setPriceUnit(double value) {
		this.priceUnit = value;
	}

	public void setProducer(String value) {
		this.producer = value;
	}

	public void setPurchase(Purchase value) {
		this.purchase = value;
	}

	public void setQuantity(double value) {
		this.quantity = value;
	}

	public void setRate(double value) {
		this.rate = value;
	}

	public void setVideo(String value) {
		this.video = value;
	}

	public void setWeight(double value) {
		this.weight = value;
	}

}
