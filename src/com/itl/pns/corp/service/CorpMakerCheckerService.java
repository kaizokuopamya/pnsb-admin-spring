package com.itl.pns.corp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.corp.entity.CorpActivitySettingMasterEntity;
import com.itl.pns.corp.entity.CorpTempSalProcessEntity;
import com.itl.pns.corp.entity.DesignationHierarchyMasterEntity;
import com.itl.pns.entity.ActivitySettingMasterEntity;

public interface CorpMakerCheckerService {
	
	public List<ActivitySettingMasterEntity> getAllActivitySettingForCorp();
	
	public List<CorpActivitySettingMasterEntity> getCorpActivitiesAndMapping(int companyId);
	
	void saveCorpActivities(List<CorpActivitySettingMasterEntity> corpActivityData);
	
	boolean bulkSalaryUpload(List<CorpTempSalProcessEntity> corpSalData);
	
	
	
	boolean addDesignationHierarchy(DesignationHierarchyMasterEntity designationData);

	boolean updateDesignationHierarchy(DesignationHierarchyMasterEntity designationData);

	public List<DesignationHierarchyMasterEntity> getAllDesignationHierarchy();

	public List<DesignationHierarchyMasterEntity> getDesignationHierarchyById(int id);
	
	public List<DesignationHierarchyMasterEntity> getDesignationHierarchyByCompId(int compId);
	
	public List<DesignationHierarchyMasterEntity>getAuthTypeByCompIdAndDesignationId(DesignationHierarchyMasterEntity corpReq);
	
	public boolean saveCorpUserLevels( List<CorpActivitySettingMasterEntity> corpActivityData);
	
	public boolean updateCorpUserLevels( List<CorpActivitySettingMasterEntity> corpActivityData);
	
	
	
	
	

	
	
	

}
