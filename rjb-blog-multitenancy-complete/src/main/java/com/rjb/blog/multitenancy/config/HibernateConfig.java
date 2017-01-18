package com.rjb.blog.multitenancy.config;

import javax.inject.Inject;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.rjb.blog.multitenancy.hibernate.CurrentTenantIdentifierResolverImpl;
import com.rjb.blog.multitenancy.hibernate.MultiTenantConnectionProviderImpl;
import com.rjb.blog.multitenancy.hibernate.MultiTenantEntityInterceptor;
import com.rjb.blog.multitenancy.spring.MultiTenantDataSource;
import com.rjb.blog.multitenancy.util.ArrayUtil;
import com.rjb.blog.multitenancy.util.StringUtil;

@Configuration
public class HibernateConfig {

	@Inject
	private PropertiesConfig propertiesConfig;

	@Inject
	private ContextConfig contextConfig;

	@Inject
	private DataSourceConfig dataSourceConfig;

	/**
	 * Convenience for {@link #sessionFactoryBean().getObject()}.
	 */
	// Do not make this a @Bean
	public SessionFactory sessionFactory() {
		return this.sessionFactoryBean().getObject();
	}

	@Bean
	public LocalSessionFactoryBean sessionFactoryBean() {
		return this.createSessionFactoryBean();
	}

	protected LocalSessionFactoryBean createSessionFactoryBean() {

		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(this.dataSourceConfig.dataSource());

		if (StringUtil.isEmpty(this.propertiesConfig.hibernateDialect())) {
			throw new IllegalStateException("hibernateDialect is required");
		}

		factoryBean.getHibernateProperties().setProperty(AvailableSettings.DIALECT,
				this.propertiesConfig.hibernateDialect());

		if (!ArrayUtil.isEmpty(this.propertiesConfig.hibernateMappingLocations())) {
			factoryBean
					.setMappingLocations(this.propertiesConfig.hibernateMappingLocations());
		}

		// configure for multitenancy
		if (!(this.dataSourceConfig.dataSource() instanceof MultiTenantDataSource)) {
			throw new IllegalStateException("a MultiTenantDataSource is required");
		}

		factoryBean.getHibernateProperties().setProperty(AvailableSettings.MULTI_TENANT,
				MultiTenancyStrategy.DATABASE.toString());
		factoryBean.setMultiTenantConnectionProvider(
				new MultiTenantConnectionProviderImpl((MultiTenantDataSource) this.dataSourceConfig.dataSource()));
		factoryBean.setCurrentTenantIdentifierResolver(
				new CurrentTenantIdentifierResolverImpl(contextConfig.tenantResolver()));

		// update guard
		factoryBean.setEntityInterceptor(new MultiTenantEntityInterceptor(contextConfig.tenantResolver()));

		return factoryBean;

	}
}
