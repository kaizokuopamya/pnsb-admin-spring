package com.itl.pns.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.InvestementProductDao;
import com.itl.pns.entity.InvestementProductsEntity;
import com.itl.pns.service.InvestementProductService;

@Service
public class InvestementProductServiceImpl implements InvestementProductService {

	static Logger LOGGER = Logger.getLogger(InvestementProductServiceImpl.class);
	
	@Autowired
	InvestementProductDao investementProductDao;
	
	@Override
	public List<InvestementProductsEntity> getInvestementProducts() {
		return investementProductDao.getInvestementProducts();
	}

	@Override
	public List<InvestementProductsEntity> getInvestementProductById(InvestementProductsEntity investementProductData) {
		return investementProductDao.getInvestementProductById(investementProductData);
	}

	@Override
	public boolean addInvestementProducts(InvestementProductsEntity investementProductData) {
		return investementProductDao.addInvestementProducts(investementProductData);
	}

	@Override
	public boolean updateInvestementProducts(InvestementProductsEntity investementProductData) {
		return investementProductDao.updateInvestementProducts(investementProductData);
	}

}
