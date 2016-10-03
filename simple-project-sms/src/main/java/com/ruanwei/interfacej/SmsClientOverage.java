package com.ruanwei.interfacej;

import java.net.URLEncoder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ruanwei.tool.SmsClientAccessTool;
import com.ruanwei.tool.SmsResult;

/**
 * <p>
 * <date>2012-03-01</date><br/>
 * <span>软维提供的JAVA接口信息（短信，彩信）调用API</span><br/>
 * <span>----------查询余额-------------</span>
 * </p>
 * 
 * @author LIP
 * @version 1.0.1
 */
public class SmsClientOverage {

	/**
	 * <p>
	 * <date>2012-03-01</date><br/>
	 * <span>余额获取方法1--必须传入必填内容</span><br/>
	 * <p>
	 * 其一：发送方式，默认为POST<br/>
	 * 其二：发送内容编码方式，默认为UTF-8
	 * </p>
	 * <br/>
	 * </p>
	 * 
	 * @param url
	 *            ：必填--发送连接地址URL--比如>http://118.145.30.35/sms.aspx
	 * @param userid
	 *            ：必填--用户ID，为数字
	 * @param account
	 *            ：必填--用户帐号
	 * @param password
	 *            ：必填--用户密码
	 * @return 返回余额查询字符串
	 */
	
	public static SmsResult queryOverage(String url, String userid,
			String account, String password) {
		return getSmsResult(query(url, userid, account, password));
	}
	
	
	private static String query(String url, String userid,
			String account, String password) {

		try {
			StringBuffer sendParam = new StringBuffer();
			sendParam.append("action=overage");
			sendParam.append("&userid=").append(userid);
			sendParam.append("&account=").append(
					URLEncoder.encode(account, "UTF-8"));
			sendParam.append("&password=").append(
					URLEncoder.encode(password, "UTF-8"));

			return SmsClientAccessTool.getInstance().doAccessHTTPPost(url,
					sendParam.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "未发送，异常-->" + e.getMessage();
		}
	}
	
	private static SmsResult getSmsResult(String result) {
		SmsResult sr = new SmsResult();
		try {
			Document document = DocumentHelper.parseText(result);
			Element root = document.getRootElement(); 
			Element status = root.element("returnstatus");
			if ("success".equals(status.getText().toLowerCase()) || "sucess".equals(status.getText().toLowerCase())) {
				sr.setSuccess(true);
				StringBuffer sb = new StringBuffer();
				Element payinfo = root.element("payinfo");
				if ( null != payinfo) {
					sb.append("支付方式:"+payinfo.getText());
				}
				Element overage = root.element("overage");
				if ( null != overage) {
					sb.append(" 余额:"+overage.getText());
					sr.setData(overage.getText());
				}
				Element sendTotal = root.element("sendTotal");
				if ( null != sendTotal) {
					sb.append(" 总点数:"+sendTotal.getText());
				}
				sr.setMsg(sb.toString());
			}else {
				sr.setSuccess(false);
				Element message = root.element("message");
				sr.setMsg(message.getText());
			}
			return sr;
		} catch (DocumentException e) {
			e.printStackTrace();
			sr.setSuccess(false);
			sr.setMsg(e.getLocalizedMessage());
		}  
		return sr;
	}
}
