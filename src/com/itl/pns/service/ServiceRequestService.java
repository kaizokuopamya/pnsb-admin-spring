package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.ServiceRequestEntity;

public interface ServiceRequestService {

	List<ServiceRequestEntity> getChannelDetailService();

	List<ServiceRequestEntity> getServiceRequestReportDetails(ServiceRequestEntity serviceRequestEntity);

	

}