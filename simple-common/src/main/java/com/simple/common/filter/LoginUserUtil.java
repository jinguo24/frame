package com.simple.common.filter;

import javax.servlet.http.HttpServletRequest;


public class LoginUserUtil {

	public static Object getCurrentUser(HttpServletRequest request) {
		return request.getSession().getAttribute("__jizhi_sp_login_user");
	}
	
	public static void setCurrentUser(HttpServletRequest request,Object o) {
		request.getSession().setAttribute("__jizhi_sp_login_user",o);
	}
	
	public static void removeCurrentPhone(HttpServletRequest request) {
		request.getSession().removeAttribute("__jizhi_sp_login_user");
	}
}
