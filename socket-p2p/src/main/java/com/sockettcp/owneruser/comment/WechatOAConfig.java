package com.sockettcp.owneruser.comment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WechatOAConfig {
	
	public static final String APP_ID = "wx120fed0b96c13248";
	public static final String secret = "6378a36e4588df7201ecae3ad00c52f9";
	public static final String mch_id = "1502561711";
	
	
	private static Logger logger = LoggerFactory.getLogger(WechatOAConfig.class);
	 


	public static AccessToken getAccessToken(String code) {
		AccessToken accessToken = null;
		Map<String, Object> message = new HashMap<>();
		String uri = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APP_ID+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
		JSONObject parseObject = JSON.parseObject(NetUtil.get(uri));
		 if (null != parseObject) {
			  try {
				  accessToken = new AccessToken();
				  accessToken.setAccess_token(parseObject.getString("access_token"));
				  accessToken.setExpires_in(parseObject.getString("expires_in"));
				  accessToken.setRefresh_token(parseObject.getString("refresh_token"));
				  accessToken.setOpenid(parseObject.getString("openid"));
				  accessToken.setScope(parseObject.getString("scope"));
			} catch (Exception e) {
				accessToken = null;
				int errorCode = parseObject.getInteger("errcode");
				String errorMsg = parseObject.getString("errmsg");
				logger.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		 }
		
		return accessToken;
	}



	public static WechatUserInfo getUserInfo(String access_token, String openid) {
		WechatUserInfo wechatUserInfo = null;
		Map<String, Object> message = new HashMap<>();
		String uri = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
		JSONObject parseObject = JSON.parseObject(NetUtil.get(uri));
		if (null != parseObject) {
			try {
				wechatUserInfo = new WechatUserInfo();
				wechatUserInfo.setOpenid(parseObject.getString("openid"));
				// 昵称
				wechatUserInfo.setNickname(parseObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				wechatUserInfo.setSex(parseObject.getInteger("sex"));
				// 用户所在国家
				wechatUserInfo.setCountry(parseObject.getString("country"));
				// 用户所在省份
				wechatUserInfo.setProvince(parseObject.getString("province"));
				// 用户所在城市
				wechatUserInfo.setCity(parseObject.getString("city"));
				// 用户头像
				wechatUserInfo.setHeadimgurl(parseObject.getString("headimgurl"));
				// 用户特权信息
				List<String> list = JSON.parseArray(parseObject.getString("privilege"),String.class);
				wechatUserInfo.setPrivilegeList(list);
				//与开放平台共用的唯一标识，只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
				wechatUserInfo.setUnionid(parseObject.getString("unionid"));
			} catch (Exception e) {
				wechatUserInfo = null;
				int errorCode = parseObject.getInteger("errcode");
				String errorMsg = parseObject.getString("errmsg");
				logger.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return wechatUserInfo;
	}



	public static String getAccessToken() {
		String access_token = null;
		String uri = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APP_ID+"&secret="+secret;
		try {
			URL urlGet = new URL(uri);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = JSON.parseObject(message);
			access_token = demoJson.getString("access_token");
			is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		return access_token;
	}
	

	public static String getJsapiTicket(String access_token) {
		String ticket = null;
		//String accessToken = getAccessToken();
		String uri = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
		 try {
				URL urlGet = new URL(uri);
				HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
				http.setRequestMethod("GET"); // 必须是get方式请求
				http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				http.setDoOutput(true);
				http.setDoInput(true);
				System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
				System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
				http.connect();
				InputStream is = http.getInputStream();
				int size = is.available();
				byte[] jsonBytes = new byte[size];
				is.read(jsonBytes);
				String message = new String(jsonBytes, "UTF-8");
				JSONObject demoJson = JSON.parseObject(message);
				ticket = demoJson.getString("ticket");
				is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}

	
	
}