package com.simple.common.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

public class ValidCode {
	public static final char[] code = {'a','b','c','d','e','f','g',
		  'h','i','j','k','l','m','n',
		  'o','p','q','r','s','t',
		  'u','v','w','x','y','z',
		  'A','B','C','D','E','F','G',
		  'H','I','J','K','L','M','N',
		  'O','P','Q','R','S','T',
		  'U','V','W','X','Y','Z',
		  '0','1','2','3','4',
		  '5','6','7','8','9'};
	
	public static String getValidCode() {
		StringBuffer checkcode=new StringBuffer();
		// 每循环一次，生成一位
		for(int i=0;i<code.length;i++)
		{
		  int generated=(new Random()).nextInt(62);
		  checkcode.append(code[generated]);
		}
		return checkcode.toString();
	}
	
	public static void getValidCodeImage(String validCode,HttpServletResponse response) throws IOException {
		// 创建内存图片，参数为图片的大小以及类型
		BufferedImage image = new  BufferedImage(49,14,BufferedImage.TYPE_INT_RGB);
		// 得到Graphics句柄
		Graphics  g = image.getGraphics();
		// 设置画笔颜色
		// g.setColor(Color.yellow);
		// 画背景
		g.fillRect(0,1,49,12);
		// 设置字体色
		g.setColor(Color.black);
		// 画验证码
		g.drawString(validCode,4,11);
		// 图象生效
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	
	
	
	
	
}
