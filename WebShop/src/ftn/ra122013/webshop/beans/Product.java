package ftn.ra122013.webshop.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletContext;

import org.codehaus.jackson.annotate.JsonBackReference;

import ftn.ra122013.webshop.dao.WebShopDAO;

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
	private double unitPrice;
	private String producer;
	private double quantity;
	private double rate;
	private String videoUrl;
	private double weight;
	private String store;
	
	private String getPath(ServletContext ctx){
		return ctx.getRealPath("/media/" + code + "/");
	}

	public String generatePath(ServletContext ctx){
		String path = "pic";
		int n = 1;
		while((new File(getPath(ctx),path + n + ".image")).exists()){
			n++;
		}
		return path + n;
	}
	
	public void addImage(InputStream is, ServletContext ctx){
		String path = generatePath(ctx);
		File imageFile = new File(getPath(ctx),path + ".image");
		try {
			FileOutputStream out = new FileOutputStream(imageFile, false);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = is.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean removeImage(String path, ServletContext context){
		File imgFile = new File(getPath(context),path);
		return imgFile.delete();
	}
	
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

	public String getCountry() {
		return this.country;
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

	public String getVideoUrl() {
		return this.videoUrl;
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

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public void setWeight(double value) {
		this.weight = value;
	}

}
