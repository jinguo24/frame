package com.simple.weixin.auth;

import java.io.Serializable;

/**
 *
 * @Description: js sdk接口注入权限验证配置
 * @author lining
 * @date 2015年6月8日 下午2:55:54
 */
public class JsConfigInfo implements Serializable {

	private static final long serialVersionUID = -3750679317448584375L;

	/**时间戳*/
	private String timestamp;
	/**随机字符串*/
	private String nonceStr;
	/**生成签名串*/
	private String signature;
	/**appid微信公共账号*/
	private String appid;;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("[appid:").append(appid)
			.append(", timestamp:").append(timestamp)
			.append(", nonceStr:").append(nonceStr)
			.append(", signature:").append(signature).append("]");
		return sb.toString();
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
}