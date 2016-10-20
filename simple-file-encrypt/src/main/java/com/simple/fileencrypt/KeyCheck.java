package com.simple.fileencrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
public class KeyCheck {

	public static boolean isValid(String fileName) throws Exception {
		String content = readFileByLines(fileName);
		//String content = "oChQdchPrejqlFRqmUALCFIlmUyP870m";
		if (null == content || content.trim().isEmpty()) {
			throw new RuntimeException("密钥内容为空");
		}
		content = DesEncrypt.decrypt(content, "simplekeyencrypt");
		//检查mac地址
		String[] cs = content.split(":");
		String macs = cs[0];
		if (null == macs || macs.trim().isEmpty()) {
			throw new RuntimeException("没有允许的mac地址");
		}
		String[] ms = macs.split(",");
		String mac = getLocalMac();
		if (null == mac || mac.trim().isEmpty()) {
			throw new RuntimeException("没有获取到本机mac地址");
		}
		boolean isvalid = false;
		for( int i = 0 ; i < ms.length; i ++) {
			if (mac.equals(ms[i])) {
				isvalid = true;
				break;
			}
		}
		return isvalid;
	}
	
	private static String getLocalMac() throws SocketException, UnknownHostException {
		//获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<mac.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			//字节转换为整数
			int temp = mac[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
	}
	
	public static String readFileByLines(String fileName) {  
		 try {
			String encoding="UTF-8";
			 File file=new File(fileName);
			 StringBuffer sb = new StringBuffer();
			 if(file.isFile() && file.exists()){ //判断文件是否存在
			     BufferedReader br=new BufferedReader(new UnicodeReader(new FileInputStream(file),encoding)); 
			     String lineTxt = null;
			     while((lineTxt = br.readLine()) != null){
			    	 sb.append(lineTxt);
			     }
			     br.close();
			 }
			 return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    } 
	
	public static void main(String[] args) {
		System.out.println(readFileByLines("C:\\Users\\zhengfy1\\Documents\\key.txt"));
	}
}
