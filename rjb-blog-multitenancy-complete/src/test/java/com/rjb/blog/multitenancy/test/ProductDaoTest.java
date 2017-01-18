package com.rjb.blog.multitenancy.test;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.rjb.blog.multitenancy.dao.DataSegmentationException;
import com.rjb.blog.multitenancy.dao.ProductDao;
import com.rjb.blog.multitenancy.entity.Product;
import com.rjb.blog.multitenancy.test.config.TestConfig;

@ContextConfiguration(classes = { TestConfig.class })
public class ProductDaoTest extends AbstractTest {

	@Inject
	protected ProductDao productDao;

	@Test
	public void get() {
		Long expectedId = Long.valueOf(1);
		Product product = this.productDao.get(expectedId);
		assertTrue(product != null && expectedId.equals(product.getId()));
	}

	@Test
	public void getBySku() {
		String expectedSku = "SKU-1";
		Product product = this.productDao.getBySku(expectedSku);
		assertTrue(product != null && expectedSku.equals(product.getSku()));
	}

	@Test
	public void getByCategory() {
		String expectedCategory = "Test Category";
		List<Product> products = this.productDao.getByCategory(expectedCategory);
		assertTrue(products.size() == 2);
		products.forEach(product -> {
			assertTrue(expectedCategory.equals(product.getCategory()));
		});
	}

	@Test
	public void save() {
		Product product = new Product();
		assertTrue("invalid entity, id generated in constructor", product.getId() == null);
		product.setCreatedUser("test");
		product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		product.setUpdatedUser("test");
		product.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		product.setCategory("Test Category");
		product.setSku("SKU-Test");
		this.productDao.save(product);
		assertTrue(product.getId() != null);
	}

	@Test
	public void update() {
		Long expectedId = Long.valueOf(1);
		Product product = this.productDao.get(expectedId);
		if (product == null) {
			throw new IllegalStateException("missing test Product#1, cannot verify update");
		}
		Long initialVersion = product.getVersion();
		product.setCategory("New Category");
		this.productDao.update(product);
		this.productDao.flush(); // required to increment version
		assertTrue(product.getVersion() > initialVersion);
	}

	@Test
	public void delete() {
		Long expectedId = Long.valueOf(1);
		Product product = productDao.get(expectedId);
		if (product == null) {
			throw new IllegalStateException("missing test Product#1, cannot verify delete");
		}
		this.productDao.delete(product);
		assertTrue(this.productDao.get(expectedId) == null);
	}

	@Test
	public void get_tenant_2() {
		Long expectedId = Long.valueOf(4);
		Product product = this.productDao.get(expectedId);
		assertTrue(product != null && expectedId.equals(product.getId()));
	}

	@Test
	public void get_tenant_2_accessTenant1() {
		Long expectedId = Long.valueOf(1);
		Product product = this.productDao.get(expectedId);
		assertTrue(product == null);
	}

	@Test(expected = DataSegmentationException.class)
	public void save_tenant_2_blockCrossTenant() {
		Product product = new Product();
		product.setCreatedUser("test");
		product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		product.setUpdatedUser("test");
		product.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
		product.setCategory("Test Category");
		product.setSku("SKU-Test");
		// try to save into tenant 1
		product.setTenantId("rjb-blog-mutlitenancy-1");
		this.productDao.save(product);
	}
}
