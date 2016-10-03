package com.simple.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	
	public static String stringMD5(String input) {
	      try {
	         // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
	         MessageDigest messageDigest =MessageDigest.getInstance("MD5");
	         // 输入的字符串转换成字节数组
	         byte[] inputByteArray = input.getBytes();
	         // inputByteArray是输入字符串转换得到的字节数组
	         messageDigest.update(inputByteArray);
	         // 转换并返回结果，也是字节数组，包含16个元素
	         byte[] resultByteArray = messageDigest.digest();
	         // 字符数组转换成字符串返回
	         return byteArrayToHex(resultByteArray);
	      } catch (NoSuchAlgorithmException e) {
	         return null;
	      }
   	}
	/**
	 * 此处将字符串转换成大写了   
	 * @param byteArray
	 * @return
	 */
   	private static String byteArrayToHex(byte[] byteArray) {
	      // 首先初始化一个字符数组，用来存放每个16进制字符
	      char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
	      // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
	      char[] resultCharArray =new char[byteArray.length * 2];
	      // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
	      int index = 0;
	      for (byte b : byteArray) {
	         resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
	         resultCharArray[index++] = hexDigits[b& 0xf];
	      }
	      // 字符数组组合成字符串返回
	      return new String(resultCharArray);
   	}
   	
   	public static byte[] stringNormalMD5Bytes(String input) {
	      try {
	         // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
	         MessageDigest messageDigest =MessageDigest.getInstance("MD5");
	         // 输入的字符串转换成字节数组
	         byte[] inputByteArray = input.getBytes("utf-8");
	         // inputByteArray是输入字符串转换得到的字节数组
	         messageDigest.update(inputByteArray);
	         // 转换并返回结果，也是字节数组，包含16个元素
	         byte[] resultByteArray = messageDigest.digest();
	         return resultByteArray;
	         // 字符数组转换成字符串返回
	         //return normalbyteArrayToHex(resultByteArray);
	      } catch (NoSuchAlgorithmException e) {
	         return null;
	      } catch (UnsupportedEncodingException e) {
	    	  return null;
		}
 	}
   	
   	private static String normalbyteArrayToHex(byte[] byteArray) {
	      // 首先初始化一个字符数组，用来存放每个16进制字符
	      char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a', 'b', 'c', 'd', 'e', 'f'  };
	      // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
	      char[] resultCharArray =new char[byteArray.length * 2];
	      // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
	      int index = 0;
	      for (byte b : byteArray) {
	         resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
	         resultCharArray[index++] = hexDigits[b& 0xf];
	      }
	      // 字符数组组合成字符串返回
	      return new String(resultCharArray);
 	}
	   
	public static void main(String[] args) {
		   System.out.println(stringMD5("000000"));
		   //System.out.println(stringNormalMD5("000000"));
		   System.out.println(Base64.getBase64(stringNormalMD5Bytes("123456中国")));
		
		String s = "{\"User\":{\"ID\":\"6a880a50-03fe-4bc1-b368-\"},\"Sign\":\"bAOaZJzDRQX9o8dz73XibA==\"";
		System.out.println(s.substring(s.indexOf(":")+1,s.lastIndexOf(",")));
	}
	
}
