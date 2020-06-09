package com.sockettcp.owneruser.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sockettcp.common.config.BootdoConfig;
import com.sockettcp.common.utils.QRCodeUtil;
import com.sockettcp.common.utils.R;
import com.sockettcp.common.utils.ShiroUtils;
import com.sockettcp.owneruser.dao.UserDao;
import com.sockettcp.owneruser.domain.UserDO;
import com.sockettcp.owneruser.service.UserService;




@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private BootdoConfig bootdoConfig;
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDO get(Long id){
		return userDao.get(id);
	}
	
	@Override
	public List<UserDO> list(Map<String, Object> map){
		return userDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userDao.count(map);
	}
	
	@Override
	public int save(UserDO user){
		return userDao.save(user);
	}
	
	@Override
	public int update(UserDO user){
		return userDao.update(user);
	}
	
	@Override
	public int remove(Long id){
		return userDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return userDao.batchRemove(ids);
	}

	@Override
	public UserDO getByOpenid(String openId) {
		return userDao.getByOpenid(openId);
	}

	@Override
	public boolean exit(Map<String, Object> params) {
		boolean exit;
		exit = userDao.list(params).size() > 0;
		return exit;
	}

	@Override
	public UserDO getbyname(String username) {
		return userDao.getbyname(username);
		
	}

	@Override
	public int cutGoldCount(UserDO user) {
		return userDao.cutGoldCount(user);
	}

	@Override
	public R createYaoqingMa() {
	//生成二维码
		try {
			String destPath = bootdoConfig.getUploadPath();
			String rand = new Random().nextInt(99999999)+".jpg";
			QRCodeUtil.encode("http://39.107.78.243:8110/zhuce?fenXiang="+ShiroUtils.getUserId(), null, destPath+"/"+rand, true);
			UserDO userDO  = new UserDO();
			userDO.setId(ShiroUtils.getUserId());
			userDO.setQRCode("/files/"+rand);
			userDao.update(userDO);
			return R.ok("/files/"+rand);
		} catch (Exception e) {
		
			e.printStackTrace();
		}		
		return R.error();
	}

}
