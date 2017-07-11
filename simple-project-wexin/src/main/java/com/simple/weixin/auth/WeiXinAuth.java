package com.simple.weixin.auth;

import java.io.UnsupportedEncodingException;
public class WeiXinAuth {

	public static OAuthAccessToken getOAuthAccessToken(String code) {
		return WeiXinHelper.getOAuthAccessToken(code);
	}
	
	public static String getAuthUrl(String callBackUrl,boolean isAuth,String state) {
		try {
			if (isAuth) {
				return WeiXinHelper.getOAuthURL(callBackUrl, ScopeType.snsapi_userinfo, state);
			}else {
				return WeiXinHelper.getOAuthURL(callBackUrl, ScopeType.snsapi_base, state);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static OAuthUserInfo getOAuthUserInfo(String access_token, String openId) {
		return WeiXinHelper.getOAuthUserInfo(access_token, openId);
	}
	
	public static void installAcessToken() {
		WeiXinHelper.installAcessToken();
	}
	
	public static void installJsTicket() {
		WeiXinHelper.installJsTicket();
	}
	
	public static JsConfigInfo getJsConfigInfo(String url) throws Exception {
		return WeiXinHelper.createJsConfig(url);
	}
	
	/**
	 * 通过显示授权获取个人信息
	 * @param code
	 * @return
	 */
	public static OAuthUserInfo authInfo(String code) {
		OAuthAccessToken authAccessToken = getOAuthAccessToken(code);
		if(authAccessToken != null) {
			//获取用户信息
			if(authAccessToken.getScope().equals(ScopeType.snsapi_userinfo)) {
				OAuthUserInfo oAuthUserInfo = WeiXinHelper.getOAuthUserInfo(authAccessToken.getAccess_token(),
						authAccessToken.getOpenid());
				return oAuthUserInfo;
			} 
			return null;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取已关注的个人信息
	 * @param code
	 * @return
	 */
	public static UserInfoIsFollow authInfoByFollowd(String globalToken,String code) {
		OAuthAccessToken authAccessToken = getOAuthAccessToken(code);
		if(authAccessToken != null) {
			//获取用户信息
			if(!authAccessToken.getScope().equals(ScopeType.snsapi_userinfo)) {
				UserInfoIsFollow userInfoIsFollow = WeiXinHelper.getUserInfoIsFollow(authAccessToken.getOpenid());
				return userInfoIsFollow;
			}else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	
}
