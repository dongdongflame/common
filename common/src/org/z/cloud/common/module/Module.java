package org.z.cloud.common.module;

public interface Module {

	public boolean start();

	public boolean stop();

	public boolean reStart();

	public long getStartTime();

	public Object service(Object... params);

}
