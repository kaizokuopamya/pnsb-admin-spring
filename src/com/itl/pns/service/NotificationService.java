package com.itl.pns.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.NotificationMaster;
import com.itl.pns.entity.NotificationTemplateMaster;
import com.itl.pns.entity.NotificationsEntity;

@Service
public interface NotificationService {

	public List<NotificationMaster> getNotificationMasterList();

	public List<NotificationMaster> getNotificationMasterById(int id);

	public List<NotificationTemplateMaster> getNotificationTempData();

	public List<NotificationTemplateMaster> getNotificationTempDataById(BigInteger id);

	public boolean saveNotificationTempData(NotificationTemplateMaster notificationTempData);

	public boolean updateNotificationTempData(NotificationTemplateMaster notificationTempData);

	public boolean deletetNotificationTemp(BigInteger id);

	public List<RegistrationDetailsBean> getCustDetails(RegistrationDetailsBean detailsBean);

	public List<NotificationsEntity> getNotificationList(BigDecimal appId, String fromDate, String toDate);

	public boolean sendNotificationTocust(NotificationsEntity notification);

	public boolean addNotificationDetails(List<NotificationsEntity> notification);

	public boolean updateNotificationDetails(NotificationsEntity notificationTempData);

	public ResponseMessageBean chechNotificatioExit(NotificationTemplateMaster notificationTempData);

	public ResponseMessageBean updateNotificatioExit(NotificationTemplateMaster notificationTempData);

	public ResponseMessageBean sendNotificationToAll(NotificationsEntity notification);

	public int getTotalAttamptCount(NotificationsEntity notificationData, int timeRetries);

	public void saveNotificationAttampt(NotificationsEntity notifications);

}
