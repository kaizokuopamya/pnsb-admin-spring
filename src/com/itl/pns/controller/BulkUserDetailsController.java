package com.itl.pns.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itl.pns.bean.BulkUserUploadBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.BulkUsersCreationEntity;
import com.itl.pns.entity.BulkUsersExcelDetailsEntity;
import com.itl.pns.entity.User;
import com.itl.pns.repository.UserMasterRepository;
import com.itl.pns.service.BulkUsersCreationService;
import com.itl.pns.service.LoginService;
import com.itl.pns.util.Utils;

@RestController
@RequestMapping("bulkUser")
public class BulkUserDetailsController {
	private static final Logger logger = LogManager.getLogger(BulkUserDetailsController.class);

	@Autowired
	BulkUsersCreationService bulkUsersCreationService;
	
	@Autowired
	UserMasterRepository userMasterRepository;


	@RequestMapping(value = "/bulkUsersCreation", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessageBean> createBulkUsersFile(
			@RequestParam("bulkUserDetailsFile") MultipartFile file1, @RequestParam("userId") Integer id1) {
		logger.info("In BulkUserDetails Controller -> createBulkUsersFile() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		BulkUserUploadBean bulkUser = null;
		List<UserDetailsBean> userDetails = null;
		try {
			userDetails = bulkUsersCreationService.useruserDetails(id1);
			String fileName = file1.getOriginalFilename();

			if(file1.getOriginalFilename().endsWith(".xlsx")) {
			bulkUser = bulkUsersCreationService.createBulkUsersFile(file1,id1,userDetails);

			if (null != bulkUser && !bulkUser.getBulkUsersCreation().isEmpty()) {
				res.setResponseCode("200");
				res.setResult(bulkUser.getBulkUsersCreation());
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage(bulkUser.getExcelRemark());
			}
			}else {
				res.setResponseCode("202");
				res.setResponseMessage("Please Upload .xlsx File Format");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getBulkUsersExcelDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBulkUsersExcelDetails(@RequestBody RequestParamBean requestBean) {
		logger.info("In BulkUserDetails Controller -> getBulkUsersExcelDetails() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		List<BulkUsersExcelDetailsEntity> bulkUsersCreation = null;
		try {
			bulkUsersCreation = bulkUsersCreationService.getBulkUsersExcelDetails(requestBean);

			if (null != bulkUsersCreation && !bulkUsersCreation.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(bulkUsersCreation);
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

	@RequestMapping(value = "/getBulkUsersDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBulkUsersDetails(@RequestBody RequestParamBean requestBean) {
		logger.info("In BulkUserDetails Controller -> getBulkUsersDetails() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		List<BulkUsersCreationEntity> bulkUsersCreation = null;
		try {
			bulkUsersCreation = bulkUsersCreationService.getBulkUsersData(requestBean.getId1());
			for (BulkUsersCreationEntity list : bulkUsersCreation) {
				if (null != list.getRole()) {
					if (ApplicationConstants.MAKER_ID == list.getRole().intValue()) {
						list.setRoles("Maker");
					}else if (ApplicationConstants.CHECKER_ID == list.getRole().intValue()) {
						list.setRoles("Checker");
					}else if (ApplicationConstants.ZOMAKER_ID == list.getRole().intValue()) {
						list.setRoles("ZoMaker");
					}else if (ApplicationConstants.ZOCHECKER_ID == list.getRole().intValue()) {
						list.setRoles("ZoChecker");
					}else if (ApplicationConstants.HOMAKER_ID == list.getRole().intValue()) {
						list.setRoles("HoMaker");
					}else if (ApplicationConstants.HOCHECKER_ID == list.getRole().intValue()) {
						list.setRoles("HoChecker");
					}
				}

			}

			if (null != bulkUsersCreation && !bulkUsersCreation.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(bulkUsersCreation);
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

	@RequestMapping(value = "/getBulkUsersValidate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBulkUsersValidate(@RequestBody RequestParamBean requestBean) {
		logger.info("In BulkUserDetails Controller-> getBulkUsersValidate() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		BulkUserUploadBean bulkUsersCreation = new BulkUserUploadBean();
		BulkUserUploadBean bulkUsersCreation1 = new BulkUserUploadBean();
		List<BulkUsersCreationEntity> bulkUsersUp = new ArrayList<>();
		List<BulkUsersCreationEntity> bulkUsersIn = new ArrayList<>();
		List<BulkUsersCreationEntity> bulkUsersCreationB = null;
		List<BulkUsersCreationEntity> bulkUsers = new ArrayList<>();
		List<BigDecimal> listId = new ArrayList<>();
		Integer errorCount = null;
		Integer successCount = null;
		Integer excelId = Integer.valueOf(requestBean.getId2());
		try {
			bulkUsersCreationB = bulkUsersCreationService.getBulkUsersData(requestBean.getId2());
			Integer userId = Integer.valueOf(requestBean.getId1());
			if (!bulkUsersCreationB.isEmpty()) {
				for (BulkUsersCreationEntity data : bulkUsersCreationB) {
					data.setUpdateby(Integer.valueOf(requestBean.getId1()));
					data.setStatus(7);
					listId.add(data.getId());
				}
			}

			Boolean statu = bulkUsersCreationService.getBulkUsers(bulkUsersCreationB);

			bulkUsersUp = bulkUsersCreationService.getBulkUsersDataUp(excelId);

			if (null != bulkUsersUp && !bulkUsersUp.isEmpty()) {
				bulkUsersCreation = bulkUsersCreationService.getBulkUsersValidateUp(bulkUsersUp);
			}

			bulkUsersIn = bulkUsersCreationService.getBulkUsersDataIp(excelId);
			if (null != bulkUsersIn && !bulkUsersIn.isEmpty()) {

				bulkUsersCreation1 = bulkUsersCreationService.getBulkUsersValidateIn(bulkUsersIn);
			}

			if (null != bulkUsersCreation.getBulkUsersCreation()
					&& !bulkUsersCreation.getBulkUsersCreation().isEmpty()) {
				bulkUsers.addAll(bulkUsersCreation.getBulkUsersCreation());
			}

			if (null != bulkUsersCreation1.getBulkUsersCreation()
					&& !bulkUsersCreation1.getBulkUsersCreation().isEmpty()) {
				bulkUsers.addAll(bulkUsersCreation1.getBulkUsersCreation());
			}

			successCount = bulkUsersCreation.getSuccessCount() + bulkUsersCreation1.getSuccessCount();

			Integer makerErrorCount = bulkUsersCreationService.getBulkUsersDataMakerErrorCount(excelId);
			if (makerErrorCount.intValue() != 0) {
				errorCount = bulkUsersCreation.getErrorCount() + bulkUsersCreation1.getErrorCount()
						+ makerErrorCount.intValue();
			} else {
				errorCount = bulkUsersCreation.getErrorCount() + bulkUsersCreation1.getErrorCount();
			}
			Date date = new Date();

			if (null != bulkUsers && !bulkUsers.isEmpty()) {
				Boolean status = bulkUsersCreationService.getBulkUsersDataUpdate(bulkUsers);
				if (status.equals(status)) {
					Integer sflags;
					if (errorCount.intValue() != 0) {
						sflags = 4;
					} else {
						sflags = 9;
					}

					bulkUsersCreationService.getBulkUsersExcelDataUpdate(BigDecimal.valueOf(excelId), date,
							successCount, errorCount, sflags);
				}
			}

			for (BulkUsersCreationEntity list : bulkUsers) {
				if (null != list.getRole()) {
					if (ApplicationConstants.MAKER_ID == list.getRole().intValue()) {
						list.setRoles("Maker");
					}else if (ApplicationConstants.CHECKER_ID == list.getRole().intValue()) {
						list.setRoles("Checker");
					}else if (ApplicationConstants.ZOMAKER_ID == list.getRole().intValue()) {
						list.setRoles("ZoMaker");
					}else if (ApplicationConstants.ZOCHECKER_ID == list.getRole().intValue()) {
						list.setRoles("ZoChecker");
					}else if (ApplicationConstants.HOMAKER_ID == list.getRole().intValue()) {
						list.setRoles("HoMaker");
					}else if (ApplicationConstants.HOCHECKER_ID == list.getRole().intValue()) {
						list.setRoles("HoChecker");
					}
				}

			}

			if (null != bulkUsers && !bulkUsers.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(bulkUsers);
				res.setResponseMessage("Approved Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/bulkUsersCreationReject", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> bulkUsersCreationReject(@RequestBody RequestParamBean requestBean)
			throws IOException {
		logger.info("In BulkUserDetails Controller-> bulkUsersCreationReject() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		String isAdded = "";
		List<String> idList = new ArrayList<>();
		try {
			Date date = new Date();
			isAdded = bulkUsersCreationService.bulkUsersCreationReject(requestBean);
			bulkUsersCreationService.getBulkUsersExcelDataReject(requestBean.getId1(), date, requestBean.getId2(),
					requestBean.getId3());

			if ("Success" == isAdded && !isAdded.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(isAdded);
				res.setResponseMessage("Rejected Successfully");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getBulkUsersExcelStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBulkUsersExcelStatus(@RequestBody RequestParamBean requestBean) {
		logger.info("In BulkUserDetails Controller -> getBulkUsersExcelDetails() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		List<BulkUsersExcelDetailsEntity> bulkUsersCreation = null;
		List<BulkUserUploadBean> bulkUser = new ArrayList<>();
		try {

			String s = requestBean.getId1();
	    	char c = s.charAt(0);
	    	if(ApplicationConstants.ZOMAKER_ID == Integer.valueOf(requestBean.getId2())) {
	    		bulkUsersCreation = bulkUsersCreationService.getBulkUsersExcelZonalStatus(requestBean.getId1());
	    	}else {
			    bulkUsersCreation = bulkUsersCreationService.getBulkUsersExcelStatus();
	    	}    

			for (BulkUsersExcelDetailsEntity bulk : bulkUsersCreation) {
				BulkUserUploadBean bulkUsers = new BulkUserUploadBean();
				bulkUsers.setExcelId(bulk.getId());
				bulkUsers.setExcelName(bulk.getExcelname());
				if (bulk.getStatus() == 0) {
					bulkUsers.setExcelStatus("Pending");
				} else if (bulk.getStatus() == 9) {
					bulkUsers.setExcelStatus("Approved");
				} else if (bulk.getStatus() == 6) {
					bulkUsers.setExcelStatus("Rejected");
					bulkUsers.setExcelRemark(bulk.getRemark());
				} else if (bulk.getStatus() == 4) {
					bulkUsers.setExcelStatus("Failure");
				}
				bulkUsers.setExcelDate(bulk.getCreatedon().toString());
				if (null != bulk.getTotalCount() && bulk.getTotalCount() != "" && !bulk.getTotalCount().isEmpty()) {
					bulkUsers.setTotalCount(Integer.valueOf(bulk.getTotalCount()).intValue());
				}
				if (null != bulk.getSuccessCount() && bulk.getSuccessCount() != ""
						&& !bulk.getSuccessCount().isEmpty()) {
					bulkUsers.setSuccessCount(Integer.valueOf(bulk.getSuccessCount()).intValue());
				}
				if (null != bulk.getErrorCount() && bulk.getErrorCount() != "" && !bulk.getErrorCount().isEmpty()) {
					bulkUsers.setErrorCount(Integer.valueOf(bulk.getErrorCount()).intValue());
				}
				bulkUser.add(bulkUsers);

			}

			if (null != bulkUser) {
				res.setResponseCode("200");
				res.setResult(bulkUser);
				res.setResponseMessage("Success");
			} else {
				// res.setResponseCode("202");
				// res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.info("Exception:", e);

		}

		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getBulkUsersExcelErrorDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getBulkUsersExcelErrorDetails(
			@RequestBody RequestParamBean requestBean) {
		logger.info("In BulkUserDetails Controller -> getBulkUsersExcelErrorDetails() :: ");
		ResponseMessageBean res = new ResponseMessageBean();
		List<BulkUsersCreationEntity> bulkUsersCreation = null;
		try {
			bulkUsersCreation = bulkUsersCreationService.getBulkUsersExcelErrorDetails(requestBean.getId1());
			for (BulkUsersCreationEntity list : bulkUsersCreation) {
				if (null != list.getRole()) {
					if (ApplicationConstants.MAKER_ID == list.getRole().intValue()) {
						list.setRoles("Maker");
					} else if (ApplicationConstants.CHECKER_ID == list.getRole().intValue()) {
						list.setRoles("Checker");
					}else if (ApplicationConstants.ZOMAKER_ID == list.getRole().intValue()) {
						list.setRoles("ZoMaker");
					}else if (ApplicationConstants.ZOCHECKER_ID == list.getRole().intValue()) {
						list.setRoles("ZoChecker");
					}else if (ApplicationConstants.HOMAKER_ID == list.getRole().intValue()) {
						list.setRoles("HoMaker");
					}else if (ApplicationConstants.HOCHECKER_ID == list.getRole().intValue()) {
						list.setRoles("HoChecker");
					}
				}

			}

			if (null != bulkUsersCreation && !bulkUsersCreation.isEmpty()) {
				res.setResponseCode("200");
				res.setResult(bulkUsersCreation);
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
