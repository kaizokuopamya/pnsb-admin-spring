package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FacilityStatusBean;
import com.itl.pns.bean.LanguageJsonBean;
import com.itl.pns.bean.ProductBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TransactionLogBean;
import com.itl.pns.bean.UserAccountLeadsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.MasterConfigDao;
import com.itl.pns.entity.AccountSchemeMasterEntity;
import com.itl.pns.entity.ActivityMaster;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.entity.AppMasterEntity;
import com.itl.pns.entity.CertificateConfigEntity;
import com.itl.pns.entity.ConfigMasterEntity;
import com.itl.pns.entity.LanguageJson;
import com.itl.pns.entity.LimitMasterEntity;
import com.itl.pns.entity.MaskingRulesEntity;
import com.itl.pns.entity.OfferDetailsEntity;
import com.itl.pns.entity.Product;
import com.itl.pns.entity.SchedularTransMasterEntity;
import com.itl.pns.entity.StatusMasterEntity;
import com.itl.pns.repository.ActivityMasterRepository;
import com.itl.pns.repository.ConfigRepository;
import com.itl.pns.repository.LanguageJsonRepository;
import com.itl.pns.repository.LimitMasterRepository;
import com.itl.pns.repository.MaskingRulesRepository;
import com.itl.pns.repository.ProductRepository;
import com.itl.pns.service.MasterConfigService;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;

@Service
public class MasterConfigServiceImpl implements MasterConfigService {

	private static final Logger logger = LogManager.getLogger(MasterConfigServiceImpl.class);

	@Autowired
	MasterConfigDao masterConfigDao;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFLowReqUtility;

	@Autowired
	ActivityMasterRepository activitymasterrepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ConfigRepository configRepository;

	@Autowired
	LanguageJsonRepository languageJsonRepository;

	@Autowired
	MaskingRulesRepository maskingRulesRepository;
	
	@Autowired
	LimitMasterRepository limitMasterRepository;

