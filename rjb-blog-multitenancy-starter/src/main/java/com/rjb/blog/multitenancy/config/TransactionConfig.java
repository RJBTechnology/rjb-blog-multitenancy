package com.rjb.blog.multitenancy.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

	@Inject
	private HibernateConfig hibernateConfig;

	@Inject
	private DataSourceConfig dataSourceConfig;

	@Bean
    public PlatformTransactionManager txManager() throws Exception {
		HibernateTransactionManager returnVal = new HibernateTransactionManager(
				hibernateConfig.sessionFactory());
		returnVal.setDataSource(dataSourceConfig.dataSource());
		return returnVal;
    }
}
