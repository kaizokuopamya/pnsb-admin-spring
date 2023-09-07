package com.itl.pns.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.CategoryDao;
import com.itl.pns.dao.CompCategoryDao;
import com.itl.pns.entity.CategoryCompanyMasterEntity;
import com.itl.pns.entity.CategoryCompanyProductEntity;
import com.itl.pns.entity.CategoryMasterEntity;
import com.itl.pns.service.CompCategoryService;
@Service
public class CompCategoryServiceImpl implements CompCategoryService {

	@Autowired
	CompCategoryDao compCategoryDao;
	
	
	@Override
	public boolean addCompCategoryMasterData(CategoryMasterEntity categoryMasterData) {
		return compCategoryDao.addCompCategoryMasterData(categoryMasterData);
	}

	@Override
	public boolean updateCompCategoryMasterData(CategoryMasterEntity categoryMasterData) {
		return compCategoryDao.updateCompCategoryMasterData(categoryMasterData);
	}

	@Override
	public List<CategoryMasterEntity> getCompCategoriesMasterById(BigInteger id) {
		return compCategoryDao.getCompCategoriesMasterById(id);
	}

	@Override
	public List<CategoryMasterEntity> getAllCompCategoriesMaster() {
		return compCategoryDao.getAllCompCategoriesMaster();
	}

	@Override
	public boolean addComapnyMasterData(CategoryCompanyMasterEntity categorcategoryCompData) {
		return compCategoryDao.addComapnyMasterData(categorcategoryCompData);
	}

	@Override
	public boolean updateComapnyMasterData(CategoryCompanyMasterEntity categorcategoryCompData) {
		return compCategoryDao.updateComapnyMasterData(categorcategoryCompData);
	}

	@Override
	public List<CategoryCompanyMasterEntity> getComapnyMasterDataById(BigInteger id) {
		return compCategoryDao.getComapnyMasterDataById(id);
	}
	
	@Override
	public List<CategoryCompanyMasterEntity> getComapnyMasterDataByCategoryId(BigInteger categoryId) {
		return compCategoryDao.getComapnyMasterDataByCategoryId(categoryId);
	}
	@Override
	public List<CategoryCompanyMasterEntity> getAllComapnyMasterData() {
		return compCategoryDao.getAllComapnyMasterData();
	}

	@Override
	public boolean addProductMasterData(CategoryCompanyProductEntity productData) {
		return compCategoryDao.addProductMasterData(productData);
	}

	@Override
	public boolean updateProductMasterData(CategoryCompanyProductEntity productData) {
		return compCategoryDao.updateProductMasterData(productData);
	}

	@Override
	public List<CategoryCompanyProductEntity> getProductMasterDataById(BigInteger id) {
		return compCategoryDao.getProductMasterDataById(id);
	}

	@Override
	public List<CategoryCompanyProductEntity> getAllProductMasterData() {
		return compCategoryDao.getAllProductMasterData();
	}

	
}
