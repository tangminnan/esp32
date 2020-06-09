package com.sockettcp.common.controller;

import org.springframework.stereotype.Controller;

import com.sockettcp.common.utils.ShiroUtils;
import com.sockettcp.owneruser.domain.UserDO;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
	public Long getforIds() {
		return getUser().getUserId();
	}
}