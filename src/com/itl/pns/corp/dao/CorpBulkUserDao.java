package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.corp.entity.CorpUserEntity;

public interface CorpBulkUserDao {

	public boolean saveBulkCorpUsers(List<CorpUserEntity> custDataList);
}
