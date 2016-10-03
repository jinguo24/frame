package com.simple.weixin.auth;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Description 微信用户信息，用户已关注
 * @author lining
 * @date 2015年5月6日 下午61540
 */
public class UserInfoIsFollow {

	private String subscribe;
	
	private String openid;
	
	private String nickname;
	
	private String sex;
	
	private String language;
	
	private String city;
	
	private String province;
	
	private String country;
	
	private String headimgurl;
	
	private String subscribe_time;
	
	private String unionid;
	
	/**
	 * 
	 * @Description:
	 * @param jsonString
	 * @return
	 */
	public static UserInfoIsFollow analyticToken(String jsonString) {
		UserInfoIsFollow userInfo = JSONObject.parseObject(jsonString, UserInfoIsFollow.class);
		return userInfo;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(String subscribe_time) {
		this.subscribe_time = subscribe_time;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}
