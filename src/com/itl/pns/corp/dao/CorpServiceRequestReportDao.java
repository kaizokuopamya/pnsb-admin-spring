
package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.corp.entity.CorpServiceRequestEntity;

public interface CorpServiceRequestReportDao {

	public List<CorpServiceRequestEntity> getChannelDetailsService();

	// public List<CorpServiceRequestEntity> gettypeDetails();

	public List<CorpServiceRequestEntity> getServiceRequestReportDetail(
			CorpServiceRequestEntity corpserviceRequestEntity);
	
	public List<CorpServiceRequestEntity> getChannelDetailsActiveService();

}
