package com.sockettcp.owneruser.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.sockettcp.carousel.domain.BannerDO;
import com.sockettcp.carousel.service.BannerService;
import com.sockettcp.common.annotation.Log;
import com.sockettcp.common.controller.BaseController;
import com.sockettcp.common.utils.MD5Utils;
import com.sockettcp.common.utils.ShiroUtils;
import com.sockettcp.owneruser.comment.GenerateCode;
import com.sockettcp.owneruser.domain.UserDO;
import com.sockettcp.owneruser.service.UserService;
import com.sockettcp.smsservice.service.ISMSService;




@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ISMSService sMSService;
    @Autowired
	private BannerService bannerService;
 
	
    @Log("请求访问主页")
	@GetMapping({ "" })
	String indexs(Model model) {
		
		// 查询banner数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isEnable", "0");
		List<BannerDO> bannerList = bannerService.list(params);
		model.addAttribute("bannerList", bannerList);
		
		return "shouye";
	}
    
    
    @Log("请求访问主页")
   	@GetMapping({ "/shouye" })
   	String index(Model model) {
   		
   		// 查询banner数据
   		Map<String, Object> params = new HashMap<String, Object>();
   		params.put("isEnable", "0");
   		List<BannerDO> bannerList = bannerService.list(params);
   		model.addAttribute("bannerList", bannerList);
   		
   		return "shouye";
   	}
   
    
    @Autowired
    UserService userService;
	
    @ResponseBody	
    @Log("密码登录")  
	@PostMapping("/login")
    Map<String, Object> loginP(String phone, String password) {
 	    Map<String, Object> message = new HashMap<>();
 	    password = MD5Utils.encrypt(phone, password);
	   	UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
	   	Subject subject = SecurityUtils.getSubject();
	   		try {
	   			Map<String, Object> mapP = new HashMap<String, Object>();
	   			mapP.put("username", phone);
	   			boolean flag = userService.exit(mapP);
	   			System.out.println("============flag============="+flag);
	   			if (!flag) {
	   				message.put("msg","该手机号码未注册");
	   				return message;
	   			}
	   			UserDO udo= userService.getbyname(phone);
	   			if(udo.getDeleteFlag()==0){
	   				message.put("msg","禁止登录，请联系客服");
	   				return message;
	   			}
	   			subject.login(token);
	   			udo.setLoginTime(new Date());
	   			userService.update(udo);
                
	   			System.out.println("======udo========="+udo);
	   			 
                message.put("UserDO", udo);
	   			message.put("msg","登录成功");
	   		} catch (AuthenticationException e) {
	   			message.put("msg","用户或密码错误");
	   		}
	    	return message;
    }
   
 /*   @Log("发送验证码")
    @PostMapping("/captcha")
    Map<String, String> captcha(String phone, String type) {
        Map<String, String> message = new HashMap<>();
        try {
            if (phone == null || "".equals(phone)) {
                message.put("msg", "手机号码不能为空");
            } else {
                SMSTemplate contentType = SMSContent.ZHUCE;
                if ("0".equals(type)) {
                    contentType = SMSContent.ZHUCE;//注册
                }
                if ("1".equals(type)) {
                    contentType = SMSContent.LOGIN;//登录
                }
                
                Map<String, Object> map = sMSService.sendCodeNumber(SMSPlatform.zhenjiu, phone, contentType);
                if (map == null) {
                    message.put("msg", "验证码发送出现问题,请三分钟后再试");
                } else {
	                String code = map.get("randomCode").toString();
                	//String code = "666666";
	                Subject subject = SecurityUtils.getSubject();
	                subject.getSession().setAttribute("sys.login.check.code", phone + code);
	                message.put("msg", "发送成功");
	                message.put("sessionId",subject.getSession().getId().toString());
                }
            }
        } catch (Exception e) {
            logger.info("SMS==================验证码发送出现问题========" + phone + "======");
            message.put("msg", "验证码发送出现问题,请三分钟后再试");
        }
        return message;
    }*/
    
    /*
    pom.xml
    <dependency>
      <groupId>com.aliyun</groupId>
      <artifactId>aliyun-java-sdk-core</artifactId>
      <version>4.0.3</version>
    </dependency>
    */
    
    /**
     * @param phone 手机号
     * @param type  类型 1：注册   0：登录	 2：密码重置
     * @说明 发送验证码
     */
    @ResponseBody
    @Log("发送验证码")
	@PostMapping("/getSms")
       static Map<String, String> getSms(String phone,String type){
    		Map<String, String> message = new HashMap<>();
    		
    		try { 
    			if (phone == null || "".equals(phone)) {
	                message.put("msg", "手机号码不能为空");
	            }else {
	            	DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIuSJwWbe9wy4T", "8hE1RX514FbSsKqzgtyuxztT7a24ln");
		            IAcsClient client = new DefaultAcsClient(profile);
		            
		            Integer templateParam = (int)((Math.random()*9+1)*100000);
		            System.out.println("templateParam========================"+templateParam);
		            CommonRequest request = new CommonRequest();
		            //request.setProtocol(ProtocolType.HTTPS);
		            request.setMethod(MethodType.POST);
		            request.setDomain("dysmsapi.aliyuncs.com");
		            request.setVersion("2017-05-25");
		            request.setAction("SendSms");
		             
		            request.putQueryParameter("PhoneNumbers", phone);
		           
		            request.putQueryParameter("SignName", "寰太教育");
		            
		            if ("0".equals(type)) {			//登陆
		            	request.putQueryParameter("TemplateCode", "SMS_171235219");
	                }else if ("1".equals(type)) {			//注册
	                	 request.putQueryParameter("TemplateCode", "SMS_171235217");
	                }else if ("2".equals(type)){			//修改密码
	                	request.putQueryParameter("TemplateCode", "SMS_171235216");
	                }
		           
		            request.putQueryParameter("TemplateParam",  "{\"code\":\""+templateParam+"\"}");
	            
	            
	                CommonResponse response = client.getCommonResponse(request);
	                
	                Subject subject = SecurityUtils.getSubject();
	                subject.getSession().setAttribute("sys.login.check.code", phone + templateParam);
	                message.put("msg", "发送成功");
	                message.put("sessionId",subject.getSession().getId().toString());
	            } 
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            }
            return message;
    }
    
    
    
	   @Log("验证码登录")
	   @PostMapping("/loginC")
	    Map<String, Object> loginC(String phone, String codenum) {
	        Map<String, Object> message = new HashMap<>();
	        String msg = "";
	        Subject subject = SecurityUtils.getSubject();
	        
	        Object object = subject.getSession().getAttribute("sys.login.check.code");
	        try {
	            if (object != null) {
	                String captcha = object.toString();
	                if (captcha == null || "".equals(captcha)) {
	                    message.put("msg", "验证码已失效，请重新点击发送验证码");
	                } else {
	                    // session中存放的验证码是手机号+验证码
	                    if (!captcha.equalsIgnoreCase(phone + codenum)) {
	                        message.put("msg", "手机验证码错误");
	                    } else {
	                        Map<String, Object> mapP = new HashMap<String, Object>();
	                        mapP.put("username", phone);
	                        boolean flag = userService.exit(mapP);
	                        if (!flag) {
	                            message.put("msg", "该手机号码未注册");
	                        } else {
	                            UserDO udo = userService.getbyname(phone);
	                            if (udo==null||udo.getDeleteFlag() == 0) {
	                                message.put("msg", "禁止登录，请联系客服");
	                            } else {
	                            	
	                            	String password = udo.getPassword();
	                            	System.out.println("==================="+password+"========================");
	                            	UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
	                            	subject.login(token);
	                            	
	                                udo.setLoginTime(new Date());
	                                
	                                userService.update(udo);
	                               
	                                message.put("id", udo.getId());
	                                message.put("nickname", udo.getNickname());
	                                message.put("heardUrl", udo.getHeardUrl());
	                                message.put("loginTime", udo.getLoginTime());
	                                message.put("msg", "登录成功");
	                                
	                            	
	                            }
	                        }
	                    }
	                }
	            } else {
	                message.put("msg", "手机验证码错误");
	            }
	        } catch (AuthenticationException e) {
	            message.put("msg", "手机号或验证码错误");
	        }
	        return message;
	    }

 
	@ResponseBody
    @Log("用户注册")
    @PostMapping("/register")
    Map<String, String> register(String phone, String codenum,String password,Long fenXiang) {
        Map<String, String> message = new HashMap<>();
        String msg = "";
        if (StringUtils.isBlank(phone)) {
            message.put("msg", "手机号码不能为空");
        } else {
            Subject subject = SecurityUtils.getSubject();
            Object object = subject.getSession().getAttribute("sys.login.check.code");
            if (object != null) {
            	String captcha = object.toString();
            	//String captcha = "666666";
                if (captcha == null || "".equals(captcha)) {
                    message.put("msg", "验证码已失效，请重新点击发送验证码");
                } else {
                    // session中存放的验证码是手机号+验证码
                    if (!captcha.equalsIgnoreCase(phone + codenum)) {
                        message.put("msg", "手机验证码错误");
                    } else {
                        Map<String, Object> mapP = new HashMap<String, Object>();
                        mapP.put("username", phone);
                        boolean flag = userService.exit(mapP);
                        System.out.println("===============flag================"+flag);
                        
                        if (flag) {
                            message.put("msg", "手机号码已存在");
                        } else {
                            UserDO udo = new UserDO();
                            Long userId = GenerateCode.gen16(8);
                            udo.setUserId(userId);
                            udo.setUsername(phone);
                            udo.setPhone(phone);
                            udo.setUserLoginType(0);
                            password = MD5Utils.encrypt(udo.getUsername(), password);
                            udo.setPassword(password);
                            udo.setDeleteFlag(1);
                            udo.setRegisterTime(new Date());
                            if (userService.save(udo) > 0) {
                                message.put("msg", "注册成功");
                                /*if(invitationService.get(phone)!=null)//是否被邀请注册
                                	invitationService.updateIfRegister(phone);*/
                            } else {
                                message.put("msg", "注册失败");
                            }
                        }
                    }
                }
            } else {
                message.put("msg", "手机验证码错误");
            }
        }
        return message;
    }
    
    @Log("忘记密码")
	@PostMapping("/retpwd")
    Map<String, String> retpwd(String username, String password,  String codenum) {
        Map<String, String> message = new HashMap<>();
		if (StringUtils.isBlank(username)) {
			message.put("msg","手机号码不能为空");
		}else{
			UserDO udo= userService.getbyname(username);
			Subject subject = SecurityUtils.getSubject();
			Object object =subject.getSession().getAttribute("sys.login.check.code");
			if (object != null) {
	            String captcha = object.toString();
	            if (captcha == null || "".equals(captcha)) {
	                message.put("msg", "验证码已失效，请重新点击发送验证码");
	            } else {
	                // session中存放的验证码是手机号+验证码
	                if (!captcha.equalsIgnoreCase(username + codenum)) {
	                    message.put("msg", "手机验证码错误");
	                } else {
	                    Map<String, Object> mapP = new HashMap<String, Object>();
	                    mapP.put("username", username);
	                    boolean flag = userService.exit(mapP);
	                    if (!flag) {
	                        message.put("msg", "该手机号码未注册");
	                    }else{
	                    	password = MD5Utils.encrypt(username, password);
	                    	udo.setPassword(password);
	            			if (userService.update(udo) > 0) {
	            				message.put("msg","修改成功");
	            			}
	                    }
	                }
	            }
	        } else {
	            message.put("msg", "手机验证码错误");
	        }
		}
		return message;
	}
    
    
    
    
    @Log("用户登录")
    @GetMapping("/login")
    String login(Model model) {
		return "/user/denglu";
	}
    
    @Log("用户注册")
    @GetMapping("/zhuce")
    String zhuce(@RequestParam(value="fenXiang",required=false)  Long fenXiang,Model model) {
    	if(fenXiang==null)
    		fenXiang=0L;
    	model.addAttribute("fenXiang", fenXiang);
    	return "/user/zhuce";
	}
    
    @Log("找回密码")
    @GetMapping("/zhaohuimima")
    String zhaohuimima() {
    	return "/user/zhaohuimima";
	}
    
    @Log("登出")
    @GetMapping("/logout")
    String logout() {
        Map<String, String> message = new HashMap<>();
        ShiroUtils.logout();
        message.put("msg", "登出成功");
        
        return "/user/denglu";
    }
    
    @Log("微信登录")  
	@PostMapping("/loginWechat")
    Map<String, Object> loginWechat(String openId,String heardUrl,String nickname) {
    	   Subject subject = SecurityUtils.getSubject();
 	       Map<String, Object> message = new HashMap<>();
	       System.out.println("=========openId=============="+openId);
 	    	try{
 	    		UserDO userDO = userService.getByOpenid(openId);
 	    		System.out.println("==========UserDO=========="+userDO);
 	    		if(userDO!=null){
 	    			String phone = userDO.getPhone();
    	    		UsernamePasswordToken token = new UsernamePasswordToken(phone, openId);
                    subject.login(token);
                    userDO.setLoginTime(new Date());
                    userService.update(userDO);
                    message.put("data", userDO);
    	    	    message.put("code", 0);
    	    		message.put("msg", "登录成功");
 	    		}else{
 	    			UserDO users = new UserDO();
 	    			users.setDeleteFlag(1);
 	    			users.setRegisterTime(new Date());
 	    			Long userId = GenerateCode.gen16(8);
 	    			users.setUserId(userId);
 	    			users.setHeardUrl(heardUrl);
 	    			users.setNickname(nickname);
 	    			users.setOpenId(openId);
 	    			users.setUsername(openId);
 	    			users.setUserLoginType(0);
 	    			userService.save(users);
 	    			UsernamePasswordToken token = new UsernamePasswordToken(openId,openId);
                    subject.login(token);
 	    			System.out.println("==========users=========="+users);
 	    			message.put("code", 0);
    	    		message.put("msg", "微信登录成功，请修改用户的相关信息");
    	    		message.put("data", users);
 	    		}
	   			
	   		}catch (AuthenticationException e) {
	   			message.put("msg", "异常！请重新登录尝试");
	   		}
	    	return message;
    }
    
   
         
     	
}
