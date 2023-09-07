package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.CountryRestrictionBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.EmailRequestBean;
import com.itl.pns.bean.RegistrationDetailsBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.WalletPointsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.CustomerDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.ChannelMasterEntity;
import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.entity.CustomerOtherInfoEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.RMMASTER;
import com.itl.pns.entity.SecurityQuestionMaster;
import com.itl.pns.entity.WalletPoints;
import com.itl.pns.repository.CustomerRepository;
import com.itl.pns.repository.SecurityQuestionsRepository;
import com.itl.pns.repository.WalletPonitRepository;
import com.itl.pns.service.CustomerService;
import com.itl.pns.service.MasterConfigService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EmailUtil;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.RandomNumberGenerator;

@Service
public class CustomerServiceImpl implements CustomerService {

	static Logger LOGGER = Logger.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerDao customerDao;

	@Autowired
	EmailUtil email;

	@Autowired
	CustomerRepository custRepository;

	@Autowired
	WalletPonitRepository walletponitrepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	SecurityQuestionsRepository securityQuestionRepository;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	MasterConfigService masterConfigService;

	@Override
	public List<CountryRestrictionBean> getcountryRestriction(CountryRestrictionBean bean) {
		List<CountryRestrictionBean> list = customerDao.getcountryRestriction(bean);
		return list;
	}

	@Override
	public Boolean updateCountryRestrictionStatus(CountryRestrictionBean countryRestrictionBean) {
		return customerDao.updateCountryRestrictionStatus(countryRestrictionBean);

	}

	@Override
	public List<RegistrationDetailsBean> getCustomerDetails(DateBean datebean) {
		return customerDao.getCustomerDetails(datebean);
	}

	@Override
	public List<RegistrationDetailsBean> getCustomerDetailsById(int id) {
		return customerDao.getCustomerDetailsById(id);

	}

	@Override
	public List<OfferDetailsEntity> getOfferDetails(int statusid) {
		return customerDao.getOfferDetails(statusid);
	}

