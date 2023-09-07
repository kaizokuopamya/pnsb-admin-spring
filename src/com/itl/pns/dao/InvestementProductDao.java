package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.InvestementProductsEntity;

public interface InvestementProductDao {

	public List<InvestementProductsEntity> getInvestementProducts();

	public List<InvestementProductsEntity> getInvestementProductById(InvestementProductsEntity investementProductData);

	public boolean addInvestementProducts(InvestementProductsEntity investementProductData);

	public boolean updateInvestementProducts(InvestementProductsEntity investementProductData);
	
	
	public ResponseMessageBean isInvestementProductExist(InvestementProductsEntity investementProductData);
	
	public ResponseMessageBean isUpdateInvestementProductExist(InvestementProductsEntity investementProductData);
}
