package com.rjb.blog.multitenancy.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rjb.blog.multitenancy.config.ContextConfig;
import com.rjb.blog.multitenancy.context.TenantResolver;

@Configuration
public class TestContextConfig implements ContextConfig {

	@Bean
	public TenantResolver tenantResolver() {
		return new TestTenantResolver();
	}

	public class TestTenantResolver implements TenantResolver {

		private String tenantId;

		@Override
		public String getTenantId() {
			return this.tenantId;
		}

		/**
		 * We support manual set for tests.
		 * 
		 * @param tenantId
		 */
		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}
	}
}
