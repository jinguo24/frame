package com.simple.fileencrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;

public class FileEncryptUtil {
	 Key key; 
	  public FileEncryptUtil(String str) { 
	    getKey(str);//生成密匙 
	  } 
	  /** 
	  * 根据参数生成KEY 
	  */ 
	  private void getKey(String strKey) { 
	    try { 
	        KeyGenerator _generator = KeyGenerator.getInstance("DES"); 
	        _generator.init(new SecureRandom(strKey.getBytes())); 
	        this.key = _generator.generateKey(); 
	        _generator = null; 
	    } catch (Exception e) { 
	        throw new RuntimeException("Error initializing SqlMap class. Cause: " + e); 
	    } 
	  } 

	  /** 
	  * 文件file进行加密并保存目标文件destFile中 
	  * 
	  * @param file   要加密的文件 如c:/test/srcFile.txt 
	  * @param destFile 加密后存放的文件名 如c:/加密后文件.txt 
	  */ 
	  public void encrypt(String file, String destFile) throws Exception { 
	    Cipher cipher = Cipher.getInstance("DES"); 
	    // cipher.init(Cipher.ENCRYPT_MODE, getKey()); 
	    cipher.init(Cipher.ENCRYPT_MODE, this.key); 
	    InputStream is = new FileInputStream(file); 
	    OutputStream out = new FileOutputStream(destFile); 
	    CipherInputStream cis = new CipherInputStream(is, cipher); 
	    byte[] buffer = new byte[1024]; 
	    int r; 
	    while ((r = cis.read(buffer)) > 0) { 
	        out.write(buffer, 0, r); 
	    } 
	    cis.close(); 
	    is.close(); 
	    out.close(); 
	  } 
	  /** 
	  * 文件采用DES算法解密文件 
	  * 
	  * @param file 已加密的文件 如c:/加密后文件.txt 
	  *         * @param destFile 
	  *         解密后存放的文件名 如c:/ test/解密后文件.txt 
	  */ 
	  public void decrypt(String file, String dest) throws Exception { 
	    Cipher cipher = Cipher.getInstance("DES"); 
	    cipher.init(Cipher.DECRYPT_MODE, this.key); 
	    InputStream is = new FileInputStream(file); 
	    OutputStream out = new FileOutputStream(dest); 
	    CipherOutputStream cos = new CipherOutputStream(out, cipher); 
	    byte[] buffer = new byte[1024]; 
	    int r; 
	    while ((r = is.read(buffer)) >= 0) { 
	        cos.write(buffer, 0, r); 
	    } 
	    cos.close(); 
	    out.close(); 
	    is.close(); 
	  } 
	  
	  public String getFileSubfix(String filepath) {
		  return filepath.substring(filepath.lastIndexOf(".")+1);
	  }
	  
	  
	  public static void main(String[] args) throws Exception { 
		  FileEncryptUtil td = new FileEncryptUtil("aaa"); 
	    td.encrypt("C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版.pdf", "C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密.pdf"); //加密 
	    //td.decrypt("D:/加密后文件1.pdf", "D:/解密后文件1.pdf"); //解密 
	    //System.out.println(td.getFileSubfix("C:/werwerwerwer/asdfafdsasdf/123123/rwerwer/12e78.jpg"));
	  } 
	 
}
