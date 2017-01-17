package com.rjb.blog.multitenancy.util;

import java.util.Collection;

public class CollectionUtil {

	public static <T> boolean isEmpty(Collection<T> c) {
		return c == null || c.isEmpty();
	}

}
