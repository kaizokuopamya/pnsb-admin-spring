package com.itl.pns.dao;

import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.CategoryCompanyMasterEntity;
import com.itl.pns.entity.CategoryCompanyProductEntity;
import com.itl.pns.entity.CategoryMasterEntity;

public interface CompCategoryDao {

	
	public boolean addCompCategoryMasterData(CategoryMasterEntity categoryMasterData);

	public boolean updateCompCategoryMasterData(CategoryMasterEntity categoryMasterData);

	public List<CategoryMasterEntity> getCompCategoriesMasterById(BigInteger id);

	public List<CategoryMasterEntity> getAllCompCategoriesMaster();
	
	public ResponseMessageBean checkCategoryExist(CategoryMasterEntity categoryMasterData) ;
	
	public ResponseMessageBean checkUpdateCategoryExist(CategoryMasterEntity categoryMasterData);
	
	
	
	
	
	public boolean addComapnyMasterData(CategoryCompanyMasterEntity categorcategoryCompData);

	public boolean updateComapnyMasterData(CategoryCompanyMasterEntity categorcategoryCompData);

	public List<CategoryCompanyMasterEntity> getComapnyMasterDataById(BigInteger id);
	
	public List<CategoryCompanyMasterEntity> getComapnyMasterDataByCategoryId(BigInteger categoryId);
	

	public List<CategoryCompanyMasterEntity> getAllComapnyMasterData();
	
    public ResponseMessageBean checkCompExitForCategory(CategoryCompanyMasterEntity categorcategoryCompData) ;
	
	public ResponseMessageBean checkUpdateCompExitForCategory(CategoryCompanyMasterEntity categorcategoryCompData);
	
	
	public boolean addProductMasterData(CategoryCompanyProductEntity productData);

	public boolean updateProductMasterData(CategoryCompanyProductEntity productData);

	public List<CategoryCompanyProductEntity> getProductMasterDataById(BigInteger id);

	public List<CategoryCompanyProductEntity> getAllProductMasterData();
	
    public ResponseMessageBean checkProductExitForCompany(CategoryCompanyProductEntity productData) ;
	
	public ResponseMessageBean checkUpdateProductExitForCompany(CategoryCompanyProductEntity productData);
}
