package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonBackReference;

import java.util.HashSet;

public class Product extends Reviewed implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8404789795298641087L;
	private String categoryName;
	private String code;
	private String color;
	private double dimension;
	private String name;
	private String country;
	private ArrayList picture;
	private double unitPrice;
	private String producer;
	private double quantity;
	private double rate;
	private String video;
	private double weight;
	private String store;

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	/* GETTERS */
	public String getCategoryName() {
		return this.categoryName;
	}

	public String getCode() {
		return this.code;
	}

	public String getColor() {
		return this.color;
	}

	public double getDimension() {
		return this.dimension;
	}

	public String getName() {
		return this.name;
	}

	public String getOriginCountry() {
		return this.country;
	}

	public ArrayList getPicture() {
		return this.picture;
	}

	public double getUnitPrice() {
		return this.unitPrice;
	}

	public String getProducer() {
		return this.producer;
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
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public void setColor(String value) {
		this.color = value;
	}

	public void setDimension(double value) {
		this.dimension = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setCountry(String value) {
		this.country = value;
	}

	public void setPicture(ArrayList value) {
		this.picture = value;
	}

	public void setUnitPrice(double value) {
		this.unitPrice = value;
	}

	public void setProducer(String value) {
		this.producer = value;
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
