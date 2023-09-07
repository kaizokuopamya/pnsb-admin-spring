
package com.itl.pns.corp.service;

import java.util.List;

import com.itl.pns.corp.entity.CorpServiceRequestEntity;

public interface CorpServiceRequestService {

	public List<CorpServiceRequestEntity> getChannelDetailsService(); // public

	List<CorpServiceRequestEntity> gettypeDetails();

	List<CorpServiceRequestEntity> getServiceRequestReportDetail(CorpServiceRequestEntity corpserviceRequestEntity);

	public List<CorpServiceRequestEntity> getChannelDetailsActiveService();
}
