package com.itl.pns.corp.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.CorpCompanyCorpUserDupBean;
import com.itl.pns.bean.CorpCompanyDataBean;
import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpUserBean;
import com.itl.pns.bean.CorpUserEntityBean;
import com.itl.pns.bean.CorpUserMenuMapBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.VerifyCifPara;
import com.itl.pns.corp.dao.OfflineCorpUserDao;
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
import com.itl.pns.corp.repository.CorpMenuMapRepository;
import com.itl.pns.corp.service.OfflineCorpUserService;

@Service
@Component
@Transactional
public class OfflineCorpUserServiceImpl implements OfflineCorpUserService {

	@Autowired
	OfflineCorpUserDao offlineCorpUserDao;
	
	@Autowired
	CorpMenuMapRepository corpMenuMapRepository;

	@Override
	public List<CorpCompanyDataBean> getOfflineCorpCompData(List<Integer> statusList, String branchCode) {
		return offlineCorpUserDao.getOfflineCorpCompData(statusList, branchCode);
	}

	@Override
	public List<CorpCompanyDataBean> getOfflineCorpCompDataById(CorpCompanyDataBean corpCompMasterData) {
		return offlineCorpUserDao.getOfflineCorpCompDataById(corpCompMasterData);
	}

	@Override
	public List<CorpCompanyMasterEntity> getOfflineCorpCompDataByRrn(CorpCompanyMasterEntity corpCompMasterData) {
		return offlineCorpUserDao.getOfflineCorpCompDataByRrn(corpCompMasterData);
	}

	@Override
	public List<CorpCompanyDataBean> getOfflineCorpByCompNameCifCorpId(CorpCompanyDataBean corpCompReq) {
		return offlineCorpUserDao.getOfflineCorpByCompNameCifCorpId(corpCompReq);
	}

	@Override
	public List<CorpMenuMapEntity> getOfflineCorpMenuByCompanyId(CorpMenuMapEntity corpMenuData) {
		return offlineCorpUserDao.getOfflineCorpMenuByCompanyId(corpMenuData);
	}

	@Override
	public List<CorpAccMapEntity> getOfflineCorpAccByCompanyId(CorpAccMapEntity corpAccData) {
		return offlineCorpUserDao.getOfflineCorpAccByCompanyId(corpAccData);
	}

	@Override
	public List<CorpUserMenuMapEntity> getUserMenuByCorpUserId(CorpUserMenuMapEntity corpUserMenuData) {
		return offlineCorpUserDao.getUserMenuByCorpUserId(corpUserMenuData);
	}

	@Override
	public List<CorpUserAccMapEntity> getUserAccountByCorpUserId(CorpUserAccMapEntity corpUserAccData) {
		return offlineCorpUserDao.getUserAccountByCorpUserId(corpUserAccData);
	}

	@Override
	public List<CorpUserEntity> getAllCorpUsersByCompId(CorpUserEntity corpuserReqData, boolean isDecrypted) {
		return offlineCorpUserDao.getAllCorpUsersByCompId(corpuserReqData, isDecrypted);
	}

	@Override
	public ResponseMessageBean updateCorpCompData(CorpCompanyMasterEntity corpCompReq) {
		return offlineCorpUserDao.updateCorpCompData(corpCompReq);
	}

	@Override
	public boolean updateCorpUserData(List<CorpUserEntity> corpUserData) {
		return offlineCorpUserDao.updateCorpUserData(corpUserData);
	}

	@Override
	public boolean deleteCorpUserData(CorpUserEntity corpData) {
		return offlineCorpUserDao.deleteCorpUserData(corpData);
	}

	@Override
	public String getDataByCif(VerifyCifPara verifyCifPara) {
		return offlineCorpUserDao.getDataByCif(verifyCifPara);
	}

	@Override
	public boolean getCompanyDetailsByCif(String cif) {
		return offlineCorpUserDao.getCompanyDetailsByCif(cif);
	}

	@Override
	public ResponseMessageBean checkCorpIdExist(CorpCompanyMasterEntity companyMasterEntity) {
		return offlineCorpUserDao.checkCorpIdExist(companyMasterEntity);
	}

	@Override
	public List<CorpUserEntityTmp> getAllCorpUsersByCompIdTmp(CorpUserEntityTmp corpuserReqData, boolean isDecrypted) {
		return offlineCorpUserDao.getAllCorpUsersByCompIdTemp(corpuserReqData, isDecrypted);
	}

	@Override
	public List<CorpAccMapEntityTmp> getOfflineCorpAccByCompanyIdTmp(CorpAccMapEntityTmp corpAccData) {
		return offlineCorpUserDao.getOfflineCorpAccByCompanyIdTmp(corpAccData);
	}

	@Override
	public List<CorpUserMenuMapBean> getUserMenuListByCorpUserIdTmp(BigInteger userId) {
		return offlineCorpUserDao.getUserMenuListByCorpUserIdTmp(userId);
	}

	public ResponseMessageBean resendUserKitOnEmail(CorpDataBean corpDataBean) {
		return offlineCorpUserDao.resendUserKitOnEmail(corpDataBean);
	}

	public ResponseMessageBean blockUnblock(CorpUserBean corpUserBean) {
		return offlineCorpUserDao.blockUnblock(corpUserBean);
	}

