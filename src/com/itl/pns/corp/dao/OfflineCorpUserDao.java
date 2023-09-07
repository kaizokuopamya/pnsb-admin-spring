package com.itl.pns.corp.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.itl.pns.bean.CorpCompanyCorpUserDupBean;
import com.itl.pns.bean.CorpCompanyDataBean;
import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpMenuAccMasterBean;
import com.itl.pns.bean.CorpUserAccMapBean;
import com.itl.pns.bean.CorpUserBean;
import com.itl.pns.bean.CorpUserEntityBean;
import com.itl.pns.bean.CorpUserMenuMapBean;
import com.itl.pns.bean.CorporateResponse;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.VerifyCifPara;
import com.itl.pns.corp.entity.CorpAccMapEntity;
import com.itl.pns.corp.entity.CorpAccMapEntityTmp;
import com.itl.pns.corp.entity.CorpCompanyMasterDupEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpMenuMapEntity;
import com.itl.pns.corp.entity.CorpUserAccMapEntity;
import com.itl.pns.corp.entity.CorpUserAccMapEntityTmp;
import com.itl.pns.corp.entity.CorpUserDupEntity;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserEntityTmp;
import com.itl.pns.corp.entity.CorpUserMenuMapEntity;
import com.itl.pns.corp.entity.CorpUserMenuMapEntityTmp;

public interface OfflineCorpUserDao {

	List<CorpCompanyDataBean> getOfflineCorpCompData(List<Integer> statusList, String branchCode);

	List<CorpCompanyDataBean> getOfflineCorpCompDataById(CorpCompanyDataBean corpCompData);

	List<CorpCompanyMasterEntity> getOfflineCorpCompDataByRrn(CorpCompanyMasterEntity corpCompData);

	List<CorpCompanyDataBean> getOfflineCorpByCompNameCifCorpId(CorpCompanyDataBean corpCompReq);

	public ResponseMessageBean updateCorpCompData(CorpCompanyMasterEntity corpCompReq);

	List<CorpMenuMapEntity> getOfflineCorpMenuByCompanyId(CorpMenuMapEntity corpMenuData);

	List<CorpAccMapEntity> getOfflineCorpAccByCompanyId(CorpAccMapEntity corpAccData);

	List<CorpUserMenuMapEntity> getUserMenuByCorpUserId(CorpUserMenuMapEntity corpUserMenuData);

	List<CorpUserAccMapEntity> getUserAccountByCorpUserId(CorpUserAccMapEntity corpUserAccData);

	public List<CorpUserMenuMapEntity> getUserMenuListByCorpCompId(BigInteger companyId);

	public List<CorpUserAccMapEntity> getUserAccountListByCorpCompId(BigInteger companyId);

	public boolean saveToCorpCompMasterData(CorpDataBean corpData);

	public boolean updateOfflineCorpMenuMapData(CorpMenuAccMasterBean corpMenuAccData, BigDecimal updatedBy);

	public boolean updateOfflineCorpUserMenuData(List<CorpUserMenuMapEntity> corpMenuData);

	public boolean updateOfflineCorpUserAccData(List<CorpUserAccMapEntity> corpAccData);

	public boolean updateCorpUserData(List<CorpUserEntity> corpUserData);

	public boolean updateCorpUserMenuMapDetails(List<CorpUserEntity> corpUserData);

	public boolean updateCorpUserAccMapDetails(List<CorpUserEntity> corpUserData);

	public boolean deleteCorpUserMenu(BigInteger compId);

	public boolean deleteCorpUserAccounts(BigInteger compId);

	public boolean deleteFromCorpuserAccMapById(List<String> accNo, BigInteger compId);

	public List<CorpUserMenuMapBean> getUserMenuListByCorpUserId(CorpUserMenuMapEntity corpUserMenuData);

	public List<CorpUserAccMapBean> getUserAccountListByCorpUserId(CorpUserAccMapEntity corpUserAccMapBean);

	public List<CorpUserEntity> getCorpUserById(BigInteger id);

	public boolean deleteCorpUserData(CorpUserEntity corpData);

	public ResponseMessageBean isCompanyUserExist(CorpUserEntity corpUser);

	public ResponseMessageBean addCorpMasData(CorpDataBean corpData);

	public String getDataByCif(VerifyCifPara verifyCifPara);

	public boolean getCompanyDetailsByCif(String cif);

	public List<CorpUserEntity> getAllCorpUsersByCompId(CorpUserEntity corpuserReqData, boolean isDecrypted);

	public ResponseMessageBean checkCorpIdExist(CorpCompanyMasterEntity companyMasterEntity);

	public ResponseMessageBean saveOfflineCorpUserMasterData(CorpDataBean corpData);

	public ResponseMessageBean updateOfflineCorpUserMasterDataTemp(CorpDataBean corpData);

	public boolean updateAddCorpMasterUser(CorpDataBean corpData, String branchCode);

	public List<CorpUserEntityTmp> getAllCorpUsersByCompIdTemp(CorpUserEntityTmp corpuserReqData, boolean isDecrypted);

	public boolean updateOfflineCorpAccMapData(CorpMenuAccMasterBean corpMenuAccData, BigDecimal updatedBy);

