package com.itl.pns.corp.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.CorpTransLimitBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.dao.CorporateDao;
import com.itl.pns.corp.entity.CorpAccUserTypeEntity;
import com.itl.pns.corp.entity.CorpAcctTransLimitUsersEntity;
import com.itl.pns.corp.entity.CorpAcctTransMasterEntity;
import com.itl.pns.corp.entity.CorpApproverMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMenuMappingEntity;
import com.itl.pns.corp.entity.CorpHierarchyMaster;
import com.itl.pns.corp.entity.CorpLevelMasterEntity;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.entity.CorpMenuMapping;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserTypeEntity;
import com.itl.pns.corp.service.CorporateService;
import com.itl.pns.repository.CorpAccTransLimitUserRepository;
import com.itl.pns.repository.CorpAccTransMasterRepository;
import com.itl.pns.repository.CorpCompMenuMapRepo;
import com.itl.pns.repository.CorpMenuMappingRepository;

@Service
@Component
public class CorporateServiceImpl implements CorporateService {

	static Logger LOGGER = Logger.getLogger(CorporateServiceImpl.class);

	@Autowired
	CorpCompMenuMapRepo corpCompMenuMapRepo;

	@Autowired
	CorpMenuMappingRepository corpMenuMappingRepository;

	@Autowired
	CorporateDao corpDao;

	@Autowired
	CorpAccTransLimitUserRepository corpAccTransLimitUserRepository;

	@Autowired
	CorpAccTransMasterRepository corpAccTransMasterRepository;

	@Override
	public boolean addCorpMenu(CorpMenuEntity corpMenuData) {
		return corpDao.addCorpMenu(corpMenuData);
	}

	@Override
	public boolean updateCorpMenu(CorpMenuEntity corpMenuData) {
		return corpDao.updateCorpMenu(corpMenuData);
	}

	@Override
	public List<CorpMenuEntity> getCorpMenuById(int id) {
		return corpDao.getCorpMenuById(id);
	}

	@Override
	public List<CorpMenuEntity> getAllCorpMenus() {
		return corpDao.getAllCorpMenus();
	}

	@Override
	public boolean addCorpCompanyDetails(CorpCompanyMasterEntity corpCompanyData) {
		return corpDao.addCorpCompanyDetails(corpCompanyData);
	}

	@Override
	public boolean updateCorpCompanyDetails(CorpCompanyMasterEntity corpCompanyData) {
		return corpDao.updateCorpCompanyDetails(corpCompanyData);
	}

	@Override
	public List<CorpCompanyMasterEntity> getCorpCompanyDetailsByID(int id) {
		return corpDao.getCorpCompanyDetailsByID(id);
	}

	@Override
	public List<CorpCompanyMasterEntity> getAllCorpCompanyDetails() {
		return corpDao.getAllCorpCompanyDetails();
	}

	@Override
	public List<CorpUserTypeEntity> getCorpUserTypes() {
		return corpDao.getCorpUserTypes();
	}

	@Override
	public boolean addCorpUserTypes(CorpUserTypeEntity corpUserTypeData) {
		return corpDao.addCorpUserTypes(corpUserTypeData);
	}

	@Override
	public boolean updateCorpUserTypes(CorpUserTypeEntity corpUserTypeData) {
		return corpDao.updateCorpUserTypes(corpUserTypeData);
	}

	@Override
	public List<CorpUserTypeEntity> getCorpUserTypesById(int id) {
		return corpDao.getCorpUserTypesById(id);
	}

	@Override
	public List<CorpCompanyMenuMappingEntity> getCorpCompanyMenuListById(int id) {
		List<CorpCompanyMenuMappingEntity> list = corpDao.getCorpCompanyMenuListById(id);
		return list;

	}

	@Override
	public List<CorpCompanyMenuMappingEntity> getCorpCompanyMenuListByCompanyId(int compId) {
		List<CorpCompanyMenuMappingEntity> list = corpDao.getCorpCompanyMenuListByCompanyId(compId);
		return list;

	}

