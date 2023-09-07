package com.itl.pns.corp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.PgRequestDao;
import com.itl.pns.corp.entity.PgRequestEntity;
import com.itl.pns.corp.service.PgRequestService;

@Service
public class PgRequestServiceImpl implements PgRequestService {
	
	@Autowired
	private PgRequestDao pgRequestDao;

	@Override
	public List<PgRequestEntity> getMerchantDetails(PgRequestEntity pgRequestEntity) {
		return pgRequestDao.getMerchantDetails(pgRequestEntity);
	}

}
