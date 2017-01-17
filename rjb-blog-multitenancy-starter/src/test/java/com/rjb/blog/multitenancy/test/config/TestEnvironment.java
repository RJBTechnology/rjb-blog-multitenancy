package com.rjb.blog.multitenancy.test.config;

public interface TestEnvironment {

	void initialize() throws Exception;

	void destroy() throws Exception;

}
