package com.csy.live.http.util.msg;

import org.springframework.stereotype.Component;

@Component
public class SendMsgUtil {

	public String sendMsg(String mobile, String content) {
		WebServiceSoap soap = new WebService().getWebServiceSoap();
		return soap.sendSMSEx("SDK-BBX-010-20982", "7c7-e630", mobile, content + "【思埠快购】", "7");
	}

	public static void main(String[] args) {
		System.out.println(new SendMsgUtil().sendMsg("13112210725", "找回密码"));
	}
}
