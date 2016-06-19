/*
 * Decompiled with CFR 0_114.
 */
package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Category
implements Serializable {
    private static final long serialVersionUID = 1335127503884715632L;
    private String description;
    private String name;
    private ArrayList<Category> children;
    private Category parent;

    public Category findCategory(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        Category category = null;
        for (Category c : this.children) {
            category = c.findCategory(name);
            if (category != null) break;
        }
        return category;
    }

    public Category(String description, String name) {
        this.description = description;
        this.name = name;
    }

    public Category() {
    }

    public void addChild(Category child) {
        this.children.add(child);
    }

    public void removeChild(String categoryName) {
        for (Category c : this.children) {
            if (!c.getName().equals(categoryName)) continue;
            this.children.remove(c);
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

    public boolean equals(Object obj) {
        return ((Category)obj).getName().equals(this.name);
    }
}
