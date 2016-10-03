package com.simple.weixin.auth;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description: jsapi_ticket
 * @author lining
 * @date 2015年5月11日 下午3:17:16
 */
public class JsApiTicket {

	private String ticket;
	
	private String expires_in;
	
	/**
	 * 
	 * @Description:
	 * @param jsonString
	 * @return
	 */
	public static JsApiTicket analytic(String jsonString) {
		JsApiTicket userInfo = JSONObject.parseObject(jsonString, JsApiTicket.class);
		return userInfo;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
}
