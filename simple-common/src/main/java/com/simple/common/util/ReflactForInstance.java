package com.simple.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ReflactForInstance {

	private static final String MODEL_CLASS_PATH = "com.lenovo.fis.model.";
	
	public static final String COMMON_DATA_FORMATE = "yyyy-MM-dd HH:mm:ss";

	private static Logger logger = Logger.getLogger(ReflactForInstance.class);
	
	public static Object createAndPutValue(String className, Map<String,String> values) throws Exception {
		if(values == null)
		{
			return null;
		}
		Object object = null;
		try {
			Class<?> model = Class.forName(MODEL_CLASS_PATH + className);
			object = reflactToEntity(model, null, values);
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return object;
	}
	
	
	public static <T> Object reflactToEntity(Class<T> cls, Object object, Map<String,String> values) throws Exception
	{
		String fieldName = "";
		Class<?> fieldType = null;
		String fieldValue = "";
		try {
			Class<?> model = cls;
			object = object == null ? model.newInstance() : object;
			Class<?> parentClass = model.getSuperclass();
			if(parentClass != null)
			{
				reflactToEntity(parentClass, object, values);
			}
			Field[] fields = model.getDeclaredFields();
			Set<String> set = values.keySet();
			for(Field field : fields){
				fieldName = field.getName();
				fieldType = field.getType();
				for(String key : set){
					if(fieldName.toLowerCase().equals(key.toLowerCase())){
						Method fieldSetMethod = model.getDeclaredMethod("set" + convertFirstCharToUpper(key), fieldType);
						fieldValue = values.get(key);
						setEntityFieldValue(fieldSetMethod,object,fieldType,values.get(key));
						break;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(String.format("Error:数据类型转换失败，columnName=【"+fieldName+"】，fieldType=【"+fieldType.getName()+"】，fieldValue=【"+fieldValue+"】 messge：【%s】", e.getMessage()));
			throw new Exception(String.format("Error:数据类型转换失败，columnName=【"+fieldName+"】，fieldType=【"+fieldType.getName()+"】，fieldValue=【"+fieldValue+"】 messge：【%s】", e.getMessage()));
		}
		return object;
	}
	
	private static String convertFirstCharToUpper(String field){
		if(StringUtils.isNotBlank(field)){
			return field.substring(0, 1).toUpperCase() + field.substring(1, field.length());
		}
		return field;
	}
	
	public static void setEntityFieldValue(Method method, Object entity, Class<?> fieldType, String value) throws Exception{
		SimpleDateFormat sf = new SimpleDateFormat(COMMON_DATA_FORMATE);
		if(fieldType.getName().equals("int")){
			if(StringUtils.isNotEmpty(value)){
				method.invoke(entity, Integer.valueOf(value));
			}
		}else if(fieldType.getName().equals("long")){
			if(StringUtils.isNotEmpty(value)){
				method.invoke(entity, Long.valueOf(value));
			}
		}else if(fieldType.getName().equals("java.lang.String")){
			if(StringUtils.isNotEmpty(value)){
				value = value.trim();
				method.invoke(entity, value);
			}
		}else if(fieldType.getName().equals("java.sql.Date") || fieldType.getName().equals("java.util.Date")){
			if(StringUtils.isNotEmpty(value)){
				method.invoke(entity, value.contains(":") ? sf.parse(value) : new Date(Long.valueOf(value)));
			}
		}else if(fieldType.getName().equals("java.math.BigDecimal")){
			if(StringUtils.isNotEmpty(value)){
				method.invoke(entity, new BigDecimal(value));
			}
		}else if(fieldType.getName().equals("byte")){
			if(StringUtils.isNotEmpty(value)){
				method.invoke(entity, Byte.valueOf(value));
			}
		}else if(fieldType.getName().equals("java.lang.Integer")){
			if(StringUtils.isNotEmpty(value)){
				method.invoke(entity, Integer.valueOf(value));
			}
		}else if(fieldType.getName().equals("java.lang.Double")){
			if(StringUtils.isNotEmpty(value)){
				method.invoke(entity, Double.valueOf(value));
			}
		}else if(fieldType.getName().equals("java.lang.Long")){
			if(StringUtils.isNotEmpty(value)){
				method.invoke(entity, Long.valueOf(value));
			}
		}else{
			throw new Exception("未知的数据类型：" + fieldType.getName());
		}
	}
}
