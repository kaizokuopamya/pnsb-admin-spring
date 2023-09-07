package com.itl.pns.corp.service;

import java.util.List;

import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.Product;

public interface CorpProductService {

	
	public List<ProductBean> getCorpProducts();

	public List<ProductBean> getCorpProductById(int id);
	
	public boolean saveCorpProductDetails(Product product);

	public void updateCorpProductDetails(Product product);
	
	
	public ResponseMessageBean checkCorpProduct(Product product);
	
	public ResponseMessageBean checkCorpUpdateProduct(Product product);
}
