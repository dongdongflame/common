package org.z.cloud.commom.module;

public interface Module {

	public boolean start();

	public boolean stop();

	public boolean reStart();

	public Object service(Object... params);

}
