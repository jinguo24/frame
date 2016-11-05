package com.simple.elasticsearch.v200;

import io.searchbox.annotations.JestId;

public class Test {

	@JestId
	private String testId;
	private String name;
	private String desc;
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
