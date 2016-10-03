package com.simple.weixin.pay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.JDOMException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.simple.common.config.EnvPropertiesConfiger;
import com.simple.common.util.PrimaryKeyUtil;
import com.simple.weixin.refund.ClientCustomSSL;

public class WeiXinPay {

	public static WeiXinPrePayResult pay(HttpServletRequest request,String openId,String orderNo,double money) throws JDOMException, IOException {
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", EnvPropertiesConfiger.getValue("weixin_appid"));
		parameters.put("mch_id", EnvPropertiesConfiger.getValue("mch_id"));
		parameters.put("nonce_str", PrimaryKeyUtil.getRandomString());
		parameters.put("body", EnvPropertiesConfiger.getValue("mch_name")+"-"+EnvPropertiesConfiger.getValue("goods_type_name"));
		parameters.put("out_trade_no", orderNo);
		parameters.put("total_fee", String.valueOf(Integer.parseInt(new DecimalFormat("0").format(money*100))));
		//parameters.put("spbill_create_ip",PayCommonUtil.getIpAddr(request));
		parameters.put("spbill_create_ip",PayCommonUtil.getRemoteHost(request));
		parameters.put("notify_url", EnvPropertiesConfiger.getValue("notify_url"));
		parameters.put("trade_type", "JSAPI");
		parameters.put("openid", openId);
		String sign = PayCommonUtil.createSign(parameters,true);
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		String result =PayCommonUtil.httpsRequest(EnvPropertiesConfiger.getValue("unified_order_url"), "POST", requestXML);
		Map<String, String> map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
		return JSON.parseObject(JSON.toJSONString(map),WeiXinPrePayResult.class);
	}
	
	public static WeiXinPayConfig getPayConfig(WeiXinPrePayResult result,HttpServletRequest request) {
		SortedMap<Object,Object> params = new TreeMap<Object,Object>();
        params.put("appId", result.getAppid());
        String timeStamp = Long.toString(new Date().getTime());
        params.put("timeStamp", timeStamp);
        String nonceStr = PrimaryKeyUtil.getRandomString();
        params.put("nonceStr", nonceStr);
        params.put("package", "prepay_id="+result.getPrepay_id());
        params.put("signType", "MD5");
        String paySign =  PayCommonUtil.createSign(params,true);
        params.put("packageValue", "prepay_id="+result.getPrepay_id());    //这里用packageValue是预防package是关键字在js获取值出错
        params.put("paySign", paySign);                                                          //paySign的生成规则和Sign的生成规则一致
        String userAgent = request.getHeader("user-agent");
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);
        params.put("agent", new String(new char[]{agent}));//微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
        
        WeiXinPayConfig wc = new WeiXinPayConfig();
        wc.setAppId(result.getAppid());
        wc.setTimeStamp(timeStamp);
        wc.setNonceStr(nonceStr);
        wc.setLpackage("prepay_id="+result.getPrepay_id());
        wc.setSignType("MD5");
        wc.setPackageValue("prepay_id="+result.getPrepay_id());
        wc.setPaySign(paySign);
        wc.setAgent(new String(new char[]{agent}));
        return wc;
	}
	
	
	public static void paySuccess(HttpServletRequest request, HttpServletResponse response) throws Exception{
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
        outSteam.close();
        inStream.close();
        String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
        Map<Object, Object> map = XMLUtil.doXMLParse(result);
        //for(Object keyValue : map.keySet()){
        //    System.out.println(keyValue+"="+map.get(keyValue));
        //}
        if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            //TODO 对数据库的操作
            response.getWriter().write(PayCommonUtil.setXML("SUCCESS", ""));   //告诉微信服务器，我收到信息了，不要在调用回调action了
            System.out.println("-------------"+PayCommonUtil.setXML("SUCCESS", ""));
        }
    }
	
	public static WeiXinRefundResult refund(String orderNo,double orderFee) throws Exception {
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", EnvPropertiesConfiger.getValue("weixin_appid"));
		parameters.put("mch_id", EnvPropertiesConfiger.getValue("mch_id"));
		parameters.put("nonce_str", PrimaryKeyUtil.getRandomString());
		parameters.put("out_trade_no", orderNo);
		parameters.put("out_refund_no", orderNo);
		parameters.put("total_fee", String.valueOf(Integer.parseInt(new DecimalFormat("0").format(orderFee*100))));
		parameters.put("refund_fee", String.valueOf(Integer.parseInt(new DecimalFormat("0").format(orderFee*100))));
		parameters.put("op_user_id", EnvPropertiesConfiger.getValue("mch_id"));
		String sign = PayCommonUtil.createSign(parameters,true);
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		String result =ClientCustomSSL.doRefund(EnvPropertiesConfiger.getValue("mch_id"), 
				EnvPropertiesConfiger.getValue("certFilePath"), EnvPropertiesConfiger.getValue("refund_order_url"), requestXML);
		//String result =WeixinRefundSSL.refund("https://api.mch.weixin.qq.com/secapi/pay/refund", "1377344802", "D://apiclient_cert.p12", requestXML);
		Map<String, String> map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
		return JSON.parseObject(JSON.toJSONString(map),WeiXinRefundResult.class);
	}
	
	
	
	
	public static void main(String[] args) {
		try {
			WeiXinRefundResult xr = refund("20160820150410011NAN2",0.01);
			System.out.println(xr.getAppid());
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
	
	
