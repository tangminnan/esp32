package com.sockettcp.owneruser.comment;

import java.util.HashMap;
import java.util.Map;


public class WechatShareUtil {

	
	public static Map<String, Object> getWinXinEntity(String url) {
		Map<String, Object> map = new HashMap<>();
		JsapiTicket ticketDO = new JsapiTicket();
		String accessToken = WechatOAConfig.getAccessToken();
		String ticket = WechatOAConfig.getJsapiTicket(accessToken);
		Map<String, String> ret = Sign.sign(ticket, url);
		// System.out.println(ret.toString());
		ticketDO.setTicket(ret.get("jsapi_ticket"));
		ticketDO.setSignature(ret.get("signature"));
		ticketDO.setNoncestr(ret.get("nonceStr"));
		ticketDO.setTimestamp(ret.get("timestamp"));
		map.put("data", ticketDO);
		System.out.println("\n\n" + ret.toString() + "\n\n");
		return map;
		
	}
	

}
