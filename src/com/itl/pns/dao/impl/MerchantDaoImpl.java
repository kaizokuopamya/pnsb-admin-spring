package com.itl.pns.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.entity.MerchantEntity;
import com.itl.pns.dao.MerchantDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.repository.MerchantRepository;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@Repository
@Transactional
public class MerchantDaoImpl implements MerchantDao {

	static Logger LOGGER = Logger.getLogger(MerchantDaoImpl.class);

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	MerchantRepository merchantRepository;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean add(MerchantEntity merchantEntity) {

		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(merchantEntity.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		int userStatus = merchantEntity.getStatusId().intValue();
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(merchantEntity.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				merchantEntity.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
			}
			merchantEntity.setUpdatedOn(new Date());
			merchantEntity.setCreatedOn(new Date());
			session.save(merchantEntity);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<MerchantEntity> list = getAllMerchantDetails();
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(merchantEntity.getUser_ID());
				adminData.setCreatedByRoleId(merchantEntity.getRole_ID());
				adminData.setPageId(merchantEntity.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(merchantEntity.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(merchantEntity));
				adminData.setActivityId(merchantEntity.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(merchantEntity.getRemark());
				adminData.setActivityName(merchantEntity.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("MERCHANT_DETAILS");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(merchantEntity.getSubMenu_ID(), list.get(0).getId(),
						merchantEntity.getCreatedBy(), merchantEntity.getRemark(), merchantEntity.getRole_ID(),
						mapper.writeValueAsString(merchantEntity));
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
		return false;

	}

	@Override
	public boolean update(MerchantEntity merchantEntity) {
		Session session = sessionFactory.getCurrentSession();
		int userStatus = merchantEntity.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(merchantEntity.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(merchantEntity.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				merchantEntity.setStatusId(BigDecimal.valueOf(statusId));
			}

			merchantEntity.setUpdatedOn(new Date());
			session.update(merchantEntity);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(merchantEntity.getId().intValue(),
								merchantEntity.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedOn(merchantEntity.getCreatedOn());
				adminData.setCreatedByUserId(merchantEntity.getUser_ID());
				adminData.setCreatedByRoleId(merchantEntity.getRole_ID());
				adminData.setPageId(merchantEntity.getSubMenu_ID()); // set submenuId as pageid
				adminData.setCreatedBy(merchantEntity.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(merchantEntity));
				adminData.setActivityId(merchantEntity.getSubMenu_ID()); // set submenuId as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
				adminData.setRemark(merchantEntity.getRemark());
				adminData.setActivityName(merchantEntity.getActivityName());
				adminData.setActivityRefNo(merchantEntity.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("MERCHANT_DETAILS");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(merchantEntity.getSubMenu_ID(),
							merchantEntity.getId(), merchantEntity.getCreatedBy(), merchantEntity.getRemark(),
							merchantEntity.getRole_ID(), mapper.writeValueAsString(merchantEntity));
				}

			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(merchantEntity.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), merchantEntity.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<MerchantEntity> getAllMerchantDetails() {
		List<MerchantEntity> list = null;
		try {
			String sqlQuery = "select md.id ,md.merchantName, md.merchantCode, md.merchantKey, md.merchantAccountNo, md.glSubHead, md.checksumKey, md.statusId, st.NAME as statusName, md.createdOn,md.createdBy,md.updatedOn,md.updatedBy from merchant_details md "
					+ "  inner join statusmaster st on st.id=md.statusId";

			list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("merchantName")
					.addScalar("merchantCode").addScalar("merchantKey").addScalar("merchantAccountNo")
					.addScalar("glSubHead").addScalar("checksumKey").addScalar("statusId").addScalar("statusName")
					.addScalar("createdOn").addScalar("createdBy").addScalar("updatedOn").addScalar("updatedBy")
					.setResultTransformer(Transformers.aliasToBean(MerchantEntity.class)).list();
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

		return list;
	}

	@Override
	public MerchantEntity findByMerchantId(MerchantEntity merchantEntity) {
		return merchantRepository.findOne(merchantEntity.getId());
	}

	@Override
	public ResponseMessageBean checkUpdateMerchantExist(MerchantEntity merchantEntity) {
		ResponseMessageBean responseMessageBean = new ResponseMessageBean();
		merchantEntity = merchantRepository.findOne(merchantEntity.getId());
		if (!ObjectUtils.isEmpty(merchantEntity)) {
			responseMessageBean.setResponseCode("200");
		} else {
			responseMessageBean.setResponseCode("202");
		}

		return responseMessageBean;
	}

}
