package com.simple.weixin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.*;

public class Sha1Util {

	static Logger logger = LoggerFactory.getLogger(Sha1Util.class);
	
	 /**
     * 验证签名。
     * 
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String getSignature(String sKey) throws Exception {
        String ciphertext = null;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(sKey.toString().getBytes());
        ciphertext = byteToStr(digest);
        return ciphertext.toLowerCase();
    }
 
 /** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param byteArray 
     * @return 
     */ 
    private static String byteToStr(byte[] byteArray) {  
        String strDigest = "";  
        for (int i = 0; i < byteArray.length; i++) {  
            strDigest += byteToHexStr(byteArray[i]);  
        }  
        return strDigest;  
    }  
  /** 
     * 将字节转换为十六进制字符串 
     *  
     * @param mByte 
     * @return 
     */ 
    private static String byteToHexStr(byte mByte) {  
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  
   
        String s = new String(tempArr);  
        return s;  
    }
    
    public static void main(String[] args) throws Exception {
    	String s = "jsapi_ticket=kgt8ON7yVITDhtdwci0qeYwbScckT5nItM2a4VdsIKR_Qut--K0v4TYLqhDksPUXry1XD8OkVDRZuPS75_38Hw&noncestr=N2bU56tuSLrAKsiqL3eYFP1By-8318&timestamp=1472883668&url=http://juxier.com/api/product/shareConfgi";
		String s0 = "1472884013360";
    	System.out.println(getSignature(s));
    	System.out.println(s0.substring(0,10));
	}
}
