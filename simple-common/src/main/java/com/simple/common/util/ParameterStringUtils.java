package com.simple.common.util;

import org.apache.commons.lang.StringUtils;

public class ParameterStringUtils extends StringUtils{
	public static boolean isNotNull(String parameter){
		return isNotEmpty(parameter) ? 
				(!"\"\"".equals(parameter) ? 
						!"''".equals(parameter) : false ): false;
	}
	
	public static boolean isNull(String parameter){
		return isNotNull(parameter) ? false : true;
	}
}
