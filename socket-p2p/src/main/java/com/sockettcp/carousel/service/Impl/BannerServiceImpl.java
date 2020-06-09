package com.sockettcp.carousel.service.Impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sockettcp.carousel.dao.BannerDao;
import com.sockettcp.carousel.domain.BannerDO;
import com.sockettcp.carousel.service.BannerService;
import com.sockettcp.common.config.BootdoConfig;


@Service
public class BannerServiceImpl implements BannerService {
	@Autowired
	private BannerDao sysFileMapper;

	@Autowired
	private BootdoConfig bootdoConfig;
	@Override
	public BannerDO get(Long id){
		return sysFileMapper.get(id);
	}
	
	@Override
	public List<BannerDO> list(Map<String, Object> map){
		return sysFileMapper.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return sysFileMapper.count(map);
	}
	
	@Override
	public int save(BannerDO sysFile){
		return sysFileMapper.save(sysFile);
	}
	
	@Override
	public int update(BannerDO sysFile){
		return sysFileMapper.update(sysFile);
	}
	
	@Override
	public int remove(Long id){
		return sysFileMapper.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return sysFileMapper.batchRemove(ids);
	}

	@Override
	public Boolean isExist(String url) {
		Boolean isExist = false;
		if (!StringUtils.isEmpty(url)) {
			String filePath = url.replace("/files/", "");
			filePath = bootdoConfig.getUploadPath() + filePath;
			File file = new File(filePath);
			if (file.exists()) {
				isExist = true;
			}
		}
		return isExist;
	}
	}
