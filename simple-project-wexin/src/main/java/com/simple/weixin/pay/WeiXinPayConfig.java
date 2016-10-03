package com.simple.weixin.pay;

import java.io.Serializable;

public class WeiXinPayConfig implements Serializable{

	private static final long serialVersionUID = 1L;
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String lpackage;
	private String signType;
	private String packageValue;
	private String paySign;
	private String agent;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getLpackage() {
		return lpackage;
	}
	public void setLpackage(String lpackage) {
		this.lpackage = lpackage;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPackageValue() {
		return packageValue;
	}
	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
}
