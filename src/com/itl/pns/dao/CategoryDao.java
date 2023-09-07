package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.CustNotificationCategoriesEntity;
import com.itl.pns.entity.NotificationCategoriesMasterEntity;

/**
 * @author shubham.lokhande
 *
 */
public interface CategoryDao {

	public boolean addCategoriesMaster(NotificationCategoriesMasterEntity categoryMasterData);

	public boolean updateCategoriesMaster(NotificationCategoriesMasterEntity categoryMasterData);

	public List<NotificationCategoriesMasterEntity> getCategoriesMasterById(int id);

	public List<NotificationCategoriesMasterEntity> getAllCategoriesMaster();
	
	
	public boolean addCustNotificationCategories(CustNotificationCategoriesEntity categoryMasterData);

	public boolean updateCustNotificationCategories(CustNotificationCategoriesEntity categoryMasterData);

	public List<CustNotificationCategoriesEntity> getCustNotificationCategoriesById(int id);

	public List<CustNotificationCategoriesEntity> getAllCustNotificationCategories();
	
	public ResponseMessageBean checkCategoryExist(NotificationCategoriesMasterEntity categoryMasterData) ;
	
	public ResponseMessageBean checkUpdateCategoryExist(NotificationCategoriesMasterEntity categoryMasterData);
	
}
