package com.rjb.blog.multitenancy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

import com.rjb.blog.multitenancy.util.ResourceUtil;

@Configuration
@PropertySources({ @PropertySource("classpath:env.properties") })
public class PropertiesConfig {

	@Value("${global.log4j.config:log4j.properties}")
	private String log4jConfigFile;

	@Value("${db.jndiName:}")
	private String dataSourceJndiName;

	@Value("${hibernate.dialect:}")
	private String hibernateDialect;

	@Value("${hibernate.mapping.locations:}")
	private String hibernateMappingLocations;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public String log4jConfigFile() {
		return log4jConfigFile;
	}

	@Bean
	public String dataSourceJndiName() {
		return dataSourceJndiName;
	}

	@Bean
	public String hibernateDialect() {
		return hibernateDialect;
	}

	@Bean
	public Resource[] hibernateMappingLocations() {
		return ResourceUtil.getResources(this.hibernateMappingLocations);
	}
}
