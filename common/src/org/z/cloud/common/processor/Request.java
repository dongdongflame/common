package org.z.cloud.common.processor;

public class Request {

	private String moduleName;
	private String methodName;
	private Object[] arguments;

	public Request() {
	}

	public Request(String moduleName, Object... arguments){
		this(moduleName,null, arguments);
		
	}
	
	public Request(String moduleName, String methodName, Object... arguments) {
		this.moduleName = moduleName;
		this.methodName = methodName;
		this.arguments = arguments;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Object[] getArguments() {
		return arguments;
	}

	public void setArguments(Object... arguments) {
		this.arguments = arguments;
	}

}