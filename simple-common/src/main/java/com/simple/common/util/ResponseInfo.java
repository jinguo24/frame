package com.simple.common.util;

import java.io.Serializable;

public class ResponseInfo implements Serializable {

	private static final long serialVersionUID = -297769035415722180L;
	
	private ResponseStatus status;

	private Object data = null;

	public ResponseInfo() {

	}

	public ResponseInfo(ResponseStatus status) {
		this.status = status;
	}
	public ResponseInfo(ResponseStatus status, Object data) {
		this.status = status;
		this.data = data;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}