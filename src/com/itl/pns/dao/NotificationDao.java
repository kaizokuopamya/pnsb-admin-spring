package com.itl.pns.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.NotificationMaster;
import com.itl.pns.entity.NotificationTemplateMaster;
import com.itl.pns.entity.Notifications;
import com.itl.pns.entity.NotificationsEntity;

public interface NotificationDao {

	public List<NotificationMaster> getNotificationMasterList();

	public List<NotificationMaster> getNotificationMasterById(int id);

	public List<NotificationTemplateMaster> getNotificationTempData();

	public List<NotificationTemplateMaster> getNotificationTempDataById(BigInteger id);

	public boolean deletetNotificationTemp(BigInteger id);

	List<RegistrationDetailsBean> getCustDetails(RegistrationDetailsBean detailsBean);

	boolean saveNotificationTempData(NotificationTemplateMaster notificationTempData);

	boolean updateNotificationTempData(NotificationTemplateMaster notificationTempData);

	public List<NotificationsEntity> getNotificationList(BigDecimal appId, String fromDate, String toDate);

	public boolean sendNotificationTocust(NotificationsEntity notification);

	public boolean addNotificationDetails(List<NotificationsEntity> notification);

	public boolean updateNotificationDetails(NotificationsEntity notificationData);

	public ResponseMessageBean chechNotificatioExit(NotificationTemplateMaster notificationTempData);

	public ResponseMessageBean updateNotificatioExit(NotificationTemplateMaster notificationTempData);

	public ResponseMessageBean sendNotificationToAll(NotificationsEntity notification);

	public int getTotalAttamptCount(NotificationsEntity notificationData, int timeInterval);

	public void saveNotificationAttampt(NotificationsEntity notifications);
}
