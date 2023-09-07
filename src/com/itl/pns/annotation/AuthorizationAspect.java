package com.itl.pns.annotation;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.AnnouncementsEntity;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.Utils;

@Aspect
@Component
public class AuthorizationAspect {

	static Logger LOGGER = Logger.getLogger(AuthorizationAspect.class);

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@After("@annotation(authorization)")
	public void afterAuthorizationAspect(JoinPoint authorizationJoinPoint, Authorization authorization) {
		Object[] obj = authorizationJoinPoint.getArgs();
		if (!ObjectUtils.isEmpty(obj)) {
			try {
				AnnouncementsEntity announcementsEntity = (AnnouncementsEntity) obj[0];

				String roleName = adminWorkFlowReqUtility
						.getRoleNameByRoleId(announcementsEntity.getRole_ID().intValue());
				int statusId = 0;
				if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
					statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
				} else if (roleName.equalsIgnoreCase(ApplicationConstants.CHECKER)) {
					statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.APPROVED);
				}
				List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
						.isMakerCheckerPresentForReq(announcementsEntity.getActivityName());

				if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
						|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

//					List<AnnouncementsEntity> list= getAllAnnouncementsDetails();
					ObjectMapper mapper = new ObjectMapper();

					AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
					adminData.setAppId(announcementsEntity.getAppId());
					adminData.setCreatedByUserId(announcementsEntity.getUser_ID());
					adminData.setCreatedByRoleId(announcementsEntity.getRole_ID());
					adminData.setPageId(announcementsEntity.getSubMenu_ID()); // set submenuId as pageid
					adminData.setCreatedBy(announcementsEntity.getCreatedby());
					adminData.setContent(mapper.writeValueAsString(announcementsEntity));
					adminData.setActivityId(announcementsEntity.getSubMenu_ID()); // set submenuId as activityid
					adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
					adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
					adminData.setRemark(announcementsEntity.getRemark());
					adminData.setActivityName(announcementsEntity.getActivityName());
					adminData.setActivityRefNo(BigDecimal.valueOf(new Long(Utils.generateRandomActivationCode())));
					adminData.setUserAction(BigDecimal.valueOf(statusId));
					adminData.setTableName("ANNOUNCEMENTS");
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData); // 97- ADMIN_CHECKER_PENDIN

					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(announcementsEntity.getSubMenu_ID(),
							adminData.getActivityRefNo(), announcementsEntity.getCreatedby(), announcementsEntity.getRemark(),
							announcementsEntity.getRole_ID(), mapper.writeValueAsString(announcementsEntity));
				}

			} catch (Exception e) {
				LOGGER.info("" + e.getMessage());
			}

		}

	}

}
