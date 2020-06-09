package com.sockettcp.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sockettcp.common.domain.LogDO;
import com.sockettcp.common.domain.PageDO;
import com.sockettcp.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
