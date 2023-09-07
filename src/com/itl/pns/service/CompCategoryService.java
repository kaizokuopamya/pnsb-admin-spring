package com.itl.pns.service;

import java.math.BigInteger;
import java.util.List;

import com.itl.pns.entity.CategoryCompanyMasterEntity;
import com.itl.pns.entity.CategoryCompanyProductEntity;
import com.itl.pns.entity.CategoryMasterEntity;

public interface CompCategoryService {

	
	public boolean addCompCategoryMasterData(CategoryMasterEntity categoryMasterData);

	public boolean updateCompCategoryMasterData(CategoryMasterEntity categoryMasterData);

	public List<CategoryMasterEntity> getCompCategoriesMasterById(BigInteger id);

	public List<CategoryMasterEntity> getAllCompCategoriesMaster();
	
	
	
	public boolean addComapnyMasterData(CategoryCompanyMasterEntity categorcategoryCompData);

	public boolean updateComapnyMasterData(CategoryCompanyMasterEntity categorcategoryCompData);

	public List<CategoryCompanyMasterEntity> getComapnyMasterDataById(BigInteger id);
	
	public List<CategoryCompanyMasterEntity> getComapnyMasterDataByCategoryId(BigInteger categoryId);

	public List<CategoryCompanyMasterEntity> getAllComapnyMasterData();
	
	
	public boolean addProductMasterData(CategoryCompanyProductEntity productData);

	public boolean updateProductMasterData(CategoryCompanyProductEntity productData);

	public List<CategoryCompanyProductEntity> getProductMasterDataById(BigInteger id);

	public List<CategoryCompanyProductEntity> getAllProductMasterData();
	
}
