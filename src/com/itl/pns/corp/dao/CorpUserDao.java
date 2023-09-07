package com.itl.pns.corp.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpMenuAccBean;
import com.itl.pns.bean.CorpMenuAccountBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.entity.CorpAccReqEntity;
import com.itl.pns.corp.entity.CorpCompDataMasterEntity;
import com.itl.pns.corp.entity.CorpCompReqEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserAccReqEntity;
import com.itl.pns.corp.entity.CorpUserMenuReqEntity;
import com.itl.pns.corp.entity.CorpUserReqEntity;

public interface CorpUserDao {

	public List<CorpCompReqEntity> getCorpCompRequests();

	public List<CorpCompReqEntity> getCorpCompRequestsById(CorpCompReqEntity corpCompReqEntity);

	public List<CorpCompReqEntity> getCorpCompRequestsByRrn(CorpCompReqEntity corpCompReqEntity);

	public boolean updateCorpCompReqData(CorpCompDataMasterEntity compData);

	public List<CorpMenuReqEntity> getCorpMenuByCompanyId(CorpMenuReqEntity CorpMenuData);

	public List<CorpAccReqEntity> getCorpAccByCompanyId(CorpAccReqEntity corpAccData);

	public boolean saveCorpMenuAccData(CorpMenuAccountBean menuAccData);

	public boolean verifyAccountNumber(CorpMenuAccBean corpMenuAccData);

	public boolean insertToCorpMenuMap(CorpMenuReqEntity menuData);

	public boolean insertToCorpAccMap(CorpAccReqEntity accData);

	public List<CorpUserReqEntity> getAllCorpUsersByCompId(CorpUserReqEntity corpuserReqData);

	public List<CorpUserMenuReqEntity> getMenuListByCorpUserId(CorpUserMenuReqEntity corpUserMenuData);

	public List<CorpUserAccReqEntity> getAccountListByCorpUserId(CorpUserAccReqEntity corpUserAccData);

	public List<CorpUserMenuReqEntity> getMenuListByCorpCompId(BigInteger companyId);

	public List<CorpUserAccReqEntity> getAccountListByCorpCompId(BigInteger companyId);

	public List<CorpCompReqEntity> getCorpByCompNameCifCorpId(CorpCompReqEntity corpCompReqEntity);

	public boolean saveAllCorpData(@RequestBody CorpDataBean corpData);

	public boolean saveToCorpCompMasterData(CorpDataBean corpData);

	public boolean saveToCorpMenuMap(CorpDataBean corpData);

	public boolean saveToCorpAccMap(CorpDataBean corpData);

	public boolean saveCorpUserMasterData(CorpDataBean corpData);

	public boolean saveToCorpUsersMenuMap(CorpDataBean corpData);

	public boolean saveToCorpUsersAccMap(CorpDataBean corpData);

	public BigInteger getUserIdByRRN(String rrn);

	public boolean updateStatusOfCorpRequestData(CorpDataBean corpData);

	public ResponseMessageBean isCompanyExist(CorpCompDataMasterEntity corpCompData);

	public ResponseMessageBean isCorpCompanyExist(CorpCompanyMasterEntity corpCompMasterData);

	public String getCompanyMasterById(BigDecimal id);
	
	public String getCompanyMasterByIdTmp(BigDecimal id);

//	public String getCompanyMasterByCompanyCode(String companyCode);

//	public boolean saveToCorpMenuMapTmp(CorpDataBeanTmp corpData);
//
//	public boolean saveToCorpAccMapTmp(CorpDataBeanTmp corpData);
	
	public String getBranchCodeMasterByIdTmp(BigDecimal id);
}
