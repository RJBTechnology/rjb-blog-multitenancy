package com.rjb.blog.multitenancy.entity;

public class Product extends AbstractEntity {

	private String category;
	private String sku;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
}
