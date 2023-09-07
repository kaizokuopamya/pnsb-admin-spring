package com.itl.pns.corp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.CorpBulkUserDao;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.service.CorpBulkUserService;

@Service
@Component
public class CorpBulkUserServiceImpl implements CorpBulkUserService {

	@Autowired
	CorpBulkUserDao corpBulkUserDao;

	@Override
	public boolean saveBulkCorpUsers(List<CorpUserEntity> custDataList) {
		
		return corpBulkUserDao.saveBulkCorpUsers(custDataList);
	}
}
