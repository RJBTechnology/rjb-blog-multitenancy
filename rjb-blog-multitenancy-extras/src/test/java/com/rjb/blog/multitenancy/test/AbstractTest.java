package com.rjb.blog.multitenancy.test;

import java.io.InputStream;
import java.util.Properties;

import javax.inject.Inject;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.rjb.blog.multitenancy.test.config.TestContextConfig.TestTenantResolver;
import com.rjb.blog.multitenancy.test.config.TestEnvironment;
import com.rjb.blog.multitenancy.util.StringUtil;

/**
 * 
 * A base class for tests that:
 * <ul>
 * <li>Run in a transaction w/ default rollback.</li>
 * <li>Run with {@link SpringJUnit4ClassRunner}.</li>
 * <li>Provide an optional {@link TestEnvironment} class name in the
 * {@value #TEST_PROPERTIES_CLASSPATH_FILE} file under the key
 * {@value #TEST_ENVIRONMENT_CLASSNAME}. The environment is initialized
 * {@link BeforeClass @BeforeClass} (i.e. before Spring context initialization)
 * and destroyed {@link AfterClass @AfterClass}.</li>
 * </ul>
 *
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractTest {

	public static final String TEST_PROPERTIES_CLASSPATH_FILE = "/env.properties";

	public static final String TEST_ENVIRONMENT_CLASSNAME = "test.environment.className";

	protected static TestEnvironment testEnvironment;

	@Inject
	protected TestTenantResolver tenantResolver;

	@Rule
	public TestName testName = new TestName();

	@BeforeClass
	public static void beforeClass() {

		try {

			// Spring isn't initialized yet, so we load properties manually
			final Properties properties = new Properties();
			try (final InputStream stream = AbstractTest.class.getResourceAsStream(TEST_PROPERTIES_CLASSPATH_FILE)) {
				properties.load(stream);
			}

			// a little home brewed CDI to initialize the test environment
			// again, Spring isn't initialized yet
			String testEnvironmentClassName = properties.getProperty(TEST_ENVIRONMENT_CLASSNAME);
			if (!StringUtil.isEmpty(testEnvironmentClassName)) {
				testEnvironment = (TestEnvironment) Class.forName(testEnvironmentClassName).newInstance();
				testEnvironment.initialize();
			}

		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	@AfterClass
	public static void afterClass() {
		try {
			if (testEnvironment != null) {
				testEnvironment.destroy();
			}
		} catch (Exception e) {
			// nothing we can do
			e.printStackTrace();
		}
	}

	@BeforeTransaction
	public void beforeTransaction() {
		if (testName.getMethodName().contains("tenant_2")) {
			this.tenantResolver.setTenantId("rjb-blog-mutlitenancy-2");
		} else if (testName.getMethodName().contains("tenant_3")) {
			this.tenantResolver.setTenantId("rjb-blog-mutlitenancy-3");
		} else if (testName.getMethodName().contains("tenant_4")) {
			this.tenantResolver.setTenantId("rjb-blog-mutlitenancy-4");
		} else {
			// default to tenant 1
			// so all tests in the ProductDaoTest base class will still pass
			this.tenantResolver.setTenantId("rjb-blog-mutlitenancy-1");
		}
	}
}
