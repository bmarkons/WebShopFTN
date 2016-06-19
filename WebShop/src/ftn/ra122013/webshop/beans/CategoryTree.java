package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryTree implements Serializable {

	private static final long serialVersionUID = -1835883394740158719L;

	ArrayList<Category> rootCategories = new ArrayList<Category>();

	public Category findCategory(String name) {
		if (name == null) {
			return null;
		}
		Category category = null;
		for (Category c : rootCategories) {
			category = c.findCategory(name);
			if (category != null) {
				break;
			}
		}
		return category;
	}

	public void add(String parentName, Category newCategory) {
		if (parentName == null) {
			this.rootCategories.add(newCategory);
			return;
		}
		Category parent = findCategory(parentName);
		if (parent != null) {
			parent.addChild(newCategory);
			newCategory.setParent(parent);
		}
	}

	public Category remove(String categoryName) {
		Category category = findCategory(categoryName);
		if (category != null) {
			Category parent = category.getParent();
			if (parent == null) {
				for (Category c : rootCategories) {
					if (c.getName().equals(categoryName)) {
						rootCategories.remove(c);
					}
				}
			} else {
				parent.removeChild(categoryName);
			}
		}
		return category;
	}

	public void changeParent(String oldParent, String newParent, String categoryName) {
		// TODO
	}
}
