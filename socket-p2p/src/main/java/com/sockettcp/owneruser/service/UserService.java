package com.sockettcp.owneruser.service;

import java.util.List;
import java.util.Map;

import com.sockettcp.common.utils.R;
import com.sockettcp.owneruser.domain.OwnerUserDO;
import com.sockettcp.owneruser.domain.UserDO;

/**
 * 用户信息表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-07-16 12:08:28
 */
public interface UserService {
	
	UserDO get(Long id);
	
	List<UserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	UserDO getByOpenid(String openId);
	
	boolean exit(Map<String, Object> params);
	
	UserDO getbyname(String username);
	
	int cutGoldCount(UserDO user);

	R createYaoqingMa();
}
