package com.itl.pns.corp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.CorpCompanyCorpUserDupBean;
import com.itl.pns.bean.CorpCompanyDataBean;
import com.itl.pns.bean.CorpDataBean;
import com.itl.pns.bean.CorpMasterUserMenuAccBean;
import com.itl.pns.bean.CorpMenuAccMasterBean;
import com.itl.pns.bean.CorpUserAccMapBean;
import com.itl.pns.bean.CorpUserBean;
import com.itl.pns.bean.CorpUserEntityBean;
import com.itl.pns.bean.CorpUserMenuMapBean;
import com.itl.pns.bean.CorporateResponse;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserRoleNameBean;
import com.itl.pns.bean.VerifyCifPara;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.corp.dao.CorpUserDao;
import com.itl.pns.corp.dao.OfflineCorpUserDao;
import com.itl.pns.corp.entity.CorpAccMapEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterDupEntity;
import com.itl.pns.corp.entity.CorpCompanyMasterEntity;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.entity.CorpMenuMapEntity;
import com.itl.pns.corp.entity.CorpUserAccMapEntity;
import com.itl.pns.corp.entity.CorpUserDupEntity;
import com.itl.pns.corp.entity.CorpUserEntity;
import com.itl.pns.corp.entity.CorpUserMenuMapEntity;
import com.itl.pns.corp.repository.CorpMenuMapRepository;
import com.itl.pns.corp.service.CorporateService;
import com.itl.pns.corp.service.OfflineCorpUserService;
import com.itl.pns.entity.CorpRoles;
import com.itl.pns.entity.User;
import com.itl.pns.repository.CorpRolesRepository;
import com.itl.pns.repository.UserMasterRepository;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.Utils;

/**
 * @author shubham.lokhande
 *
 */

@RestController
@RequestMapping("OfflineCorpUser")
public class OfflineCorpUserController {

	private static final Logger logger = LogManager.getLogger(OfflineCorpUserController.class);

	@Autowired
	private OfflineCorpUserService corpUserService;

	@Autowired
	private OfflineCorpUserDao offlineCorpUserDao;

	@Autowired
	private CorpUserDao corpUserDao;

	@Autowired
	private CorpRolesRepository corpRoleRepository;

	@Autowired
	private UserMasterRepository userRepository;

	@Autowired
	private CorpMenuMapRepository corpMenuMapRepository;

	@Autowired
	CorporateService corporateService;