	@Override
	public List<LanguageJson> getLanguageJson() {
		List<LanguageJson> list = masterConfigDao.getLanguageJson();

		return list;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean addLanguagejson(LanguageJson languagejson) {

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(languagejson.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(languagejson.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				languagejson.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
			}

			Date date = new Date();
			languagejson.setCreatedon(date);
			languageJsonRepository.save(languagejson);
			return true;
		} catch (Exception e) {
			logger.error("Exception Occured", e);
			return false;
		}

	}

	@Override
	public List<LanguageJsonBean> getLanguageJsonById(long id) {
		List<LanguageJsonBean> languagejson = masterConfigDao.getLanguageJsonById(id);
		return languagejson;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateLanguageJson(LanguageJson languagejson) {
		boolean isupdated = false;
		int userStatus = languagejson.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(languagejson.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(languagejson.getActivityName());

		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				languagejson.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
			}

			LanguageJson t = languageJsonRepository.findByid(languagejson.getId());
			t.setLanguagetext(languagejson.getLanguagetext());
			t.setStatusId(languagejson.getStatusId());
			t.setLanguagecode(t.getLanguagecode());
			t.setEnglishtext(languagejson.getEnglishtext());
			t.setLanguagecodedesc(languagejson.getLanguagecodedesc());
			languageJsonRepository.saveAndFlush(t);

			languagejson.setCreatedon(t.getCreatedon());
			String encLanText = EncryptorDecryptor.decryptDataForLangJson(languagejson.getLanguagetext());
			languagejson.setLanguagetext(encLanText);
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(languagejson.getId().intValue(),
								languagejson.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedOn(t.getCreatedon());
				adminData.setCreatedByUserId(languagejson.getUser_ID());
				adminData.setCreatedByRoleId(languagejson.getRole_ID());
				adminData.setPageId(languagejson.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				adminData.setCreatedBy(t.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(languagejson));
				adminData.setActivityId(languagejson.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(languagejson.getRemark());
				adminData.setActivityName(languagejson.getActivityName());
				adminData.setActivityRefNo(languagejson.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("LANGUAGEJSON_NEW");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(languagejson.getSubMenu_ID(), languagejson.getId(),
						languagejson.getCreatedby(), languagejson.getRemark(), languagejson.getRole_ID(),
						mapper.writeValueAsString(languagejson));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(languagejson.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), languagejson.getSubMenu_ID());
			}
			isupdated = true;
		} catch (Exception e) {
			logger.error("Exception Occured", e);
			isupdated = false;
		}
		return isupdated;
	}

	@Override
	public List<ConfigMasterEntity> getConfigMaster() {
		return masterConfigDao.findAllConfiguration();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean addConfigMasterDetails(ConfigMasterEntity configMasterEntity) {

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(configMasterEntity.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(configMasterEntity.getActivityName());

		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				configMasterEntity.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																				// ADMIN_CHECKER_PENDIN
			}

			ConfigMasterEntity duplicateConfig = configRepository
					.findByConfigKeyAndAppId(configMasterEntity.getConfigKey(), configMasterEntity.getAppId());

			Date date = new Date();
			if (duplicateConfig != null) {
				return false;
			} else {
				configMasterEntity.setCreatedOn(date);
				configMasterEntity.setCreatedBy(configMasterEntity.getCreatedBy());
				configRepository.save(configMasterEntity);
			}
		}

		catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateConfigMaster(ConfigMasterEntity configMasterBean) {
		boolean isupdated = false;
		int userStatus = configMasterBean.getStatusId().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(configMasterBean.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(configMasterBean.getActivityName());
		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				configMasterBean.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																			// ADMIN_CHECKER_PENDIN
			}
			ConfigMasterEntity t = configRepository.getOne(configMasterBean.getId());
			t.setConfigKey(configMasterBean.getConfigKey());
			configMasterBean.setCreatedOn(t.getCreatedOn());
			t.setConfigValue(configMasterBean.getConfigValue());
			t.setDescription(configMasterBean.getDescription());
			t.setCreatedBy(configMasterBean.getCreatedBy());
			t.setStatusId(configMasterBean.getStatusId());
			configRepository.saveAndFlush(t);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(configMasterBean.getId().intValue(),
								configMasterBean.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(configMasterBean.getAppId());
				adminData.setCreatedOn(t.getCreatedOn());
				adminData.setCreatedByUserId(configMasterBean.getUser_ID());
				adminData.setCreatedByRoleId(configMasterBean.getRole_ID());
				adminData.setPageId(configMasterBean.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// pageid
				adminData.setCreatedBy(configMasterBean.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(configMasterBean));
				adminData.setActivityId(configMasterBean.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(configMasterBean.getRemark());
				adminData.setActivityName(configMasterBean.getActivityName());
				adminData.setActivityRefNo(configMasterBean.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CONFIGURATIONMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(configMasterBean.getSubMenu_ID(),
						configMasterBean.getId(), configMasterBean.getCreatedBy(), configMasterBean.getRemark(),
						configMasterBean.getRole_ID(), mapper.writeValueAsString(configMasterBean));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(configMasterBean.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), configMasterBean.getSubMenu_ID());
			}
			isupdated = true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			isupdated = false;
		}
		return isupdated;

	}

	@Override
	public List<ConfigMasterEntity> getConfigById(BigInteger id) {
		List<ConfigMasterEntity> languagejson = masterConfigDao.getConfigById(id);
		return languagejson;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<StatusMasterEntity> getStatus() {
		List<StatusMasterEntity> list = masterConfigDao.getStatus();

		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<AppMasterEntity> getAppMasterList() {
		List<AppMasterEntity> list = masterConfigDao.getAppMasterList();

		return list;
	}

	@Override
	public List<OfferDetailsEntity> getOfferDetails() {
		List<OfferDetailsEntity> list = masterConfigDao.getOfferDetails();
		return list;
	}

	@Override
	public List<TransactionLogBean> getAllTransactionLogByDate(DateBean datebean) {
		return masterConfigDao.getTransactionLogByDate(datebean);
	}

	@Override
	public List<ProductBean> getProducts() {
		List<ProductBean> list = masterConfigDao.getProducts();
		return list;
	}

	@Override
	public List<ProductBean> getProductById(int id) {
		return masterConfigDao.getProductById(id);
	}

	@Override
	/*
	 * @Transactional(value = "jpaTransactionManager",propagation =
	 * Propagation.REQUIRES_NEW)
	 */
	public boolean saveProductDetails(Product product) {
		try {
			product.setCreatedon(new Date());
			return masterConfigDao.saveProductDetails(product);

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateProductDetails(Product product) {
		int userStatus = product.getStatusId();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(product.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(product.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				product.setStatusId(statusId); // 97- ADMIN_CHECKER_PENDIN
			}

			Product prod = productRepository.getOne(product.getId());

			prod.setProductName(product.getProductName());
			prod.setDescription(product.getDescription());
			prod.setAppId(product.getAppId());
			prod.setStatusId(product.getStatusId());
			prod.setProdType(product.getProdType());

			productRepository.save(prod);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(product.getId().intValue(), product.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedOn(prod.getCreatedon());
				adminData.setCreatedByUserId(product.getUser_ID());
				adminData.setCreatedByRoleId(product.getRole_ID());
				adminData.setPageId(product.getSubMenu_ID()); // set submenuId
																// as pageid
				adminData.setCreatedBy(BigDecimal.valueOf(prod.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(product));
				adminData.setActivityId(product.getSubMenu_ID()); // set
																	// submenuId
																	// as
																	// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(product.getRemark());
				adminData.setActivityName(product.getActivityName());
				adminData.setActivityRefNo(BigDecimal.valueOf(product.getId()));
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("PRODUCTMASTER");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(product.getSubMenu_ID(),
						new BigDecimal(product.getId()), new BigDecimal(prod.getCreatedby()), product.getRemark(),
						product.getRole_ID(), mapper.writeValueAsString(product));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(BigInteger.valueOf(product.getId()),
						BigInteger.valueOf(userStatus), product.getSubMenu_ID());
			}

		} catch (Exception ex) {
			logger.info("Exception:", ex);
		}
	}

	@Override
	public List<ProductBean> getProductType() {
		List<ProductBean> list = masterConfigDao.getProductType();
		return list;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean addFacilityStatus(ActivityMaster activitymaster) {
		boolean isadded = false;
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(activitymaster.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(activitymaster.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				activitymaster.setStatusid(statusId); // 97-
														// ADMIN_CHECKER_PENDIN
			}

			activitymaster.setCreatedon(new Date());
			activitymasterrepository.save(activitymaster);

			isadded = true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			isadded = false;
			System.out.println(e);
		}
		return isadded;
	}

	public boolean addFacilityStatusToAdminWorkFlow(ActivityMaster activitymaster, int userStatus) {

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(activitymaster.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(activitymaster.getActivityName());

		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				List<FacilityStatusBean> list = masterConfigDao.getAllFacilityStatus();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(activitymaster.getAppid()));
				adminData.setCreatedByUserId(activitymaster.getUser_ID());
				adminData.setCreatedByRoleId(activitymaster.getRole_ID());
				adminData.setPageId(activitymaster.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// pageid
				adminData.setCreatedBy(BigDecimal.valueOf(activitymaster.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(activitymaster));
				adminData.setActivityId(activitymaster.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(activitymaster.getRemark());
				adminData.setActivityName(activitymaster.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ACTIVITYMASTER");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(activitymaster.getSubMenu_ID(), list.get(0).getId(),
						new BigDecimal(activitymaster.getCreatedby()), activitymaster.getRemark(),
						activitymaster.getRole_ID(), mapper.writeValueAsString(activitymaster));
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void updateFacilityStatus(ActivityMaster activitymaster) {
		ActivityMaster t = activitymasterrepository.getOne(activitymaster.getId());
		t.setStatusid(activitymaster.getStatusid());
		activitymasterrepository.save(t);

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateFacilityStatusDetails(ActivityMaster activitymaster) {
		boolean isupdated = false;
		int userStatus = activitymaster.getStatusid();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(activitymaster.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(activitymaster.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				activitymaster.setStatusid(statusId); // 97-
														// ADMIN_CHECKER_PENDIN
			}

			ActivityMaster t = activitymasterrepository.getOne(activitymaster.getId());
			t.setActivitycode(activitymaster.getActivitycode());
			t.setAppid(activitymaster.getAppid());
			t.setStatusid(activitymaster.getStatusid());
			t.setEncryptiontype(activitymaster.getEncryptiontype());
			t.setLimits(activitymaster.getLimits());
			t.setFt_nft(activitymaster.getFt_nft());
			t.setDisplayname(activitymaster.getDisplayname());
			activitymasterrepository.save(t);
			isupdated = true;

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(activitymaster.getId(), activitymaster.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(BigDecimal.valueOf(activitymaster.getAppid()));
				adminData.setCreatedOn(t.getCreatedon());
				adminData.setCreatedByUserId(activitymaster.getUser_ID());
				adminData.setCreatedByRoleId(activitymaster.getRole_ID());
				adminData.setPageId(activitymaster.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// pageid
				adminData.setCreatedBy(BigDecimal.valueOf(t.getCreatedby()));
				adminData.setContent(mapper.writeValueAsString(activitymaster));
				adminData.setActivityId(activitymaster.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(activitymaster.getRemark());
				adminData.setActivityName(activitymaster.getActivityName());
				adminData.setActivityRefNo(BigDecimal.valueOf(activitymaster.getId()));
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("ACTIVITYMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(activitymaster.getSubMenu_ID(),
						new BigDecimal(activitymaster.getId()), new BigDecimal(t.getCreatedby()),
						activitymaster.getRemark(), activitymaster.getRole_ID(),
						mapper.writeValueAsString(activitymaster));

			} else {
				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(BigInteger.valueOf(activitymaster.getId()),
						BigInteger.valueOf(userStatus), activitymaster.getSubMenu_ID());
			}

			isupdated = true;

		} catch (Exception e) {
			logger.info("Exception:", e);
			System.out.println(e);
			isupdated = false;
		}
		return isupdated;
	}

	@Override
	public List<FacilityStatusBean> getAllFacilityStatusById(int id) {
		return masterConfigDao.getAllFacilityStatusById(id);
	}

	@Override
	public List<FacilityStatusBean> getAllFacilityStatus() {
		return masterConfigDao.getAllFacilityStatus();
	}

	@Override
	public List<MaskingRulesEntity> getMaskingRulesList() {
		List<MaskingRulesEntity> list = null;
		try {
			list = masterConfigDao.findMaskingRulesList();
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return list;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean updateMaskingRules(MaskingRulesEntity maskingRulesEntity) {
		int userStatus = maskingRulesEntity.getStatusid().intValue();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(maskingRulesEntity.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(maskingRulesEntity.getActivityName());

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				maskingRulesEntity.setStatusid(BigDecimal.valueOf(statusId)); // 97-
																				// ADMIN_CHECKER_PENDIN
			}

			MaskingRulesEntity maskRules = maskingRulesRepository.findOne(maskingRulesEntity.getId());
			maskingRulesEntity.setCreatedByName(
					adminWorkFlowReqUtility.getCreatedByNameByCreatedId(maskRules.getCreatedby().intValue()));

			maskingRulesEntity.setCreatedon(maskRules.getCreatedon());
			maskRules.setRulenamedesc(maskingRulesEntity.getRulenamedesc());
			maskRules.setMaskall_yn(maskingRulesEntity.getMaskall_yn());
			maskRules.setMaskchar(maskingRulesEntity.getMaskchar());
			maskRules.setFieldname(maskingRulesEntity.getFieldname());
			if (!ObjectUtils.isEmpty(maskingRulesEntity.getMaskfirstdigits())) {
				maskRules.setMaskfirstdigits(maskingRulesEntity.getMaskfirstdigits());
			} else {
				maskRules.setMaskfirstdigits(null);
			}
			if (!ObjectUtils.isEmpty(maskingRulesEntity.getMasklastdigits())) {
				maskRules.setMasklastdigits(maskingRulesEntity.getMasklastdigits());
			} else {
				maskRules.setMasklastdigits(null);
			}
			maskRules.setStatusid(maskingRulesEntity.getStatusid());
			maskRules.setAppid(maskingRulesEntity.getAppid());
			maskingRulesRepository.save(maskRules);

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(maskingRulesEntity.getId().intValue(),
								maskingRulesEntity.getSubMenu_ID());

				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedOn(maskRules.getCreatedon());
				adminData.setCreatedByUserId(maskingRulesEntity.getUser_ID());
				adminData.setCreatedByRoleId(maskingRulesEntity.getRole_ID());
				adminData.setPageId(maskingRulesEntity.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// pageid
				adminData.setCreatedBy(maskRules.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(maskingRulesEntity));
				adminData.setActivityId(maskingRulesEntity.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(maskingRulesEntity.getRemark());
				adminData.setActivityName(maskingRulesEntity.getActivityName());
				adminData.setActivityRefNo(maskingRulesEntity.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("MASKINGRULES");
				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

				}
				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(maskingRulesEntity.getSubMenu_ID(),
						maskingRulesEntity.getId(), maskRules.getCreatedby(), maskingRulesEntity.getRemark(),
						maskingRulesEntity.getRole_ID(), mapper.writeValueAsString(maskingRulesEntity));
			} else {

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(maskingRulesEntity.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), maskingRulesEntity.getSubMenu_ID());

			}

			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

	}

	@Override
	public List<MaskingRulesEntity> getMaskingRulesId(int id) {
		List<MaskingRulesEntity> list = masterConfigDao.getMaskingRulesId(id);
		return list;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean addMaskingRules(MaskingRulesEntity maskingRulesEntity) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(maskingRulesEntity.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);

		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(maskingRulesEntity.getActivityName());
		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				maskingRulesEntity.setStatusid(BigDecimal.valueOf(statusId));
			}

			maskingRulesEntity.setCreatedon(new Date());
			maskingRulesRepository.save(maskingRulesEntity);

			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

	}

	@Override
	public ResponseMessageBean checkProduct(Product product) {
		return masterConfigDao.checkProduct(product);
	}

	@Override
	public ResponseMessageBean checkUpdateProduct(Product product) {
		return masterConfigDao.checkUpdateProduct(product);
	}

	@Override
	public boolean addToadminWorkFlowReq(ConfigMasterEntity configMasterEntity, int userStatus) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(configMasterEntity.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);

		try {

			List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
					.isMakerCheckerPresentForReq(configMasterEntity.getActivityName());
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				List<ConfigMasterEntity> list = getConfigMaster();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(configMasterEntity.getAppId());
				adminData.setCreatedByUserId(configMasterEntity.getUser_ID());
				adminData.setCreatedByRoleId(configMasterEntity.getRole_ID());
				adminData.setPageId(configMasterEntity.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// pageid
				adminData.setCreatedBy(configMasterEntity.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(configMasterEntity));
				adminData.setActivityId(configMasterEntity.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(configMasterEntity.getRemark());
				adminData.setActivityName(configMasterEntity.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CONFIGURATIONMASTER");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(configMasterEntity.getSubMenu_ID(),
						list.get(0).getId(), configMasterEntity.getCreatedBy(), configMasterEntity.getRemark(),
						configMasterEntity.getRole_ID(), mapper.writeValueAsString(configMasterEntity));
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateToadminWorkFlowReq(ConfigMasterEntity configMasterBean, int userStatus) {
		boolean isupdated = false;

		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(configMasterBean.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(configMasterBean.getActivityName());

		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				configMasterBean.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																			// ADMIN_CHECKER_PENDIN
			}

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				ObjectMapper mapper = new ObjectMapper();

				List<AdminWorkFlowRequestEntity> AdminDataList = adminWorkFlowReqUtility
						.getAdminWorkFlowDataByActivityRefNo(configMasterBean.getId().intValue(),
								configMasterBean.getSubMenu_ID());

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setAppId(configMasterBean.getAppId());
				adminData.setCreatedOn(configMasterBean.getCreatedOn());
				adminData.setCreatedByUserId(configMasterBean.getUser_ID());
				adminData.setCreatedByRoleId(configMasterBean.getRole_ID());
				adminData.setPageId(configMasterBean.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// pageid
				adminData.setCreatedBy(configMasterBean.getCreatedBy());
				adminData.setContent(mapper.writeValueAsString(configMasterBean));
				adminData.setActivityId(configMasterBean.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(configMasterBean.getRemark());
				adminData.setActivityName(configMasterBean.getActivityName());
				adminData.setActivityRefNo(configMasterBean.getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("CONFIGURATIONMASTER");

				if (ObjectUtils.isEmpty(AdminDataList)) {
					adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);
				} else if (!ObjectUtils.isEmpty(AdminDataList)) {
					adminData.setId(BigDecimal.valueOf(AdminDataList.get(0).getId().intValue()));
					adminWorkFlowReqUtility.updateAdminWorkFlowRequest(adminData);

					// Save data to admin work flow history
					adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(configMasterBean.getSubMenu_ID(),
							configMasterBean.getId(), configMasterBean.getCreatedBy(), configMasterBean.getRemark(),
							configMasterBean.getRole_ID(), mapper.writeValueAsString(configMasterBean));
				}

				// if record is present in admin work flow then update status
				adminWorkFlowReqUtility.getCheckerDataByctivityRefId(configMasterBean.getId().toBigInteger(),
						BigInteger.valueOf(userStatus), configMasterBean.getSubMenu_ID());
			}
			isupdated = true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			isupdated = false;
		}
		return isupdated;
	}

	@Override
	public boolean addLanguageJsonToAdminWorkFlow(LanguageJson languagejson, int userStatus) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(languagejson.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(languagejson.getActivityName());

		String encLanText = EncryptorDecryptor.decryptDataForLangJson(languagejson.getLanguagetext());
		languagejson.setLanguagetext(encLanText);

		try {
			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

				List<LanguageJson> list = masterConfigDao.getLanguageJson();
				ObjectMapper mapper = new ObjectMapper();

				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
				adminData.setCreatedByUserId(languagejson.getUser_ID());
				adminData.setCreatedByRoleId(languagejson.getRole_ID());
				adminData.setPageId(languagejson.getSubMenu_ID()); // set
																	// submenuId
																	// as pageid
				adminData.setCreatedBy(languagejson.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(languagejson));
				adminData.setActivityId(languagejson.getSubMenu_ID()); // set
																		// submenuId
																		// as
																		// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(languagejson.getRemark());
				adminData.setActivityName(languagejson.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminData.setTableName("LANGUAGEJSON_NEW");
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(languagejson.getSubMenu_ID(), list.get(0).getId(),
						languagejson.getCreatedby(), languagejson.getRemark(), languagejson.getRole_ID(),
						mapper.writeValueAsString(languagejson));
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean addMaskingRulesToAdminWorkFlow(MaskingRulesEntity maskingRulesEntity, int userStatus) {
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(maskingRulesEntity.getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.ADMIN_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(maskingRulesEntity.getActivityName());
		try {

			if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getChecker()))
					&& (ApplicationConstants.YESVALUE)
							.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
				List<MaskingRulesEntity> list = getMaskingRulesList();
				ObjectMapper mapper = new ObjectMapper();
				AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();

				adminData.setCreatedByUserId(maskingRulesEntity.getUser_ID());
				adminData.setCreatedByRoleId(maskingRulesEntity.getRole_ID());
				adminData.setPageId(maskingRulesEntity.getSubMenu_ID()); // set
																			// submenuId
																			// as
																			// pageid
				adminData.setCreatedBy(maskingRulesEntity.getCreatedby());
				adminData.setContent(mapper.writeValueAsString(maskingRulesEntity));
				adminData.setActivityId(maskingRulesEntity.getSubMenu_ID()); // set
																				// submenuId
																				// as
																				// activityid
				adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97-
																		// ADMIN_CHECKER_PENDIN
				adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6-
																		// checker
																		// roleid
				adminData.setRemark(maskingRulesEntity.getRemark());
				adminData.setActivityName(maskingRulesEntity.getActivityName());
				adminData.setActivityRefNo(list.get(0).getId());
				adminData.setTableName("MASKINGRULES");
				adminData.setUserAction(BigDecimal.valueOf(userStatus));
				adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

				// Save data to admin work flow history
				adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(maskingRulesEntity.getSubMenu_ID(),
						list.get(0).getId(), maskingRulesEntity.getCreatedby(), maskingRulesEntity.getRemark(),
						maskingRulesEntity.getRole_ID(), mapper.writeValueAsString(maskingRulesEntity));
			}

			return true;
		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}

	}

	@Override
	public List<UserAccountLeadsBean> getCustAccountDetailsById(int id) {

		return masterConfigDao.getCustAccountDetailsById(id);
	}

	@Override
	public List<UserAccountLeadsBean> getCustAllAccountDetails() {

		return masterConfigDao.getCustAllAccountDetails();
	}

	@Override
	public List<LanguageJson> getDistinctLanguageJsonCode() {
		List<LanguageJson> list = masterConfigDao.getDistinctLanguageJsonCode();

		return list;
	}

	@Override
	public List<LanguageJson> getLanguageJsonByLangCode(LanguageJson languagejson) {
		return masterConfigDao.getLanguageJsonByLangCode(languagejson);

	}

	@Override
	public List<LanguageJson> getLanguageJsonByLangText(LanguageJson languagejson) {
		return masterConfigDao.getLanguageJsonByLangText(languagejson);

	}

	@Override
	public boolean updateLanguageJsonList(List<LanguageJson> languagejson) {
		return masterConfigDao.updateLanguageJsonList(languagejson);
	}

	@Override
	public List<CertificateConfigEntity> getCertificateConfigMaster() {
		return masterConfigDao.getCertificateConfigMaster();
	}

	@Override
	public List<CertificateConfigEntity> getCertificateConfigById(int id) {
		return masterConfigDao.getCertificateConfigById(id);
	}

	@Override
	public boolean addCertificateConfigMaster(CertificateConfigEntity certificateConfig) {
		return masterConfigDao.addCertificateConfigMaster(certificateConfig);
	}

	@Override
	public boolean updateCertificateConfigMaster(CertificateConfigEntity certificateConfig) {
		return masterConfigDao.updateCertificateConfigMaster(certificateConfig);
	}

	@Override
	public boolean updateAccountSchemeMaster(AccountSchemeMasterEntity accountSchemeMaster) {
		return masterConfigDao.updateAccountSchemeMaster(accountSchemeMaster);
	}

	@Override
	public List<AccountSchemeMasterEntity> getAccountSchemeMasterById(int id) {
		return masterConfigDao.getAccountSchemeMasterById(id);
	}

	@Override
	public List<AccountSchemeMasterEntity> getAccountSchemeMaster() {
		return masterConfigDao.getAccountSchemeMaster();
	}

	@Override
	public boolean addAccountSchemeMaster(AccountSchemeMasterEntity accountSchemeMaster) {
		return masterConfigDao.addAccountSchemeMaster(accountSchemeMaster);
	}

	@Override
	public boolean addSchedulatTransMaster(SchedularTransMasterEntity schedularData) {
		return masterConfigDao.addSchedulatTransMaster(schedularData);
	}

	@Override
	public boolean updateSchedulatTransMaster(SchedularTransMasterEntity schedularData) {
		return masterConfigDao.updateSchedulatTransMaster(schedularData);
	}

	@Override
	public List<SchedularTransMasterEntity> getSchedulatTransMaster() {
		return masterConfigDao.getSchedulatTransMaster();
	}

	@Override
	public List<SchedularTransMasterEntity> getSchedulatTransMasterById(int id) {
		return masterConfigDao.getSchedulatTransMasterById(id);
	}

	@Override
	public List<SchedularTransMasterEntity> getScheduleTransactionDetails() {

		return masterConfigDao.getScheduleTransactionDetails();
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public List<LimitMasterEntity> getLimitMasterDetails(String id1) {
		BigDecimal appId = new BigDecimal(id1);
		List<LimitMasterEntity> template = limitMasterRepository.getLimitMasterDetails(appId);
		return template;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<AppMasterEntity> getAppListForLimitMaster() {
		List<AppMasterEntity> list = masterConfigDao.getAppListForLimitMaster();

		return list;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public Boolean addLimitMasterList(LimitMasterEntity limitMaster) {
		logger.info("MasterConfigServiceImpl->addLimitMasterList----------Start");
		try {
			if(!ObjectUtils.isEmpty(limitMaster)) {
				limitMaster.setCreatedOn(new Date());
				limitMaster.setType("G");
				limitMaster.setLimitType('G');
				limitMaster.setActivityId(new BigDecimal(0));
				limitMaster.setStatusId(new BigDecimal(3));
				limitMaster.setCustomerId(new BigDecimal(0));
				limitMasterRepository.save(limitMaster);
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		logger.info("MasterConfigServiceImpl->addLimitMasterList----------End");
		return true;

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public List<LimitMasterEntity> getLimitMasterDetailsById(String id1) {
		BigDecimal limitMasterId = new BigDecimal(id1);
		List<LimitMasterEntity> template = limitMasterRepository.getLimitMasterDetailsById(limitMasterId);
		return template;
	}

	@Override
	public Boolean editLimitMaster(LimitMasterEntity limitMaster) {
		logger.info("MasterConfigServiceImpl->editLimitMaster----------Start");
		try {
			if(!ObjectUtils.isEmpty(limitMaster)) {
				limitMasterRepository.save(limitMaster);
			}

		} catch (Exception e) {
			logger.info("Exception:", e);
			return false;
		}
		logger.info("MasterConfigServiceImpl->editLimitMaster----------End");
		return true;

	}
}
