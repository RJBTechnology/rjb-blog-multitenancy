package com.rjb.blog.multitenancy.dao;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * 
 * A thin wrapper over {@link SessionFactory#getCurrentSession()}.
 *
 * @param <T>
 */
@Transactional
public abstract class AbstractHibernateDao<T> implements Dao<T> {

	private final Class<T> entityClass;
	private SessionFactory sessionFactory;

	public AbstractHibernateDao(Class<T> entityClass, SessionFactory sessionFactory) {
		this.entityClass = entityClass;
		this.sessionFactory = sessionFactory;
	}

	protected Class<? extends T> getEntityClass() {
		return this.entityClass;
	}

	protected String getEntityName() {
		return this.getEntityClass().getName();
	}

	protected Session getSession() throws DaoException {
		try {
			return this.sessionFactory.getCurrentSession();
		} catch (Exception e) {
			throw DaoException.getInstance(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(Serializable id) throws DaoException {
		try {
			return (T) this.getSession().get(this.getEntityName(), id);
		} catch (Exception e) {
			throw DaoException.getInstance(e);
		}
	}

	@Override
	public Serializable save(T instance) throws DaoException {
		try {
			return this.getSession().save(this.getEntityName(), instance);
		} catch (Exception e) {
			throw DaoException.getInstance(e);
		}
	}

	@Override
	public T update(T entity) throws DaoException {
		try {
			this.getSession().update(this.getEntityName(), entity);
			return entity;
		} catch (Exception e) {
			throw DaoException.getInstance(e);
		}
	}

	@Override
	public void delete(T entity) throws DaoException {
		try {
			this.getSession().delete(entity);
		} catch (Exception e) {
			throw DaoException.getInstance(e);
		}
	}

	@Override
	public void saveOrUpdate(T instanceOrEntity) throws DaoException {
		try {
			this.getSession().saveOrUpdate(this.getEntityName(), instanceOrEntity);
		} catch (Exception e) {
			throw DaoException.getInstance(e);
		}
	}

	@Override
	public void flush() throws DaoException {
		try {
			this.getSession().flush();
		} catch (Exception e) {
			throw DaoException.getInstance(e);
		}
	}

	@Override
	public void clear() throws DaoException {
		try {
			this.getSession().clear();
		} catch (Exception e) {
			throw DaoException.getInstance(e);
		}
	}

}