	@Override
	public List<CorpMenuMapEntity> getOfflineCorpMenuByCompanyIdBeam(CorpMenuMapEntity corpMenuMapEntity) {
		return offlineCorpUserDao.getOfflineCorpMenuByCompanyIdBeam(corpMenuMapEntity);
	}

	@Override
	public List<CorpAccMapEntity> getOfflineCorpAccByCompanyIdBean(CorpAccMapEntity corpAccData) {
		return offlineCorpUserDao.getOfflineCorpAccByCompanyIdBean(corpAccData);
	}

	@Override
	public List<CorpUserEntityBean> getAllCorpUsersByCompIdBeanStatus(CorpUserEntityBean corpuserBeanData,
			boolean isDecrypted) {
		return offlineCorpUserDao.getAllCorpUsersByCompIdBeanStatus(corpuserBeanData, isDecrypted);
	}

	@Override
	public List<CorpUserEntityBean> getAllCorpUsersByCompIdBean(CorpUserEntityBean corpuserBeanData,
			boolean isDecrypted) {
		return offlineCorpUserDao.getAllCorpUsersByCompIdBean(corpuserBeanData, isDecrypted);
	}

	public List<CorpCompanyDataBean> getOfflineCorpCompBlockUnblockData(List<Integer> statusList, String branchCode) {
		return offlineCorpUserDao.getOfflineCorpCompBlockUnblockData(statusList, branchCode);
	}

	public List<CorpCompanyDataBean> getOfflineCorpCompBlockUnblockDataFromUsers(List<Integer> statusList,
			String branchCode) {
		return offlineCorpUserDao.getOfflineCorpCompBlockUnblockDataFromUsers(statusList, branchCode);
	}

	public List<String> getOfflineCorpCompDataByBranchCodeAndStatus(CorpCompanyMasterEntity corpCompMasterData) {
		return offlineCorpUserDao.getOfflineCorpCompDataByBranchCodeAndStatus(corpCompMasterData);
	}

	public List<CorpCompanyMasterDupEntity> getOfflineCorpCompDataDupByBranchCodeAndStatus(
			CorpCompanyMasterDupEntity corpCompMasterData) {
		return offlineCorpUserDao.getOfflineCorpCompDataDupByBranchCodeAndStatus(corpCompMasterData);
	}

	public void saveOfflineCorpCompAndCorpUserData(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean,List<CorpUserEntity> corpUserEntities) {
		offlineCorpUserDao.saveOfflineCorpCompAndCorpUserData(corpCompanyCorpUserDupBean, corpUserEntities);
	}

	public List<CorpCompanyCorpUserDupBean> getOfflineCorpCompAndCorpUserData(
			CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {
		return offlineCorpUserDao.getOfflineCorpCompAndCorpUserData(corpCompanyCorpUserDupBean);
	}

	public List<CorpCompanyCorpUserDupBean> getOfflineCorpCompAndCorpUserDataDup(
			CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {
		return offlineCorpUserDao.getOfflineCorpCompAndCorpUserDataDup(corpCompanyCorpUserDupBean);
	}

	public List<CorpCompanyMasterDupEntity> getOfflineCorpCompDupByCif(
			CorpCompanyMasterDupEntity corpCompanyMasterDupEntity) {
		return offlineCorpUserDao.getOfflineCorpCompDupByCif(corpCompanyMasterDupEntity);
	}

	public List<CorpUserDupEntity> getOfflineCorpUsersDupById(CorpUserDupEntity corpUserDupEntity) {
		return offlineCorpUserDao.getOfflineCorpUsersDupById(corpUserDupEntity);
	}

	public List<CorpCompanyMasterEntity> getOfflineCorpCompDatByCif(CorpCompanyMasterEntity corpCompanyMasterEntity) {
		return offlineCorpUserDao.getOfflineCorpCompDatByCif(corpCompanyMasterEntity);
	}

	public List<CorpUserEntity> getOfflineCorpUsersById(CorpUserEntity corpUserEntity) {
		return offlineCorpUserDao.getOfflineCorpUsersById(corpUserEntity);

	}

	public void updateCorpCompanyAndUserDetails(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {
		offlineCorpUserDao.updateCorpCompanyAndUserDetails(corpCompanyCorpUserDupBean);
	}

	public ResponseMessageBean checkCorpIdInDup(CorpCompanyMasterEntity companyMasterEntity) {
		return offlineCorpUserDao.checkCorpIdInDup(companyMasterEntity);
	}

	@Override
	public List<CorpUserDupEntity> getAllCorpUsersDupByCompId(CorpUserDupEntity corpuserReqData, boolean isDecrypted, boolean makerValidated) {
		return offlineCorpUserDao.getAllCorpUsersDupByCompId(corpuserReqData, isDecrypted, makerValidated);
	}

	@Override
	public ResponseMessageBean updateCorpUsersMode(CorpCompanyMasterEntity corpCompReq) {
		return offlineCorpUserDao.updateCorpUsersMode(corpCompReq);
	}

	@Override
	public void updateCompanyDetails(CorpCompanyMasterEntity corpCompanyMasterEntity) {
		offlineCorpUserDao.updateCompanyDetails(corpCompanyMasterEntity);		
	}

	@Override
	public void rejectDupUser(CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {
		offlineCorpUserDao.rejectDupUser(corpCompanyCorpUserDupBean);		
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public void corpCompMenuUpdateById(BigDecimal corpId) {
		corpMenuMapRepository.corpCompMenuUpdateById(corpId);	
	}

}