	@PostMapping(value = "/getOfflineCorpCompData", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfflineCorpCompData(@RequestBody CorpCompanyMasterEntity corpCompReq,
			HttpServletRequest httpRequest) {
		logger.info("UserActivity :: View Corporate Data : userId : " + Utils.getUserId(httpRequest));
		logger.info("userId : " + Utils.getUserId(httpRequest) + ",  getOfflineCorpCompData service calling");
		logger.info("userId : " + Utils.getUserId(httpRequest) + ", Corporate Company request..........."
				+ corpCompReq.toString());
		logger.info("userId : " + Utils.getUserId(httpRequest) + ", Branch Code From UI....."
				+ corpCompReq.getBranchCode());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<User> users = userRepository.findByuserid(corpCompReq.getUserId());
			List<CorpCompanyDataBean> corpCompMasterDataTmp = new ArrayList<>();
			List<CorpCompanyDataBean> corpCompMasterData = new ArrayList<>();

			if (corpCompReq.getStatusList().contains(0) || corpCompReq.getStatusList().contains(35)
					|| corpCompReq.getStatusList().contains(36)) {
				logger.info("userId : " + Utils.getUserId(httpRequest) + ", Get Data from Temp table.....");
				corpCompMasterDataTmp = corpUserService.getOfflineCorpCompData(corpCompReq.getStatusList(),
						users.get(0).getBranchCode());

				if (corpCompReq.getStatusList().contains(36)) {
					List<Integer> statusList = new ArrayList<>();
					statusList.add(53);
					statusList.add(54);

					corpCompMasterData = corpUserService.getOfflineCorpCompBlockUnblockDataFromUsers(statusList,
							users.get(0).getBranchCode());

				}
				// TODO uncomment code for delete functionality
//				corpCompMasterDataTmp = corpUserService.getOfflineCorpCompData(corpCompReq.getStatusList(),
//						users.get(0).getBranchCode());
			}

			if (corpCompReq.getStatusList().contains(8) || corpCompReq.getStatusList().contains(3)
					|| corpCompReq.getStatusList().contains(141)) {
				logger.info("userId : " + Utils.getUserId(httpRequest) + ", Get Data from master table.....");
				corpCompMasterData = corpUserService.getOfflineCorpCompData(corpCompReq.getStatusList(),
						users.get(0).getBranchCode());
			}

			List<CorpCompanyDataBean> newList = new ArrayList<>();
			newList.addAll(corpCompMasterData);
			newList.addAll(corpCompMasterDataTmp);

			if (!ObjectUtils.isEmpty(newList)) {
				for (CorpCompanyDataBean compObg : newList) {
					if (null != compObg.getCompanyName()) {
						compObg.setCompanyName(EncryptorDecryptor.decryptData(compObg.getCompanyName()));
					}
					if (null != compObg.getCif() && compObg.getCif().contains("=")) {
						compObg.setCif(EncryptorDecryptor.decryptData(compObg.getCif()));
					}
					if (null != compObg.getCompanyCode() && compObg.getCompanyCode().contains("=")) {
						compObg.setCompanyCode(EncryptorDecryptor.decryptData(compObg.getCompanyCode()));
					}
					if (null != compObg.getPancardNo() && compObg.getPancardNo().contains("=")) {
						compObg.setPancardNo(EncryptorDecryptor.decryptData(compObg.getPancardNo()));
					}
					logger.info("userId : " + Utils.getUserId(httpRequest) + " ,Corporate Company Response: "
							+ newList.toString());
				}
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(newList);
			} else {
				logger.info("userId : " + Utils.getUserId(httpRequest) + " ,No Record Found");
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.info("userId : " + Utils.getUserId(httpRequest) + " :Exception");
			logger.error(e.getMessage(), e);
			response.setResponseMessage("Invalid Request");
			response.setResponseCode("202");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/getOfflineCorpCompDataById", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpCompRequestsById(@RequestBody CorpCompanyDataBean corpCompReq,
			HttpServletRequest httpRequest) {
		logger.info("UserActivity :: Edit Corporate : userId : " + Utils.getUserId(httpRequest));
		logger.info("userId : " + Utils.getUserId(httpRequest) + ", getOfflineCorpCompDataById service calling");
		logger.info("userId : " + Utils.getUserId(httpRequest) + ", corporate request: " + corpCompReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpCompanyDataBean> corpCompMasterDataTmp = new ArrayList<>();
			corpCompReq.setStatusList(Arrays.asList(0, 35, 36, 8, 3, 141));
			corpCompMasterDataTmp = corpUserService.getOfflineCorpCompDataById(corpCompReq);
			VerifyCifPara verifyCifPara = new VerifyCifPara();
			verifyCifPara.setCorpCompId(EncryptorDecryptor.decryptData(corpCompMasterDataTmp.get(0).getCif()));

			String cifData = corpUserService.getDataByCif(verifyCifPara);
			corpCompMasterDataTmp.get(0).setCifData(cifData);

			if (!ObjectUtils.isEmpty(corpCompMasterDataTmp)) {
				for (CorpCompanyDataBean compObg : corpCompMasterDataTmp) {
					if (compObg.getCompanyName() != null) {
						compObg.setCompanyName(EncryptorDecryptor.decryptData(compObg.getCompanyName()));
					}
					if (compObg.getCif() != null && compObg.getCif().contains("=")) {
						compObg.setCif(EncryptorDecryptor.decryptData(compObg.getCif()));
					}
					if (compObg.getCompanyCode() != null && compObg.getCompanyCode().contains("=")) {
						compObg.setCompanyCode(EncryptorDecryptor.decryptData(compObg.getCompanyCode()));
					}
					if (compObg.getPancardNo() != null && compObg.getPancardNo().contains("=")) {
						compObg.setPancardNo(EncryptorDecryptor.decryptData(compObg.getPancardNo()));
					}
					if (null != compObg.getApprovalLevel()) {
						compObg.setUserTypes(compObg.getApprovalLevel().charAt(0));
					}
				}
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompMasterDataTmp);
				logger.info("userId : " + Utils.getUserId(httpRequest) + ", Edit successfully");

			} else {
				logger.info("userId : " + Utils.getUserId(httpRequest) + ", No Records Found");
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
			logger.info("userId : " + Utils.getUserId(httpRequest) + "corporate response...."
					+ corpCompMasterDataTmp.get(0).toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("userId : " + Utils.getUserId(httpRequest) + "Error in edit corporate, " + e);

		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfflineCorpCompDataByRrn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfflineCorpCompDataByRrn(
			@RequestBody CorpCompanyMasterEntity corpCompReq) {
		logger.info("getOfflineCorpCompDataByRrn service calling.........");
		logger.info("corporate company request.............." + corpCompReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpCompanyMasterEntity> corpCompMasterData = corpUserService.getOfflineCorpCompDataByRrn(corpCompReq);
			if (!ObjectUtils.isEmpty(corpCompMasterData)) {

				for (CorpCompanyMasterEntity compObg : corpCompMasterData) {
					if (compObg.getCif() != null && compObg.getCif().contains("=")) {
						compObg.setCif(EncryptorDecryptor.decryptData(compObg.getCif()));
					}
					if (compObg.getCompanyCode() != null && compObg.getCompanyCode().contains("=")) {
						compObg.setCompanyCode(EncryptorDecryptor.decryptData(compObg.getCompanyCode()));
					}
					if (compObg.getPancardNo() != null && compObg.getPancardNo().contains("=")) {
						compObg.setPancardNo(EncryptorDecryptor.decryptData(compObg.getPancardNo()));
					}
				}
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompMasterData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/getOfflineCorpByCompNameCifCorpId", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfflineCorpByCompNameCifCorpId(
			@RequestBody CorpCompanyDataBean corpCompReq) {
		logger.info("getOfflineCorpByCompNameCifCorpId service calling.........");
		logger.info("corporate company request.............." + corpCompReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpCompanyDataBean> corpCompMasterData = corpUserService
					.getOfflineCorpByCompNameCifCorpId(corpCompReq);
			if (!ObjectUtils.isEmpty(corpCompMasterData)) {

				for (CorpCompanyDataBean compObg : corpCompMasterData) {
					if (compObg.getCif() != null && compObg.getCif().contains("=")) {
						compObg.setCif(EncryptorDecryptor.decryptData(compObg.getCif()));
					}
					if (compObg.getCompanyCode() != null && compObg.getCompanyCode().contains("=")) {
						compObg.setCompanyCode(EncryptorDecryptor.decryptData(compObg.getCompanyCode()));
					}
					if (compObg.getPancardNo() != null && compObg.getPancardNo().contains("=")) {
						compObg.setPancardNo(EncryptorDecryptor.decryptData(compObg.getPancardNo()));
					}
					if (compObg.getCompanyName() != null && compObg.getCompanyName().contains("=")) {
						compObg.setCompanyName(EncryptorDecryptor.decryptData(compObg.getCompanyName()));
					}
				}
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompMasterData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/updateCorpCompData", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpCompData(@RequestBody CorpCompanyMasterEntity corpCompReq,
			HttpServletRequest httpRequest) {
		logger.info("UserActivity :: Update Corporate : userId : " + Utils.getUserId(httpRequest));
		logger.info("userId : " + Utils.getUserId(httpRequest) + ", updateCorpCompData service calling.........");
		logger.info("userId : " + Utils.getUserId(httpRequest) + ", update corporate request.............."
				+ corpCompReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isUpdate = false;
		try {
			corpCompReq.setUpdatedBy(Utils.getUpdatedBy(httpRequest));
			response = corpUserService.updateCorpCompData(corpCompReq);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/updateCorpUsersMode", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpUsersMode(@RequestBody CorpCompanyMasterEntity corpCompReq,
			HttpServletRequest httpRequest) {
		logger.info("updateCorpCompData service calling.........");
		logger.info("corporate company request.............." + corpCompReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isUpdate = false;
		logger.info("update corpCompnayData: " + corpCompReq);
		try {
			corpCompReq.setUpdatedBy(Utils.getUpdatedBy(httpRequest));
			response = corpUserService.updateCorpUsersMode(corpCompReq);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfflineCorpMenuByCompanyId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpMenuByCompanyId(@RequestBody CorpMenuMapEntity CorpMenuData) {
		logger.info("getOfflineCorpMenuByCompanyId service calling.........");
		logger.info("Corporate Menu.............." + CorpMenuData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpMenuMapEntity> corpCompData = corpUserService.getOfflineCorpMenuByCompanyId(CorpMenuData);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfflineCorpAccByCompanyId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpAccByCompanyId(@RequestBody CorpAccMapEntity corpAccData) {
		logger.info("getOfflineCorpMenuByCompanyId service calling.........");
		logger.info("corporate offline request.............." + corpAccData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpAccMapEntity> corpCompData1 = corpUserService.getOfflineCorpAccByCompanyId(corpAccData);
			List<CorpAccMapEntity> corpCompData = new ArrayList<>();
			for (CorpAccMapEntity co : corpCompData1) {
				CorpAccMapEntity corpAccMapEntity = co;
				corpAccMapEntity.setAccountNo(EncryptorDecryptor.decryptData(co.getAccountNo()));
				corpCompData.add(corpAccMapEntity);
			}

			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/getOfflineMenuAndAccByCompanyId", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfflineMenuAndAccByCompanyId(
			@RequestBody @Valid CorpMenuMapEntity corpMenuMapEntity, HttpServletRequest httpRequest) {
		logger.info("UserActivity :: View corporate menu and account : userId : " + Utils.getUserId(httpRequest));
		logger.info("userId : " + Utils.getUserId(httpRequest) + ",  View menu and account Request.."
				+ corpMenuMapEntity.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		String userType = corpMenuMapEntity.getApprovalLevel();
		String lvel = corpMenuMapEntity.getLevelMaster();
		try {
			CorpMenuAccMasterBean corpMenuAccMasterBean = new CorpMenuAccMasterBean();
			CorpAccMapEntity corpAccObj = new CorpAccMapEntity();
			corpAccObj.setCorpId(corpMenuMapEntity.getCorpId());
			List<CorpMenuEntity> corpMenuDataList = corporateService.getAllCorpMenus();
			List<CorpMenuMapEntity> corpMenuData1 = corpUserService
					.getOfflineCorpMenuByCompanyIdBeam(corpMenuMapEntity);
			List<String> menuNameList = new ArrayList<>();
			corpMenuDataList.forEach(m -> menuNameList.add(m.getMenuName()));
			List<String> corporateMenuNameList = new ArrayList<>();
			for (CorpMenuMapEntity cM : corpMenuData1) {
				if ("S".equals(userType) // singal user
						&& ApplicationConstants.PFMS.equals(cM.getMenuName())
						|| "1".equals(lvel) // Multi user L1
								&& ApplicationConstants.PFMS.equals(cM.getMenuName())) {
					logger.info("userId : " + Utils.getUserId(httpRequest) + ", getMenuName : " + cM.getMenuName());
					logger.info("userId : " + Utils.getUserId(httpRequest) + ", getCorpId : " + cM.getCorpId());
					corpUserService.corpCompMenuUpdateById(cM.getCorpId());
				}
			}
			List<CorpMenuMapEntity> corpMenuData = corpUserService.getOfflineCorpMenuByCompanyIdBeam(corpMenuMapEntity);
			List<CorpAccMapEntity> corpAccData = corpUserService.getOfflineCorpAccByCompanyIdBean(corpAccObj);

			for (CorpMenuMapEntity menuData : corpMenuData) {
				corporateMenuNameList.add(menuData.getMenuName());
			}
			corporateMenuNameList.removeAll(menuNameList);

			Iterator it = corpMenuData.iterator();
			while (it.hasNext()) {
				CorpMenuMapEntity corMenu = (CorpMenuMapEntity) it.next();
				if (corporateMenuNameList.contains(corMenu.getMenuName())
						&& !"General View".equals(corMenu.getMenuName())) {
					it.remove();
				}
			}

			corpMenuAccMasterBean.setCorpMenuList(corpMenuData);
			corpMenuAccMasterBean.setCorpAccList(corpAccData);
			if (!ObjectUtils.isEmpty(corpMenuAccMasterBean)) {
				for (CorpAccMapEntity compAcc : corpAccData) {
					if (compAcc.getAccountNo() != null && compAcc.getAccountNo().contains("=")) {
						compAcc.setAccountNo(EncryptorDecryptor.decryptData(compAcc.getAccountNo()));
					}
				}
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpMenuAccMasterBean);
				logger.info("userId : " + Utils.getUserId(httpRequest) + ", View corporate menu and account."
						+ response.getResult().toString());
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
				logger.info("userId : " + Utils.getUserId(httpRequest)
						+ ", View corporate menu and account. No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/corpRoleList", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> corpRoleList() {
		logger.info("corpRoleList service calling.........");
		ResponseMessageBean response = new ResponseMessageBean();
		List<CorpRoles> corRolesList = corpRoleRepository.findAll();
		response.setResponseCode("200");
		response.setResult(corRolesList);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateOfflineCorpMenuMapData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateOfflineCorpMenuMapData(
			@RequestBody CorpMenuAccMasterBean corpMenuAccData, HttpServletRequest httpRequest) {
		logger.info("updateOfflineCorpMenuMapData service calling.........");
		logger.info("Corporeate MenuAcc.............." + corpMenuAccData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isUpdate = false;
		try {
			isUpdate = offlineCorpUserDao.updateOfflineCorpMenuMapData(corpMenuAccData,
					Utils.getUpdatedBy(httpRequest));

			if (isUpdate) {
				response.setResponseMessage("Details Updated Successfully");
				response.setResponseCode("200");

			} else {
				response.setResponseMessage("Error While Updating Company Details");
				response.setResponseCode("500");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateOfflineCorpAccMapData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateOfflineCorpAccMapData(
			@RequestBody CorpMenuAccMasterBean CorpMenuAccData, HttpServletRequest httpRequest) {
		logger.info("updateOfflineCorpAccMapData service calling.........");
		logger.info("coporate Menu Acc.............." + CorpMenuAccData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isUpdate = false;
		try {
			isUpdate = offlineCorpUserDao.updateOfflineCorpAccMapData(CorpMenuAccData, Utils.getUpdatedBy(httpRequest));

			if (isUpdate) {
				response.setResponseMessage("Details Updated Successfully");
				response.setResponseCode("200");

			} else {
				response.setResponseMessage("Error While Updating Company Details");
				response.setResponseCode("500");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateOfflineCorpMenuAccMapData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateOfflineCorpMenuAccMapData(
			@RequestBody CorpMenuAccMasterBean corpMenuAccData, HttpServletRequest httpRequest) {
		logger.info("updateOfflineCorpMenuAccMapData service calling.........");
		logger.info("corpoateMenuAcc.............." + corpMenuAccData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isUpdate = false;
		try {
			System.out.println("RRn==" + (String.valueOf(System.currentTimeMillis())));

			isUpdate = offlineCorpUserDao.updateOfflineCorpMenuMapData(corpMenuAccData,
					Utils.getUpdatedBy(httpRequest));
			isUpdate = offlineCorpUserDao.updateOfflineCorpAccMapData(corpMenuAccData, Utils.getUpdatedBy(httpRequest));

			if (isUpdate) {
				response.setResponseMessage("Details Updated Successfully");
				response.setResponseCode("200");

			} else {
				response.setResponseMessage("Error While Updating Company Details");
				response.setResponseCode("500");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserMenuByCorpUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getUserMenuByCorpUserId(
			@RequestBody CorpUserMenuMapEntity corpUserMenuData) {
		logger.info("getUserMenuByCorpUserId service calling.........");
		logger.info("corpoateMenuAcc Request.............." + corpUserMenuData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpUserMenuMapEntity> corpCompData = corpUserService.getUserMenuByCorpUserId(corpUserMenuData);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserAccountByCorpUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getUserAccountByCorpUserId(
			@RequestBody CorpUserAccMapEntity corpUserAccData) {
		logger.info("getUserMenuByCorpUserId service calling.........");
		logger.info("corporateUserAcc Request.............." + corpUserAccData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpUserAccMapEntity> corpCompData = corpUserService.getUserAccountByCorpUserId(corpUserAccData);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpUsersMenuAccByCompId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpUsersMenuAccByCompId(
			@RequestBody CorpUserEntityBean corpUserEntityBean) {
		logger.info("getCorpUsersMenuAccByCompId service calling.........");
		logger.info("corporateUser Request.............." + corpUserEntityBean.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			boolean isDecrypted = true;
			List<Integer> statusList = new ArrayList<>();

			if (corpUserEntityBean.getStatusid().intValue() == 3) {
				statusList.add(3);
				statusList.add(16);
				statusList.add(54);
				statusList.add(53);
				statusList.add(8);
			} else if (corpUserEntityBean.getStatusid().intValue() == 8
					|| corpUserEntityBean.getStatusid().intValue() == 141
					|| corpUserEntityBean.getStatusid().intValue() == 35) {
				statusList.add(3);
				statusList.add(8);
				statusList.add(141);
			}
			statusList.add(corpUserEntityBean.getStatusid().intValue());
			corpUserEntityBean.setStatusList(statusList);

			List<CorpUserEntityBean> corpCompData = corpUserService.getAllCorpUsersByCompIdBean(corpUserEntityBean,
					isDecrypted);
			List<CorpUserMenuMapBean> corpUserMenuList = offlineCorpUserDao
					.getUserMenuListByCorpCompIdBean(corpUserEntityBean.getCorp_comp_id().toBigInteger());
			List<CorpUserAccMapBean> corpUserAccList = offlineCorpUserDao
					.getUserAccountListByCorpCompIdBean(corpUserEntityBean.getCorp_comp_id().toBigInteger());

			for (CorpUserEntityBean corpUser : corpCompData) {
				List<CorpUserMenuMapBean> corpUserMenuData = new ArrayList<>();
				for (CorpUserMenuMapBean menuObj : corpUserMenuList) {
					CorpUserMenuMapBean mapMenuObj = new CorpUserMenuMapBean();
					if (corpUser.getId().intValue() == menuObj.getCorpUserId().intValue()) {
						mapMenuObj.setCorpCompId(menuObj.getCorpCompId());
						mapMenuObj.setId(menuObj.getId());
						mapMenuObj.setCorpMenuId(menuObj.getCorpMenuId());
						mapMenuObj.setCorpSubMenuId(menuObj.getCorpSubMenuId());
						mapMenuObj.setStatusId(menuObj.getStatusId());
						mapMenuObj.setUserRrn(menuObj.getUserRrn());
						mapMenuObj.setCorpUserId(menuObj.getCorpUserId());
						mapMenuObj.setMenuName(menuObj.getMenuName());
						mapMenuObj.setSubMenuName(menuObj.getSubMenuName());
						mapMenuObj.setUpdatedby(BigDecimal.valueOf(1));
						if (!ObjectUtils.isEmpty(mapMenuObj)) {
							corpUserMenuData.add(mapMenuObj);
						}
					}
				}
				corpUser.setCorpUserMenuData(corpUserMenuData);
			}
			for (CorpUserEntityBean corpUser : corpCompData) {
				List<CorpUserAccMapBean> corpUserAccData = new ArrayList<>();
				for (CorpUserAccMapBean accObj : corpUserAccList) {
					CorpUserAccMapBean mapAccObj = new CorpUserAccMapBean();
					if (corpUser.getId().intValue() == accObj.getCorpUserId().intValue()) {
						mapAccObj.setCorpCompId(accObj.getCorpCompId());
						mapAccObj.setId(accObj.getId());
						mapAccObj.setAccountNo(EncryptorDecryptor.decryptData(accObj.getAccountNo()));
						mapAccObj.setStatusId(accObj.getStatusId());
						mapAccObj.setUserRrn(accObj.getUserRrn());
						mapAccObj.setCorpUserId(accObj.getCorpUserId());
						mapAccObj.setUpdatedby(BigDecimal.valueOf(1));
						if (!ObjectUtils.isEmpty(mapAccObj)) {
							corpUserAccData.add(mapAccObj);
						}
					}

				}
				corpUser.setCorpUserAccData(corpUserAccData);
			}

			if (!ObjectUtils.isEmpty(corpCompData)) {
				logger.info("-----------------------------------");
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
				logger.info("corporateUser Response: " + corpCompData.toString());
				logger.info("-----------------------------------");
			} else {
				logger.info("corporateUser Response: No Records Found");
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/saveAllCorpMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveAllCorpMasterData(@RequestBody CorpDataBean corpData,
			HttpServletRequest httpRequest) {

		logger.info("saveAllCorpMasterData service calling.........");
		logger.info("corporate Request.............." + corpData.toString());

		ResponseMessageBean response = new ResponseMessageBean();
		boolean isDataSaved = false;
		try {
			logger.info("CorpData Request: " + corpData.toString());
			corpData.setCreatedByUpdatedBy(Utils.getUpdatedBy(httpRequest));

			corpData.setReqStatus("Approved");
			corpData.setCorpCompReqId(corpData.getCorpCompData().getId());
			offlineCorpUserDao.saveToCorpCompMasterData(corpData);
			corpUserDao.saveToCorpMenuMap(corpData);
			corpUserDao.saveToCorpAccMap(corpData);
			offlineCorpUserDao.saveOfflineCorpUserMasterData(corpData);

			isDataSaved = true;

			if (isDataSaved) {
				response.setResponseMessage("Records Saved Successfully");
				response.setResponseCode("200");
			} else {
				response.setResponseMessage("Request Rejected");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveToCorpCompMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveToCorpCompMasterData(@RequestBody CorpDataBean corpData) {
		logger.info("saveToCorpCompMasterData service calling.........");
		logger.info("CorpDataBean Request.............." + corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean responsecode = new ResponseMessageBean();
		boolean isDataSaved = false;
		try {
			responsecode = corpUserDao.isCompanyExist(corpData.getCorpCompData());
			if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
				isDataSaved = offlineCorpUserDao.saveToCorpCompMasterData(corpData);

				if (isDataSaved) {
					response.setResponseMessage("Company Details Saved Successfully");
					response.setResponseCode("200");
					response.setResult(corpData.getCorpCompId());
				} else {
					response.setResponseMessage("Request Rejected");
					response.setResponseCode("202");
				}
			} else {
				response.setResponseMessage("Company Details Updated Successfully ");
				response.setResponseCode("200");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveToCorpMenuMap", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveToCorpMenuMap(@RequestBody CorpDataBean corpData,
			HttpServletRequest httpRequest) {
		logger.info("saveToCorpMenuMap service calling.........");
		logger.info("CorpDataBean Request.............." + corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isDataSaved = false;
		try {
			corpData.setCreatedByUpdatedBy(Utils.getUpdatedBy(httpRequest));
			corpData.setReqStatus("Approved");
			isDataSaved = corpUserDao.saveToCorpMenuMap(corpData);

			if (isDataSaved) {
				response.setResponseMessage("Company Menus Saved Successfully");
				response.setResponseCode("200");
			} else {
				response.setResponseMessage("Request Rejected");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveToCorpAccMap", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveToCorpAccMap(@RequestBody CorpDataBean corpData,
			HttpServletRequest httpRequest) {
		logger.info("saveToCorpAccMap service calling.........");
		logger.info("CorpDataBean Request.............." + corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isDataSaved = false;
		try {
			corpData.setCreatedByUpdatedBy(Utils.getUpdatedBy(httpRequest));
			isDataSaved = corpUserDao.saveToCorpAccMap(corpData);

			if (isDataSaved) {
				response.setResponseMessage("Company Accounts Saved Successfully");
				response.setResponseCode("200");
			} else {
				response.setResponseMessage("Request Rejected");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveOfflineCorpUserMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveOfflineCorpUserMasterDataTemp(@RequestBody CorpDataBean corpData,
			HttpServletRequest httpRequest) {
		logger.info("UserActivity :: submit offline corporate user : userId : " + Utils.getUserId(httpRequest));
		logger.info("userId : " + Utils.getUserId(httpRequest) + ", saveOfflineCorpUserMasterData Request.............."
				+ corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		corpData.setCreatedByUpdatedBy(Utils.getUpdatedBy(httpRequest));
		try {
			response = offlineCorpUserDao.submitOfflineCorpUserMasterData(corpData);
			// TODO COMMENT FOR UAT RELEASE
//			offlineCorpUserDao.verifyCompanyAndUserOgstatus(corpData);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/approveRejectCorpUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> approveRejectCorpUserTmp(@RequestBody CorpDataBean corpData,
			HttpServletRequest httpRequest) {
		logger.info("UserActivity :: Approve/Reject By Checker : userId : " + Utils.getUserId(httpRequest));
		logger.info("userId : " + Utils.getUserId(httpRequest) + ", approveRejectCorpUser service calling.........");
		logger.info("userId : " + Utils.getUserId(httpRequest) + ", CorpDataBean Request.............."
				+ corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		CorporateResponse corpResponse = new CorporateResponse();
		try {
			String companyName = corpUserDao.getCompanyMasterById(corpData.getCorpCompId());
			corpData.setCreatedByUpdatedBy(Utils.getUpdatedBy(httpRequest));
			corpResponse = offlineCorpUserDao.approveRejectCompanyUser(corpData,
					EncryptorDecryptor.decryptData(companyName));
			if (corpResponse.isApproved()) {
				Collection<BigDecimal> newUsers = offlineCorpUserDao.findCorpUserIdByCompId(corpResponse.getCorpId());
				// Send email and SMS
				offlineCorpUserDao.sendEmailAndSms(EncryptorDecryptor.decryptData(companyName),
						corpResponse.getCorpId(), corpResponse.getCompanyCode(), corpResponse.getTempPassword(),
						newUsers, corpResponse.getCreatedByUpdateBy());
				try {
					if (ObjectUtils.isEmpty(corpResponse.getOldCorpId())) {
						offlineCorpUserDao.linkDlinkAccounts(corpResponse.getUserIdMap(), corpResponse.getCorpId());
					} else {
						offlineCorpUserDao.linkDlinkAccounts(corpResponse.getUserIdMap(), corpResponse.getOldCorpId());
					}

				} catch (Exception e) {
					logger.error("userId : " + Utils.getUserId(httpRequest) + ", " + corpData.getCorpCompId()
							+ " Exception is: " + e);
				}

				response.setResponseCode("200");
				response.setResponseMessage("Approved Successfully");

			} else if (corpResponse.isPendingApprove()) {
				offlineCorpUserDao.sendEmailAndSms(EncryptorDecryptor.decryptData(companyName),
						corpResponse.getCorpId(), corpResponse.getCompanyCode(), corpResponse.getTempPassword(),
						corpResponse.getNewUsers(), corpResponse.getCreatedByUpdateBy());
				response.setResponseCode("200");
				response.setResponseMessage("Approved Successfully");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("Rejected Successfully");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCorpUserMenuAccData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpUserMenuAccData(
			@RequestBody CorpMasterUserMenuAccBean corpMenuAccReq) {
		logger.info("approveRejectCorpUser service calling.........");
		logger.info("CorpMenuAcc Request.............." + corpMenuAccReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isDataUpdated = false;
		try {
			isDataUpdated = offlineCorpUserDao.updateOfflineCorpUserMenuData(corpMenuAccReq.getCorpUsersMenu());
			isDataUpdated = offlineCorpUserDao.updateOfflineCorpUserAccData(corpMenuAccReq.getCorpUsersAcc());

			if (isDataUpdated) {
				response.setResponseMessage("Records Updated Successfully");
				response.setResponseCode("200");
			} else {
				response.setResponseMessage("Error While Updating Records");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCorpUserData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCorpUserData(@RequestBody CorpMasterUserMenuAccBean corpUserData) {
		logger.info("updateCorpUserData service calling.........");
		logger.info("CorpUser Request.............." + corpUserData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isDataSaved = false;
		try {
			isDataSaved = corpUserService.updateCorpUserData(corpUserData.getCorpUsers());
			if (isDataSaved) {
				response.setResponseMessage("Records Updated Successfully");
				response.setResponseCode("200");
			} else {
				response.setResponseMessage("Request Rejected");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteOfflineCorpMenuMapData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> deleteOfflineCorpMenuMapData(
			@RequestBody CorpMenuAccMasterBean CorpMenuAccData, HttpServletRequest httpRequest) {
		logger.info("deleteOfflineCorpMenuMapData service calling.........");
		logger.info("CorpMenuAcc Request.............." + CorpMenuAccData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isUpdate = false;
		try {
			isUpdate = offlineCorpUserDao.updateOfflineCorpMenuMapData(CorpMenuAccData,
					Utils.getUpdatedBy(httpRequest));

			if (isUpdate) {
				response.setResponseMessage("Details Updated Successfully");
				response.setResponseCode("200");

			} else {
				response.setResponseMessage("Error While Updating Company Details");
				response.setResponseCode("500");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updatOfflineCorpUserData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updatOfflineCorpUserData(@RequestBody CorpDataBean corpData) {
		logger.info("updateCorpUserData service calling.........");
		logger.info("corpData Request.............." + corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isUpdated = false;
		try {
			isUpdated = offlineCorpUserDao.updateCorpUserData(corpData.getCorpUserMasterData());
			isUpdated = offlineCorpUserDao.updateCorpUserMenuMapDetails(corpData.getCorpUserData());
			isUpdated = offlineCorpUserDao.updateCorpUserAccMapDetails(corpData.getCorpUserData());

			if (isUpdated) {
				response.setResponseMessage("Records Updated Successfully");
				response.setResponseCode("200");
			} else {
				response.setResponseMessage("Request Rejected");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateAddCorpMasterUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateAddCorpMasterUser(@RequestBody CorpDataBean corpData,
			HttpServletRequest httpRequest) {
		logger.info("updateAddCorpMasterUser service calling.........");
		logger.info("corpData Request.............." + corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		ResponseMessageBean resp = new ResponseMessageBean();
		logger.info("User Request....." + corpData.getCorpUserMasterData());
		try {

			CorpCompanyDataBean corpCompData = new CorpCompanyDataBean();
			corpCompData.setId(corpData.getCorpCompId());
			List<Integer> statusList = new ArrayList<>();
			statusList.add(3);
			statusList.add(0);
			statusList.add(35);
//			statusList.add(8);
			corpCompData.setStatusList(statusList);
			List<CorpCompanyDataBean> corpCompanyDataBeanList = offlineCorpUserDao
					.getOfflineCorpCompDataById(corpCompData);
			String approvalLevel = corpCompanyDataBeanList.get(0).getApprovalLevel();

			List<CorpUserEntity> addCorpUserList = new ArrayList<>();
			List<CorpUserEntity> updateCorpUserList = new ArrayList<>();
			Map<String, BigDecimal> mobileMap = new HashMap<>();
			Map<String, BigDecimal> emailMap = new HashMap<>();
			List<String> uiUserNameList = new ArrayList<>();
			for (CorpUserEntity corpUser : corpData.getCorpUserMasterData()) {
				corpUser.setCorp_comp_id(corpData.getCorpCompId());
				resp = offlineCorpUserDao.isCompanyUserExist(corpUser);
				if (resp.getResponseCode().equalsIgnoreCase("500")) {
					corpUser.setId(new BigDecimal(resp.getResult().toString()));
					updateCorpUserList.add(corpUser);
				} else {
					addCorpUserList.add(corpUser);
				}

				logger.info("corpUser.getOgstatus() " + corpUser.getOgstatus());

				if (!ObjectUtils.isEmpty(mobileMap) || !ObjectUtils.isEmpty(emailMap)) {
					if (mobileMap.containsKey(corpUser.getPersonal_Phone())
							&& emailMap.containsKey(corpUser.getEmail_id())
							&& corpUser.getOgstatus().equals(mobileMap.get(corpUser.getPersonal_Phone()))
							&& corpUser.getOgstatus().equals(emailMap.get(corpUser.getEmail_id()))) {
						response.setResponseMessage("Duplicate Mobile Number And Email Is Not Allowed");
						response.setResponseCode("202");
						return new ResponseEntity<>(response, HttpStatus.OK);

					} else if (mobileMap.containsKey(corpUser.getPersonal_Phone())
							&& corpUser.getOgstatus().equals(mobileMap.get(corpUser.getPersonal_Phone()))) {
						response.setResponseMessage("Duplicate Mobile Number Is Not Allowed");
						response.setResponseCode("202");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} else if (emailMap.containsKey(corpUser.getEmail_id())
							&& corpUser.getOgstatus().equals(emailMap.get(corpUser.getEmail_id()))) {
						response.setResponseMessage("Duplicate Email Is Not Allowed");
						response.setResponseCode("202");
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
				}
				mobileMap.put(corpUser.getPersonal_Phone(), corpUser.getOgstatus());
				emailMap.put(corpUser.getEmail_id(), corpUser.getOgstatus());
				if (approvalLevel.equals("S") && !ObjectUtils.isEmpty(corpUser.getOgstatus())
						&& !BigDecimal.valueOf(102).equals(corpUser.getOgstatus())) {
					uiUserNameList.add(corpUser.getUser_name());
				}

			}

			if (!ObjectUtils.isEmpty(corpCompanyDataBeanList)) {
				if (approvalLevel.equals("S")) {
					CorpUserEntityBean corpEntityBean = new CorpUserEntityBean();
					corpEntityBean.setCorp_comp_id(corpData.getCorpCompId());
					corpEntityBean.setStatusList(statusList);
					logger.info("Single User of Corporate Id: " + corpData.getCorpCompId() + " and statusid: "
							+ statusList);
					List<CorpUserEntityBean> corpUserEntityList = offlineCorpUserDao
							.getAllCorpUsersByCompIdBeanStatus(corpEntityBean, false);
					logger.info("corpUser of Corporate Id: " + corpData.getCorpCompId() + " and user: "
							+ corpUserEntityList.toString());
					Set<String> userList = new HashSet<>();
					if (!ObjectUtils.isEmpty(corpUserEntityList)) {
						for (CorpUserEntityBean corpUser : corpUserEntityList) {
							if (!BigDecimal.valueOf(102).equals(corpUser.getOgstatus())
									&& !BigDecimal.valueOf(10).equals(corpUser.getStatusid())) {
								userList.add(EncryptorDecryptor.decryptData(corpUser.getUser_name()));
								if (!ObjectUtils.isEmpty(uiUserNameList) && !uiUserNameList
										.contains(EncryptorDecryptor.decryptData(corpUser.getUser_name()))) {
									String mobileNo = EncryptorDecryptor.decryptData(corpUser.getPersonal_Phone());
									String email = EncryptorDecryptor.decryptData(corpUser.getEmail_id());

									if (mobileMap.containsKey(mobileNo) && emailMap.containsKey(email)) {
										response.setResponseMessage("Duplicate Mobile Number And Email Is Not Allowed");
										response.setResponseCode("202");
										return new ResponseEntity<>(response, HttpStatus.OK);

									} else if (mobileMap.containsKey(mobileNo)) {
										response.setResponseMessage("Duplicate Mobile Number Is Not Allowed");
										response.setResponseCode("202");
										return new ResponseEntity<>(response, HttpStatus.OK);
									} else if (emailMap.containsKey(email)) {
										response.setResponseMessage("Duplicate Email Is Not Allowed");
										response.setResponseCode("202");
										return new ResponseEntity<>(response, HttpStatus.OK);
									}
								}

							}
						}
						if (userList.size() > 0) {
							logger.info("Remove all user: " + userList.toString() + " from userlst: " + uiUserNameList);
							uiUserNameList.removeAll(userList);
						}

						if (userList.size() > 1 || userList.size() == 0 && uiUserNameList.size() > 1
								|| userList.size() == 1 && uiUserNameList.size() == 1) {
							response.setResponseCode("202");
							response.setResponseMessage(
									"User already exist, additional user cannot be allowed through single user hierarchy");
							response.setResult(1);
							return new ResponseEntity<>(response, HttpStatus.OK);
						} else {
							logger.info("1: No Record Found for user in corporate: " + corpData.getCorpCompId());
						}

					} else {
						logger.info("2: No Record Found for user in corporate: " + corpData.getCorpCompId());
					}
				}
			} else {
				logger.info("3: No Record Found for user in corporate: " + corpData.getCorpCompId());
			}
			CorpDataBean corpUserData = new CorpDataBean();
			corpUserData.setCreatedByUpdatedBy(Utils.getUpdatedBy(httpRequest));
			corpUserData.setCorpCompId(corpData.getCorpCompId());
			corpUserData.setUserTypes(corpData.getUserTypes());
			corpUserData.setAdminTypes(corpData.getAdminTypes());
			if (!ObjectUtils.isEmpty(addCorpUserList) && addCorpUserList.size() > 0) {
				corpUserData.setCorpUserMasterData(addCorpUserList);
				corpUserData.setBranchCode(corpData.getBranchCode());
				response = offlineCorpUserDao.saveOfflineCorpUserMasterData(corpUserData);
			}
			if (!ObjectUtils.isEmpty(updateCorpUserList) && updateCorpUserList.size() > 0) {
				corpUserData.setCorpUserMasterData(updateCorpUserList);
				offlineCorpUserDao.updateAddCorpMasterUser(corpUserData, corpData.getBranchCode());
			}
			if (!ObjectUtils.isEmpty(response.getResult()) && (boolean) response.getResult()) {
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			if (!corpData.getCorpUserMasterData().get(0).getStatusid().equals(BigDecimal.valueOf(8))) {
				Map<String, Object> tempStatus = offlineCorpUserDao.validateHierarchy(
						BigDecimal.valueOf(corpData.getCorpUserMasterData().get(0).getCorp_comp_id().intValue()));
				if (!ObjectUtils.isEmpty(tempStatus)) {
					Integer statusCode = (Integer) tempStatus.get("statusCode");
					if (statusCode != 0) {
						String statusMsg = tempStatus.get("statusMsg").toString();
						response.setResponseCode("202");
						response.setResponseMessage(statusMsg);
					} else {
						if (offlineCorpUserDao.isCorporateWithTransactionRights(
								corpData.getCorpUserMasterData().get(0).getCorp_comp_id().intValue())) {
							response.setResponseMessage(
									"Users cannot have transaction rights with Corporate limit of 0");
							response.setResponseCode("202");
							return new ResponseEntity<>(response, HttpStatus.OK);
						}
						if (offlineCorpUserDao.isUserLimitMoreThanCorporateLimit(
								corpData.getCorpUserMasterData().get(0).getCorp_comp_id().intValue())) {
							response.setResponseMessage("User transaction limit cannot be more than Corporate limit.");
							response.setResponseCode("202");
							return new ResponseEntity<>(response, HttpStatus.OK);
						} else {
							response.setResponseMessage("Request Saved Successfully");
							response.setResponseCode("200");
							return new ResponseEntity<>(response, HttpStatus.OK);
						}
					}
				}
			} else {
				response.setResponseMessage("Request Saved Successfully");
				response.setResponseCode("200");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setResponseMessage("Unable to process request, Please contact Adminstrator");
			response.setResponseCode("202");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserMenuListByCorpUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getUserMenuListByCorpUserId(
			@RequestBody CorpUserMenuMapEntity corpUserMenuData) {
		logger.info("getUserMenuListByCorpUserId service calling.........");
		logger.info("corpUserMenuData Request.............." + corpUserMenuData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpUserMenuMapBean> corpCompData = offlineCorpUserDao.getUserMenuListByCorpUserId(corpUserMenuData);
			if (!ObjectUtils.isEmpty(corpCompData)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserAccountListByCorpUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getUserAccountListByCorpUserId(
			@RequestBody CorpUserAccMapEntity corpUserAccData) {
		logger.info("getUserAccountListByCorpUserId service calling.........");
		logger.info("corpUserAccData Request.............." + corpUserAccData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpUserAccMapBean> corpCompData = offlineCorpUserDao.getUserAccountListByCorpUserId(corpUserAccData);

			if (!ObjectUtils.isEmpty(corpCompData)) {
				for (CorpUserAccMapBean corp : corpCompData) {
					corp.setAccountNo(EncryptorDecryptor.decryptData(corp.getAccountNo()));
				}
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpCompData);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpUserById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpUserById(@RequestBody CorpUserEntity corpUserData) {
		logger.info("getCorpUserById service calling.........");
		logger.info("corpUserData Request.............." + corpUserData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<CorpUserEntity> corpUserDataList = offlineCorpUserDao
					.getCorpUserById(corpUserData.getId().toBigInteger());
			if (!ObjectUtils.isEmpty(corpUserDataList)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpUserDataList);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteCorpUserData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> deleteCorpUserData(@RequestBody CorpUserEntity corpData) {
		logger.info("deleteCorpUserData service calling.........");
		logger.info("corpData Request.............." + corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isUpdate = false;
		try {
			isUpdate = corpUserService.deleteCorpUserData(corpData);

			if (isUpdate) {
				response.setResponseMessage("Details Updated Successfully");
				response.setResponseCode("200");

			} else {
				response.setResponseMessage("Error While Updating Company Details");
				response.setResponseCode("500");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/AddCorpMasData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCorpMasData(@RequestBody CorpDataBean corpData,
			HttpServletRequest httpRequest) {
		ResponseMessageBean response = new ResponseMessageBean();
		logger.info("UserActivity :: create new Corporate : userId : " + Utils.getUserId(httpRequest));
		logger.info(
				"userId : " + Utils.getUserId(httpRequest) + ", create company Request Data.." + corpData.toString());

		corpData.setCreatedByUpdatedBy(Utils.getUpdatedBy(httpRequest));
		try {
			response = offlineCorpUserDao.addCorpMasData(corpData);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Exception occured: AddcopMasData: " + e);
			response.setResponseCode("202");
			response.setResponseMessage("Error occured, please contact adminstrator");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getDataByCif", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getDataByCif(@RequestBody VerifyCifPara verifyCifPara) {
		logger.info("getDataByCif service calling.........");
		logger.info("corpData Request.............." + verifyCifPara.toString());
		verifyCifPara.setUserID(verifyCifPara.getUserId());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isCifExist = corpUserService.getCompanyDetailsByCif(verifyCifPara.getCorpCompId());
		if (isCifExist) {
			response.setResponseCode("202");
			response.setResponseMessage("CIF already registered");
			return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
		}

		String str = corpUserService.getDataByCif(verifyCifPara);

		JSONObject json = new JSONObject(str);
		JSONObject responseJson = (JSONObject) json.get("responseParameter");
		String opStatus = (String) responseJson.get("opstatus");
		String branchCode = "";
		if (opStatus.equals("00")) {
			if (!responseJson.isNull("branchCode")) {
				branchCode = (String) responseJson.get("branchCode");
			}
		} else {
			String result = (String) responseJson.get("Result");
			response.setResponseCode("202");
			response.setResponseMessage(result);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		List<User> user = userRepository.findByuserid(verifyCifPara.getUserId());
		logger.info("User Branch Code: "+user.get(0).getBranchCode());
		logger.info("CIF Branch Code: "+branchCode);
		if (branchCode.equals(user.get(0).getBranchCode())) {
			logger.info("branch Verified");
			response.setResult(str);
			response.setResponseCode("200");
		} else {
			response.setResponseCode("202");
			response.setResponseMessage("Entered CIF doesn't belong to this Branch");
		}
		logger.info("Response: " + str);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/checkCorpIdExist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> checkCorpIdExist(
			@RequestBody CorpCompanyMasterEntity companyMasterEntity) {
		logger.info("Checker corp Id is exist.......");
		logger.info("companyMasterEntity Request.............." + companyMasterEntity.toString());

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			if (companyMasterEntity.getCompanyCode().length() < 8) {
				res.setResponseCode("202");
				res.setResponseMessage("Usage of keywords restricted.(ex: Delete, Update, Select)");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
			res = corpUserService.checkCorpIdExist(companyMasterEntity);
			if (res.getResponseCode() == "200") {
				res = corpUserService.checkCorpIdInDup(companyMasterEntity);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	// added by jeetu

	@RequestMapping(value = "/getUserDetailsByCorpCompId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getUserDetailsByCorpCompId(@RequestBody CorpUserEntity corpUserData) {
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			logger.info("getUserDetails service started..........");
			logger.info("corpUserData.........." + corpUserData);
			List<CorpUserEntity> corpUserDataList = offlineCorpUserDao.getUserDetailsByCorpCompId(corpUserData);

			for (CorpUserEntity corp : corpUserDataList) {
				corp.setUser_name(EncryptorDecryptor.decryptData(corp.getUser_name()));
				corp.setEmail_id(EncryptorDecryptor.decryptData(corp.getEmail_id()));
				corp.setPersonal_Phone(EncryptorDecryptor.decryptData(corp.getPersonal_Phone()));
				corp.setPancardNumber(EncryptorDecryptor.decryptData(corp.getPancardNumber()));
				corp.setCorp_comp_id(corpUserData.getCorp_comp_id());
				corp.setUser_disp_name(EncryptorDecryptor.decryptData(corp.getUser_disp_name()));
			}

			if (!ObjectUtils.isEmpty(corpUserDataList)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(corpUserDataList);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/sendUserKitOnEmail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> sendUserKitOnEmail(@RequestBody CorpDataBean corpDataBean,
			HttpServletRequest httpRequest) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			corpDataBean.setCreatedByUpdatedBy(Utils.getUpdatedBy(httpRequest));
			logger.info("Resend Email/SMS service started..........Corp");
			logger.info("corpDataBean.........." + corpDataBean.toString());
			res = corpUserService.resendUserKitOnEmail(corpDataBean);

//			res.setResponseMessage("Email/Sms send successfully");
//			res.setResponseCode("200");
			logger.info("Resend Email/SMS service ended..........");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/blockUnblock", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> blockUnblock(@RequestBody @Valid CorpUserBean corpUserBean) {
		logger.info("start block unblock service started............");
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			response = (ResponseMessageBean) offlineCorpUserDao.blockUnblock(corpUserBean);

//			if (response.getResponseCode().equals("202")) {
//				response.setResponseMessage("Already Updated");
//			}
			logger.info("start block unblock service ended............");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Vishal
	@RequestMapping(value = "/updateOfflineCorpUserMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateOfflineCorpUserMasterDataTemp(@RequestBody CorpDataBean corpData) {
		logger.info("updateOfflineCorpUserMasterData service.............");
		logger.info("corpData.........." + corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			corpData.getCorpUserMasterData().get(0).setCorp_comp_id(corpData.getCorpCompId());
			if (corpData.getUserTypes().equals("S")) {
				response = offlineCorpUserDao.saveOfflineCorpSingleUserMasterDatatosave(corpData);
			} else {
				response = offlineCorpUserDao.updateOfflineCorpUserMasterDataTemp(corpData);
			}

			if (response.getResponseCode() == "200") {
				response.setResponseMessage("Saved Successfully");
				response.setResponseCode("200");
			} else {
				response.setResponseMessage("Request Rejected");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteAndMoveToTmpByCorpcompid", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> deleteAndMoveToTmpByCorpcompid(@RequestBody CorpDataBean corpData) {
		logger.info("deleteAndMoveToTmpByCorpcompid service.............");
		logger.info("corpData.........." + corpData.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			offlineCorpUserDao.deleteAndMoveToTmpByCorpcompid(corpData);
			response.setResponseCode("200");
			response.setResponseMessage("Deleted Successfully");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/moveAndDeleteByCorpCompIdAndUser", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> moveAndDeleteByCorpCompIdAndUser(
			@RequestBody CorpDataBean corporateUserBean) {
		logger.info("moveAndDeleteByCorpCompIdAndUser service.............");
		logger.info("corpData.........." + corporateUserBean.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		boolean isDeleted = false;
		logger.info("Move and Delete record service start..................");
		try {
			List<String> userNameList = new ArrayList<>();
			logger.info("Move and Delete record service request.................." + corporateUserBean.getCorpCompId()
					+ "|" + corporateUserBean.getCorpUserMasterData().get(0).getUser_name());
			corporateUserBean.getCorpUserMasterData()
					.forEach(s -> userNameList.add(EncryptorDecryptor.encryptData(s.getUser_name())));

			List<CorpUserEntity> corpUserEntity = offlineCorpUserDao.getAllCorpUsersByUserName(userNameList,
					corporateUserBean.getCorpCompId());

			logger.info("Start record movement....");
			for (CorpUserEntity coruCorpUserEntity : corpUserEntity) {

				// TODO uncomment code for enable delete functionality
				isDeleted = offlineCorpUserDao.moveAndDeleteDataFromTmpToMasterTableForUser(
						corporateUserBean.getCorpCompId(), coruCorpUserEntity.getId(),
						coruCorpUserEntity.getOgstatus());
//				isDeleted = offlineCorpUserDao.moveAndDeleteDataFromTmpToMasterTableForUser(
//						corporateUserBean.getCorpCompId(), coruCorpUserEntity.getId());
			}
			logger.info("User Deleted: " + isDeleted);
			if (isDeleted) {
				response.setResponseCode("200");
				response.setResponseMessage("User Deleted Successfully");
			} else {
				response.setResponseCode("202");
				response.setResponseMessage("User Deleted Successfully");
			}
			logger.info("Move and Delete record service end..................");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfflineCorpCompDataByBranchCodeAndStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfflineCorpCompDataByBranchCodeAndStatus(
			@RequestBody CorpCompanyMasterEntity corpCompReq) {
		logger.info("getOfflineCorpCompDataByBranchCodeAndStatus service.............");
		logger.info("corpData.........." + corpCompReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<Integer> statusList = new ArrayList<>();
			statusList.add(8);
			corpCompReq.setStatusList(statusList);
			List<String> cifList = new ArrayList<>();
			cifList = corpUserService.getOfflineCorpCompDataByBranchCodeAndStatus(corpCompReq);
			if (!ObjectUtils.isEmpty(cifList)) {
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(cifList);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfflineCorpCompDataDupByBranchCodeAndStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfflineCorpCompDataDupByBranchCodeAndStatus(
			@RequestBody CorpCompanyMasterDupEntity corpCompReq) {
		logger.info("getOfflineCorpCompDataDupByBranchCodeAndStatus service.............");
		logger.info("CorpCompanyMasterDup.........." + corpCompReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<Integer> statusList = new ArrayList<>();
			statusList.add(8);
			statusList.add(3);
			corpCompReq.setStatusList(statusList);
			List<String> cifList = new ArrayList<>();
			List<CorpCompanyMasterDupEntity> corpCompMasterData = corpUserService
					.getOfflineCorpCompDataDupByBranchCodeAndStatus(corpCompReq);
			if (!ObjectUtils.isEmpty(corpCompMasterData)) {
				for (CorpCompanyMasterDupEntity compObg : corpCompMasterData) {
					if (compObg.getCif() != null && compObg.getCif().contains("=")) {
						cifList.add(EncryptorDecryptor.decryptData(compObg.getCif()));
					}
				}
				response.setResponseMessage("Success");
				response.setResponseCode("200");
				response.setResult(cifList);
			} else {
				response.setResponseMessage("No Records Found");
				response.setResponseCode("202");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveOfflineCorpCompAndCorpUserData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> saveOfflineCorpCompAndCorpUserData(
			@RequestBody CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean, HttpServletRequest httpRequest) {
		logger.info("getOfflineCorpCompDataDupByBranchCodeAndStatus service.............");
		logger.info("CorpCompanyMasterDup.........." + corpCompanyCorpUserDupBean.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<User> user = userRepository.findByuserid(corpCompanyCorpUserDupBean.getUserLoginId());
			corpCompanyCorpUserDupBean.setMakerId(user.get(0).getId().intValue());
			corpCompanyCorpUserDupBean.setCreatedBy(Utils.getUpdatedBy(httpRequest));

			CorpUserEntity corpUserEntity = new CorpUserEntity();
			corpUserEntity.setCorp_comp_id(corpCompanyCorpUserDupBean.getId());
			List<Integer> statusList = new ArrayList<>();
			statusList.add(8);
			corpUserEntity.setStatusList(statusList);
			List<CorpUserEntity> corpUserEntities = corpUserService.getAllCorpUsersByCompId(corpUserEntity, true);

			corpUserService.saveOfflineCorpCompAndCorpUserData(corpCompanyCorpUserDupBean, corpUserEntities);
			response.setResponseCode("200");
			response.setResponseMessage("Request Successfully Send To Branch Checker For Verification");
		} catch (Exception e) {
			logger.error("Exception" + e);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateOfflineCorpCompAndCorpUserData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateOfflineCorpCompAndCorpUserData(
			@RequestBody CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean, HttpServletRequest httpRequest) {
		logger.info("updateOfflineCorpCompAndCorpUserData service.............");
		logger.info("CorpCompanyMasterDup.........." + corpCompanyCorpUserDupBean.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		try {
			List<User> user = userRepository.findByuserid(corpCompanyCorpUserDupBean.getUserLoginId());
			corpCompanyCorpUserDupBean.setCheckerId(user.get(0).getId().intValue());
			corpCompanyCorpUserDupBean.setUpdatedBy(Utils.getUpdatedBy(httpRequest));
			corpUserService.updateCorpCompanyAndUserDetails(corpCompanyCorpUserDupBean);
			response.setResponseCode("200");
			response.setResponseMessage("Request Approved Successfully.");
		} catch (Exception e) {
			logger.error("Exception" + e);
			response.setResponseMessage("Invalid Request");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfflineCorpCompAndCorpUserData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfflineCorpCompAndCorpUserData(
			@RequestBody CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {
		logger.info("getOfflineCorpCompAndCorpUserData service.............");
		logger.info("corpCompanyCorpUserDupBean.........." + corpCompanyCorpUserDupBean.toString());
		ResponseMessageBean response = new ResponseMessageBean();

		try {

			List<CorpCompanyCorpUserDupBean> corpCompanyAndCorpUserDup = corpUserService
					.getOfflineCorpCompAndCorpUserDataDup(corpCompanyCorpUserDupBean);
			List<CorpCompanyCorpUserDupBean> corpCompanyAndCorpUser = null;
			if (ObjectUtils.isEmpty(corpCompanyAndCorpUserDup)) {
				corpCompanyAndCorpUser = corpUserService.getOfflineCorpCompAndCorpUserData(corpCompanyCorpUserDupBean);

				if (!ObjectUtils.isEmpty(corpCompanyAndCorpUser)) {
					for (CorpCompanyCorpUserDupBean compObg : corpCompanyAndCorpUser) {
						compObg.setCompanyName(EncryptorDecryptor.decryptData(compObg.getCompanyName()));
						compObg.setCompanyCode(EncryptorDecryptor.decryptData(compObg.getCompanyCode()));
						compObg.setPancardNo(EncryptorDecryptor.decryptData(compObg.getPancardNo()));
						compObg.setUser_disp_name(EncryptorDecryptor.decryptData(compObg.getUser_disp_name()));
						compObg.setUser_name(EncryptorDecryptor.decryptData(compObg.getUser_name()));
						compObg.setEmail_id(EncryptorDecryptor.decryptData(compObg.getEmail_id()));
						compObg.setPersonal_Phone(EncryptorDecryptor.decryptData(compObg.getPersonal_Phone()));
						compObg.setWork_phone(EncryptorDecryptor.decryptData(compObg.getWork_phone()));
						if (!ObjectUtils.isEmpty(compObg.getPassport())) {
							compObg.setPassport(EncryptorDecryptor.decryptData(compObg.getPassport()));
						}

						compObg.setAadharCardNo(EncryptorDecryptor.decryptData(compObg.getAadharCardNo()));
						compObg.setPancardNumber(EncryptorDecryptor.decryptData(compObg.getPancardNumber()));
						compObg.setDob(EncryptorDecryptor.decryptData(compObg.getDob()));
						compObg.setRemark(compObg.getRemark());
						response.setResponseCode("200");
						response.setResult(compObg);
					}

				}
			} else {
				for (CorpCompanyCorpUserDupBean compObg : corpCompanyAndCorpUserDup) {
					compObg.setCompanyName(EncryptorDecryptor.decryptData(compObg.getCompanyName()));
					compObg.setCompanyCode(EncryptorDecryptor.decryptData(compObg.getCompanyCode()));
					compObg.setPancardNo(EncryptorDecryptor.decryptData(compObg.getPancardNo()));
					compObg.setUser_disp_name(EncryptorDecryptor.decryptData(compObg.getUser_disp_name()));
					compObg.setUser_name(EncryptorDecryptor.decryptData(compObg.getUser_name()));
					compObg.setEmail_id(EncryptorDecryptor.decryptData(compObg.getEmail_id()));
					compObg.setPersonal_Phone(EncryptorDecryptor.decryptData(compObg.getPersonal_Phone()));
					compObg.setWork_phone(EncryptorDecryptor.decryptData(compObg.getWork_phone()));
					if (!ObjectUtils.isEmpty(compObg.getPassport())) {
						compObg.setPassport(EncryptorDecryptor.decryptData(compObg.getPassport()));
					}

					compObg.setAadharCardNo(EncryptorDecryptor.decryptData(compObg.getAadharCardNo()));
					compObg.setPancardNumber(EncryptorDecryptor.decryptData(compObg.getPancardNumber()));
					compObg.setDob(EncryptorDecryptor.decryptData(compObg.getDob()));
					compObg.setRemark(compObg.getRemark());
					response.setResponseCode("200");
					response.setResult(compObg);
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getOfflineCorpCompAndCorpUserDataDup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getOfflineCorpCompAndCorpUserDataDup(
			@RequestBody CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean) {

		logger.info("getOfflineCorpCompAndCorpUserDataDup service.............");
		logger.info("corpCompanyCorpUserDupBean.........." + corpCompanyCorpUserDupBean.toString());

		ResponseMessageBean response = new ResponseMessageBean();

		if (ObjectUtils.isEmpty(corpCompanyCorpUserDupBean.getUser_name())) {
			response.setResponseCode("202");
			response.setResponseMessage("Please select UserName");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		try {
			List<CorpCompanyCorpUserDupBean> corpCompanyAndCorpUser = corpUserService
					.getOfflineCorpCompAndCorpUserDataDup(corpCompanyCorpUserDupBean);

			if (!ObjectUtils.isEmpty(corpCompanyAndCorpUser)) {
				for (CorpCompanyCorpUserDupBean compObg : corpCompanyAndCorpUser) {
					compObg.setCompanyName(EncryptorDecryptor.decryptData(compObg.getCompanyName()));
					compObg.setCompanyCode(EncryptorDecryptor.decryptData(compObg.getCompanyCode()));
					compObg.setPancardNo(EncryptorDecryptor.decryptData(compObg.getPancardNo()));
					compObg.setUser_disp_name(EncryptorDecryptor.decryptData(compObg.getUser_disp_name()));
					compObg.setUser_name(EncryptorDecryptor.decryptData(compObg.getUser_name()));
					compObg.setEmail_id(EncryptorDecryptor.decryptData(compObg.getEmail_id()));
					compObg.setPersonal_Phone(EncryptorDecryptor.decryptData(compObg.getPersonal_Phone()));
					compObg.setWork_phone(EncryptorDecryptor.decryptData(compObg.getWork_phone()));
					if (!ObjectUtils.isEmpty(compObg.getPassport())) {
						compObg.setPassport(EncryptorDecryptor.decryptData(compObg.getPassport()));
					}
					compObg.setAadharCardNo(EncryptorDecryptor.decryptData(compObg.getAadharCardNo()));
					compObg.setPancardNumber(EncryptorDecryptor.decryptData(compObg.getPancardNumber()));
					compObg.setDob(EncryptorDecryptor.decryptData(compObg.getDob()));

					response.setResponseCode("200");
					response.setResult(compObg);
				}

			} else {
				response.setResponseCode("202");
				response.setResponseMessage("No Record Found.");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = "/checkCorporateIDExist", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> checkCifExist(@RequestBody CorpCompanyMasterEntity companyMasterEntity) {
		logger.info("Checker corp Id is exist.......");
		logger.info("Company request.........." + companyMasterEntity.toString());
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			res = corpUserService.checkCorpIdExist(companyMasterEntity);
			if (res.getResponseCode().equals("200")) {
				res = corpUserService.checkCorpIdInDup(companyMasterEntity);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserDupListByCif", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getUserDupListByCif(
			@RequestBody CorpCompanyMasterDupEntity corpCompReq) {
		logger.info("getUserDupListByCif service.......");
		logger.info("Company request.........." + corpCompReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		if (ObjectUtils.isEmpty(corpCompReq.getCif())) {
			response.setResponseCode("202");
			response.setResponseMessage("Please select cif");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		List<CorpCompanyMasterDupEntity> corpCompanyList = corpUserService.getOfflineCorpCompDupByCif(corpCompReq);
		CorpUserDupEntity corpUserDupEntity = new CorpUserDupEntity();
		corpUserDupEntity.setCorp_comp_id(corpCompanyList.get(0).getId());
		List<Integer> statusList = new ArrayList<>();
		statusList.add(8);
		corpUserDupEntity.setStatusList(statusList);
		List<CorpUserDupEntity> corpUserDupEntities = corpUserService.getAllCorpUsersDupByCompId(corpUserDupEntity,
				true, true);
		List<UserRoleNameBean> userRoleNameList = new ArrayList<>();

		for (CorpUserDupEntity corpDupEntity : corpUserDupEntities) {
			if (corpDupEntity.isCheckerValidated() == 0) {
				UserRoleNameBean userRoleName = new UserRoleNameBean();
				userRoleName.setUserName(corpDupEntity.getUser_name());
				userRoleName.setUserRole(corpDupEntity.getCorpRoleName());
				userRoleNameList.add(userRoleName);
			}
		}
		response.setResponseCode("200");
		response.setResult(userRoleNameList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserListByCif", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getUserListByCif(@RequestBody CorpCompanyMasterEntity corpCompReq) {
		logger.info("getUserListByCif service.......");
		logger.info("Company request.........." + corpCompReq.toString());
		ResponseMessageBean response = new ResponseMessageBean();
		if (ObjectUtils.isEmpty(corpCompReq.getCif())) {
			response.setResponseCode("202");
			response.setResponseMessage("Please select cif");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		List<CorpCompanyMasterEntity> corpCompanyList = corpUserService.getOfflineCorpCompDatByCif(corpCompReq);
		CorpUserEntity corpUserEntity = new CorpUserEntity();
		corpUserEntity.setCorp_comp_id(corpCompanyList.get(0).getId());
		List<Integer> statusList = new ArrayList<>();
		statusList.add(8);
		corpUserEntity.setStatusList(statusList);
		List<CorpUserEntity> corpUserEntities = corpUserService.getAllCorpUsersByCompId(corpUserEntity, true);
		response.setResponseCode("200");
		CorpUserDupEntity corpUserDup = new CorpUserDupEntity();
		corpUserDup.setCorp_comp_id(corpCompanyList.get(0).getId());
		corpUserDup.setStatusList(statusList);
		List<CorpUserDupEntity> corpUserDupEntities = corpUserService.getAllCorpUsersDupByCompId(corpUserDup, true,
				true);
		List<String> corpUserList = new ArrayList<>();
		corpUserDupEntities.forEach(s -> corpUserList.add(s.getUser_name()));

		List<UserRoleNameBean> userRoleNameList = new ArrayList<>();
		corpUserEntities.forEach(s -> {
			if (!corpUserList.contains(s.getUser_name())) {
				UserRoleNameBean userRoleName = new UserRoleNameBean();
				userRoleName.setUserName(s.getUser_name());
				userRoleName.setUserRole(s.getCorpRoleName());
				userRoleNameList.add(userRoleName);
			}
		});

		response.setResult(userRoleNameList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectDupUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> rejectDupUser(
			@RequestBody CorpCompanyCorpUserDupBean corpCompanyCorpUserDupBean, HttpServletRequest httpRequest) {
		ResponseMessageBean response = new ResponseMessageBean();

		List<User> user = userRepository.findByuserid(corpCompanyCorpUserDupBean.getUserLoginId());
		corpCompanyCorpUserDupBean.setCheckerId(user.get(0).getId().intValue());
		corpCompanyCorpUserDupBean.setUpdatedBy(Utils.getUpdatedBy(httpRequest));
		corpUserService.rejectDupUser(corpCompanyCorpUserDupBean);

		response.setResponseCode("200");
		response.setResponseMessage("User rejected successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/invalidRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> invalidRequest() {
		ResponseMessageBean response = new ResponseMessageBean();
		response.setResponseCode("202");
		response.setResponseMessage("Usage of keywords restricted.(ex: Delete, Update, Select)");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
