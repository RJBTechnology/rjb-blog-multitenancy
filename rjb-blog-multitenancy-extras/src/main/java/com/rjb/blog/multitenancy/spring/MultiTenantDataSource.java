package com.rjb.blog.multitenancy.spring;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.rjb.blog.multitenancy.context.TenantResolver;

public class MultiTenantDataSource extends AbstractRoutingDataSource {

	private TenantResolver tenantResolver;

	public MultiTenantDataSource(TenantResolver tenantResolver) {
		this.tenantResolver = tenantResolver;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return tenantResolver.getTenantId();
	}
}
