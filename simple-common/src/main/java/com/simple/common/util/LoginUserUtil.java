package com.simple.common.util;

import javax.servlet.http.HttpServletRequest;

public class LoginUserUtil {

	public static Object getCurrentUser(HttpServletRequest request) {
		return  request.getSession().getAttribute("_sp_current_user");
	}
	public static void setCurrentUser(HttpServletRequest request,Object o) {
		request.getSession().setAttribute("_sp_current_user",o);
	}
	public static void removeCurrentUser(HttpServletRequest request) {
		request.getSession().removeAttribute("_sp_current_user");
	}
}
