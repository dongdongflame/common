package org.z.cloud.common.processor;

public class Response {

	private boolean success;
	private Object result;
	private String message;

	public Response() {
	}

	public Response(boolean success, Object result, String message) {
		super();
		this.success = success;
		this.result = result;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}