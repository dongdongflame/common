package org.z.cloud.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.joor.Reflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil {

	private static Map<String, Class<?>> cachedClass = new ConcurrentHashMap<>();

	private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

	private static void loggerError(Exception e) {
		logger.error(e.getMessage(), e);
	}

	public static Class<?> getClass(String className) {
		Class<?> cls = getCachedClass(className);
		if (cls != null)
			return cls;
		cls = classForName(className);
		if (cls == null)
			return null;
		setCachedClass(cls.getName(), cls);
		return cls;
	}

	private static Class<?> getCachedClass(String className) {
		if (StringUtil.isEmpty(className))
			return null;
		return cachedClass.get(className);
	}

	private static Class<?> classForName(String className) {
		if (StringUtil.isEmpty(className))
			return null;
		try {
			return (Class<?>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			loggerError(e);
			return null;
		}
	}

	private synchronized static void setCachedClass(String className, Class<?> cls) {
		cachedClass.put(className, cls);
	}

	public static Object getInstance(Class<?> cls) {
		if (cls == null)
			return null;
		try {
			return cls.newInstance();
		} catch (InstantiationException e) {
			loggerError(e);
		} catch (IllegalAccessException e) {
			loggerError(e);
		}
		return null;
	}

	public static Object execute(Object obj, String methodName, Object... args) {
		if (obj == null || StringUtil.isEmpty(methodName))
			return null;
		try {
			return Reflect.on(obj).call(methodName, args).get();
		} catch (Exception e) {
			loggerError(e);
			return null;
		}
	}

	public static Object BytesToObject(byte[] bytes) {
		Object obj = new Object();
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		try {
			bi = new ByteArrayInputStream(bytes);
			oi = new ObjectInputStream(bi);
			obj = oi.readObject();
		} catch (Exception e) {
			loggerError(e);
		} finally {
			try {
				if (bi != null)
					bi.close();
				if (oi != null)
					oi.close();
			} catch (IOException e) {
				loggerError(e);
			}
		}
		return obj;
	}

	public static byte[] ObjectToBytes(Object object) {
		byte[] bytes = new byte[1024];
		ByteArrayOutputStream bo = null;
		ObjectOutputStream oo = null;
		try {
			bo = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(bo);
			oo.writeObject(object);
			bytes = bo.toByteArray();
		} catch (IOException e) {
			loggerError(e);
		} finally {
			try {
				if (bo != null)
					bo.close();
				if (oo != null)
					oo.close();
			} catch (IOException e) {
				loggerError(e);
			}
		}
		return bytes;
	}

}