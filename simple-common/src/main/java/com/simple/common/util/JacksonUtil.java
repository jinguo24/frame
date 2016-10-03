package com.simple.common.util;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * <br> json处理类，将json与实体之间相互转化
 * @author shenjc
 *
 */
public class JacksonUtil {
	
	private static Logger logger = Logger.getLogger(JacksonUtil.class);
	
	public static final ObjectMapper mapper = new ObjectMapper();

	public static String toJson(Object obj) {
		StringWriter writer = new StringWriter();
		JsonGenerator gen;
		try {
			gen = new JsonFactory().createJsonGenerator(writer);
			mapper.writeValue(gen, obj);
			gen.close();
			String json = writer.toString();
			writer.close();
			return json;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getCause());
		}
		
		return null;
	}
	
	public static <T> T fromJson(String json, Class<T> classOfT) {
		Object object;
		try {
			object = mapper.readValue(json, classOfT);
			return (T) object;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getCause());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getCause());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getCause());
		}
		return null;
	}
	
	
	public static void main(String[] args) throws IOException {
		user test = new user();
		test.setName("abc");
		test.setSex("abc");
		String json = JacksonUtil.toJson(test);
		System.out.println(json);
		user u = JacksonUtil.fromJson(json, user.class);
		System.out.println(u.getName());
	}
}

class user {
	String name ;
	String sex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}
