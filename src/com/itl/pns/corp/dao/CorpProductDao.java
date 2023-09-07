package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.Product;
public interface CorpProductDao {

	
	public List<ProductBean> getCorpProducts();

	public List<ProductBean> getCorpProductById(int id);
	
	public boolean saveCorpProductDetails(Product product);
	
	
	public ResponseMessageBean checkCorpProduct(Product product);
	
	public ResponseMessageBean checkCorpUpdateProduct(Product product);
}
