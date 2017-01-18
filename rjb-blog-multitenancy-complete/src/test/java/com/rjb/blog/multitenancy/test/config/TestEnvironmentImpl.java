package com.rjb.blog.multitenancy.test.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;

import com.rjb.blog.multitenancy.util.ResourceUtil;

public class TestEnvironmentImpl extends AbstractHsqlDbTestEnvironment {

	@Override
	protected Collection<String> getDataBaseNames() {
		return Collections.singletonList("rjb-blog-mutlitenancy-1");
	}

	protected Resource[] getDatabasePopulatorScripts(String databaseName) {
		if ("rjb-blog-mutlitenancy-1".equals(databaseName)) {
			return ResourceUtil.getResourcesFromPattern("/scripts/hsqlbd/setup-1.sql");
		}
		return null;
	}

	protected Collection<Map<String, String>> getDataSources() {
		Map<String, String> dataSource1 = new HashMap<String, String>();
		dataSource1.put(AbstractHsqlDbTestEnvironment.DATABASE_NAME, "rjb-blog-mutlitenancy-1");
		dataSource1.put(AbstractHsqlDbTestEnvironment.SCHEMA_NAME, "RJB_BLOG_MULTITENANCY_1");
		dataSource1.put(AbstractHsqlDbTestEnvironment.USERNAME, "rjb-blog-mutlitenancy-1");
		dataSource1.put(AbstractHsqlDbTestEnvironment.PASSWORD, "rjb-blog-mutlitenancy-1");
		dataSource1.put(AbstractHsqlDbTestEnvironment.JNDI_NAME, "java:/jdbc/rjb-blog-mutlitenancy-1");

		Map<String, String> dataSource2 = new HashMap<String, String>();
		dataSource2.put(AbstractHsqlDbTestEnvironment.DATABASE_NAME, "rjb-blog-mutlitenancy-1");
		dataSource2.put(AbstractHsqlDbTestEnvironment.SCHEMA_NAME, "RJB_BLOG_MULTITENANCY_1");
		dataSource2.put(AbstractHsqlDbTestEnvironment.USERNAME, "rjb-blog-mutlitenancy-2");
		dataSource2.put(AbstractHsqlDbTestEnvironment.PASSWORD, "rjb-blog-mutlitenancy-2");
		dataSource2.put(AbstractHsqlDbTestEnvironment.JNDI_NAME, "java:/jdbc/rjb-blog-mutlitenancy-2");

		return Arrays.asList(dataSource1, dataSource2);
	}

	protected int getSqlLogLevel() {
		return 2;
	}
}
