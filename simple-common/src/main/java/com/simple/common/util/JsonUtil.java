package com.simple.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

/**
 * 
 * @author kangjie
 *
 */
public class JsonUtil {
	private static Logger logger = Logger.getLogger(JsonUtil.class);
	public static final ObjectMapper OM = new ObjectMapper();
	static{
		OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	public static JavaType assignList(Class<? extends Collection> collection, Class<? extends Object> object) {
		return JsonUtil.OM.getTypeFactory().constructParametricType(collection, object);
	}
	public static <T> ArrayList<T> readValuesAsArrayList(String key, Class<T> object) {
		ArrayList<T> list = null;
		try {
			list = OM.readValue(key, assignList(ArrayList.class, object));
		} catch (JsonParseException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	public static String toJson(Object obj){
		try {
			return OM.writeValueAsString(obj);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	public static <T> T fromJson(String json, Class<T> clazz){
		try {
			return OM.readValue(json, clazz);
		} catch (JsonParseException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
