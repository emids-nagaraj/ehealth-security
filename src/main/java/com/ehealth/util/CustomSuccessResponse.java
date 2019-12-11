package com.ehealth.util;

/**
 * @author nagaraj
 *
 */
public class CustomSuccessResponse {

	private Boolean success;
	private String message;
	private Object data;

	public CustomSuccessResponse() {
		super();
	}

	public CustomSuccessResponse(Boolean success, String message, Object data) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public CustomSuccessResponse(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}


	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
