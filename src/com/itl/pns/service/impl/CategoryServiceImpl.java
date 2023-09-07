package com.itl.pns.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.CategoryDao;
import com.itl.pns.entity.CategoryCompanyProductEntity;
import com.itl.pns.entity.CustNotificationCategoriesEntity;
import com.itl.pns.entity.NotificationCategoriesMasterEntity;
import com.itl.pns.service.CategoryService;

/**
 * @author shubham.lokhande
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryDao categoryDao;

	@Override
	public boolean addCategoriesMaster(NotificationCategoriesMasterEntity categoryMasterData) {

		return categoryDao.addCategoriesMaster(categoryMasterData);
	}

	@Override
	public boolean updateCategoriesMaster(NotificationCategoriesMasterEntity categoryMasterData) {
		return categoryDao.updateCategoriesMaster(categoryMasterData);
	}

	@Override
	public List<NotificationCategoriesMasterEntity> getCategoriesMasterById(int id) {
		return categoryDao.getCategoriesMasterById(id);
	}

	@Override
	public List<NotificationCategoriesMasterEntity> getAllCategoriesMaster() {
		return categoryDao.getAllCategoriesMaster();
	}

	@Override
	public boolean addCustNotificationCategories(CustNotificationCategoriesEntity categoryMasterData) {
		return categoryDao.addCustNotificationCategories(categoryMasterData);
	}

	@Override
	public boolean updateCustNotificationCategories(CustNotificationCategoriesEntity categoryMasterData) {
		return categoryDao.updateCustNotificationCategories(categoryMasterData);
	}

	@Override
	public List<CustNotificationCategoriesEntity> getCustNotificationCategoriesById(int id) {
		return categoryDao.getCustNotificationCategoriesById(id);
	}

	@Override
	public List<CustNotificationCategoriesEntity> getAllCustNotificationCategories() {
		return categoryDao.getAllCustNotificationCategories();
	}

}
