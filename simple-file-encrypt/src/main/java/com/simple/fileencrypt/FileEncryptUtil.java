package com.simple.fileencrypt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	  * @param addtional 额外的字符串，加到文件头部
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
		  * 文件file进行加密并保存目标文件destFile中 
		  * 
		  * @param file   要加密的文件 如c:/test/srcFile.txt 
		  * @param destFile 加密后存放的文件名 如c:/加密后文件.txt 
		  * @param addtional 额外的字符串，加到文件头部
		  */ 
	  public void encrypt(InputStream is, String destFile) throws Exception { 
		    Cipher cipher = Cipher.getInstance("DES"); 
		    // cipher.init(Cipher.ENCRYPT_MODE, getKey()); 
		    cipher.init(Cipher.ENCRYPT_MODE, this.key); 
		    //InputStream is = new FileInputStream(file); 
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
	  * @param destFile 解密后存放的文件名 如c:/ test/解密后文件.txt 
	  * @param remover 去掉的字符串
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
	  
	  /**
	   * 从文件头加入字符
	   * @param file
	   * @param dest
	   * @param str
	   * @throws Exception
	   */
	  public void addStr(String file,String dest,String str) throws Exception {
		  InputStream is = new FileInputStream(file); 
		  OutputStream out = new FileOutputStream(dest); 
		  byte[] buffer = new byte[1024]; 
		  int r; 
		  out.write(str.getBytes());
		  while ((r = is.read(buffer)) >= 0) { 
			  out.write(buffer, 0, r); 
		  } 
		  out.close(); 
		  is.close();
	  }
	  
	  /**
	   * 从文件头去掉字符
	   * @param file
	   * @param dest
	   * @param str
	   * @throws Exception
	   */
	  public void removeStr(String file,String dest,String str) throws Exception {
		  InputStream is = new FileInputStream(file); 
		  OutputStream out = new FileOutputStream(dest); 
		  byte[] buffer = new byte[1024]; 
		  int r; 
		  int length = str.length();
		  int count = 0 ;
		  while ((r = is.read(buffer)) >= 0) { 
			  if (count == 0 && length > 0 ) {
				  out.write(buffer, length, r-length); 
			  }else {
				  out.write(buffer, 0, r); 
			  }
			  count ++;
		  } 
		  out.close(); 
		  is.close();
	  }
	  
	  /**
	   * 从头开始取出字符
	   * @param length
	 * @throws Exception 
	   */
	  public String getStr(String file,int length) throws Exception {
		  InputStream is = new FileInputStream(file);
		  int r;
		  int count = 0;
		  StringBuffer s = new StringBuffer();
		  while ((r = is.read()) > 0) { 
			  	if (count>=length) {
			  		break;
			  	}
		        s.append((char)r); 
		        count ++;
		  }
		  is.close(); 
		  return s.toString();
	  }
	  
	  public String getFileSubfix(String filepath) {
		  return filepath.substring(filepath.lastIndexOf(".")+1);
	  }
	  
	  public static void main(String[] args) throws Exception { 
		  FileEncryptUtil td = new FileEncryptUtil("simplefileencryptkey"); 
		  td.encrypt("C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版.pdf", "C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密.pdf"); //加密 
		  //td.addStr("C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版.pdf", "C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密_add.pdf", ":MAC:E8-B1-FC-3A-3C-70");
		  //td.removeStr("C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密_add.pdf", "C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密_add_remove.pdf", ":MAC:E8-B1-FC-3A-3C-70");
		  //System.out.println(td.getStr("C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密_add.pdf", 10));
		  //td.decrypt("C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密.pdf", "C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密_解密.pdf",null); //解密
	    //td.encrypt("C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密.pdf", "C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密.pdf",":MAC:"); //加密 
	    //td.decrypt("C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密.pdf", "C:\\Users\\zhengfy1\\Desktop\\PMBOK2008中文版_加密_解密.pdf"); //解密 
	    //System.out.println(td.getFileSubfix("C:/werwerwerwer/asdfafdsasdf/123123/rwerwer/12e78.jpg"));
	  } 
	 
}
