package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.entity.MerchantEntity;
import com.itl.pns.dao.MerchantDao;
import com.itl.pns.service.MerchantService;

@Service
public class MerchantServiceImpl implements MerchantService {

	@Autowired
	private MerchantDao merchantDao;

	@Override
	public boolean add(MerchantEntity merchantEntity) {
		return merchantDao.add(merchantEntity);
	}

	@Override
	public boolean update(MerchantEntity merchantEntity) {
		return merchantDao.update(merchantEntity);
	}

	@Override
	public List<MerchantEntity> getAllMerchantDetails() {
		return merchantDao.getAllMerchantDetails();
	}

	@Override
	public MerchantEntity findByMerchantId(MerchantEntity merchantEntity) {
		return merchantDao.findByMerchantId(merchantEntity);
	}

	@Override
	public ResponseMessageBean checkUpdateMerchantExist(MerchantEntity merchantEntity) {
		return merchantDao.checkUpdateMerchantExist(merchantEntity);
	}

}
