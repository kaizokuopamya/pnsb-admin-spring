package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.entity.MerchantEntity;

public interface MerchantService {

	public boolean add(MerchantEntity merchantEntity);

	public boolean update(MerchantEntity merchantEntity);

	public List<MerchantEntity> getAllMerchantDetails();

	public MerchantEntity findByMerchantId(MerchantEntity merchantEntity);

	public ResponseMessageBean checkUpdateMerchantExist(MerchantEntity merchantEntity);
}
