package com.rjb.blog.multitenancy.hibernate;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;

import com.rjb.blog.multitenancy.context.TenantResolver;
import com.rjb.blog.multitenancy.dao.DataSegmentationException;
import com.rjb.blog.multitenancy.util.ArrayUtil;
import com.rjb.blog.multitenancy.util.StringUtil;

public class MultiTenantEntityInterceptor extends EmptyInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2372143420877673397L;

	private TenantResolver tenantResolver;

	public MultiTenantEntityInterceptor(TenantResolver tenantResolver) {
		this.tenantResolver = tenantResolver;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, org.hibernate.type.Type[] types) {
		return this.handleTenant(entity, id, currentState, propertyNames, types);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
			org.hibernate.type.Type[] types) {
		return this.handleTenant(entity, id, state, propertyNames, types);
	}

	private boolean handleTenant(Object entity, Serializable id, Object[] currentState, String[] propertyNames,
			org.hibernate.type.Type[] types) {

		int index = ArrayUtil.indexOf(propertyNames, "tenantId");
		if (index < 0) {
			return false;
		}

		String activeTenantId = this.tenantResolver.getTenantId();
		Object tenantId = currentState[index];

		// on a new entity, set tenant id to current tenant
		if (tenantId == null || StringUtil.isEmpty(tenantId.toString())) {
			currentState[index] = activeTenantId;
			return true;
		}

		// on update, block cross tenant attempt
		else if (!tenantId.equals(activeTenantId)) {
			throw new DataSegmentationException(
					"cross tenant update, tenantId=" + tenantId + ", activeTenantId=" + activeTenantId);
		}

		return true;
	}
}