	public List<CorpAccMapEntityTmp> getOfflineCorpAccByCompanyIdTmp(CorpAccMapEntityTmp corpAccData);

	public List<CorpUserMenuMapEntityTmp> getUserMenuListByCorpCompIdTmp(BigInteger companyId);

	public List<CorpUserAccMapEntityTmp> getUserAccountListByCorpCompIdTmp(BigInteger companyId);

	public Map<String, Object> validateHierarchy(BigDecimal corpCompId);

	public CorporateResponse approveRejectCompanyUser(CorpDataBean corpData, String companyName);

	public ResponseMessageBean saveOfflineCorpSingleUserMasterData(CorpDataBean corpData);

	public List<CorpUserMenuMapBean> getUserMenuListByCorpUserIdTmp(BigInteger userId);

	// added by jeetu
	public List<CorpUserEntity> getUserDetailsByCorpCompId(CorpUserEntity corpUserEntity);

	public ResponseMessageBean resendUserKitOnEmail(CorpDataBean corpDataBean);

	public ResponseMessageBean blockUnblock(CorpUserBean corpUserBean);

	public ResponseMessageBean submitOfflineCorpUserMasterData(CorpDataBean corpData);

	ResponseMessageBean saveOfflineCorpSingleUserMasterDatatosave(CorpDataBean corpData);

	void deleteAndMoveToTmpByCorpcompid(CorpDataBean corpData);

	public List<CorpUserEntity> getAllCorpUsersByUserName(List<String> userList, BigDecimal corpCompId);

	public boolean moveAndDeleteDataFromTmpToMasterTableForUser(BigDecimal corpCompId, BigDecimal userId,
			BigDecimal ogStatus);

	List<CorpUserEntityTmp> getAllCorpUsersByUserNameTmp(List<String> userList, BigDecimal corpCompId);

	public List<CorpMenuMapEntity> getOfflineCorpMenuByCompanyIdBeam(CorpMenuMapEntity corpMenuMapEntity);

	public List<CorpAccMapEntity> getOfflineCorpAccByCompanyIdBean(CorpAccMapEntity corpAccData);

	public List<CorpUserEntityBean> getAllCorpUsersByCompIdBeanStatus(CorpUserEntityBean corpuserBeanData,
			boolean isDecrypted);

	public List<CorpUserEntityBean> getAllCorpUsersByCompIdBean(CorpUserEntityBean corpuserBeanData,
			boolean isDecrypted);

	public List<CorpUserMenuMapBean> getUserMenuListByCorpCompIdBeanTmp(BigInteger companyId);

	public List<CorpUserMenuMapBean> getUserMenuListByCorpCompIdBean(BigInteger companyId);

	public List<CorpUserAccMapBean> getUserAccountListByCorpCompIdBeanTmp(BigInteger companyId);

	public List<CorpUserAccMapBean> getUserAccountListByCorpCompIdBean(BigInteger companyId);

	public List<CorpCompanyDataBean> getOfflineCorpCompBlockUnblockData(List<Integer> statusList, String branchCode);

	public List<CorpCompanyDataBean> getOfflineCorpCompBlockUnblockDataFromUsers(List<Integer> statusList,
			String branchCode);

	boolean isUserLimitMoreThanCorporateLimit(int corpCompId);

	boolean isCorporateWithTransactionRights(int corpCompId);

	public List<String> getOfflineCorpCompDataByBranchCodeAndStatus(CorpCompanyMasterEntity corpCompMasterData);

	public void saveOfflineCorpCompAndCorpUserData(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean,
			List<CorpUserEntity> corpUserEntities);

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

	List<CorpUserDupEntity> getAllCorpUsersDupByCompId(CorpUserDupEntity corpuserReqData, boolean isDecrypted,
			boolean makerValidated);

	boolean deleteFromCorpuserMapMenuById(List<BigDecimal> uncommon, BigInteger compId);

	public List<CorpAccMapEntity> getAllOfflineCorpAccByCompanyId(CorpAccMapEntity corpAccData);

	public ResponseMessageBean updateCorpUsersMode(CorpCompanyMasterEntity corpCompReq);

	void updateCompanyDetails(CorpCompanyMasterEntity corpCompanyMasterEntity);

	void rejectDupUser(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean);

	public List<CorpUserEntity> getAllCorpUsersByCompanyId(BigDecimal corpCompId);

	public HashSet<BigDecimal> findCorpUserIdByCompId(BigDecimal corpCompId);

	public boolean sendEmailAndSms(String companyName, BigDecimal corpId, String companyCode,
			Map<String, String> tempPassword, Collection<BigDecimal> newUsers, BigDecimal createdByUpdateBy);

	public void linkDlinkAccounts(Map<BigDecimal, List> userIdMap, BigDecimal oldCorpId);

	public List<CorpCompanyMasterEntity> getOfflineCorpCompDataByIdNew(CorpCompanyMasterEntity corpCompMasterData);
	
	public boolean deleteFromCorpuserMapMenuAndSubMenuById(BigDecimal menuId, BigDecimal subMenuId, BigInteger compId);

	//TODO COMMENT CODE FOR UAT RELEASE
//	public void verifyCompanyAndUserOgstatus(CorpDataBean corpData);

}
