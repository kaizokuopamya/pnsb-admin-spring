package com.itl.pns.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ActivityMasterBean;
import com.itl.pns.bean.ActivityNotificationBean;
import com.itl.pns.entity.ActivityMaster;
import com.itl.pns.entity.ActivityNotificationMasterEntity;
import com.itl.pns.entity.CustomizeMenus;
import com.itl.pns.entity.MenuSubmenuEntity;
import com.itl.pns.entity.SubMenuEntity;
import com.itl.pns.repository.ActivityNotificationRepository;
import com.itl.pns.service.ActivityNotificationService;



@Service
@Qualifier("activityNotification")
public class ActivityNotificationServiceImpl implements ActivityNotificationService{

	@Autowired
	ActivityNotificationRepository actiNotiRepo;
	
	static Logger LOGGER = Logger.getLogger(ActivityNotificationServiceImpl.class);

	@Override
	public  List<Object[]> findAllData() {
		 List<Object[]> list = null;
		try {
		//	list=actiNotiRepo.findAll();
			 list	=	actiNotiRepo.findAllActivityNotification();
			System.err.println("Hello");
			
			 
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return list;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean saveActivityNotification(List<ActivityNotificationMasterEntity> actiNotiData) {

		try {

			if (!ObjectUtils.isEmpty(actiNotiData)) {
				actiNotiRepo.save(actiNotiData);
			}

		} catch (Exception ex) {
			LOGGER.info("Exception:", ex);
		}

		return true;
	}

	@Override
	public  List<Object[]> getAllActivityNotificationsById(
			ActivityNotificationMasterEntity actiNotiData) {
		 List<Object[]> list = null; 
			try {
			//	list=actiNotiRepo.findAll();
			   list= actiNotiRepo.findByid(actiNotiData.getId());
				System.err.println("Hello");
				
				 
			} catch (Exception e) {
				LOGGER.info("Exception:", e);
			}
			return list;
		}
	

}
