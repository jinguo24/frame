package com.simple.common.config;

import java.util.Properties;


public class EnvPropertiesConfiger {

	private static Properties properties;
	
	static {
		properties = PropertyConfiger.loadProperties("env");
	}
	 
	public static String getValue(String key) {
		return properties.getProperty(key);
	}
	
	public static Properties getProperties() {
		return properties;
	}
}
