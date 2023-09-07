package com.itl.pns.corp.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.CorpCompanyCorpUserDupBean;
import com.itl.pns.bean.CorpCompanyDataBean;
import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpUserBean;
import com.itl.pns.bean.CorpUserEntityBean;
import com.itl.pns.bean.CorpUserMenuMapBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.VerifyCifPara;
import com.itl.pns.corp.entity.CorpAccMapEntity;
import com.itl.pns.corp.entity.CorpAccMapEntityTmp;
import com.itl.pns.corp.entity.CorpCompanyMasterDupEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpMenuMapEntity;
import com.itl.pns.corp.entity.CorpUserAccMapEntity;
import com.itl.pns.corp.entity.CorpUserDupEntity;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserEntityTmp;
import com.itl.pns.corp.entity.CorpUserMenuMapEntity;

public interface OfflineCorpUserService {

	public List<CorpCompanyDataBean> getOfflineCorpCompData(List<Integer> statusId, String branchCode);

	public List<CorpCompanyDataBean> getOfflineCorpCompDataById(CorpCompanyDataBean corpCompMasterData);

	public List<CorpCompanyMasterEntity> getOfflineCorpCompDataByRrn(CorpCompanyMasterEntity corpCompReqEntity);

	public List<CorpCompanyDataBean> getOfflineCorpByCompNameCifCorpId(CorpCompanyDataBean corpCompReq);

	public List<CorpAccMapEntity> getOfflineCorpAccByCompanyId(CorpAccMapEntity corpAccData);

	public List<CorpUserMenuMapEntity> getUserMenuByCorpUserId(CorpUserMenuMapEntity corpUserMenuData);

	public List<CorpUserAccMapEntity> getUserAccountByCorpUserId(CorpUserAccMapEntity corpUserAccData);

	public List<CorpUserEntity> getAllCorpUsersByCompId(CorpUserEntity corpuserReqData, boolean isDecrypted);

	public boolean updateCorpUserData(List<CorpUserEntity> corpUserData);

	public boolean deleteCorpUserData(CorpUserEntity corpData);

	public ResponseMessageBean updateCorpCompData(CorpCompanyMasterEntity corpCompReq);

	public String getDataByCif(VerifyCifPara verifyCifPara);

	public boolean getCompanyDetailsByCif(String cif);

	public ResponseMessageBean checkCorpIdExist(CorpCompanyMasterEntity companyMasterEntity);

	public List<CorpUserEntityTmp> getAllCorpUsersByCompIdTmp(CorpUserEntityTmp corpuserReqData, boolean isDecrypted);

	public List<CorpAccMapEntityTmp> getOfflineCorpAccByCompanyIdTmp(CorpAccMapEntityTmp corpAccData);

	public List<CorpUserMenuMapBean> getUserMenuListByCorpUserIdTmp(BigInteger userId);

	public ResponseMessageBean resendUserKitOnEmail(CorpDataBean corpDataBean);

	public ResponseMessageBean blockUnblock(CorpUserBean corpUserBean);

	public List<CorpMenuMapEntity> getOfflineCorpMenuByCompanyId(CorpMenuMapEntity corpMenuData);

	public List<CorpMenuMapEntity> getOfflineCorpMenuByCompanyIdBeam(CorpMenuMapEntity corpMenuMapEntity);

	public List<CorpAccMapEntity> getOfflineCorpAccByCompanyIdBean(CorpAccMapEntity corpAccData);

	public List<CorpUserEntityBean> getAllCorpUsersByCompIdBeanStatus(CorpUserEntityBean corpuserBeanData,
			boolean isDecrypted);

	public List<CorpUserEntityBean> getAllCorpUsersByCompIdBean(CorpUserEntityBean corpuserBeanData,
			boolean isDecrypted);

	public List<CorpCompanyDataBean> getOfflineCorpCompBlockUnblockData(List<Integer> statusList, String branchCode);

	public List<CorpCompanyDataBean> getOfflineCorpCompBlockUnblockDataFromUsers(List<Integer> statusList,
			String branchCode);

	public List<String> getOfflineCorpCompDataByBranchCodeAndStatus(CorpCompanyMasterEntity corpCompMasterData);

	public void saveOfflineCorpCompAndCorpUserData(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean, List<CorpUserEntity> corpUserEntities);

	public List<CorpCompanyCorpUserDupBean> getOfflineCorpCompAndCorpUserData(
			CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean);

	public List<CorpCompanyCorpUserDupBean> getOfflineCorpCompAndCorpUserDataDup(
			CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean);

	public List<CorpCompanyMasterDupEntity> getOfflineCorpCompDupByCif(
			CorpCompanyMasterDupEntity corpCompanyMasterDupEntity);

	public List<CorpUserDupEntity> getOfflineCorpUsersDupById(CorpUserDupEntity corpUserDupEntity);

	public List<CorpCompanyMasterEntity> getOfflineCorpCompDatByCif(CorpCompanyMasterEntity corpCompanyMasterEntity);

	public List<CorpUserEntity> getOfflineCorpUsersById(CorpUserEntity corpUserEntity);

	public void updateCorpCompanyAndUserDetails(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean);

	public List<CorpCompanyMasterDupEntity> getOfflineCorpCompDataDupByBranchCodeAndStatus(
			CorpCompanyMasterDupEntity corpCompMasterData);

	public ResponseMessageBean checkCorpIdInDup(CorpCompanyMasterEntity companyMasterEntity);

	public List<CorpUserDupEntity> getAllCorpUsersDupByCompId(CorpUserDupEntity corpuserReqData, boolean isDecrypted,boolean makerValidated);

	public ResponseMessageBean updateCorpUsersMode(CorpCompanyMasterEntity corpCompReq);

	public void updateCompanyDetails(CorpCompanyMasterEntity corpCompanyMasterEntity);

	public void rejectDupUser(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean);

	public void corpCompMenuUpdateById(BigDecimal corpId);
	
}