	@Override
	public boolean addCorpUserTypeAccount(CorpAccUserTypeEntity corpUserAccData) {
		return corpDao.addCorpUserTypeAccount(corpUserAccData);
	}

	@Override
	public boolean updateCorpUserTypeAccount(CorpAccUserTypeEntity corpUserAccData) {
		return corpDao.updateCorpUserTypeAccount(corpUserAccData);
	}

	@Override
	public List<CorpAccUserTypeEntity> getAllCorpUserTypeAccount() {
		return corpDao.getAllCorpUserTypeAccount();
	}

	@Override
	public List<CorpAccUserTypeEntity> getCorpUserTypeAccountById(int id) {
		return corpDao.getCorpUserTypeAccountById(id);
	}

	@Override
	public Boolean addCorpCompanyMenu(CorpCompanyMenuMappingEntity corpCompMenuData) {
		return corpDao.addCorpCompanyMenu(corpCompMenuData);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveCorpCompMenuRights(List<CorpCompanyMenuMappingEntity> corpCompanyMenuMap) {
		try {
			boolean success = true;
			if (corpCompanyMenuMap != null && !corpCompanyMenuMap.isEmpty()) {
				List<CorpCompanyMenuMappingEntity> menuSubmenuEntityNew = corpCompMenuMapRepo
						.findByCompanyId(corpCompanyMenuMap.get(0).getCompanyId().toBigInteger());
				if (!menuSubmenuEntityNew.isEmpty())// If Exist
				{
					System.out.println(menuSubmenuEntityNew.get(0).getCompanyId());
					System.out.println("Exists data");
					corpCompMenuMapRepo.deleteByCompanyId(menuSubmenuEntityNew.get(0).getCompanyId().toBigInteger());
				}
			}
			if (success) {
				corpCompMenuMapRepo.save(corpCompanyMenuMap);
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveCorpMenuRights(List<CorpMenuMapping> corpMenuMapping) {
		try {
			boolean success = true;
			if (corpMenuMapping != null && !corpMenuMapping.isEmpty()) {
				List<CorpMenuMapping> menuSubmenuEntityNew = corpMenuMappingRepository.findByRoleidAndCorporatecompid(
						corpMenuMapping.get(0).getRoleid().toBigInteger(), corpMenuMapping.get(0).getCorporatecompid().toBigInteger());
				if (!menuSubmenuEntityNew.isEmpty())// If Exist
				{
					for (CorpMenuMapping menuData : menuSubmenuEntityNew) {

						System.out.println(menuSubmenuEntityNew.get(0).getRoleid() + "----"
								+ menuSubmenuEntityNew.get(0).getCorporatecompid());
						System.out.println("Exists data");
						corpMenuMappingRepository.deleteByRoleidAndCorporatecompid(menuData.getRoleid().toBigInteger(),
								menuData.getCorporatecompid().toBigInteger(), menuData.getId().toBigInteger());
					}
				}
			}
			if (success) {

				corpMenuMappingRepository.save(corpMenuMapping);
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
	}

	@Override
	public List<CorpMenuMapping> getCorpMenuRightsForRoleId(int companyId, int roleId) {
		List<CorpMenuMapping> list = null;
		try {
			return corpDao.getCorpMenuRightsForRoleId(companyId, roleId);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public boolean addCorpUser(CorpUserEntity corpUserData) {
		return corpDao.addCorpUser(corpUserData);
	}

	@Override
	public boolean updateCorpUser(CorpUserEntity corpUserData) {
		return corpDao.updateCorpUser(corpUserData);
	}

	@Override
	public List<CorpUserEntity> getCorpUserById(int id) {
		return corpDao.getCorpUserById(id);
	}

	@Override
	public List<CorpUserEntity> getAllCorpUsers() {
		return corpDao.getAllCorpUsers();
	}

	@Override
	public Boolean updateCorpCompanyMenu(CorpCompanyMenuMappingEntity corpCompMenuData) {
		return corpDao.updateCorpCompanyMenu(corpCompMenuData);
	}

	@Override
	public List<CorpCompanyMenuMappingEntity> getAllCorpCompanyMenu() {
		return corpDao.getAllCorpCompanyMenu();
	}

	@Override
	public ResponseMessageBean checkIsUserExist(CorpUserEntity corpUserData) {
		return corpDao.checkIsUserExist(corpUserData);
	}

	@Override
	public List<CorpLevelMasterEntity> getCorpLevels() {
		return corpDao.getCorpLevels();
	}

	@Override
	public List<CorpUserEntity> getCorpUsersByCompId(int compId) {
		return corpDao.getCorpUsersByCompId(compId);
	}

	@Override
	public List<CorpHierarchyMaster> getCorpHierarchyList() {
		return corpDao.getCorpHierarchyList();
	}

	@Override
	public List<CorpApproverMasterEntity> getCorpApproverTypeList() {
		return corpDao.getCorpApproverTypeList();
	}

	@Override
	public boolean setCorpTransactionsLimit(CorpTransLimitBean corpTransData) {
		return corpDao.setCorpTransactionsLimit(corpTransData);
	}

	@Override
	public CorpTransLimitBean getAllTransationByAccNoAndCompId(String accNo, int compId) {
		return corpDao.getAllTransationByAccNoAndCompId(accNo, compId);
	}

	@Override
	public CorpTransLimitBean getTranByAccNoAndCompIdAndTransId(String accNo, int compId, long transId) {
		return corpDao.getTranByAccNoAndCompIdAndTransId(accNo, compId, transId);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateCorpTransactionsLimit(CorpTransLimitBean corpTransData) {

		try {

			List<CorpAcctTransMasterEntity> corpAccTransMasterList = corpAccTransMasterRepository
					.findByTranLimitIdAndAccountNumber(corpTransData.getCorpLimitData().get(0).getTransLimitId().toBigInteger(),
							corpTransData.getAccountNumber());
			if (!corpAccTransMasterList.isEmpty()) {
				for (CorpAcctTransMasterEntity corpTransAccData : corpAccTransMasterList) {
					List<CorpAcctTransLimitUsersEntity> corpAccTransUserList = corpAccTransLimitUserRepository
							.findByCatmId(corpTransAccData.getId().toBigInteger());
					for (CorpAcctTransLimitUsersEntity corpAccTransUserData : corpAccTransUserList) {
						corpAccTransLimitUserRepository.deleteByCatmId(corpTransAccData.getId().toBigInteger(),
								corpAccTransUserData.getId().toBigInteger());
					}
					corpAccTransMasterRepository.deleteByTransLimitid(corpTransAccData.getTranLimitId().toBigInteger(),
							corpTransAccData.getId().toBigInteger());
				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}

		return true;

	}
	
	@Override
	public List<CorpUserTypeEntity> getDynamicRolesByCompId(int compId) {
		return corpDao.getDynamicRolesByCompId(compId);
	}

	@Override
	public List<CorpUserEntity> getFirstCorpUserById(BigInteger id) {
		return corpDao.getFirstCorpUserById(id);
	}

	@Override
	public List<CorpUserEntity> getFirstAllCorpUsers() {
		return corpDao.getFirstAllCorpUsers();
	}

	@Override
	public boolean updateFirstCorpUsers(CorpUserEntity corpUserData) {
		return corpDao.updateFirstCorpUsers(corpUserData);
	}

	@Override
	public List<CorpCompanyMenuMappingEntity> getAllCorpCompanyMenuByCompId(CorpCompanyMenuMappingEntity compId) {
		return corpDao.getAllCorpCompanyMenuByCompId(compId);
	}

	@Override
	public ResponseMessageBean getAccountsByCif(String cif,String rrn, String referenceNumber) {
		return corpDao.getAccountsByCif(cif,rrn,referenceNumber);
	}

	@Override
	public ResponseMessageBean checkEmailExist(String email) {
		return corpDao.checkEmailExistByEmailId(email);
	}
}
