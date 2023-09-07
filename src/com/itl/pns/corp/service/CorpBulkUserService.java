package com.itl.pns.corp.service;

import java.util.List;

import com.itl.pns.corp.entity.CorpUserEntity;

public interface CorpBulkUserService {

	
	public boolean saveBulkCorpUsers(List<CorpUserEntity> custDataList);
}
