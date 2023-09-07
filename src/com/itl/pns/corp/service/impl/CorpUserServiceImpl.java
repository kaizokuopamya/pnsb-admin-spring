package com.itl.pns.corp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.entity.CorpAccReqEntity;
import com.itl.pns.corp.entity.CorpCompDataMasterEntity;
import com.itl.pns.corp.entity.CorpCompReqEntity;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.entity.CorpMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserAccMapEntity;
import com.itl.pns.corp.entity.CorpUserAccReqEntity;
import com.itl.pns.corp.entity.CorpUserMenuMapEntity;
import com.itl.pns.corp.entity.CorpUserMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserReqEntity;
import com.itl.pns.corp.service.CorpUserService;
import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpMenuAccBean;
import com.itl.pns.bean.CorpMenuAccountBean;
import com.itl.pns.corp.dao.CorpUserDao;
@Service
@Component
public class CorpUserServiceImpl implements CorpUserService {

	@Autowired
	CorpUserDao  CorpUserDao;

	@Override
	public List<CorpCompReqEntity> getCorpCompRequests() {
		return CorpUserDao.getCorpCompRequests();
	}
	

	@Override
	public List<CorpCompReqEntity> getCorpCompRequestsById(CorpCompReqEntity corpCompReqEntity) {
		return CorpUserDao.getCorpCompRequestsById(corpCompReqEntity);
	}


	@Override
	public List<CorpCompReqEntity> getCorpCompRequestsByRrn(CorpCompReqEntity corpCompReqEntity) {
		return CorpUserDao.getCorpCompRequestsByRrn(corpCompReqEntity);
	}


	@Override
	public boolean updateCorpCompReqData(CorpCompDataMasterEntity compData) {
		return CorpUserDao.updateCorpCompReqData(compData);
	}


	@Override
	public List<CorpMenuReqEntity> getCorpMenuByCompanyId(CorpMenuReqEntity CorpMenuData) {
		return CorpUserDao.getCorpMenuByCompanyId(CorpMenuData);
	}


	@Override
	public List<CorpAccReqEntity> getCorpAccByCompanyId(CorpAccReqEntity corpAccData) {
		return CorpUserDao.getCorpAccByCompanyId(corpAccData);
	}


	@Override
	public boolean saveCorpMenuAccData(CorpMenuAccountBean menuAccData) {
		return CorpUserDao.saveCorpMenuAccData(menuAccData);
	}


	@Override
	public boolean verifyAccountNumber(CorpMenuAccBean corpMenuAccData) {
		return CorpUserDao.verifyAccountNumber(corpMenuAccData);
	}


	@Override
	public List<CorpUserReqEntity> getAllCorpUsersByCompId(CorpUserReqEntity corpuserReqData) {
		return CorpUserDao.getAllCorpUsersByCompId(corpuserReqData);
	}


	@Override
	public List<CorpUserMenuReqEntity> getMenuListByCorpUserId(CorpUserMenuReqEntity corpUserMenuData) {
		return CorpUserDao.getMenuListByCorpUserId(corpUserMenuData);
	}


	@Override
	public List<CorpUserAccReqEntity> getAccountListByCorpUserId(CorpUserAccReqEntity  corpUserAccData) {
		return CorpUserDao.getAccountListByCorpUserId(corpUserAccData);
	}


	@Override
	public List<CorpCompReqEntity> getCorpByCompNameCifCorpId(CorpCompReqEntity corpCompReqEntity) {
		return CorpUserDao.getCorpByCompNameCifCorpId(corpCompReqEntity);
	}


	@Override
	public boolean saveAllCorpData(CorpDataBean corpData) {
		return CorpUserDao.saveAllCorpData(corpData);
	}


	

}
