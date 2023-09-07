package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.NotificationDao;
import com.itl.pns.dao.impl.DonationDaoImpl;
import com.itl.pns.entity.NotificationMaster;
import com.itl.pns.entity.NotificationTemplateMaster;
import com.itl.pns.entity.Notifications;
import com.itl.pns.entity.NotificationsEntity;
import com.itl.pns.repository.NotificationTemplateRepository;
import com.itl.pns.service.NotificationService;

@Service
@Qualifier("notificationService")
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LogManager.getLogger(DonationDaoImpl.class);

	@Autowired
	NotificationDao notificationDao;

	@Autowired
	@Qualifier("sessionFactory")
	public SessionFactory sessionFactory;

	@Autowired
	NotificationTemplateRepository notificationTempRepo;

	@Override
	public List<NotificationMaster> getNotificationMasterList() {

		List<NotificationMaster> list = notificationDao.getNotificationMasterList();
		return list;

	}

	@Override
	public List<NotificationMaster> getNotificationMasterById(int id) {
		List<NotificationMaster> list = notificationDao.getNotificationMasterById(id);
		return list;
	}

	@Override
	public List<NotificationTemplateMaster> getNotificationTempData() {
		List<NotificationTemplateMaster> list = notificationDao.getNotificationTempData();
		return list;
	}

	@Override
	public List<NotificationTemplateMaster> getNotificationTempDataById(BigInteger id) {
		List<NotificationTemplateMaster> list = notificationDao.getNotificationTempDataById(id);
		return list;
	}

	@Override
	public boolean saveNotificationTempData(NotificationTemplateMaster notificationTempData) {

		return notificationDao.saveNotificationTempData(notificationTempData);

	}

	@Override
	public boolean updateNotificationTempData(NotificationTemplateMaster notificationTempData) {
		boolean isupdated = false;
		try {
			notificationDao.updateNotificationTempData(notificationTempData);
			isupdated = true;

		} catch (Exception ex) {
			logger.info("Exception:", ex);
			isupdated = false;
		}
		return isupdated;
	}

	@Override
	public boolean deletetNotificationTemp(BigInteger id) {
		return notificationDao.deletetNotificationTemp(id);
	}

	@Override
	public List<RegistrationDetailsBean> getCustDetails(RegistrationDetailsBean detailsBean) {
		return notificationDao.getCustDetails(detailsBean);
	}

	@Override
	public List<NotificationsEntity> getNotificationList(BigDecimal appId, String fromDate, String toDate) {
		return notificationDao.getNotificationList(appId,fromDate,toDate);
	}

	@Override
	public boolean sendNotificationTocust(NotificationsEntity notification) {
		return notificationDao.sendNotificationTocust(notification);
	}

	@Override
	public boolean addNotificationDetails(List<NotificationsEntity> notification) {
		return notificationDao.addNotificationDetails(notification);
	}

	@Override
	public boolean updateNotificationDetails(NotificationsEntity notificationData) {
		return notificationDao.updateNotificationDetails(notificationData);
	}

	@Override
	public ResponseMessageBean chechNotificatioExit(NotificationTemplateMaster notificationTempData) {
		return notificationDao.chechNotificatioExit(notificationTempData);
	}

	@Override
	public ResponseMessageBean updateNotificatioExit(NotificationTemplateMaster notificationTempData) {
		return notificationDao.updateNotificatioExit(notificationTempData);
	}

	@Override
	public ResponseMessageBean sendNotificationToAll(NotificationsEntity notification) {
		return notificationDao.sendNotificationToAll(notification);
	}

	@Override
	public int getTotalAttamptCount(NotificationsEntity notificationData, int timeInterval) {
		return notificationDao.getTotalAttamptCount(notificationData, timeInterval);
	}

	@Override
	public void saveNotificationAttampt(NotificationsEntity notifications) {
		notificationDao.saveNotificationAttampt(notifications);
	}

}
