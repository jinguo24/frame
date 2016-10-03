package com.simple.weixin.pay;

public enum PayError {
	NOAUTH("NOAUTH","商户无此接口权限:商户未开通此接口权限"),
	NOTENOUGH("NOTENOUGH","用户帐号余额不足"),
	ORDERPAID("ORDERPAID","商户订单已支付"),
	ORDERCLOSED("ORDERCLOSED","订单已关闭"),
	SYSTEMERROR("SYSTEMERROR","系统错误"),
	APPID_NOT_EXIST("APPID_NOT_EXIST","APPID不存在:参数中缺少APPID"),
	MCHID_NOT_EXIST("MCHID_NOT_EXIST","MCHID不存在:参数中缺少MCHID"),
	APPID_MCHID_NOT_MATCH("APPID_MCHID_NOT_MATCH","appid和mch_id不匹配"),
	LACK_PARAMS("LACK_PARAMS","缺少参数"),
	OUT_TRADE_NO_USED("OUT_TRADE_NO_USED","商户订单号重复:同一笔交易不能多次提交"),
	SIGNERROR("SIGNERROR","签名错误:参数签名结果不正确"),
	XML_FORMAT_ERROR("XML_FORMAT_ERROR","XML格式错误"),
	REQUIRE_POST_METHOD("REQUIRE_POST_METHOD","请使用post方法"),
	POST_DATA_EMPTY("POST_DATA_EMPTY","post数据为空"),
	NOT_UTF8("NOT_UTF8","编码格式错误:未使用指定编码格式");
	private PayError(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	private String code;
	private String msg;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public static String getErrorMsg(String code) {
		for (PayError pe : PayError.values()) {
			if (pe.getCode().equals(code)) {
				return pe.getMsg();
			}
		}
		return null;
	}
}
