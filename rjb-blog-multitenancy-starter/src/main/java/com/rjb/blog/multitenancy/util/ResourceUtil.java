package com.rjb.blog.multitenancy.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ResourceUtil {

	public static Resource[] getResources(String commaDelimitedResourcePatterns) {
		return getResources(StringUtil.split(commaDelimitedResourcePatterns, ","));
	}

	public static Resource[] getResources(String[] patterns) {
		List<Resource> returnVal = new ArrayList<Resource>();
		for (String s : patterns) {
			if (StringUtil.isEmpty(s))
				continue;
			returnVal.addAll(Arrays.asList(ResourceUtil.getResourcesFromPattern(s)));
		}
		return returnVal.toArray(new Resource[returnVal.size()]);
	}

	public static Resource[] getResourcesFromPattern(String pattern) {
		try {
			return ResourceUtil.getResourcePatternResolver().getResources(pattern);
		} catch (Exception e) {
			throw new IllegalStateException("failed to load resources for pattern=" + pattern, e);
		}
	}

	private static ResourcePatternResolver getResourcePatternResolver() {
		return new PathMatchingResourcePatternResolver();
	}
}
