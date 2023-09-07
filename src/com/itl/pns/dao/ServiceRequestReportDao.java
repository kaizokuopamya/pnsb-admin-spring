package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.entity.ServiceRequestEntity;

public interface ServiceRequestReportDao {

	List<ServiceRequestEntity> getChannelDetailService();

	List<ServiceRequestEntity> getServiceRequestReportDetails(ServiceRequestEntity serviceRequestEntity);

}
