/*
 * Decompiled with CFR 0_114.
 */
package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

public class Category implements Serializable {
	private static final long serialVersionUID = 1335127503884715632L;
	private String description;
	private String name;
	@JsonManagedReference
	private ArrayList<Category> children;
	@JsonBackReference
	private Category parent;

	public Category findCategory(String name) {
		if (this.name.equals(name)) {
			return this;
		}
		Category category = null;
		for (Category c : this.children) {
			category = c.findCategory(name);
			if (category != null)
				break;
		}
		return category;
	}

	public Category(String description, String name) {
		this.description = description;
		this.name = name;
	}

	public Category() {
		super();
	}

	public void addChild(Category child) {
		this.children.add(child);
	}

	public void removeChild(String categoryName) {
		for (Object obj : this.children.toArray()) {
			Category c = (Category) obj;
			if (c.getName().equals(categoryName)){
				children.remove(c);
			}
		}
	}

	public Category getParent() {
		return this.parent;
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<Category> getChildren() {
		return this.children;
	}

	public void setParent(Category value) {
		this.parent = value;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object obj) {
		return ((Category) obj).getName().equals(this.name);
	}
}
