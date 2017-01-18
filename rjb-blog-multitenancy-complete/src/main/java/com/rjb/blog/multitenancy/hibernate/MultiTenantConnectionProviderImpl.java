package com.rjb.blog.multitenancy.hibernate;

import java.util.HashMap;

import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import com.rjb.blog.multitenancy.spring.MultiTenantDataSource;

public class MultiTenantConnectionProviderImpl extends AbstractMultiTenantConnectionProvider {

	private DatasourceConnectionProviderImpl dataSourceConnectionProvider;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1796618421575501644L;

	public MultiTenantConnectionProviderImpl(MultiTenantDataSource dataSource) {
		this.dataSourceConnectionProvider = new DatasourceConnectionProviderImpl();
		this.dataSourceConnectionProvider.setDataSource(dataSource);
		// this triggers the 'available' flag
		this.dataSourceConnectionProvider.configure(new HashMap<Object, Object>());
	}

	@Override
	protected ConnectionProvider getAnyConnectionProvider() {
		return this.getDatasourceConnectionProvider();
	}

	@Override
	protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
		return this.getDatasourceConnectionProvider();
	}

	protected DatasourceConnectionProviderImpl getDatasourceConnectionProvider() {
		return this.dataSourceConnectionProvider;
	}
}
