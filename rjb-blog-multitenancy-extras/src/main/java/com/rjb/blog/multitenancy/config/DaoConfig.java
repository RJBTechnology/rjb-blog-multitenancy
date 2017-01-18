package com.rjb.blog.multitenancy.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rjb.blog.multitenancy.dao.ProductDao;
import com.rjb.blog.multitenancy.dao.ProductDaoImpl;
import com.rjb.blog.multitenancy.entity.Product;

@Configuration
public class DaoConfig {

	@Inject
	private HibernateConfig hibernateConfig;

	@Bean
	public ProductDao productDao() {
		return new ProductDaoImpl(Product.class, hibernateConfig.sessionFactory());
	}

}
