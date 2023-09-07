package com.itl.pns.corp.service;

import java.util.List;

import com.itl.pns.corp.entity.PgRequestEntity;

public interface PgRequestService  {
	
	public List<PgRequestEntity> getMerchantDetails(PgRequestEntity pgRequestEntity);

}
