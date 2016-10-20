package com.simple.fileencrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesEncrypt {

	/** 
     * 数据加密，算法（DES） 
     * 
     * @param data 
     *            要进行加密的数据 
     * @return 加密后的数据 
     */  
	public static String encrypt(String data,String keystr) {  
        String encryptedData = null;  
        try {  
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(keystr.getBytes("UTF-8"));  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey key = keyFactory.generateSecret(deskey);  
            // 加密对象  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);  
            // 加密，并把字节数组编码成字符串  
            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes("UTF-8")));  
        } catch (Exception e) {  
//            log.error("加密错误，错误信息：", e);  
            throw new RuntimeException("加密错误，错误信息：", e);  
        }  
        return encryptedData;  
    }  
	
	/** 
     * 数据解密，算法（DES） 
     * 
     * @param cryptData 
     *            加密数据 
     * @return 解密后的数据 
     */  
    public static String decrypt(String cryptData,String keystr) {  
        String decryptedData = null;  
        try {  
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();  
            DESKeySpec deskey = new DESKeySpec(keystr.getBytes());  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey key = keyFactory.generateSecret(deskey);  
            // 解密对象  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.DECRYPT_MODE, key, sr);  
            // 把字符串解码为字节数组，并解密  
            decryptedData = new String(cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(cryptData)));  
        } catch (Exception e) {  
//            log.error("解密错误，错误信息：", e);  
            throw new RuntimeException("解密错误："+e.getLocalizedMessage(), e);  
        }  
        return decryptedData;  
    }  
	
	
	//测试
	public static void main(String args[]) {
		//待加密内容
		String str = "28-D2-44-FB-3D-0B";
		//密码，长度要是8的倍数
		String password = "simplekeyencrypt";
		try {
			String s = encrypt(str,password);
			System.out.println(s);
			System.out.println(decrypt(s,password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//byte[] result = DesEncrypt.encrypt(str.getBytes(),password);
		//System.out.println("加密后："+new String(result));
	
		//直接将如上内容解密
		//try {
		//	byte[] decryResult = DesEncrypt.decrypt(result, password);
		//	System.out.println("解密后："+new String(decryResult));
		//} catch (Exception e1) {
		//	e1.printStackTrace();
		//}
	}
	
}
