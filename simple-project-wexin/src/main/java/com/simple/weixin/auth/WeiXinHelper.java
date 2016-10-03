package com.simple.weixin.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.util.PrimaryKeyUtil;
import com.simple.weixin.pay.PayCommonUtil;
import com.simple.weixin.util.HttpUtil;
import com.simple.weixin.util.Sha1Util;

public class WeiXinHelper {

	private static final Log log = LogFactory.getLog(WeiXinHelper.class);
	
	/**网页授权获，code换取网页授权access_token*/
	public static final String oauth_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token";
	/**网页授权获，取用户信息*/
	public static final String oauth_user_info_url = "https://api.weixin.qq.com/sns/userinfo";
	/**获取用户信息地址，该接口需要用户关注服务号，否则拉取不到信息*/
	public static final String user_info_is_follow_url = "http://api.weixin.qq.com/cgi-bin/user/info";
	/**全局access_token获取地址*/
	public static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + EnvPropertiesConfiger.getValue("weixin_appid") +"&secret=" + EnvPropertiesConfiger.getValue("weixin_secret");
	/**网页授权获，OAuth2.0请求接口地址*/
	public static final String oauth_page_connect_url = "https://open.weixin.qq.com/connect/oauth2/authorize";
	/**微信JS接口的临时票据获取地址*/
	public static final String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	private static String GLOBAL_ACCESS_TOKEN = null;//全局token
	private static String JS_TICKET = null;
	private static Object lock_global_token = new Object();
	private static Object lock_js_ticket = new Object();
	
	public static void installAcessToken() {
		GLOBAL_ACCESS_TOKEN = getGlobalAccessTokenByRequest();
	}
	
	public static String getGlobalAccessToken() {
		if (StringUtils.isEmpty(GLOBAL_ACCESS_TOKEN)) {
			synchronized (lock_global_token) {
				while (StringUtils.isEmpty(GLOBAL_ACCESS_TOKEN)) {
					installAcessToken(); 
				}
			}
		}
		return GLOBAL_ACCESS_TOKEN;
	}
	
	public static void installJsTicket() {
		JsApiTicket jt = getJsApiTicketFromTX();
		if ( null != jt ) {
			JS_TICKET = jt.getTicket();
		}
	}
	
	
	public static String getJsTicket() {
		if (StringUtils.isEmpty(JS_TICKET)) {
			synchronized (lock_js_ticket) {
				while (StringUtils.isEmpty(JS_TICKET)) {
					installJsTicket(); 
				}
			}
		}
		return JS_TICKET;
	}
	
	/**
	 * 
	 * @Description: 网页授权，通过code换取网页授权access_token
	 * @param code
	 * @return
	 */
	public static OAuthAccessToken getOAuthAccessToken(String code) {
		OAuthAccessToken oaat = null;
		StringBuilder getUrl = new StringBuilder();
		getUrl.append(oauth_access_token_url)
			.append("?appid=").append(EnvPropertiesConfiger.getValue("weixin_appid"))
			.append("&secret=").append(EnvPropertiesConfiger.getValue("weixin_secret"))
			.append("&code=").append(code)
			.append("&grant_type=authorization_code");
		try {
			HttpGet httpget = new HttpGet(getUrl.toString());
			HttpResponse response = HttpUtil.execute(httpget);
			String result = EntityUtils.toString(response.getEntity());
			//logger.info("request success, result = {}.", result);
			oaat = OAuthAccessToken.analyticToken(result);
		} catch (Exception e) {
			//logger.error("请求腾讯获取oauth_access_token失败:" + e.toString(), e);
		}
		return oaat;
	}
	
	/**
	 * 
	 * @Description: 网页授权，获取用户信息
	 * @param access_token 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 * @param openId
	 * @return
	 */
	public static OAuthUserInfo getOAuthUserInfo(String access_token, String openId) {
		OAuthUserInfo userinfo = null;
		StringBuilder getUrl = new StringBuilder();
		getUrl.append(oauth_user_info_url)
			.append("?access_token=").append(access_token)
			.append("&openid=").append(openId)
			.append("&lang=zh_CN");
		try {
			HttpGet httpget = new HttpGet(getUrl.toString());
			HttpResponse response = HttpUtil.execute(httpget);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			//logger.info("request success, result = {}.", result);
			userinfo = OAuthUserInfo.analytic(result);
		} catch (Exception e) {
			//logger.error("请求腾讯获取oauth_access_token失败:" + e.toString(), e);
		}
		return userinfo;
	}
	
