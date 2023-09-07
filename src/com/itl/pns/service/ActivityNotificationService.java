package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.ActivityNotificationMasterEntity;

public interface ActivityNotificationService {

	public List<Object[]> findAllData();
	
	public boolean saveActivityNotification(List<ActivityNotificationMasterEntity> actiNotiData);
	
	public  List<Object[]>  getAllActivityNotificationsById(ActivityNotificationMasterEntity actiNotiData);
	

}
