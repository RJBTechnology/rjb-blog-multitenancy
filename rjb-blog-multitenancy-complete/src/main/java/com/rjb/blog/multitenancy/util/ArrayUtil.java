package com.rjb.blog.multitenancy.util;

public class ArrayUtil {

	public static <T> boolean isEmpty(T[] a) {
		return a == null || a.length == 0;
	}

	public static <T> int indexOf(T[] array, T object) {
		int i = 0;
		for (T t : array) {
			if (t == object || t.equals(object))
				return i;
			i++;
		}
		return -1;
	}
}
