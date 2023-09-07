package com.itl.pns.corp.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.itl.pns.bean.CorpTransLimitBean;
import com.itl.pns.bean.ResponseMessageBean;

import com.itl.pns.corp.entity.CorpAccUserTypeEntity;
import com.itl.pns.corp.entity.CorpApproverMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMenuMappingEntity;
import com.itl.pns.corp.entity.CorpHierarchyMaster;
import com.itl.pns.corp.entity.CorpLevelMasterEntity;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.entity.CorpMenuMapping;
import com.itl.pns.corp.entity.CorpTransactionLimitEntity;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserTypeEntity;



public interface CorporateService {

	public boolean addCorpMenu(CorpMenuEntity corpMenuData);

	public boolean updateCorpMenu(CorpMenuEntity corpMenuData);

	public List<CorpMenuEntity> getCorpMenuById(int id);

	public List<CorpMenuEntity> getAllCorpMenus();
	
	
	
	public boolean addCorpCompanyDetails(CorpCompanyMasterEntity corpCompanyData);

	public boolean updateCorpCompanyDetails(CorpCompanyMasterEntity corpCompanyData);

	public List<CorpCompanyMasterEntity> getCorpCompanyDetailsByID(int id);

	public List<CorpCompanyMasterEntity> getAllCorpCompanyDetails();
	
	
	
	public boolean addCorpUserTypes(CorpUserTypeEntity corpUserTypeData);

	public boolean updateCorpUserTypes(CorpUserTypeEntity corpUserTypeData);

	public List<CorpUserTypeEntity> getCorpUserTypes();

	public List<CorpUserTypeEntity> getCorpUserTypesById(int id);
	
	
	
	public boolean addCorpUserTypeAccount(CorpAccUserTypeEntity corpUserAccData);
	
	public boolean updateCorpUserTypeAccount(CorpAccUserTypeEntity corpUserAccData);
	
	public  List<CorpAccUserTypeEntity> getAllCorpUserTypeAccount();

	public  List<CorpAccUserTypeEntity> getCorpUserTypeAccountById(int id);

	

	public ResponseMessageBean checkIsUserExist(CorpUserEntity corpUserData);
	
	public List<CorpCompanyMenuMappingEntity> getCorpCompanyMenuListById(int id);
	
	public List<CorpCompanyMenuMappingEntity> getCorpCompanyMenuListByCompanyId(int compId);
	

	
	public Boolean addCorpCompanyMenu(CorpCompanyMenuMappingEntity corpCompMenuData);
	
	public Boolean updateCorpCompanyMenu(CorpCompanyMenuMappingEntity corpCompMenuData);
	
	public List<CorpCompanyMenuMappingEntity> getAllCorpCompanyMenu();

	void saveCorpCompMenuRights(List<CorpCompanyMenuMappingEntity> corpCompanyMenuMap);
	
	void saveCorpMenuRights(List<CorpMenuMapping> corpMenuMapping);
	
	List<CorpMenuMapping> getCorpMenuRightsForRoleId(int companyId, int roleId);
	
	public ResponseMessageBean checkEmailExist(String email);

	
	public boolean addCorpUser(CorpUserEntity corpUserData);

	public boolean updateCorpUser(CorpUserEntity corpUserData);

	public List<CorpUserEntity> getCorpUserById(int id);

	public List<CorpUserEntity> getAllCorpUsers();
	
	public List<CorpUserEntity> getCorpUsersByCompId(int compId);
	
	public List<CorpLevelMasterEntity> getCorpLevels();
	
	public List<CorpHierarchyMaster> getCorpHierarchyList();
	
	public List<CorpApproverMasterEntity>getCorpApproverTypeList();
	
	
	public boolean setCorpTransactionsLimit(CorpTransLimitBean corpTransData);

	public boolean updateCorpTransactionsLimit(CorpTransLimitBean corpTransData);
	
	public CorpTransLimitBean getAllTransationByAccNoAndCompId(String accNo,int compId);
	
	
	public CorpTransLimitBean getTranByAccNoAndCompIdAndTransId(String accNo,int compId,long transId);

	public List<CorpUserTypeEntity> getDynamicRolesByCompId(int compId);
	
	public List<CorpUserEntity> getFirstCorpUserById(BigInteger id);
	
	public List<CorpUserEntity> getFirstAllCorpUsers();
	
	public boolean updateFirstCorpUsers(CorpUserEntity corpUserData);
	
	public List<CorpCompanyMenuMappingEntity> getAllCorpCompanyMenuByCompId(CorpCompanyMenuMappingEntity compId);
	
	public ResponseMessageBean getAccountsByCif(String omniDashData,String rrn, String referenceNumber);


}
