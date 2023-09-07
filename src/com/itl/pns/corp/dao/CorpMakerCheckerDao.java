 package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.entity.CorpActivitySettingMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpCompanyMenuMappingEntity;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.entity.CorpTempSalProcessEntity;
import com.itl.pns.corp.entity.CorpUserTypeEntity;
import com.itl.pns.corp.entity.DesignationHierarchyMasterEntity;
import com.itl.pns.entity.AccountTypesEntity;
import com.itl.pns.entity.ActivitySettingMasterEntity;

public interface CorpMakerCheckerDao {

	public List<ActivitySettingMasterEntity> getAllActivitySettingForCorp();
	
	public List<CorpActivitySettingMasterEntity> getCorpActivitiesAndMapping(int companyId);
	
	boolean bulkSalaryUpload(List<CorpTempSalProcessEntity> corpSalData);
	
    public ResponseMessageBean checkUpdateCompanyExit(CorpCompanyMasterEntity corpCompanyMasterData);
	
	public ResponseMessageBean checkCompanyExit(CorpCompanyMasterEntity corpCompanyMasterData);
	
	public ResponseMessageBean checkAccountTypeExit(AccountTypesEntity accountTypeData);
	
	public ResponseMessageBean checkUpdateAccountTypeExit(AccountTypesEntity accountTypeData);
	
	public ResponseMessageBean checkUserTypeExit(CorpUserTypeEntity corpUserType);
	
	public ResponseMessageBean checkUpdateUserTypeExit(CorpUserTypeEntity corpUserType);
	
	public ResponseMessageBean checkCorpMenuNameExit(CorpMenuEntity corpMenuData);
		
    public ResponseMessageBean checkUpdateCorpMenuNameExit(CorpMenuEntity corpMenuData);
	
    
    public ResponseMessageBean checkCorpMenuExitForCompany(CorpCompanyMenuMappingEntity corpCompMenuData);
    
    public ResponseMessageBean checkUpdateCorpMenuExitForCompany(CorpCompanyMenuMappingEntity corpCompMenuData);
    
    
    
    
    boolean addDesignationHierarchy(DesignationHierarchyMasterEntity designationData);

	boolean updateDesignationHierarchy(DesignationHierarchyMasterEntity designationData);

	public List<DesignationHierarchyMasterEntity> getAllDesignationHierarchy();

	public List<DesignationHierarchyMasterEntity> getDesignationHierarchyById(int id);
	
	public List<DesignationHierarchyMasterEntity> getDesignationHierarchyByCompId(int compId);
	
	public List<DesignationHierarchyMasterEntity>getAuthTypeByCompIdAndDesignationId(DesignationHierarchyMasterEntity corpReq); 
	
	
	public ResponseMessageBean checkDesignationAndLevelExits(DesignationHierarchyMasterEntity designationData);

	public ResponseMessageBean checkUpdateDesignationAndLevelExits(DesignationHierarchyMasterEntity designationData);

	
	public boolean saveCorpUserLevels(List<CorpActivitySettingMasterEntity> corpActivityData);
	
	public boolean updateCorpUserLevels(List<CorpActivitySettingMasterEntity> corpActivityData);
}
