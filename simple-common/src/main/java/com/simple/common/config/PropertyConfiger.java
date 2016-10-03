package com.simple.common.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class PropertyConfiger {

	private PropertyConfiger() {
		
	}
	
	private static final Log LOGGER = LogFactory.getLog(PropertyConfiger.class);
	private static final Map<String , Properties> propertiesMap = new ConcurrentHashMap<String , Properties>();
	
	
	/**
	 * 读取配置文件里面的信息，如果没有加载过该配置文件，提示错误，返回空，需要手动先调用loadProperties
	 * @param filepath
	 * @param key
	 * @return
	 */
	public static String getPropertiesValue(String filepath,String key) {
		if(propertiesMap.containsKey(filepath)) {
			return StringUtils.trimToNull((String)propertiesMap.get(filepath).get(key));
		}else {
			LOGGER.error("Properties config error ! :"+filepath+" is not loaded, please call PropertiesConfiger.loadProperties make the file loaded.");
			return null;
		}
	}
	
	/**
	 * 加载配置文件，如果该配置已经加载，则不再加载
	 * @param filepath
	 */
	public static Properties loadProperties(String filepath) {
		String filepathKey = filepath;
		if (!filepath.endsWith(".properties")) {
			filepath = filepath+".properties";
		}
		if (propertiesMap.containsKey(filepathKey)) {
			LOGGER.warn("PropertiesConfiger.loadProperties "+filepathKey+" ignored, is already loaded. if want reload, please first call PropertiesConfiger.clearProperties." );
			return propertiesMap.get(filepathKey);
		}else {
			InputStreamReader in = null;
			try {
				Properties pro = new Properties();
				in = new InputStreamReader(PropertyConfiger.class.getClassLoader().getResourceAsStream(filepath), "UTF-8");
				pro.load(in);
				propertiesMap.put(filepathKey, pro);
				return pro;
			} catch (IOException e) {
				LOGGER.error("Properties config error",e);
			} finally {
				try {
					if ( null != in) {
						in.close();
					}
				} catch (IOException e) {
					LOGGER.error("Properties config error",e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 清空加载的配置文件
	 * @param filepath
	 */
	public static void clearProperties(String filepath) {
		if (propertiesMap.containsKey(filepath)) {
			propertiesMap.remove(filepath);
		}else {
			LOGGER.warn("PropertiesConfiger.clearProperties "+filepath+" ignored, is not loaded." );
		}
	}
	
}
