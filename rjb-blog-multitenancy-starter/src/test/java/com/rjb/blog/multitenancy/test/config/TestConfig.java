package com.rjb.blog.multitenancy.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.rjb.blog.multitenancy.config.DaoConfig;
import com.rjb.blog.multitenancy.config.TransactionConfig;
import com.rjb.blog.multitenancy.config.Log4jConfig;
import com.rjb.blog.multitenancy.config.PropertiesConfig;
import com.rjb.blog.multitenancy.config.SingleTenantDataSourceConfig;
import com.rjb.blog.multitenancy.config.HibernateConfig;

/**
 * The root config.
 *
 */
@Configuration
@Import(value = { PropertiesConfig.class, Log4jConfig.class, SingleTenantDataSourceConfig.class,
		HibernateConfig.class, DaoConfig.class, TransactionConfig.class })
public class TestConfig {

}
