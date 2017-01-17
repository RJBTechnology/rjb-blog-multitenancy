package com.rjb.blog.multitenancy.dao;

import java.io.Serializable;


/**
 * 
 * A simple CRUD contract.
 * 
 * @param <T>
 */
public interface Dao<T> {

	public T get(Serializable id) throws DaoException ;
	
	public Serializable save(T instance) throws DaoException ;
	
	public T update(T entity) throws DaoException ;
	
	public void delete(T entity) throws DaoException ;
	
	public void saveOrUpdate(T instanceOrEntity) throws DaoException ;

	public void flush() throws DaoException ;

	public void clear() throws DaoException ;

}
