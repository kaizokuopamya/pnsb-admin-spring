package com.itl.pns.controller;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.dao.CompCategoryDao;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.entity.CategoryCompanyMasterEntity;
import com.itl.pns.entity.CategoryCompanyProductEntity;
import com.itl.pns.entity.CategoryMasterEntity;
import com.itl.pns.service.CompCategoryService;
import com.itl.pns.util.AdminWorkFlowReqUtility;

@RestController
@RequestMapping("compCategory")
public class CompCategoryController {

	private static final Logger logger = LogManager.getLogger(CompCategoryController.class);

	@Autowired
	CompCategoryService compCategoryService;

	@Autowired
	AdminWorkFlowReqUtility adminWorkFlowReqUtility;

	@Autowired
	CompCategoryDao compCategoryDao;

	@RequestMapping(value = "/addCompCategoryMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addCompCategoryMasterData(
			@RequestBody CategoryMasterEntity categoryMasterData) {
		logger.info("In Comp Category Controller -> addCompCategoryMasterData()");
		ResponseMessageBean response = new ResponseMessageBean();
		categoryMasterData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categoryMasterData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("ADD");
		categoryMasterData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;

		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				responsecode = compCategoryDao.checkCategoryExist(categoryMasterData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = compCategoryService.addCompCategoryMasterData(categoryMasterData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Category Details Saved Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateCompCategoryMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateCompCategoryMasterData(
			@RequestBody CategoryMasterEntity categoryMasterData) {
		logger.info("In Comp Category Controller -> updateCompCategoryMasterData()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		categoryMasterData.setUpdatedby(categoryMasterData.getUser_ID());
		categoryMasterData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categoryMasterData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categoryMasterData.getRole_ID().intValue());
		categoryMasterData.setRoleName(roleName);
		categoryMasterData.setAction("EDIT");
		categoryMasterData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categoryMasterData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categoryMasterData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				responsecode = compCategoryDao.checkUpdateCategoryExist(categoryMasterData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = compCategoryService.updateCompCategoryMasterData(categoryMasterData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Category Details Updated Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("500");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCompCategoriesMasterById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCompCategoriesMasterById(
			@RequestBody CategoryMasterEntity requestBean) {
		logger.info("In Category Controller -> getCompCategoriesMasterById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CategoryMasterEntity> categoryMasterData = compCategoryService
					.getCompCategoriesMasterById(requestBean.getId().toBigInteger());

			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllCategoriesMaster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllCompCategoriesMaster() {
		logger.info("In Category Controller -> getAllCompCategoriesMaster()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CategoryMasterEntity> categoryMasterData = compCategoryService.getAllCompCategoriesMaster();

			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addComapnyMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addComapnyMasterData(
			@RequestBody CategoryCompanyMasterEntity categorcategoryCompData) {
		logger.info("In Comp Category Controller -> addComapnyMasterData()");
		ResponseMessageBean response = new ResponseMessageBean();
		categorcategoryCompData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categorcategoryCompData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categorcategoryCompData.getRole_ID().intValue());
		categorcategoryCompData.setRoleName(roleName);
		categorcategoryCompData.setAction("ADD");
		categorcategoryCompData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categorcategoryCompData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categorcategoryCompData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		try {
			categorcategoryCompData.setCloblogo(
					new javax.sql.rowset.serial.SerialClob(categorcategoryCompData.getLogo().toCharArray()));
		} catch (SerialException e) {
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!ObjectUtils.isEmpty(categorcategoryCompData)) {
				responsecode = compCategoryDao.checkCompExitForCategory(categorcategoryCompData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = compCategoryService.addComapnyMasterData(categorcategoryCompData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Company Details Saved Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateComapnyMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateComapnyMasterData(
			@RequestBody CategoryCompanyMasterEntity categorcategoryCompData) {
		logger.info("In Comp Category Controller -> updateComapnyMasterData()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		categorcategoryCompData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(categorcategoryCompData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(categorcategoryCompData.getRole_ID().intValue());
		categorcategoryCompData.setRoleName(roleName);
		categorcategoryCompData.setAction("EDIT");
		categorcategoryCompData.setStatusName(
				adminWorkFlowReqUtility.getStatusNameByStatusId(categorcategoryCompData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(categorcategoryCompData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			categorcategoryCompData.setCloblogo(
					new javax.sql.rowset.serial.SerialClob(categorcategoryCompData.getLogo().toCharArray()));
		} catch (SerialException e) {
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (!ObjectUtils.isEmpty(categorcategoryCompData)) {
				responsecode = compCategoryDao.checkUpdateCompExitForCategory(categorcategoryCompData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = compCategoryService.updateComapnyMasterData(categorcategoryCompData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Company Details Updated Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("500");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getComapnyMasterDataById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getComapnyMasterDataById(
			@RequestBody CategoryCompanyMasterEntity requestBean) {
		logger.info("In Category Controller -> getComapnyMasterDataById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CategoryCompanyMasterEntity> categoryMasterData = compCategoryService
					.getComapnyMasterDataById(requestBean.getId().toBigInteger());

			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getComapnyMasterDataByCategoryId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getComapnyMasterDataByCategoryId(
			@RequestBody CategoryCompanyMasterEntity requestBean) {
		logger.info("In Category Controller -> getComapnyMasterDataByCategoryId()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CategoryCompanyMasterEntity> categoryMasterData = compCategoryService
					.getComapnyMasterDataByCategoryId(requestBean.getCategoryId().toBigInteger());

			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllComapnyMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllComapnyMasterData() {
		logger.info("In Category Controller -> getAllComapnyMasterData()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CategoryCompanyMasterEntity> categoryMasterData = compCategoryService.getAllComapnyMasterData();

			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/addProductMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> addProductMasterData(
			@RequestBody CategoryCompanyProductEntity productData) {
		logger.info("In Comp Category Controller -> addProductMasterData()");
		ResponseMessageBean response = new ResponseMessageBean();
		productData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(productData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(productData.getRole_ID().intValue());
		productData.setRoleName(roleName);
		productData.setAction("ADD");
		productData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(productData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(productData.getActivityName());
		ResponseMessageBean responsecode = new ResponseMessageBean();
		Boolean res = false;
		try {
			productData.setProductImgClob(
					new javax.sql.rowset.serial.SerialClob(productData.getProductImg().toCharArray()));
		} catch (SerialException e) {
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (!ObjectUtils.isEmpty(productData)) {
				responsecode = compCategoryDao.checkProductExitForCompany(productData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {

					res = compCategoryService.addProductMasterData(productData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Product Details Saved Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Saving Records");
					}
				} else {
					response.setResponseCode("202");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While  Adding Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateProductMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> updateProductMasterData(
			@RequestBody CategoryCompanyProductEntity productData) {
		logger.info("In Comp Category Controller -> updateProductMasterData()");
		ResponseMessageBean response = new ResponseMessageBean();
		Boolean res = false;
		productData.setCreatedByName(
				adminWorkFlowReqUtility.getCreatedByNameByCreatedId(productData.getCreatedby().intValue()));
		String roleName = adminWorkFlowReqUtility.getRoleNameByRoleId(productData.getRole_ID().intValue());
		productData.setRoleName(roleName);
		productData.setAction("EDIT");
		productData
				.setStatusName(adminWorkFlowReqUtility.getStatusNameByStatusId(productData.getStatusId().intValue()));
		List<ActivitySettingMasterEntity> activityMasterData = adminWorkFlowReqUtility
				.isMakerCheckerPresentForReq(productData.getActivityName());
		try {
			productData.setProductImgClob(
					new javax.sql.rowset.serial.SerialClob(productData.getProductImg().toCharArray()));
		} catch (SerialException e) {
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResponseMessageBean responsecode = new ResponseMessageBean();
		try {
			if (!ObjectUtils.isEmpty(productData)) {
				responsecode = compCategoryDao.checkUpdateProductExitForCompany(productData);
				if (responsecode.getResponseCode().equalsIgnoreCase("200")) {
					res = compCategoryService.updateProductMasterData(productData);
					if (res) {
						response.setResponseCode("200");
						if (roleName.equalsIgnoreCase(ApplicationConstants.MAKER)
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getChecker()))
								&& (ApplicationConstants.YESVALUE)
										.equals(Character.toString(activityMasterData.get(0).getMaker()))) {

							response.setResponseMessage("Request Submitted To Admin Checker For Approval");
						} else {
							response.setResponseMessage("Product Details Updated Successfully");
						}

					} else {
						response.setResponseCode("202");
						response.setResponseMessage("Error While Updating Records");
					}
				} else {
					response.setResponseCode("500");
					response.setResponseMessage(responsecode.getResponseMessage());
				}

			}
		} catch (Exception e) {
			logger.info("Exception:", e);
			response.setResponseCode("500");
			response.setResponseMessage("Error While Updating Records!");
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/getProductMasterDataById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getProductMasterDataById(
			@RequestBody CategoryCompanyProductEntity requestBean) {
		logger.info("In Category Controller -> getProductMasterDataById()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CategoryCompanyProductEntity> categoryMasterData = compCategoryService
					.getProductMasterDataById(requestBean.getId().toBigInteger());

			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getAllProductMasterData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getAllProductMasterData() {
		logger.info("In Category Controller -> getAllProductMasterData()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CategoryCompanyProductEntity> categoryMasterData = compCategoryService.getAllProductMasterData();

			if (!ObjectUtils.isEmpty(categoryMasterData)) {
				res.setResponseCode("200");
				res.setResult(categoryMasterData);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}