	public static void main(String[] args) {
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=jYEbE2U6RTuhMRYC3VYtZXrqFdA-ZUchcIJeLE4EY5mZQPvVtFeiPKB3hYIaRy6d1CLX8Nzk3gQ9CUP7msoJTBwNwZ5-HxrgVTfqjvo_VO8&openid=o7P8uxJVRBZFh7Z9FKj2liylbpwY&lang=zh_CN";
		HttpGet httpget = new HttpGet(url);
	}
	
	public static String getGlobalAccessTokenByRequest() {
		GlobalAccessToken token = getAccessTokenFromTX();
		return token.getAccess_token();
	}
	/**
	 * @Description:获取access_token
	 * @return access_token
	 */
	private static GlobalAccessToken getAccessTokenFromTX() {
		GlobalAccessToken access_token = null;
		// 创建请求
		HttpGet httpget = new HttpGet(access_token_url);
		try {
			HttpResponse response = HttpUtil.execute(httpget);
			String result = EntityUtils.toString(response.getEntity());
			log.info("request success, result = {}."+result);
			access_token = GlobalAccessToken.analyticToken(result);
		} catch (Exception e) {
			log.error("request access_token fail.", e);
		}
		return access_token;
	}
	
	/**
	 * 
	 * @Description: 获取jsapi_ticket
	 * @return
	 */
	public static JsApiTicket getJsApiTicketFromTX() {
		JsApiTicket jsapi_ticket = null;
		StringBuilder getUrl = new StringBuilder();
		getUrl.append(jsapi_ticket_url)
			.append("?access_token=").append(getGlobalAccessToken())
			.append("&type=jsapi");
		HttpGet httpget = new HttpGet(getUrl.toString());
		try {
			HttpResponse response = HttpUtil.execute(httpget);
			String result = EntityUtils.toString(response.getEntity());
			//logger.info("request jsapi success, result = {}.", result);
		    jsapi_ticket = JsApiTicket.analytic(result);
		} catch (Exception e) {
			//logger.error("request jsapi_ticket fail.", e);
		}
		return jsapi_ticket;
	}
	
	/**
	 * 
	 * @Description: 网页授权，生成OAuth2.0接口请求地址
	 * @param callbackUrl 回调地址
	 * @param scope 应用授权作用域
	 * @param state 回调地址会带上state参数，可以填写a-zA-Z0-9的参数值，最多128字节
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getOAuthURL(String callbackUrl, ScopeType scope, String state)
			throws UnsupportedEncodingException {
		StringBuilder url = new StringBuilder();
		url.append(oauth_page_connect_url)
			.append("?appid=").append(EnvPropertiesConfiger.getValue("weixin_appid"))
			.append("&redirect_uri=").append(URLEncoder.encode(callbackUrl, "UTF-8"))
			.append("&response_type=code&scope=").append(scope.name());
		if(state != null) {
			url.append("&state=").append(state);
		}
			url.append("#wechat_redirect");
		return url.toString();
	}
	
	/**
	 *
	  * @Description: js SDK接口注入权限验证配置
	  * @author lining
	  * @date 2015年6月8日 下午3:33:46
	  * @param url 网页的URL，不包含#及其后面部分
	  * @return
	 * @throws Exception 
	 */
	public static JsConfigInfo createJsConfig(String url) throws Exception {
		if(url == null || url.equals("")) {
			return new JsConfigInfo();
		}
		String[] urls = url.split("#");
		JsConfigInfo jsConfig = new JsConfigInfo();
		jsConfig.setAppid(EnvPropertiesConfiger.getValue("weixin_appid"));
		String timestamp = Long.toString(new Date().getTime());
		String nonceStr = PrimaryKeyUtil.getRandomString();
		if (timestamp.length() > 10) {
			timestamp = timestamp.substring(0,10);
		}
		jsConfig.setTimestamp(timestamp);
		jsConfig.setNonceStr(nonceStr);
		String jsapi_ticket = getJsTicket();
		
	   	StringBuilder sb = new StringBuilder();
	   	sb.append("jsapi_ticket=").append(jsapi_ticket).append("&")
	   	       .append("noncestr=").append(nonceStr).append("&")
	   	       .append("timestamp=").append(timestamp).append("&")
	   	       .append("url=").append(urls[0].replaceAll("\\s*", ""));
	   	jsConfig.setSignature(Sha1Util.getSignature(sb.toString()));
//	   	SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
//		parameters.put("jsapi_ticket", jsapi_ticket);
//		parameters.put("noncestr", nonceStr);
//		parameters.put("timestamp", timestamp);
//		parameters.put("url", urls[0].replaceAll("\\s*", ""));
//		String sign = PayCommonUtil.createSign(parameters,false);
//		jsConfig.setSignature(sign);
		return jsConfig;
	}
	
}
