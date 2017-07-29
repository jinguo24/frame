package com.simple.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	/**
	 * 设置 cookie
	 * @param response
	 * @param name    cookie 名字
	 * @param value   cookie 值
	 * @param maxAge  cookie 生命周期  以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");
		if(maxAge > 0)
		{
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}
	
	public static String getCookie(HttpServletRequest request,String name) {
		Cookie[] cookies = request.getCookies();
		if ( null != cookies) {
			for(Cookie cookie : cookies){
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	public static void removeCookie(HttpServletRequest request,String name,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if ( null != cookies) {
			for(Cookie cookie : cookies){
				if (name.equals(cookie.getName())) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
	}
}
