package com.rjb.blog.multitenancy.test.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;

import com.rjb.blog.multitenancy.util.ResourceUtil;

public class TestEnvironmentImpl extends AbstractHsqlDbTestEnvironment {

	@Override
	protected Collection<String> getDataBaseNames() {
		return Arrays.asList("rjb-blog-mutlitenancy-1", "rjb-blog-mutlitenancy-2");
	}

	protected Resource[] getDatabasePopulatorScripts(String databaseName) {
		if ("rjb-blog-mutlitenancy-1".equals(databaseName)) {
			return ResourceUtil.getResources("/scripts/hsqlbd/setup-1.sql, /scripts/hsqlbd/setup-2.sql");
		} else if ("rjb-blog-mutlitenancy-2".equals(databaseName)) {
			return ResourceUtil.getResourcesFromPattern("/scripts/hsqlbd/setup-3.sql");
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

		// database 1, schema 2
		Map<String, String> dataSource3 = new HashMap<String, String>();
		dataSource3.put(AbstractHsqlDbTestEnvironment.DATABASE_NAME, "rjb-blog-mutlitenancy-1");
		dataSource3.put(AbstractHsqlDbTestEnvironment.SCHEMA_NAME, "RJB_BLOG_MULTITENANCY_2");
		dataSource3.put(AbstractHsqlDbTestEnvironment.USERNAME, "rjb-blog-mutlitenancy-3");
		dataSource3.put(AbstractHsqlDbTestEnvironment.PASSWORD, "rjb-blog-mutlitenancy-3");
		dataSource3.put(AbstractHsqlDbTestEnvironment.JNDI_NAME, "java:/jdbc/rjb-blog-mutlitenancy-3");

		// database 2
		Map<String, String> dataSource4 = new HashMap<String, String>();
		dataSource4.put(AbstractHsqlDbTestEnvironment.DATABASE_NAME, "rjb-blog-mutlitenancy-2");
		dataSource4.put(AbstractHsqlDbTestEnvironment.SCHEMA_NAME, "RJB_BLOG_MULTITENANCY");
		dataSource4.put(AbstractHsqlDbTestEnvironment.USERNAME, "rjb-blog-mutlitenancy");
		dataSource4.put(AbstractHsqlDbTestEnvironment.PASSWORD, "rjb-blog-mutlitenancy");
		dataSource4.put(AbstractHsqlDbTestEnvironment.JNDI_NAME, "java:/jdbc/rjb-blog-mutlitenancy-4");

		return Arrays.asList(dataSource1, dataSource2, dataSource3, dataSource4);
	}

	protected int getSqlLogLevel() {
		return 2;
	}
}
