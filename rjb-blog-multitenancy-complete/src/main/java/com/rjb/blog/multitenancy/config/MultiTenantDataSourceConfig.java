package com.rjb.blog.multitenancy.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rjb.blog.multitenancy.spring.MultiTenantDataSource;

@Configuration
public class MultiTenantDataSourceConfig implements DataSourceConfig {

	@Inject
	private PropertiesConfig propertiesConfig;

	@Inject
	private ContextConfig contextConfig;

	@Bean
	public DataSource dataSource() {
		MultiTenantDataSource returnVal = new MultiTenantDataSource(contextConfig.tenantResolver());
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.putAll(propertiesConfig.getTenantJndiDataSourceMappings());
		returnVal.setTargetDataSources(targetDataSources);
		returnVal.setLenientFallback(false);
		return returnVal;
	}

}

