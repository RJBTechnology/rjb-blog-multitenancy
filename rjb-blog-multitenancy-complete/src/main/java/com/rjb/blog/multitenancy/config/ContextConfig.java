package com.rjb.blog.multitenancy.config;

import com.rjb.blog.multitenancy.context.TenantResolver;

public interface ContextConfig {

	TenantResolver tenantResolver();

}
