package com.simple.elasticsearch;

import java.io.Serializable;

public class ESMapping implements Serializable{

	public static final String TYPE_STRING = "string";
	public static final String TYPE_INTEGER = "integer";
	public static final String TYPE_DATE = "date";
	
	private static final long serialVersionUID = 1L;

	private String column;
	private String type;
	private boolean needsIk;
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isNeedsIk() {
		return needsIk;
	}
	public void setNeedsIk(boolean needsIk) {
		this.needsIk = needsIk;
	}
}
