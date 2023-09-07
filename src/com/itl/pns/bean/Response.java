/*
  
 */
package com.itl.pns.bean;

/**
 * @author nitesh.porwal
 *
 */
public class Response {

	private String status;

	private Object data;

	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
