package com.itl.pns.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.DeviceMasterDetailsBean;
import com.itl.pns.bean.OfferDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TicketBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.OfferDetailsDAO;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.UserDeviceDetails;
import com.itl.pns.repository.DeviceMasterRepository;
import com.itl.pns.repository.OfferDetailsRepository;
import com.itl.pns.service.IOfferDetailsService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

/**
 * @author shubham.lokhande
 *
 */
@Service
public class OfferDetailsServiceImpl implements IOfferDetailsService {

	static Logger LOGGER = Logger.getLogger(OfferDetailsServiceImpl.class);
	@Autowired
	OfferDetailsDAO offerDetailsDAO;

	@Autowired
	DeviceMasterRepository deviceMasterRepository;

	@Autowired
	OfferDetailsRepository offerDetailsRepository;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public boolean insertOfferDetails(OfferDetailsEntity offerDetail) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(offerDetail.getRole_ID().intValue());
		int statusId = 0;
		if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		} else if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}

		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(offerDetail.getActivityName());

		try {
			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				offerDetail.setStatusId(BigDecimal.valueOf(statusId));

			}

			offerDetail.setCreatedon(new Date());
			offerDetail.setUpdatedon(new Date());
			offerDetailsRepository.save(offerDetail);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public List<OfferDetailsBean> getOfferDetails() {

		List<OfferDetailsBean> list = offerDetailsDAO.getOfferDetails();
		return list;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateuploadOffer(OfferDetailsEntity offerDetail) {
		boolean isupdated = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(offerDetail.getRole_ID().intValue());
		int statusId = 0;
		if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		} else if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(offerDetail.getActivityName());

		try {
			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				offerDetail.setStatusId(BigDecimal.valueOf(statusId));
			}

			offerDetail.setUpdatedon(new Date());
			OfferDetailsEntity t = offerDetailsRepository.getOne(offerDetail.getId());
			t.setValidFrom(offerDetail.getValidFrom());
			t.setValidTo(offerDetail.getValidTo());
			t.setSeqNumber(offerDetail.getSeqNumber());
			t.setWeblink(offerDetail.getWeblink());
			t.setStatusId(offerDetail.getStatusId());
			t.setLatitude(offerDetail.getLatitude());
			t.setLongitude(offerDetail.getLongitude());
			t.setImgcaption(offerDetail.getImgcaption());
			t.setImgdescSmall(offerDetail.getImgdescSmall());
			t.setUpdatedby(offerDetail.getUpdatedby());
			t.setAppId(offerDetail.getAppId());
			t.setServiceType(offerDetail.getServiceType());
//			t.setBase64ImageSmall(offerDetail.getBase64ImageSmall());
//			t.setBase64ImageLarge(offerDetail.getBase64ImageLarge());
			offerDetailsRepository.save(t);

			isupdated = true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			isupdated = false;
		}
		return isupdated;
	}

	@Override
	public List<DeviceMasterDetailsBean> getDeviceMasterDetails() {
		return offerDetailsDAO.getDeviceMasterDetails();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateDeviceDetails(UserDeviceDetails deviceDetails) {
		int userStatus = deviceDetails.getStatusId();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(deviceDetails.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(deviceDetails.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				deviceDetails.setStatusId(statusId);
			}
			UserDeviceDetails t = deviceMasterRepository.getOne(deviceDetails.getId());
			t.setStatusId(deviceDetails.getStatusId());
			deviceMasterRepository.save(t);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(deviceDetails.getId(), deviceDetails.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				deviceDetails.setCreatedOn(t.getCreatedOn());
				deviceDetails.setUpdatedOn(new Date());
				adminData.setCreatedOn(t.getCreatedOn());

				adminData.setCreatedByUserId(deviceDetails.getUser_ID());
				adminData.setCreatedByRoleId(deviceDetails.getRole_ID());
				adminData.setPageId(deviceDetails.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				adminData.setCreatedBy(BigDecimal.valueOf(t.getCreatedBy()));
				adminData.setContent(mapper.writeValueAsString(deviceDetails));
				adminData.setActivityId(deviceDetails.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(deviceDetails.getRemark());
				adminData.setActivityName(deviceDetails.getActivityName());
				adminData.setActivityRefNo(BigDecimal.valueOf(deviceDetails.getId()));
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("USERDEVICESMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(deviceDetails.getSubMenu_ID(),
						new BigDecimal(deviceDetails.getId()), new BigDecimal(t.getCreatedBy()),
						deviceDetails.getRemark(), deviceDetails.getRole_ID(),
						mapper.writeValueAsString(deviceDetails));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(BigInteger.valueOf(deviceDetails.getId()),
						BigInteger.valueOf(userStatus), deviceDetails.getSubMenu_ID());
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}
	}

	@Override
	public List<DeviceMasterDetailsBean> getDeviceMasterDetailsById(int id) {
		return offerDetailsDAO.getDeviceMasterDetailsById(id);
	}

	@Override
	public List<DeviceMasterDetailsBean> getDeviceMasterDetailsByCustId(BigInteger custId) {
		return offerDetailsDAO.getDeviceMasterDetailsByCustId(custId);
	}

	@Override
	public List<TicketBean> getKycImage(int custId) {

		return offerDetailsDAO.getKycImage(custId);
	}

	@Override
	public ResponseMessageBean isSeqnoExit(OfferDetailsEntity offerDetail) {
		return offerDetailsDAO.isSeqnoExit(offerDetail);
	}

	@Override
	public ResponseMessageBean updateCheckSeqno(OfferDetailsEntity offerDetail) {
		return offerDetailsDAO.updateCheckSeqno(offerDetail);
	}

	private List<OfferDetailsEntity> getAllOfferDetails() {

		String sqlQuery = "select ofr.ID as id,ofr.weblink as weblink,ofr.servicetype as serviceType,"
//				+ "ofr.BASE64IMAGESMALL as baseImageSmall ,ofr.BASE64IMAGELARGE as baseImageLarge,"
				+ "ofr.IMAGEDESCRIPTIONLARGE as imgdescLarge,ofr.IMAGEDESCRIPTIONSMALL as imgdescSmall,"
				+ " ofr.IMAGECAPTION as imgcaption,ofr.createdon as createdon,ofr.createdby as createdby,um.USERID as createdByName,ofr.updatedon as updatedon,ofr.updatedby as updatedby,"
				+ " ofr.seqnumber,ofr.statusId as statusId,ofr.latitude,ofr.longitude,ofr.validfrom as validFrom,ofr.validto as validTo,ofr.appid,s.shortname as statusname,a.shortname as productname "
				+ " from offersdetails ofr "
				+ " inner join statusmaster s on ofr.statusid=s.id inner join appmaster a on a.id=ofr.appid inner join user_master um on ofr.createdby = um.id where ofr.statusid=:statusid  order by ofr.createdon desc";

		List<OfferDetailsEntity> list = getSession().createSQLQuery(sqlQuery).addScalar("id").addScalar("weblink")
				.addScalar("serviceType")
//				.addScalar("baseImageLarge").addScalar("baseImageSmall")
				.addScalar("imgdescLarge").addScalar("imgdescSmall").addScalar("imgcaption").addScalar("statusName")
				.addScalar("statusId").addScalar("appId").addScalar("updatedby").addScalar("updatedon")
				.addScalar("createdby").addScalar("createdon").addScalar("latitude").addScalar("longitude")
				.addScalar("validFrom").addScalar("validTo").addScalar("seqNumber").addScalar("productName")
				.addScalar("createdByName").setResultTransformer(Transformers.aliasToBean(OfferDetailsEntity.class))
				.list();

		return list;
	}

	@Override
	public boolean addOfferToAdminWorkFlow(OfferDetailsEntity offerDetail, int userStatus) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(offerDetail.getRole_ID().intValue());
		int statusId = 0;
		if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		} else if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(offerDetail.getActivityName());

		try {
			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				List<OfferDetailsEntity> list = offerDetailsDAO.getAllOfferDetails();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(offerDetail.getAppId());
				adminData.setCreatedByUserId(offerDetail.getUser_ID());
				adminData.setCreatedByRoleId(offerDetail.getRole_ID());
				adminData.setPageId(offerDetail.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				adminData.setCreatedBy(offerDetail.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(offerDetail));
				adminData.setActivityId(offerDetail.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(offerDetail.getRemark());
				adminData.setActivityName(offerDetail.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("OFFERSDETAILS_PRD");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(offerDetail.getSubMenu_ID(), list.get(0).getId(),
						offerDetail.getCreatedby(), offerDetail.getRemark(), offerDetail.getRole_ID(),
						mapper.writeValueAsString(offerDetail));
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateuploadOfferToAdminWorkFlow(OfferDetailsEntity offerDetail, int userStatus) {
		boolean isupdated = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(offerDetail.getRole_ID().intValue());
		int statusId = 0;
		if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		} else if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(offerDetail.getActivityName());

		try {

			offerDetail.setUpdatedon(new Date());
			OfferDetailsEntity t = offerDetailsRepository.getOne(offerDetail.getId());

			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(offerDetail.getId().intValue(),
								offerDetail.getSubMenu_ID());

				offerDetail.setCreatedByName(
						adminWorkFlowReqUtility.getCreatedByNameByCreatedId(t.getCreatedby().intValue()));
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(offerDetail.getAppId());
				adminData.setCreatedOn(t.getCreatedon());
				adminData.setCreatedByUserId(offerDetail.getUser_ID());
				adminData.setCreatedByRoleId(offerDetail.getRole_ID());
				adminData.setPageId(offerDetail.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				adminData.setCreatedBy(t.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(offerDetail));
				adminData.setActivityId(offerDetail.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(offerDetail.getRemark());
				adminData.setActivityName(offerDetail.getActivityName());
				adminData.setActivityRefNo(offerDetail.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("OFFERSDETAILS_PRD");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(offerDetail.getSubMenu_ID(), offerDetail.getId(),
						t.getCreatedby(), offerDetail.getRemark(), offerDetail.getRole_ID(),
						mapper.writeValueAsString(offerDetail));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(offerDetail.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), offerDetail.getSubMenu_ID());
			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			isupdated = false;
		}
		return true;
	}

	@Override
	public List<String> serviceTypeList() {
		return offerDetailsRepository.findDistinctByServiceType();
	}

}
