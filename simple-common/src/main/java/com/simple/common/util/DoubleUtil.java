package com.simple.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleUtil {
	/**
	 * 价格，截取两位小数，不四舍五入
	 * @param value
	 * @return
	 */
	public static double formatPrice(double value) {
		int valuei = (int) (value*100);
		double valued = valuei/100.00;
		return valued;
		//BigDecimal bg = new BigDecimal(value);
        // bg.setScale(2, RoundingMode.UP).doubleValue();
	}
	
	/**
	 * 保留两位小数，四舍五入
	 * @param value
	 * @return
	 */
	public static double formatDouble(double value) {
		BigDecimal bg = new BigDecimal(value);
        return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static void main(String[] args) {
		System.out.println(formatDouble(0.016));
		System.out.println(formatPrice(30.58623));
		System.out.println(formatPrice(30.58));
		System.out.println(formatPrice(30.58123));
		System.out.println(formatPrice(30));
	}
}
