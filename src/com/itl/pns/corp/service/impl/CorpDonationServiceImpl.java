package com.itl.pns.corp.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpDonationDao;
import com.itl.pns.corp.service.CorpDonationService;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.Donation;
import com.itl.pns.repository.DonationRepository;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Service
@Qualifier("CorpDonationService")
public class CorpDonationServiceImpl implements CorpDonationService {
	
	static Logger LOGGER = Logger.getLogger(CorpDonationServiceImpl.class);

    
	@Autowired CorpDonationDao corpDonationDao;
	
	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;	
	
	@Autowired
	DonationRepository donationRepository;
	
	@Override
	public List<Donation> getCorpDonations() {
		List<Donation> list =corpDonationDao.getCorpDonations();
		return list;
	}

	@Override
	public List<Donation> getCorpDonationById(int id) {
		return corpDonationDao.getCorpDonationById(id);
	}

	@Override
	@Transactional(value = "jpaTransactionManager",propagation = Propagation.REQUIRES_NEW)
	public boolean saveCorpDonationDetails(Donation donation) {
		
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(donation.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(donation.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				donation.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			
        	donation.setBankingType(donation.getBankingType().toUpperCase());
        	donation.setCreatedon(new Date());
        	donationRepository.save(donation);
        	
        	
        }
        catch(Exception e){
        	LOGGER.info("Exception:", e);
        	return false;
        }
		return true;
	}
  /**
   * update Donation Details
   */
	@Override
	@Transactional(value = "jpaTransactionManager",propagation = Propagation.REQUIRES_NEW)
	public Boolean updateCorpDonationDetails(Donation donation) { 
		int userStatus = donation.getStatusId().intValue();
			String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(donation.getRole_ID().intValue());
			int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(donation.getActivityName());
			
			try {
				if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
						&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
					donation.setStatusId(BigDecimal.valueOf(statusId));
			}
				
				
		Donation dona = donationRepository.getOne(donation.getId());
    	
		dona.setAccountNumber(donation.getAccountNumber());
		dona.setName(donation.getName());
		dona.setStatusId(donation.getStatusId());
		dona.setBankingType(donation.getBankingType().toUpperCase());
		
    	donationRepository.save(dona);
    	
    	if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
				&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker()))
				&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
			
		ObjectMapper mapper = new ObjectMapper();
		
			List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
					.getAdminWorkFlowDataByActivityRefNo(donation.getId().intValue(),donation.getSubMenu_ID());
		
		AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

		adminData.setCreatedOn(dona.getCreatedon());
		adminData.setCreatedByUserId(donation.getUser_ID());
		adminData.setCreatedByRoleId(donation.getRole_ID());
		adminData.setPageId(donation.getSubMenu_ID());       //set submenuId as pageid
		adminData.setCreatedBy(dona.getCreatedby());
		adminData.setContent(mapper.writeValueAsString(donation));   
		adminData.setActivityId(donation.getSubMenu_ID());  //set submenuId as activityid
		adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
		adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
		adminData.setRemark(donation.getRemark());
		adminData.setActivityName(donation.getActivityName());
		adminData.setActivityRefNo(donation.getId());
		adminData.setUserAction(BigDecimal.valueOf(userStatus));
		adminData.setTableName("DONATION_PRD");

		if(ObjectUtils.isEmpty(AdminDataList)){
			adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
			}else if(!ObjectUtils.isEmpty(AdminDataList)){
				adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
				adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				
				
			}
		 //Save data to admin work flow history
		adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(donation.getSubMenu_ID(),
				donation.getId(), dona.getCreatedby(),
				donation.getRemark(), donation.getRole_ID(),mapper.writeValueAsString(donation));

	
			}else{
				//if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(donation.getId().toBigInteger(), 
						BigInteger.valueOf(userStatus),donation.getSubMenu_ID());

			}
			return true;
		} catch (Exception ex) {
			LOGGER.info("Exception:", ex);
			return false;
		}
	}
	
	@Override
	public boolean saveCorpDonationToAdminWorkFlow(Donation donation,int userStatus) {
		
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(donation.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(donation.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				donation.setStatusId(BigDecimal.valueOf(statusId));   //97-  ADMIN_CHECKER_PENDIN
			}
			
        	donation.setBankingType(donation.getBankingType().toUpperCase());
        	donation.setCreatedon(new Date());
        	donationRepository.save(donation);
        	
        	if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getChecker())) 
					&& (ApplicationConstants.YESVALUE).equals(Character.toString(activityMasterData.get(0).getMaker())))  {
				
        		List<Donation> list =corpDonationDao.getCorpDonations();
				ObjectMapper mapper = new ObjectMapper();
				
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
			
				adminData.setCreatedByUserId(donation.getUser_ID());
				adminData.setCreatedByRoleId(donation.getRole_ID());
				adminData.setPageId(donation.getSubMenu_ID());       //set submenuId as pageid
				adminData.setCreatedBy(donation.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(donation));   
				adminData.setActivityId(donation.getSubMenu_ID());  //set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId));  //97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6));    // 6- checker roleid
				adminData.setRemark(donation.getRemark());
				adminData.setActivityName(donation.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("DONATION_PRD");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				
                   //Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(donation.getSubMenu_ID(),
					list.get(0).getId(),donation.getCreatedby(),
						donation.getRemark(), donation.getRole_ID(),mapper.writeValueAsString(donation));
				}
			
        }
        catch(Exception e){
        	LOGGER.info("Exception:", e);
        	return false;
        }
		return true;
	}

}

