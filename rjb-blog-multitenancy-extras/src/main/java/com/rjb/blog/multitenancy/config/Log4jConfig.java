package com.rjb.blog.multitenancy.config;

import javax.inject.Inject;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Log4jConfig {

	@Inject
	private PropertiesConfig propertiesConfig;

	@SuppressWarnings("deprecation")
	@Bean
	public Object log4jInitializer() {
		MethodInvokingFactoryBean returnVal = new MethodInvokingFactoryBean();
		returnVal
				.setTargetClass(org.springframework.util.Log4jConfigurer.class);
		returnVal.setTargetMethod("initLogging");
		returnVal.setArguments(new Object[] { propertiesConfig.log4jConfigFile(), Long.valueOf(5000) });
		return returnVal;
	}
}
