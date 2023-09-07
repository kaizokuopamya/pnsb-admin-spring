package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.bean.MerchantDetailsBean;


public interface MerchantDetailServiceDao {

	List<MerchantDetailsBean> getMerchantDetail(MerchantDetailsBean merchantDetailsBean);

}