package com.simple.common.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.StringUtils;

public class PrimaryKeyUtil {
	
	private final static String STATIC_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static AtomicInteger sequence = new AtomicInteger();
	/**
	 * 获得一个UUID
	 * @return String UUID
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
	/**
	 * 获得指定数目的UUID
	 * @param number 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number){
		if(number < 1){
			return null;
		}
		String[] uuids = new String[number];
		for(int i=0;i<number;i++){
			uuids[i] = getUUID();
		}
		return uuids;
	}
	
	public static String getRandomString()
	{
		String randomStr = "";
		Random random = new Random();
		
		for(int i=0;i<25;i++)
		{
			randomStr += STATIC_STR.charAt(random.nextInt(62));
		}
		String time = System.currentTimeMillis()+"";
		randomStr += "-" + time.substring(time.length()-4);
		return randomStr;
	}
	
	public static String getId() {
		//读取环境变量配置 ，在web服务器中配置机器编号
		String machine = System.getProperty("SIMPLE_MACHINE_SEQUENCE");
		if (StringUtils.isEmpty(machine)) {
			machine = "NAN";
		}
		int no = sequence.incrementAndGet();
		return DateUtil.date2StringWhitNoSpilt(new Date())+machine+no;
	}

}
