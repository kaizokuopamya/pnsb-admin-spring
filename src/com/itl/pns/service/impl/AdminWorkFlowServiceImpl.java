package com.itl.pns.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.dao.AdminWorkFlowDao;
import com.itl.pns.dao.AdministrationDao;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.AdminWorkflowHistoryEntity;
import com.itl.pns.service.AdminWorkFlowService;


/**
 * @author shubham.lokhande
 *
 */
@Service
public class AdminWorkFlowServiceImpl implements AdminWorkFlowService{

	@Autowired
	AdminWorkFlowDao adminWorkFlowDao;
	
	@Autowired
	AdministrationDao userDao;

	@Override
	public List<AdminWorkFlowRequestEntity> getAllDataForChecker() {
		List<String> contentList = new ArrayList<String>();
		List<AdminWorkFlowRequestEntity> adminWorkFlowList =adminWorkFlowDao.getAllDataForChecker();
		/*for(AdminWorkFlowRequestEntity adminWorkFlowObj :adminWorkFlowList){

			List<AdminWorkflowHistoryEntity> adminHistoryData =userDao.getAdminWorkflowHistoryDetailsById
					(adminWorkFlowObj.getActivityRefNo().intValue(), adminWorkFlowObj.getPageId().intValue());
			
			for(AdminWorkflowHistoryEntity obj :adminHistoryData){
				String data = null;
				data=obj.getContent();
				contentList.add(data);
			}
			
			adminWorkFlowObj.setContentList(contentList);
		}*/
		
	
		return adminWorkFlowList;
		
		
		
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getCheckerDataById(int id) {
		
		return adminWorkFlowDao.getCheckerDataById(id);
	}
	
	@Override
	public List<AdminWorkFlowRequestEntity> getAllDataForMaker() {
		return adminWorkFlowDao.getAllDataForMaker();
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getMakerDataById(int id) {
		return adminWorkFlowDao.getMakerDataById(id);
	}

	@Override
	public Boolean updateStatusByChecker(AdminWorkFlowRequestEntity adminWorkFlowData) {
		return adminWorkFlowDao.updateStatusByChecker(adminWorkFlowData);
	}

	@Override
	public Boolean updateRequestByMaker(AdminWorkFlowRequestEntity adminWorkFlowData) {
		return adminWorkFlowDao.updateRequestByMaker(adminWorkFlowData);
	}

	@Override
	public Boolean updateStatusByListChecker(List<AdminWorkFlowRequestEntity> adminWorkFlowData) {
		return adminWorkFlowDao.updateStatusListByChecker(adminWorkFlowData);
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getAllDataForApprover() {
		return adminWorkFlowDao.getAllDataForApprover();
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getApproverDataById(int id) {
		return adminWorkFlowDao.getApproverDataById(id);
	}

	@Override
	public Boolean updateStatusByApprover(AdminWorkFlowRequestEntity adminWorkFlowData) {
		return adminWorkFlowDao.updateStatusByApprover(adminWorkFlowData);
	}

	@Override
	public Boolean updateStatusByListApprover(List<AdminWorkFlowRequestEntity> adminWorkFlowDataList) {
		return adminWorkFlowDao.updateStatusListByApprover(adminWorkFlowDataList);
	}

	@Override
	public List<AdminWorkFlowRequestEntity> getAllDataForCorpChecker() {
		return adminWorkFlowDao.getAllDataForCorpChecker();
	}


	@Override
	public List<AdminWorkFlowRequestEntity> getAllCorpDataForMaker() {
		return adminWorkFlowDao.getAllCorpDataForMaker();
	}


	@Override
	public List<AdminWorkFlowRequestEntity> getAllCorpDataForApprover() {
		return adminWorkFlowDao.getAllCorpDataForApprover();
	}

	@Override
	public Boolean updateStatusByCorpChecker(AdminWorkFlowRequestEntity adminWorkFlowData) {
		return adminWorkFlowDao.updateStatusByCorpChecker(adminWorkFlowData);
	}

	@Override
	public Boolean updateRequestByCorpMaker(AdminWorkFlowRequestEntity adminWorkFlowData) {
		return adminWorkFlowDao.updateRequestByCorpMaker(adminWorkFlowData);
	}

	@Override
	public Boolean updateStatusListByCorpChecker(List<AdminWorkFlowRequestEntity> adminWorkFlowData) {
		return adminWorkFlowDao.updateStatusListByCorpChecker(adminWorkFlowData);
	}

	@Override
	public Boolean updateStatusByCorpApprover(AdminWorkFlowRequestEntity adminWorkFlowData) {
		return adminWorkFlowDao.updateStatusByCorpApprover(adminWorkFlowData);
	}

	@Override
	public Boolean updateStatusListByCorpApprover(List<AdminWorkFlowRequestEntity> adminWorkFlowDataList) {
		return adminWorkFlowDao.updateStatusListByCorpApprover(adminWorkFlowDataList);
	}

	
	
}
