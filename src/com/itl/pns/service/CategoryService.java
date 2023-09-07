package com.itl.pns.service;

import java.math.BigInteger;
import java.util.List;

import com.itl.pns.entity.CategoryCompanyProductEntity;
import com.itl.pns.entity.CustNotificationCategoriesEntity;
import com.itl.pns.entity.NotificationCategoriesMasterEntity;

/**
 * @author shubham.lokhande
 *
 */
public interface CategoryService {

	public boolean addCategoriesMaster(NotificationCategoriesMasterEntity categoryMasterData);

	public boolean updateCategoriesMaster(NotificationCategoriesMasterEntity categoryMasterData);

	public List<NotificationCategoriesMasterEntity> getCategoriesMasterById(int id);

	public List<NotificationCategoriesMasterEntity> getAllCategoriesMaster();
	
	

	public boolean addCustNotificationCategories(CustNotificationCategoriesEntity categoryMasterData);

	public boolean updateCustNotificationCategories(CustNotificationCategoriesEntity categoryMasterData);

	public List<CustNotificationCategoriesEntity> getCustNotificationCategoriesById(int id);

	public List<CustNotificationCategoriesEntity> getAllCustNotificationCategories();
	
	
	
	
	
	
	

}
