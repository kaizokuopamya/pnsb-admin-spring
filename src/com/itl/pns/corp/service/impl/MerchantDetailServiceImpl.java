package com.itl.pns.corp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.MerchantDetailsBean;
import com.itl.pns.corp.dao.MerchantDetailServiceDao;

import com.itl.pns.corp.service.MerchantDetailService;


@Service
public class MerchantDetailServiceImpl implements MerchantDetailService {

	@Autowired
	MerchantDetailServiceDao merchantDetailServiceDao;

	@Override
	public List<MerchantDetailsBean> getMerchantDetail(MerchantDetailsBean merchantDetailsBean) {
		return merchantDetailServiceDao.getMerchantDetail(merchantDetailsBean);
	}

}
