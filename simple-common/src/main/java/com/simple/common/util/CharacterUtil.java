package com.simple.common.util;

public class CharacterUtil {

	
	private static String[] filterChar = {"'","\"","\\\\","#","%3E","%3e",">","u003e"}; 
	private static String[] replaceChar = {"‘","“","＼","＃","＞","＞","＞","＞"};
	
    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     */
    public static String encode(String s)
    {
        if (s == null || s.isEmpty())
        {
            return "";
        }
        for (int i = 0; i < filterChar.length; i++) {
			String fil = filterChar[i];
			s = s.replaceAll(fil, replaceChar[i]);
		}
        return s;
    }
}
