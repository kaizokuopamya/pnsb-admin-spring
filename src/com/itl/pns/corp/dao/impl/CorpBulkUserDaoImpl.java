package com.itl.pns.corp.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpBulkUserDao;
import com.itl.pns.corp.dao.CorporateDao;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.service.CorporateService;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.AdminWorkFlowRequestEntity;
import com.itl.pns.repository.CorpActivitySettingMasterRepository;
import com.itl.pns.util.AdminWorkFlowReqUtility;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.RandomNumberGenerator;

import oracle.sql.TIMESTAMP;

/**
 * @author shubham.lokhande
 *
 */
@Repository
@Transactional
public class CorpBulkUserDaoImpl implements CorpBulkUserDao {

	static Logger LOGGER = Logger.getLogger(CorpBulkUserDaoImpl.class);
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Autowired
	CorpActivitySettingMasterRepository corpActivityRepository;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CorporateDao corporateDao;

	@Autowired
	CorporateService corporateService;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean saveBulkCorpUsers(List<CorpUserEntity> custDataList) {
		Session session = sessionFactory.getCurrentSession();
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(custDataList.get(0).getRole_ID().intValue());
		int statusId = adminWorkFlowReqUtility.getStatusIdByName(ApplicationConstants.CORP_CHECKER_PENDING);
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(custDataList.get(0).getActivityName());

		try {

			for (CorpUserEntity custData : custDataList) {
				if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getChecker()))
						&& (ApplicationConstants.YESVALUE)
								.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
					custData.setStatusid(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
				} else {
					custData.setStatusid(BigDecimal.valueOf(3));
				}

				custData.setEmail_id(EncryptorDecryptor.encryptData(custData.getEmail_id()));
				custData.setWork_phone(EncryptorDecryptor.encryptData(custData.getWork_phone()));
				custData.setPersonal_Phone(EncryptorDecryptor.encryptData(custData.getPersonal_Phone()));

				ResponseMessageBean responsecode = new ResponseMessageBean();
				responsecode = corporateDao.checkIsUserExist(custData);

				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					custData.setAppid(BigDecimal.valueOf(2));
					custData.setState(BigDecimal.valueOf(1));
					custData.setCountry(BigDecimal.valueOf(1));
					custData.setCity(BigDecimal.valueOf(1));
					custData.setCreatedon(new Timestamp(System.currentTimeMillis()));

					RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
					String randomNumber = randomNumberGenerator.generateRandomString();
					String encrypUserName = EncryptDeryptUtility.md5(custData.getTempUserName());

					System.out.println("Non encrypted username: " + custData.getUser_name());
					System.out.println("Encrypted username:  " + encrypUserName);

					String encryptpass = EncryptDeryptUtility.md5(randomNumber);

					System.out.println("Non encrypted pwd: " + randomNumber);
					System.out.println("Encrypted pwd:  " + encryptpass);
					custData.setUser_name(encrypUserName);
					custData.setUser_pwd(encryptpass);

					session.save(custData);

					if (roleName.equalsIgnoreCase(ApplicationConstants.CORPORATE_MAKER)
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getChecker()))
							&& (ApplicationConstants.YESVALUE)
									.equals(Character.toString(activityMasterData.get(0).getMaker()))) {
						List<CorpUserEntity> list = corporateDao.getAllCorpUsers();

						ObjectMapper mapper = new ObjectMapper();
						AdminWorkFlowRequestEntity adminData = new AdminWorkFlowRequestEntity();
						adminData.setAppId(BigDecimal.valueOf(2));
						adminData.setCreatedByUserId(custData.getUser_ID());
						adminData.setCreatedByRoleId(custData.getRole_ID());
						adminData.setPageId(custData.getSubMenu_ID()); // set submenuId as pageid
						adminData.setCreatedBy(custData.getUser_ID());
						adminData.setContent(mapper.writeValueAsString(custData));
						adminData.setActivityId(custData.getSubMenu_ID()); // set submenuId as activityid
						adminData.setStatusId(BigDecimal.valueOf(statusId)); // 97- ADMIN_CHECKER_PENDIN
						adminData.setPendingWithRoleId(BigDecimal.valueOf(6)); // 6- checker roleid
						adminData.setRemark(custData.getRemark());
						adminData.setActivityName(custData.getActivityName());
						adminData.setActivityRefNo(list.get(0).getId());
						adminData.setTableName("CORP_USERS");
						adminData.setUserAction(BigDecimal.valueOf(3));
						adminWorkFlowReqUtility.addAdminWorkFlowRequest(adminData);

						// Save data to admin work flow history
						adminWorkFlowReqUtility.saveToAdminWorkFlowHistory(custData.getSubMenu_ID(),
								list.get(0).getId(), custData.getUser_ID(), custData.getRemark(), custData.getRole_ID(),
								mapper.writeValueAsString(custData));
					}

				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

}
