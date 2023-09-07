package com.itl.pns.corp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.CorpServiceRequestReportDao;
import com.itl.pns.corp.entity.CorpServiceRequestEntity;
import com.itl.pns.corp.service.CorpServiceRequestService;

@Service
@Component
public class CorpServiceRequestServiceImpl implements CorpServiceRequestService {

	@Autowired
	CorpServiceRequestReportDao corpserviceRequestReportDao;

	@Override
	public List<CorpServiceRequestEntity> getChannelDetailsService() {
		return corpserviceRequestReportDao.getChannelDetailsService();
	}

//	@Override
//	public List<CorpServiceRequestEntity> gettypeDetails() {
//		return corpserviceRequestReportDao.gettypeDetails();
//	}

	@Override
	public List<CorpServiceRequestEntity> getServiceRequestReportDetail(
			CorpServiceRequestEntity corpserviceRequestEntity) {
		return corpserviceRequestReportDao.getServiceRequestReportDetail(corpserviceRequestEntity);
	}

	@Override
	public List<CorpServiceRequestEntity> gettypeDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CorpServiceRequestEntity> getChannelDetailsActiveService() {
		return corpserviceRequestReportDao.getChannelDetailsActiveService();

	}

}
