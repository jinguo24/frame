package com.simple.weixin.auth;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description: 页面获取用户信息Token
 * @author lining
 * @date 2015年5月6日 下午6:36:42
 */
public class OAuthAccessToken {

	/**获取到的凭证*/
	private String access_token = "";
	
	/**凭证有效时间，单位：秒*/
	private int expires_in;
	
	/**用户刷新access_token**/
	private String refresh_token;
	
	/**用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID**/
	private String openid;
	
	/**用户授权的作用域，使用逗号（,）分隔 **/
	private ScopeType scope;
	
	/***/
	private String unionid;
	
	/**
	 * 
	 * @Description:
	 * @param jsonString
	 * @return
	 */
	public static OAuthAccessToken analyticToken(String jsonString) {
		OAuthAccessToken accessToken = JSONObject.parseObject(jsonString, OAuthAccessToken.class);
		return accessToken;
	}

	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public ScopeType getScope() {
		return scope;
	}

	public void setScope(ScopeType scope) {
		this.scope = scope;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
}