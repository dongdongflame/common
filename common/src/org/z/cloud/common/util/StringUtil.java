package org.z.cloud.common.util;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	protected static Logger logger = LoggerFactory.getLogger(StringUtil.class);

	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, "utf-8");
		} catch (Exception e) {
			return new String(bytes);
		}
	}

	public static boolean isEmpty(String v) {
		if (StringUtils.isEmpty(v)) {
			return true;
		}
		if (v.equalsIgnoreCase("null") || v.equalsIgnoreCase("undefined")) {
			return true;
		}
		return false;
	}

	public static String createUniqueID() {
		return UUID.randomUUID().toString();
	}

}