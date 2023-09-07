package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.entity.AdminWorkFlowRequestEntity;

/**
 * @author shubham.lokhande
 *
 */
public interface AdminWorkFlowDao {


	public List<AdminWorkFlowRequestEntity> getAllDataForChecker();

	public List<AdminWorkFlowRequestEntity> getCheckerDataById(int id);
	
	public List<AdminWorkFlowRequestEntity> getAllDataForMaker();

	public List<AdminWorkFlowRequestEntity> getMakerDataById(int id);
	
	public Boolean updateStatusByChecker(AdminWorkFlowRequestEntity adminWorkFlowData);
	
	public Boolean updateRequestByMaker(AdminWorkFlowRequestEntity adminWorkFlowData);

	public Boolean updateStatusListByChecker(List<AdminWorkFlowRequestEntity> adminWorkFlowData);
	
	public List<AdminWorkFlowRequestEntity> getAllDataForApprover();

	public List<AdminWorkFlowRequestEntity> getApproverDataById(int id);

	public Boolean updateStatusByApprover(AdminWorkFlowRequestEntity adminWorkFlowData);

	public Boolean updateStatusListByApprover(List<AdminWorkFlowRequestEntity> adminWorkFlowData);
	
	
	
	
	public List<AdminWorkFlowRequestEntity> getAllDataForCorpChecker();

	public List<AdminWorkFlowRequestEntity> getAllCorpDataForMaker();

	public List<AdminWorkFlowRequestEntity> getAllCorpDataForApprover();
	 
	
	  public Boolean updateStatusByCorpChecker(AdminWorkFlowRequestEntity adminWorkFlowData);
		
		public Boolean updateRequestByCorpMaker(AdminWorkFlowRequestEntity adminWorkFlowData);

		public Boolean updateStatusListByCorpChecker(List<AdminWorkFlowRequestEntity> adminWorkFlowData);

		public Boolean updateStatusByCorpApprover(AdminWorkFlowRequestEntity adminWorkFlowData);

		public Boolean updateStatusListByCorpApprover(List<AdminWorkFlowRequestEntity> adminWorkFlowDataList);
 
}
