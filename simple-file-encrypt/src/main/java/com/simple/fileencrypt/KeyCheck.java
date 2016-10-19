package com.simple.fileencrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.springframework.util.StringUtils;

public class KeyCheck {

	public static boolean isValid(String fileName) throws Exception {
		String content = readFileByChars(fileName);
		if (StringUtils.isEmpty(content)) {
			throw new RuntimeException("密钥内容为空");
		}
		content = DesEncrypt.decrypt(content, Constant.KEY_ENCRYPT_DES_KEY);
		//检查mac地址
		String[] cs = content.split(":");
		String macs = cs[0];
		if (StringUtils.isEmpty(macs)) {
			throw new RuntimeException("没有允许的mac地址");
		}
		String[] ms = macs.split(",");
		String mac = getLocalMac();
		if (StringUtils.isEmpty(mac)) {
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
	
	
    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    private static String readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            StringBuffer sb = new StringBuffer();
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                	sb.append(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                        	sb.append(tempchars[i]);
                        }
                    }
                }
            }
            return sb.toString();
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }
}
