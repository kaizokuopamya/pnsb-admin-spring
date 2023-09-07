package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.InvestementProductsEntity;

public interface InvestementProductService {

	public List<InvestementProductsEntity> getInvestementProducts();
	
	public List<InvestementProductsEntity> getInvestementProductById(InvestementProductsEntity investementProductData);
	
    public boolean  addInvestementProducts(InvestementProductsEntity investementProductData);
	
    public boolean updateInvestementProducts (InvestementProductsEntity investementProductData);
}
