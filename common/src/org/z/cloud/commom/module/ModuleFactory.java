package org.z.cloud.commom.module;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.z.cloud.common.util.ClassUtil;
import org.z.cloud.common.util.StringUtil;

class ModuleProxy implements InvocationHandler {

	private Class<?> clazz;
	private Module target;
	private Module proxy;

	ModuleProxy(Class<?> clazz) {
		if (clazz == null)
			throw new ExceptionInInitializerError("clazz is null");
		this.clazz = clazz;
	}

	public void newInstance() {
		if (proxy != null)
			return;
		synchronized (this) {
			if (proxy != null)
				return;
			target = (Module) ClassUtil.getInstance(clazz);
			proxy = (Module) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(target, args);
	}

	public Module getInstance() {
		return proxy;
	}

}

public enum ModuleFactory {

	INSTANCES;

	public final Logger logger = LoggerFactory.getLogger(ModuleFactory.class);
	private Map<String, ModuleProxy> modules = new ConcurrentHashMap<String, ModuleProxy>();

	private ModuleProxy getProxy(String moduleName) {
		if (StringUtil.isEmpty(moduleName))
			return null;
		return modules.get(moduleName);
	}

	public Module getModule(String moduleName) {
		ModuleProxy proxy = getProxy(moduleName);
		if (proxy != null)
			return getModule(proxy);
		logger.error("module [{}] not register", moduleName);
		return null;
	}

	private Module getModule(ModuleProxy proxy) {
		if (proxy == null)
			return null;
		if (proxy.getInstance() == null)
			proxy.newInstance();
		return proxy.getInstance();
	}

	public synchronized boolean registerModule(String moduleName) {
		Class<?> cls = ClassUtil.getClass(moduleName);
		if (cls == null) {
			logger.error("can not find cls [{}]", moduleName);
			return false;
		}
		modules.put(moduleName, new ModuleProxy(cls));
		return true;
	}

	public void startModule(String moduleName) {
		logger.info("module [{}] begin to start", moduleName);
		Module module = getModule(moduleName);
		if (module == null)
			return;
		if (module.start())
			logger.info("module [{}] start ok", moduleName);
		else
			logger.error("module [{}] start error", moduleName);
	}

	public boolean startModules() {
		Iterator<String> key = modules.keySet().iterator();
		while (key.hasNext())
			startModule(key.next());
		return true;
	}

}