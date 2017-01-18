package com.rjb.blog.multitenancy.dao;

import java.util.List;

import com.rjb.blog.multitenancy.entity.Product;

public interface ProductDao extends Dao<Product> {

	public Product getBySku(String sku);

	public List<Product> getByCategory(String category);

}
