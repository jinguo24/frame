package com.simple.weixin.pay;

import java.io.Serializable;

public class WeixinRefundQueryResult implements Serializable{

	private static final long serialVersionUID = 1L;

	private String return_code;//SUCCESS/FAIL
	private String return_msg;//返回信息 如非空，为错误原因,签名失败,参数格式校验错误
	private String result_code;//SUCCESS退款申请接收成功，结果通过退款查询接口查询
	private String err_code;
	private String err_code_des;
	private String appid;
	private String mch_id;
	private String device_info;
	private String nonce_str;
	private String sign;
	private String transaction_id;//微信订单号
	private String out_trade_no;//商户系统内部的订单号
	private int total_fee;//订单总金额，单位为分，只能为整数
	private int settlement_total_fee;//
	private int refund_count;
	private String out_refund_no_$n;//商户退款单号
	private String refund_id_$n;//微信退款单号
	private String refund_fee_$n;//退款总金额,单位为分,可以做部分退款
	private String refund_channel_$n;//ORIGINAL—原路退款, BALANCE—退回到余额
	/**
	 * SUCCESS—退款成功
	 * FAIL—退款失败 
	 * PROCESSING—退款处理中 
	 * NOTSURE—未确定，需要商户原退款单号重新发起 
	 * CHANGE—转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款
	 */
	private String refund_status_$n;
	
	
}
