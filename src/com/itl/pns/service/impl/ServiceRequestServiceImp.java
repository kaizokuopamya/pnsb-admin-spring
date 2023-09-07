package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.ServiceRequestReportDao;
import com.itl.pns.entity.ServiceRequestEntity;
import com.itl.pns.service.ServiceRequestService;

@Service
public class ServiceRequestServiceImp implements ServiceRequestService {

	@Autowired
	ServiceRequestReportDao serviceRequestReportDao;

	@Override
	public List<ServiceRequestEntity> getChannelDetailService() {
		return serviceRequestReportDao.getChannelDetailService();
	}

	@Override
	public List<ServiceRequestEntity> getServiceRequestReportDetails(ServiceRequestEntity serviceRequestEntity) {
		return serviceRequestReportDao.getServiceRequestReportDetails(serviceRequestEntity);
	}

	
}