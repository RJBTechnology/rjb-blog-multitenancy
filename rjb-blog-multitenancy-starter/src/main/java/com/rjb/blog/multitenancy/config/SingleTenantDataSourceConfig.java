package com.rjb.blog.multitenancy.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import com.rjb.blog.multitenancy.util.StringUtil;

@Configuration
public class SingleTenantDataSourceConfig implements DataSourceConfig {

	@Inject
	private PropertiesConfig propertiesConfig;
	
	@Bean
	public DataSource dataSource() {
		return this.getJndiDataSource();
	}

	protected DataSource getJndiDataSource() {
		if (StringUtil.isEmpty(this.propertiesConfig.dataSourceJndiName())) {
			throw new IllegalStateException("'dataSourceJndiName' property is required");
		}
		try {
			return new JndiDataSourceLookup()
					.getDataSource(this.propertiesConfig.dataSourceJndiName());
		} catch (Exception e) {
			throw new IllegalStateException(
					"failed to look up JNDI DataSource for name=" + this.propertiesConfig.dataSourceJndiName(), e);
		}
	}
}