	@Override
	public List<OfferDetailsEntity> getOfferDetailsByid(int id) {
		return customerDao.getOfferDetailsByid(id);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public Boolean updateRegistrationDetails(CustomerEntity registrationDetails) {

		int userStatus = registrationDetails.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(registrationDetails.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(registrationDetails.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				registrationDetails.setStatusid(statusId); // 97-
															// ADMIN_CHECKER_PENDIN
			}

			CustomerEntity t = customerRepository.getOne(registrationDetails.getId());

			t.setIsmpinenabled(registrationDetails.getIsmpinenabled());
			t.setStatusid(registrationDetails.getStatusid());
			t.setIstpinlocked(registrationDetails.getIstpinlocked());
			t.setPreferedlanguage(registrationDetails.getPreferedlanguage());
			t.setIsbiometricenabled(registrationDetails.getIsbiometricenabled());
			t.setIswebenabled(registrationDetails.getIswebenabled());
			t.setIsmobileenabled(registrationDetails.getIsmobileenabled());
			t.setUpdatedon(new Date());
			t.setIsUPIEnabled(registrationDetails.getIsUPIEnabled());
			t.setIsBlocked_upi(registrationDetails.getIsBlocked_upi());
			t.setIsUPIRegistered(registrationDetails.getIsUPIRegistered());
			t.setWrongattemptsmpin(registrationDetails.getWrongattemptsmpin());
			t.setWrongattemptspwd(registrationDetails.getWrongattemptspwd());
			t.setWrongattemptstpin(registrationDetails.getWrongattemptstpin());
			registrationDetails.setUpdatedon(new Date());

			t.setMPINLOCKEDON(null);
			if (t.getResetLastMobileLoggedIn() == 3) {
				t.setMobilelastloggedon(null);
			}
			if (!ObjectUtils.isEmpty(registrationDetails.getWrongattemptsmpin())) {
				if (registrationDetails.getWrongattemptsmpin() == 0) {
					t.setIsmpinlocked('N');
					t.setMPINLOCKEDON(null);
				}
			}

			customerRepository.save(t);
			registrationDetails.setUpdatedon(new Date());
			customerDao.updateCustomerData(registrationDetails);
			/*
			 * registrationDetails
			 * .setCreatedbyname(adminWorkFlowReqUtility.getCreatedByNameByCreatedId(t.
			 * getCreatedby().intValue()));
			 */
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(registrationDetails.getId().intValue(),
								registrationDetails.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setUpdatedOn(new Date());
				adminData.setCreatedOn(registrationDetails.getCreatedon());
				adminData.setCreatedByUserId(registrationDetails.getUser_ID());
				adminData.setCreatedByRoleId(registrationDetails.getRole_ID());
				adminData.setPageId(registrationDetails.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// pageid
				adminData.setCreatedBy(registrationDetails.getUser_ID());
				adminData.setContent(mapper.writeValueAsString(registrationDetails));
				adminData.setActivityId(registrationDetails.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
				// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(registrationDetails.getRemark());
				adminData.setActivityName(registrationDetails.getActivityName());
				adminData.setActivityRefNo(registrationDetails.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CUSTOMERS");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);
				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(registrationDetails.getSubMenu_ID(),
						registrationDetails.getId(), registrationDetails.getUser_ID(), registrationDetails.getRemark(),
						registrationDetails.getRole_ID(), mapper.writeValueAsString(registrationDetails));
			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId((registrationDetails.getId().toBigInteger()),
						BigInteger.valueOf(userStatus), registrationDetails.getSubMenu_ID());

			}

			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public List<WalletPointsBean> getWalletPointsData() {

		return customerDao.getWalletPointsData();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean addWalletPoints(WalletPoints walletpoints) {
		Date date = new Date();

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(walletpoints.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(walletpoints.getActivityName());
		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				walletpoints.setStatusId(statusId); // 97-
													// ADMIN_CHECKER_PENDIN

			}

			WalletPoints walletpoints1 = walletponitrepository.findByConfigtype(walletpoints.getConfigtype());
			if (walletpoints1 != null) {
				return false;
			} else {
				walletpoints.setCreatedon(date);
				walletponitrepository.save(walletpoints);

				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@Override
	public List<WalletPointsBean> getWalletPoints() {
		List<WalletPointsBean> list = customerDao.getWalletPoints();
		return list;

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateWalletPoints(WalletPoints walletPoints) {
		boolean isupdated = false;
		int userStatus = walletPoints.getStatusId();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(walletPoints.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(walletPoints.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				walletPoints.setStatusId(statusId);
			}

			WalletPoints walletPoints1 = walletponitrepository.findOne(walletPoints.getId());
			walletPoints1.setAmount(walletPoints.getAmount());
			walletPoints1.setPoints(walletPoints.getPoints());
			walletPoints1.setStatusId(walletPoints.getStatusId());
			walletPoints1.setFromdate(walletPoints.getFromdate());
			walletPoints1.setTodate(walletPoints.getTodate());
			walletponitrepository.save(walletPoints1);

			walletPoints.setCreatedon(walletPoints1.getCreatedon());
			walletPoints.setConfigtype(walletPoints1.getConfigtype());

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(walletPoints.getId(), walletPoints.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(walletPoints.getAppId()));
				adminData.setCreatedOn(walletPoints1.getCreatedon());
				adminData.setCreatedByUserId(walletPoints.getUser_ID());
				adminData.setCreatedByRoleId(walletPoints.getRole_ID());
				adminData.setPageId(walletPoints.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				adminData.setCreatedBy(BigDecimal.valueOf(walletPoints1.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(walletPoints));
				adminData.setActivityId(walletPoints.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(walletPoints.getRemark());
				adminData.setActivityName(walletPoints.getActivityName());
				adminData.setActivityRefNo(BigDecimal.valueOf(walletPoints.getId()));
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("WALLETPOINTSCONFIGURATION");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(walletPoints.getSubMenu_ID(),
						new BigDecimal(walletPoints.getId()), new BigDecimal(walletPoints1.getCreatedby()),
						walletPoints.getRemark(), walletPoints.getRole_ID(), mapper.writeValueAsString(walletPoints));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(BigInteger.valueOf(walletPoints.getId()),
						BigInteger.valueOf(userStatus), walletPoints.getSubMenu_ID());
			}

			isupdated = true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			isupdated = false;
		}
		return isupdated;
	}

	@Override
	public List<WalletPointsBean> getWalletPointsById(int id) {
		List<WalletPointsBean> list = customerDao.getWalletPointsById(id);
		return list;

	}

	@Override
	public List<SecurityQuestionMaster> getSecurityQuestions() {
		List<SecurityQuestionMaster> list = customerDao.getSecurityQuestions();
		return list;
	}

	@Override
	public List<SecurityQuestionMaster> getSecurityQuestionById(int id) {

		List<SecurityQuestionMaster> list = customerDao.getSecurityQuestionById(id);
		return list;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean addSecurityQuestions(SecurityQuestionMaster securityQuestionMaster) {

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(securityQuestionMaster.getRole_ID().intValue());
		int statusId = 0;
		if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		} else if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}

		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(securityQuestionMaster.getActivityName());

		try {
			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				securityQuestionMaster.setStatusid(BigDecimal.valueOf(statusId));
			}
			securityQuestionMaster.setCreatedon(new Date());
			securityQuestionMaster.setUpdatedon(new Date());
			securityQuestionRepository.save(securityQuestionMaster);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateSecurityQuestions(SecurityQuestionMaster securityQuestionMaster) {
		boolean isupdated = false;
		int statusId = 0;
		int userStatus = securityQuestionMaster.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(securityQuestionMaster.getRole_ID().intValue());

		if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		} else if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}

		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(securityQuestionMaster.getActivityName());

		try {
			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				securityQuestionMaster.setStatusid(BigDecimal.valueOf(statusId)); // 97-
																					// ADMIN_CHECKER_PENDIN
			}

			SecurityQuestionMaster questionMaster = securityQuestionRepository.findOne(securityQuestionMaster.getId());
			questionMaster.setUpdatedon(new Date());
			questionMaster.setAppid(securityQuestionMaster.getAppid());
			questionMaster.setStatusid(securityQuestionMaster.getStatusid());
			questionMaster.setQuestion(securityQuestionMaster.getQuestion());
			questionMaster.setUpdatedby(securityQuestionMaster.getUpdatedby());
			securityQuestionMaster.setCreatedByName(
					adminWorkFlowReqUtility.getCreatedByNameByCreatedId(questionMaster.getCreatedby().intValue()));
			securityQuestionMaster.setCreatedon(questionMaster.getCreatedon());
			securityQuestionMaster.setUpdatedon(questionMaster.getUpdatedon());
			securityQuestionRepository.save(questionMaster);

			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(securityQuestionMaster.getId().intValue(),
								securityQuestionMaster.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(securityQuestionMaster.getAppid());
				adminData.setCreatedOn(questionMaster.getCreatedon());
				adminData.setCreatedByUserId(securityQuestionMaster.getUser_ID());
				adminData.setCreatedByRoleId(securityQuestionMaster.getRole_ID());
				adminData.setPageId(securityQuestionMaster.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// pageid
				adminData.setCreatedBy(questionMaster.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(securityQuestionMaster));
				adminData.setActivityId(securityQuestionMaster.getSubMenu_ID()); // set
																					// submenuId
																					// as
																					// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(securityQuestionMaster.getRemark());
				adminData.setActivityName(securityQuestionMaster.getActivityName());
				adminData.setActivityRefNo(securityQuestionMaster.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("SECURITYQUESTIONMASTER_PRD");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(securityQuestionMaster.getSubMenu_ID(),
						securityQuestionMaster.getId(), questionMaster.getCreatedby(),
						securityQuestionMaster.getRemark(), securityQuestionMaster.getRole_ID(),
						mapper.writeValueAsString(securityQuestionMaster));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(securityQuestionMaster.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), securityQuestionMaster.getSubMenu_ID());
			}

			isupdated = true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			isupdated = false;
		}
		return isupdated;
	}

	@Override
	public List<RegistrationDetailsBean> getCustByCifMobileName(RegistrationDetailsBean detailsBean) {
		return customerDao.getCustByCifMobileName(detailsBean);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean resetCustPass(CustomerEntity user) {
		RandomNumberGenerator object = new RandomNumberGenerator();
		String newpassword = object.generateRandomString();
		String encryptpass = EncryptDeryptUtility.md5(newpassword);
		System.out.println(encryptpass);
		int userStatus = user.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(user.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(user.getActivityName());

		try {
			CustomerEntity t = customerRepository.getOne(user.getId());
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				user.setStatusid(statusId); // 97- ADMIN_CHECKER_PENDIN
			} else {
				t.setUserpassword(encryptpass);
				customerRepository.save(t);
				email.setUpandSendEmail(user.getEmail(), newpassword);
			}

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(user.getId().intValue(), user.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(t.getAppid()));
				adminData.setCreatedOn(t.getCreatedon());
				adminData.setCreatedByUserId(user.getUser_ID());
				adminData.setCreatedByRoleId(user.getRole_ID());
				adminData.setPageId(user.getSubMenu_ID()); // set submenuId as
															// pageid
				adminData.setCreatedBy(BigDecimal.valueOf(t.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(user));
				adminData.setActivityId(user.getSubMenu_ID()); // set submenuId
																// as activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(user.getRemark());
				adminData.setActivityName("CUSTOMERRESETPASSWOEDEDIT");
				adminData.setActivityRefNo(user.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CUSTOMERS");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(user.getSubMenu_ID(), user.getId(),
						new BigDecimal(t.getCreatedby()), user.getRemark(), user.getRole_ID(),
						mapper.writeValueAsString(user));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(user.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), user.getSubMenu_ID());
			}
			return true;
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
	}

	@Override
	public boolean saveRMMasterData(RMMASTER rmMaster) {

		return customerDao.saveRMMasterData(rmMaster);

	}

	@Override
	public List<RMMASTER> getRMMasterData() {
		return customerDao.getRMMasterData();
	}

	@Override
	public boolean updateRMMasterData(RMMASTER rmMaster) {
		return customerDao.updateRMMasterData(rmMaster);
	}

	@Override
	public List<RMMASTER> getRMMasterDataById(int id) {

		return customerDao.getRMMasterDataById(id);

	}

	@Override
	public boolean deletetRMMasterById(RMMASTER rmMaster) {
		return customerDao.deletetRMMasterById(rmMaster);
	}

	@Override
	public List<ChannelMasterEntity> getChannelList() {
		return customerDao.getChannelList();
	}

	@Override
	public List<RegistrationDetailsBean> getCustomerType() {

		return customerDao.getCustomerType();
	}

	@Override
	public boolean saveCustOtherInfo(CustomerOtherInfoEntity custOtherInfo) {
		return customerDao.saveCustOtherInfo(custOtherInfo);
	}

	@Override
	public List<CustomerOtherInfoEntity> getCustOtherInfo() {
		return customerDao.getCustOtherInfo();
	}

	@Override
	public boolean updateCustOtherInfo(CustomerOtherInfoEntity custOtherInfo) {
		return customerDao.updateCustOtherInfo(custOtherInfo);
	}

	@Override
	public List<CustomerOtherInfoEntity> getCustOtherInfoById(int id) {
		return customerDao.getCustOtherInfoById(id);
	}

	@Override
	public List<CustomerOtherInfoEntity> getCustOtherInfoByCustId(int custId) {
		return customerDao.getCustOtherInfoByCustId(custId);
	}

	@Override
	public ResponseMessageBean isRMNameExist(RMMASTER rmMasterData) {
		return customerDao.isRMNameExist(rmMasterData);
	}

	@Override
	public ResponseMessageBean isUpdateRMNameExist(RMMASTER rmMasterData) {
		return customerDao.isUpdateRMNameExist(rmMasterData);
	}

	@Override
	public boolean sendEmailWithAttachment(EmailRequestBean emailRequestBean) {
		return customerDao.sendEmailWithAttachment(emailRequestBean);
	}

	@Override
	public List<RegistrationDetailsBean> getAllCustomers() {
		return customerDao.getAllCustomers();
	}

	@Override
	public boolean addRewardPointToAdminWorkFlow(WalletPoints walletpoints, int userStatus) {

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(walletpoints.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(walletpoints.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				List<WalletPointsBean> list = customerDao.getWalletPoints();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(walletpoints.getAppId()));
				adminData.setCreatedByUserId(walletpoints.getUser_ID());
				adminData.setCreatedByRoleId(walletpoints.getRole_ID());
				adminData.setPageId(walletpoints.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				adminData.setCreatedBy(BigDecimal.valueOf(walletpoints.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(walletpoints));
				adminData.setActivityId(walletpoints.getSubMenu_ID()); // set
																		// submenuid
																		// as
																		// acticit
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checkerroleid
				adminData.setRemark(walletpoints.getRemark());
				adminData.setActivityName(walletpoints.getActivityName());
				adminData.setActivityRefNo(list.get(0).getID());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("WALLETPOINTSCONFIGURATION");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(walletpoints.getSubMenu_ID(), list.get(0).getID(),
						new BigDecimal(walletpoints.getCreatedby()), walletpoints.getRemark(),
						walletpoints.getRole_ID(), mapper.writeValueAsString(walletpoints));
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	public boolean addSecurityQuestionsToAdminWorkFLow(SecurityQuestionMaster securityQuestionMaster, int userStatus) {
		int statusId = 0;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(securityQuestionMaster.getRole_ID().intValue());
		if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		} else if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)) {
			statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		}
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(securityQuestionMaster.getActivityName());
		try {

			if ((roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					|| roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<SecurityQuestionMaster> list = customerDao.getSecurityQuestions();
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(securityQuestionMaster.getAppid());
				adminData.setCreatedByUserId(securityQuestionMaster.getUser_ID());
				adminData.setCreatedByRoleId(securityQuestionMaster.getRole_ID());
				adminData.setPageId(securityQuestionMaster.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// pageid
				adminData.setCreatedBy(securityQuestionMaster.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(securityQuestionMaster));
				adminData.setActivityId(securityQuestionMaster.getSubMenu_ID()); // set
																					// submenuId
																					// as
																					// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN

				// // 6-
				// checker
				// roleid
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(securityQuestionMaster.getRemark());
				adminData.setActivityName(securityQuestionMaster.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("SECURITYQUESTIONMASTER_PRD");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(securityQuestionMaster.getSubMenu_ID(),
						list.get(0).getId(), securityQuestionMaster.getCreatedby(), securityQuestionMaster.getRemark(),
						securityQuestionMaster.getRole_ID(), mapper.writeValueAsString(securityQuestionMaster));
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean saveBulkCustomers(List<CustomerEntity> custDataList) {
		return customerDao.saveBulkCustomers(custDataList);
	}

	@Override
	public List<RegistrationDetailsBean> getAllPendingCustomers() {
		return customerDao.getAllPendingCustomers();
	}

	@Override
	public boolean updateWorngAAttempsOfCustomers(CustomerEntity customer) {
		return customerDao.updateWorngAAttempsOfCustomers(customer);
	}

	@Override
	public List<RegistrationDetailsBean> getAllCustomerDetails() {
		return customerDao.getAllCustomerDetails();
	}

	@Override
	public ResponseMessageBean customerValidation(String mobile, String cif, String accountNumber, String emailId,
			String referenceNumber, String entityId) {
		return customerDao.customerValidation(mobile, cif, accountNumber, emailId, referenceNumber, entityId);
	}

	@Override
	public List<CustomerEntity> getRegistrationDetails(DateBean datebean) {

		return customerDao.getRegistrationDetails(datebean);
	}

	@Override
	public List<CustomerEntity> getRetailCustomerDetails(DateBean dateBean) {
		return customerDao.getRetailCustomerDetails(dateBean);
	}

	@Override
	public ResponseMessageBean linkDLinkAccounts(String mobile, String accountNumberData, String referenceNumber,
			String entityId) {
		return customerDao.linkDLinkAccounts(mobile, accountNumberData, referenceNumber, entityId);
	}
}
