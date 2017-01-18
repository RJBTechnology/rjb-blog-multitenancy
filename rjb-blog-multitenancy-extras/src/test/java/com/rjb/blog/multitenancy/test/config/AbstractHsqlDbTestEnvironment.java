package com.rjb.blog.multitenancy.test.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.rjb.blog.multitenancy.util.CollectionUtil;
import com.rjb.blog.multitenancy.util.StringUtil;

/**
 * 
 * An in memory HSQLDB test environment.
 * 
 */
public abstract class AbstractHsqlDbTestEnvironment implements TestEnvironment {

	public static final String DATABASE_NAME = "databaseName";
	public static final String SCHEMA_NAME = "schemaName";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String JNDI_NAME = "jndiName";

	private List<DataSource> dataSources;

	/**
	 * Perform the following:
	 * <ul>
	 * <li>Optionally start 1 or more named test databases.</li>
	 * <li>Optionally execute 1 or more database initialization scripts against
	 * each named database.</li>
	 * <li>Optionally create test {@link DataSource DataSources} and bind them
	 * into the JNDI context (under a provided name).</li>
	 * </ul>
	 */
	public void initialize() throws Exception {

		Collection<String> dataBaseNames = this.getDataBaseNames();
		if (dataBaseNames == null) {
			dataBaseNames = Collections.emptyList();
		}

		// start test databases
		dataBaseNames.forEach(databaseName -> {
			Resource[] scripts = this.getDatabasePopulatorScripts(databaseName);
			if (scripts == null || scripts.length == 0) {
				return;
			}

			DataSource ds = this.createDatabasePopulatorDataSource(databaseName);

			// populate the db
			ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
			resourceDatabasePopulator.addScripts(scripts);
			DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
			dataSourceInitializer.setDataSource(ds);
			dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
			dataSourceInitializer.afterPropertiesSet();

			this.destroyDatabasePopulatorDataSource(databaseName, ds);
		});

		// create test data sources
		// add to JNDI
		Collection<Map<String, String>> dataSources = this.getDataSources();
		if (CollectionUtil.isEmpty(dataSources)) {
			return;
		}

		this.dataSources = new ArrayList<DataSource>();
		SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
		for (Map<String, String> dataSource : dataSources) {
			DataSource ds = this.createDataSource(dataSource.get(DATABASE_NAME), dataSource.get(SCHEMA_NAME),
					dataSource.get(USERNAME), dataSource.get(PASSWORD));
			this.dataSources.add(ds);
			builder.bind(dataSource.get(JNDI_NAME), ds);
		}
		builder.activate();
	}

	/**
	 * Cleanup all resources created in {@link #initialize()}.
	 */
	@Override
	public void destroy() {
		if (this.dataSources == null) {
			return;
		}
		this.dataSources.forEach(dataSource -> {
			this.destroyDataSource(dataSource);
		});
	}

	/**
	 * Return a 'dba' connection for executing initialization scripts against a
	 * test database. By default, this is the 'sa' user. </br>
	 * </br>
	 * If overridden, {@link #destroyDatabasePopulatorDataSource(DataSource)}
	 * should be overridden as well.
	 * 
	 * @param databaseName
	 * @return
	 */
	protected DataSource createDatabasePopulatorDataSource(String databaseName) {
		return this.createDataSource(databaseName, "", "sa", "");
	}

	/**
	 * 
	 * Destroy a {@link DataSource} created by
	 * {@link #createDatabasePopulatorDataSource(String)}
	 * 
	 * @param ds
	 * @return
	 */
	protected void destroyDatabasePopulatorDataSource(String databaseName, DataSource dataSource) {
		this.destroyDataSource(dataSource);
	}

	/**
	 * If overridden, {@link #destroyDataSource(DataSource)} should be
	 * overridden as well.
	 * 
	 * @param databaseName
	 * @param schemaName
	 * @param username
	 * @param password
	 * @return
	 */
	protected DataSource createDataSource(String databaseName, String schemaName, String username, String password) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(this.getDriverClassName(databaseName));
		dataSource.setUrl(this.getUrl(databaseName));
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		if (!StringUtil.isEmpty(schemaName)) {
			dataSource.setSchema(schemaName);
		}
		return dataSource;
	}

	protected void destroyDataSource(DataSource dataSource) {
		// DriverManagerDataSource doesn't need to be destroyed
	}

	protected String getUrl(String databaseName) {
		String returnVal = "jdbc:hsqldb:mem:" + databaseName;
		if (this.getSqlLogLevel() > 0) {
			int logLevel = Math.min(3, this.getSqlLogLevel());
			returnVal += ";hsqldb.sqllog=" + logLevel;
		}
		return returnVal;
	}

	protected String getDriverClassName(String databaseName) {
		return "org.hsqldb.jdbc.JDBCDriver";
	}

	/**
	 * Default 0 (none). Override to set the 'hsqldb.sqllog' log level [0-3].
	 * Values &lt; 0 will be ignored. Values &gt; 3 will be treated as 3.
	 * 
	 * @return
	 */
	protected int getSqlLogLevel() {
		return 0;
	}

	protected Resource[] getDatabasePopulatorScripts(String databaseName) {
		return null;
	}

	protected Collection<String> getDataBaseNames() {
		return null;
	}

	/**
	 * Return a collection of test datasource to be created. Each datasource is
	 * defined by a DATABASE_NAME, SCHEMA_NAME, USERNAME, PASSWORD, JNDI_NAME
	 * 
	 * @return
	 */
	protected abstract Collection<Map<String, String>> getDataSources();

}
