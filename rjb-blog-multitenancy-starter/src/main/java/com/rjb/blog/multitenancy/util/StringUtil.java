package com.rjb.blog.multitenancy.util;

import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isEmpty(String s) {
		return s == null || s.isEmpty();
	}

	public static String[] split(String s, String quote) {
		return split(s, quote, true);
	}

	public static String[] split(String s, String quote, boolean trim) {
		String[] returnVal = s.split(Pattern.quote(quote));
		if (!trim)
			return returnVal;
		for (int i = 0; i < returnVal.length; i++) {
			returnVal[i] = returnVal[i].trim();
		}
		return returnVal;
	}
}
