package com.sockettcp.owneruser.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.sockettcp.owneruser.domain.UserDO;

/**
 * 用户信息表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2019-07-16 12:08:28
 */
@Mapper
public interface UserDao {

	UserDO get(Long id);
	
	List<UserDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	UserDO getbyname(String username);

	UserDO getByOpenid(String openId);

	int updateGoldCount(UserDO userDO);

	int updateIntegralCount(UserDO userDO);
	
	int cutGoldCount(UserDO user);

}
