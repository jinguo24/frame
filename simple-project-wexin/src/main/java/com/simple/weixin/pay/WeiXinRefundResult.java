package com.simple.weixin.pay;

import java.io.Serializable;

public class WeiXinRefundResult implements Serializable{

	private static final long serialVersionUID = 1L;

	private String return_code;//SUCCESS/FAIL
	private String return_msg;//返回信息，如非空，为错误原因,签名失败,参数格式校验错误
	
	//#############return_code为SUCCESS的时候有返回
	private String result_code;//SUCCESS/FAIL SUCCESS退款申请接收成功，结果通过退款查询接口查询,FAIL 提交业务失败
	private String err_code;//列表详见错误码列表
	private String err_code_des;//结果信息描述
	private String appid;
	private String mch_id;
	private String device_info;//微信支付分配的终端设备号，与下单一致
	private String nonce_str;
	private String sign;
	private String transaction_id;//微信订单号
	private String out_trade_no;//商户系统内部的订单号
	private String out_refund_no;//商户退款单号
	private String refund_id;//微信退款单号
	private String refund_channel;//ORIGINAL—原路退款 BALANCE—退回到余额
	private Integer refund_fee;//申请退款金额 退款总金额,单位为分,可以做部分退款
	private Integer settlement_refund_fee;//退款金额 
	private Integer total_fee;//订单金额
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getOut_refund_no() {
		return out_refund_no;
	}
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public String getRefund_channel() {
		return refund_channel;
	}
	public void setRefund_channel(String refund_channel) {
		this.refund_channel = refund_channel;
	}
	public Integer getRefund_fee() {
		return refund_fee;
	}
	public void setRefund_fee(Integer refund_fee) {
		this.refund_fee = refund_fee;
	}
	public Integer getSettlement_refund_fee() {
		return settlement_refund_fee;
	}
	public void setSettlement_refund_fee(Integer settlement_refund_fee) {
		this.settlement_refund_fee = settlement_refund_fee;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
}
