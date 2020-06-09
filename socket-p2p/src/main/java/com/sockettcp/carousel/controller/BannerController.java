package com.sockettcp.carousel.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sockettcp.carousel.domain.BannerDO;
import com.sockettcp.carousel.service.BannerService;
import com.sockettcp.common.controller.BaseController;
import com.sockettcp.common.utils.PageUtils;



/**
 * 轮播图
 * 
 * @author wjl
 */
@Controller
@RequestMapping("/carousel/banner")
public class BannerController extends BaseController {

	@Autowired
	private BannerService bannerService;
	
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list() {
		// 查询列表数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isEnable", "0");
		List<BannerDO> sysFileList = bannerService.list(params);
		PageUtils pageUtils = new PageUtils(sysFileList, sysFileList.size());
		return pageUtils;
	}

}
