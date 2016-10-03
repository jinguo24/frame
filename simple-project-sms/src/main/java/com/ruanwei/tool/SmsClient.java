package com.ruanwei.tool;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import com.ruanwei.interfacej.SmsClientOverage;
import com.simple.common.config.EnvPropertiesConfiger;

public class SmsClient {

	private static final String SEND_URL = EnvPropertiesConfiger.getValue("sendUrl");//"http://120.26.244.194:8888/sms.aspx";//
	private static final String SEND_USER_ID = EnvPropertiesConfiger.getValue("sendUserId");//"7830";//
	private static final String SEND_PASSWORD = EnvPropertiesConfiger.getValue("sendPassword");//"888888";//
	private static final String SEND_ACCOUNT = EnvPropertiesConfiger.getValue("sendAccount");//"ylxx";//;
	private static final String OVERAGE_URL = EnvPropertiesConfiger.getValue("overageUrl");;
	
	public static SmsResult getOverage() {
		return SmsClientOverage.queryOverage(OVERAGE_URL, SEND_USER_ID, SEND_ACCOUNT, SEND_PASSWORD);
	}
	
	public static SmsResult sendMsg(String phone,String content) {
		return getMessageFromSms(SmsClientAccessTool.getInstance().doAccessHTTPGet(SEND_URL+"?"+getSendParam(phone,content), null));
	}
	
	private static String getSendParam(String phone,String content) {
		StringBuffer sb = new StringBuffer();
		sb.append("action=send&userid=").append(SEND_USER_ID).append("&account=").append(SEND_ACCOUNT)
		.append("&password=").append(SEND_PASSWORD).append("&mobile=").append(phone)
		.append("&content=").append("【易代销】").append(content).append("&sendTime=&extno=");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		SmsResult sr = SmsClient.sendMsg("18600671341", "请联系15210931700发货");
		System.out.println(sr.getMsg()+">>>"+sr.isSuccess());
		SmsResult sr0 = getOverage();
		System.out.println(sr0.getMsg()+">>>>"+sr0.getData());
	}
	
	private static SmsResult getMessageFromSms(String result) {
		SmsResult sr = new SmsResult();
		try {
			Document document = DocumentHelper.parseText(result);
			Element root = document.getRootElement(); 
			Element status = root.element("returnstatus");
			if ("success".equals(status.getText().toLowerCase())|| "sucess".equals(status.getText().toLowerCase())) {
				sr.setSuccess(true);
			}else {
				sr.setSuccess(false);
			}
			Element message = root.element("message");
			sr.setMsg(message.getText());
			return sr;
		} catch (DocumentException e) {
			e.printStackTrace();
			sr.setSuccess(false);
			sr.setMsg(e.getLocalizedMessage());
		}  
		return sr;
	}
	
	public static void sendAdminMsg(String content) {
		String adminPhone = EnvPropertiesConfiger.getValue("adminPhone");
		if (!StringUtils.isEmpty(adminPhone)) {
			String[] phones = adminPhone.split(",");
			for (int i = 0 ; i< phones.length ; i ++) {
				sendMsg(phones[i],content);
			}
		}
	}
	
}
