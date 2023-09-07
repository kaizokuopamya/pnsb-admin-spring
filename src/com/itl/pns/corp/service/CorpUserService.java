package com.itl.pns.corp.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpMenuAccBean;
import com.itl.pns.bean.CorpMenuAccountBean;
import com.itl.pns.corp.entity.CorpAccReqEntity;
import com.itl.pns.corp.entity.CorpCompDataMasterEntity;
import com.itl.pns.corp.entity.CorpCompReqEntity;
import com.itl.pns.corp.entity.CorpMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserAccReqEntity;
import com.itl.pns.corp.entity.CorpUserMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserReqEntity;

public interface CorpUserService {

	public List<CorpCompReqEntity> getCorpCompRequests();

	public List<CorpCompReqEntity> getCorpCompRequestsById(CorpCompReqEntity corpCompReqEntity);
	
	public List<CorpCompReqEntity> getCorpCompRequestsByRrn(CorpCompReqEntity corpCompReqEntity);
		
	public boolean updateCorpCompReqData(CorpCompDataMasterEntity compData);
	
	public List<CorpMenuReqEntity> getCorpMenuByCompanyId(CorpMenuReqEntity CorpMenuData);
	
	public List<CorpAccReqEntity> getCorpAccByCompanyId(CorpAccReqEntity corpAccData);
	
	public boolean saveCorpMenuAccData(CorpMenuAccountBean menuAccData);
	
	public boolean verifyAccountNumber(CorpMenuAccBean corpMenuAccData);

	public List<CorpUserReqEntity> getAllCorpUsersByCompId(CorpUserReqEntity corpuserReqData);
	
	public List<CorpUserMenuReqEntity>getMenuListByCorpUserId(CorpUserMenuReqEntity corpUserMenuData);
	
	public List<CorpUserAccReqEntity>getAccountListByCorpUserId(CorpUserAccReqEntity corpUserAccData);
	
	public List<CorpCompReqEntity> getCorpByCompNameCifCorpId(CorpCompReqEntity corpCompReqEntity);
	
	public boolean saveAllCorpData(@RequestBody CorpDataBean corpData);
	
}
