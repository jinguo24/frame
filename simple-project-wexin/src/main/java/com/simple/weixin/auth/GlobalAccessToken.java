package com.simple.weixin.auth;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description: 微信请求全局access_token
 * @author:lining
 * @version:v1.0.0
 * @Created:2013年12月10日上午11:31:16
 * @Modified:
 */
public class GlobalAccessToken {

	/**获取到的凭证*/
	private String access_token;
	
	/**凭证有效时间，单位：秒*/
	private String expires_in;

	public String getAccess_token() {
		return access_token;
	}
	
	/**
	 * 
	  * @Description: 获取Token
	  * @param jsonString
	  * @return String
	  * @Created:lining 2013年12月10日上午11:41:43
	  * @Modified:
	 */
	public static GlobalAccessToken analyticToken(String jsonString) {
		GlobalAccessToken accessToken = JSONObject.parseObject(jsonString, GlobalAccessToken.class);
		return accessToken;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
}