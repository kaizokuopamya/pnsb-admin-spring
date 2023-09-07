package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.entity.ForcePasswordPolicyEntity;
import com.itl.pns.dao.PasswordPolicyDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.repository.ForcePwdPolicyEntityRepository;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository("passwordPolicyDao")
@Transactional
public class PasswordPolicyDaoImpl implements PasswordPolicyDao {

	private static final Logger logger = LogManager.getLogger(PasswordPolicyDaoImpl.class);

	@Autowired
	private ForcePwdPolicyEntityRepository forcePwdPolicyEntityRepository;

	@Autowired
	private AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Override
	public ForcePasswordPolicyEntity getForcePassword(String id) {
		ForcePasswordPolicyEntity forcePasswordPolicy = forcePwdPolicyEntityRepository.findOne(Long.valueOf(id));
		if (ObjectUtils.isEmpty(forcePasswordPolicy)) {
			return new ForcePasswordPolicyEntity();
		}
		return forcePasswordPolicy;
	}

	@Override
	public List<ForcePasswordPolicyEntity> forcePasswordPolicyList() {
		List<ForcePasswordPolicyEntity> forcePasswordPolicyList = forcePwdPolicyEntityRepository
				.findAllByStatusId(ApplicationConstants.ACTIVE_STATUS);
		if (ObjectUtils.isEmpty(forcePasswordPolicyList)) {
			return new ArrayList<ForcePasswordPolicyEntity>();
		}
		return forcePasswordPolicyList;
	}

	@Override
	public ForcePasswordPolicyEntity saveForcePassword(ForcePasswordPolicyEntity forcePasswordPolicyEntity) {

		int userStatus = forcePasswordPolicyEntity.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility
				.getRoleNameByRoleId(forcePasswordPolicyEntity.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.BANK_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(forcePasswordPolicyEntity.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				forcePasswordPolicyEntity.setStatusId(statusId); // 97- ADMIN_CHECKER_PENDIN
			}
			forcePasswordPolicyEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			forcePasswordPolicyEntity.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			forcePasswordPolicyEntity = forcePwdPolicyEntityRepository.save(forcePasswordPolicyEntity);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(new BigDecimal(forcePasswordPolicyEntity.getAppId()));
				adminData.setCreatedByUserId(forcePasswordPolicyEntity.getUser_ID());
				adminData.setCreatedByRoleId(forcePasswordPolicyEntity.getRole_ID());
				adminData.setPageId(forcePasswordPolicyEntity.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(new BigDecimal(forcePasswordPolicyEntity.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(forcePasswordPolicyEntity));
				adminData.setActivityId(forcePasswordPolicyEntity.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 182- CHECKER_PENDING
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(forcePasswordPolicyEntity.getRemark());
				adminData.setActivityName(forcePasswordPolicyEntity.getActivityName());
				adminData.setActivityRefNo(new BigDecimal(forcePasswordPolicyEntity.getId()));
				adminData.setTableName("FORCE_PWD_POLICY");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setBranchCode(forcePasswordPolicyEntity.getBranchCode());
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistoryContent(forcePasswordPolicyEntity.getSubMenu_ID(),
						new BigDecimal(forcePasswordPolicyEntity.getId()),
						new BigDecimal(forcePasswordPolicyEntity.getCreatedby()), forcePasswordPolicyEntity.getRemark(),
						forcePasswordPolicyEntity.getRole_ID(), mapper.writeValueAsString(forcePasswordPolicyEntity));
//						forcePasswordPolicyEntity.getBranchCode());
			}

			return forcePasswordPolicyEntity;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return forcePasswordPolicyEntity;
		}

	}

}
