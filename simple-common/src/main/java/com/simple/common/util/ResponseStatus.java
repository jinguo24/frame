package com.simple.common.util;

import java.io.Serializable;

public class ResponseStatus implements Serializable {

	private static final long serialVersionUID = 6180026101789656042L;
	private static final String SUCCESS  = "1";
	private static final String ERROR = "2";

	private boolean state;

	private String code;

	private String message;

	public ResponseStatus() {

	}

	public ResponseStatus(boolean state, String code, String message) {
		this.state = state;
		this.code = code;
		this.message = message;
	}
	
	public ResponseStatus(boolean state, String message) {
		this.state = state;
		if (this.state) {
			this.code = SUCCESS;
		}else {
			this.code = ERROR;
		}
		this.message = message;
	}

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}