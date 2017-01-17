package com.rjb.blog.multitenancy.dao;

public class DaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6505840056161099613L;

	protected DaoException(Throwable t) {
		super(t);
	}
	
	protected DaoException(String message, Throwable t) {
		super(message, t);
	}
	
	public DaoException(String message) {
		super(message);
	}

	public static DaoException getInstance(Throwable t) {
		if (t instanceof DaoException)
			return (DaoException) t;
		return new DaoException(t);
	}
	
	public static DaoException getInstance(String message, Throwable t) {
		if (t instanceof DaoException)
			return (DaoException) t;
		return new DaoException(message, t);
	}
}
