package com.sockettcp.owneruser.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sockettcp.common.annotation.Log;
import com.sockettcp.common.config.BootdoConfig;
import com.sockettcp.common.controller.BaseController;
import com.sockettcp.common.utils.FileUtil;
import com.sockettcp.owneruser.domain.OwnerUserDO;
import com.sockettcp.owneruser.domain.UserDO;
import com.sockettcp.owneruser.service.UserService;

@RequestMapping("/user")
@RestController
public class OwnerUserController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    private BootdoConfig bootdoConfig;

    /**
     * 个人中心
     *
     * @return
     */
    @Log("获取用户信息")
    @GetMapping("/userInfo")
    Map<String, Object> user() {
        Map<String, Object> map = new HashMap<>();
        UserDO udo = userService.get(getUserId());
        map.put("user", udo);
        return map;
    }


    /**
     * 编辑用户信息
     *
     * @return
     */
    @Log("编辑用户信息")
    @PostMapping("/editInfo")
    Map<String, Object> editInfo(OwnerUserDO user) {
        Map<String, Object> map = new HashMap<>();
        UserDO userd = userService.get(getUserId());
        if (user.getHeardUrl() != null) {
            userd.setHeardUrl(user.getHeardUrl());
        }
        if (user.getNickname() != null) {
            userd.setNickname(user.getNickname());
        }
        if (user.getUserId() != null) {
            userd.setUserId(user.getUserId());
        }
        if (user.getSex() != null) {
            userd.setSex(user.getSex());
        }
        if (user.getBirthday() != null) {
            userd.setBirthday(user.getBirthday());
        }
        if (user.getPhone() != null) {
            userd.setPhone(user.getPhone());
        }
        if(user.getFileImg() != null && user.getFileImg().getSize() > 0){
			MultipartFile sysFile = user.getFileImg();
			String fileName = sysFile.getOriginalFilename();
			fileName = FileUtil.renameToUUID(fileName);
			try {
				FileUtil.uploadFile(sysFile.getBytes(), bootdoConfig.getUploadPath(), fileName);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			userd.setHeardUrl("/files/" + fileName);
        }
        
        if (userService.update(userd) > 0) {
            map.put("msg", "保存成功");
        } else {
            map.put("msg", "保存失败");
        }
        return map;
    }



}
