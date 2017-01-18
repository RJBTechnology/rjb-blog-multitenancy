package com.rjb.blog.multitenancy.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;

import com.rjb.blog.multitenancy.entity.Product;

@Transactional
public class ProductDaoImpl extends AbstractHibernateDao<Product> implements ProductDao {

	public ProductDaoImpl(Class<Product> entityClass, SessionFactory sessionFactory) {
		super(entityClass, sessionFactory);
	}

	@Override
	public Product getBySku(String sku) {
		return this.getSession().createNamedQuery(this.getEntityName() + ".getBySku", Product.class)
				.setParameter("sku", sku).getSingleResult();
	}

	@Override
	public List<Product> getByCategory(String category) {
		return this.getSession().createNamedQuery(this.getEntityName() + ".getByCategory", Product.class)
				.setParameter("category", category).getResultList();
	}

}
