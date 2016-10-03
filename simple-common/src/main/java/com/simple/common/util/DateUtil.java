package com.simple.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateUtil {
	public static String date2String(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String date2AllString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	public static String date2StringWhitNoSpilt(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
	}
	public static String getNowWeekBegin() {
		Calendar cd = Calendar.getInstance();
		setToFirstDay(cd);
		//cd.add(Calendar.DATE, 1);
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		return date2AllString(cd.getTime());
	}
	private static void setToFirstDay(Calendar calendar) {
	        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
	            calendar.add(Calendar.DATE, -1);
	        }
	}
	
	public static String getNowWeekEnd() {
		Calendar cd = Calendar.getInstance();
		setToFirstDay(cd);
		cd.add(Calendar.DATE, 5);
		cd.set(Calendar.DAY_OF_MONTH, cd.get(Calendar.DAY_OF_MONTH)+1);
		cd.set(Calendar.HOUR_OF_DAY, 23);
		cd.set(Calendar.MINUTE, 59);
		cd.set(Calendar.SECOND, 59);
		return date2AllString(cd.getTime());
	}
	
	public static String getNowMonthBegin() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_MONTH, 1);
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		return date2AllString(cd.getTime());
	}
	
	public static String getNowMonthEnd() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_MONTH, cd.getActualMaximum(Calendar.DAY_OF_MONTH));
		cd.set(Calendar.HOUR_OF_DAY, 23);
		cd.set(Calendar.MINUTE, 59);
		cd.set(Calendar.SECOND, 59);
		return date2AllString(cd.getTime());
	}
	
	public static int getNowMonth() {
		Calendar cd = Calendar.getInstance();
		return cd.get(Calendar.MONTH)+1;
	}
	
	public static Date getNewDateBySeconds(Date date,int seconds) {
		if (seconds > 0 ) {
			return new Date(date.getTime() + Math.abs(seconds) * 1000);
		}else {
			return new Date(date.getTime() - Math.abs(seconds) * 1000);
		}
	}
	
	public static Date getNewDateByMinutes(Date date,int minutes) {
		return getNewDateBySeconds(date,60*minutes);
	}
	
	public static Date getNewDateByHours(Date date,int hours) {
		return getNewDateBySeconds(date,60*60*hours);
	}
	
	public static Date getNewDateByDays(Date date,int days) {
		return getNewDateBySeconds(date,24*60*60*days);
	}
	
	public static void main(String[] args) {
		System.out.println(getNowWeekBegin());
		System.out.println(getNowWeekEnd());
		System.out.println(getNowMonthBegin());
		System.out.println(getNowMonthEnd());
		System.out.println(getNowMonth());
		System.out.println(getNewDateByDays(new Date(),1));
	}
}
