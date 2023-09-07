package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.corp.entity.PgRequestEntity;

public interface PgRequestDao {
	
	public List<PgRequestEntity> getMerchantDetails(PgRequestEntity pgRequestEntity);

}
