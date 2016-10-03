package com.simple.common.util;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;

public class POJOUtil {

	/**
	 * 将字符串转化为实体工具类
	 * @param originStr 原始字符串
	 * @param toModel 将转换的实体
	 * @return 转化实体
	 */
	public static <T> T transferPOJO(String originStr, Class<T> toModel) {
		try {
			Map<String, Object> jsonObj = JSON.parseObject(originStr);
			Map<String, Object> originModel = BeanUtils.describe(toModel.newInstance());
			for (Map.Entry<String, Object> entryJson : jsonObj.entrySet()) {
				if(originModel.containsKey(entryJson.getKey())){
					System.out.println("=============================>" + entryJson.getKey());
					originModel.put(entryJson.getKey(), entryJson.getValue());
				}
			}
			String transStr = JSON.toJSONString(originModel);
			return JSON.parseObject(transStr, toModel);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
