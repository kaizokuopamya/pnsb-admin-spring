package com.itl.pns.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.itl.pns.bean.BulkUserUploadBean;
import com.itl.pns.bean.RequestParamBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.constant.ApplicationConstants;
import com.itl.pns.entity.BulkUsersCreationEntity;
import com.itl.pns.entity.BulkUsersExcelDetailsEntity;
import com.itl.pns.entity.User;
import com.itl.pns.entity.UserDetails;
import com.itl.pns.repository.BulkUsersCreationRepository;
import com.itl.pns.repository.BulkUsersExcelDetailsRepository;
import com.itl.pns.repository.CbsBranchListRepository;
import com.itl.pns.repository.UserDetailsRepository;
import com.itl.pns.repository.UserMasterRepository;
import com.itl.pns.service.AdministrationService;
import com.itl.pns.service.BulkUsersCreationService;
import com.itl.pns.util.RandomNumberGenerator;
import com.itl.pns.util.Utils;

@Service
@Qualifier("BulkUsersCreationService")
@Transactional
public class BulkUsersCreationServImpl implements BulkUsersCreationService {

	static Logger LOGGER = Logger.getLogger(BulkUsersCreationServImpl.class);

	@Autowired
	BulkUsersCreationRepository bulkUsersCreationRepository;

	@Autowired
	UserMasterRepository userMasterRepository;

	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Autowired
	BulkUsersExcelDetailsRepository bulkUsersExcelDetailsRepository;

	@Autowired
	CbsBranchListRepository cbsBranchListRepository;

	@Autowired
	private AdministrationService administrationService;
	
	
	@SuppressWarnings("unused")
	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public BulkUserUploadBean createBulkUsersFile(MultipartFile file1, Integer id, List<UserDetailsBean> userDetails) {
		LOGGER.info("BulkUsersCreationServImpl ->createBulkUsersFile()");
		// List<BulkUsersCreationEntity> allData = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		Map<Integer, String> map1 = new HashMap<Integer, String>();
		List<BulkUsersCreationEntity> bulkUsers1 = new ArrayList<>();
		List<BulkUserUploadBean> bulkData = new ArrayList<>();
		BulkUserUploadBean bulkUser = new BulkUserUploadBean();
		int errorCounter = 0;
		int successCounter = 0;
		int totalCounter = 0;
		int sheetsCounter = 0;
		String userBranchCode = null;
		int userRoleId = 0;

		try {
			String fileName = file1.getOriginalFilename();
			XSSFWorkbook workbook;

//			if(file1.getOriginalFilename().endsWith(".xlsx")) {
//				ExcelDocumentConverter excelDocumentConverter = new ExcelDocumentConverter();
//				HSSFWorkbook workbook1 = new HSSFWorkbook(file1.getInputStream());
//				workbook=excelDocumentConverter.convertWorkbookHSSFToXSSF(workbook1);
//			}else {
				FileInputStream file = new FileInputStream(convertMultiPartToFile(file1));
				workbook = new XSSFWorkbook(file);
//			}
			Iterator<Sheet> sheetIterator;
			sheetIterator = workbook.sheetIterator();
			Sheet sheet1;
			for (UserDetailsBean usb : userDetails) {
				userBranchCode = usb.getBranchCode();
				userRoleId = usb.getROLE_ID().intValue();
			}

			while (sheetIterator.hasNext()) {
				sheet1 = sheetIterator.next();
				LOGGER.info(sheet1.getSheetName());
				sheetsCounter++;
			}
			XSSFSheet sheet = workbook.getSheetAt(0);
			boolean excelStatus = isSheetEmpty(sheet);
			Iterator<Row> rowIterator1 = sheet.iterator();
			Iterator<Row> rowIterator = sheet.iterator();
			List<BulkUsersCreationEntity> success = new ArrayList<>();
			List<BulkUsersCreationEntity> error = new ArrayList<>();
			List<BulkUsersCreationEntity> bulkUsers = new ArrayList<>();
			List<String> hederList = new ArrayList<String>();
			int j = 0;
			String data1 = "";
			String message = "";
			String message1 = "";
			Row headerRow = sheet.getRow(0);
			String userRoleIds = null;

//			while (rowIterator1.hasNext()) {
//
//				Row row = rowIterator1.next();
//				String data = "";
//				Iterator<Cell> cellIterator1 = row.cellIterator();
//				ArrayList<String> headerCell = new ArrayList<String>(11);
//			    while(cellIterator1.hasNext())
//			    {
//			        Cell cell = cellIterator1.next();
//			        headerCell.add(cell.getStringCellValue());
//			    }
//
//			    ArrayList<String> validHeaders = new ArrayList<>(Arrays.asList("Sr.No","USER_ID","FIRST_NAME","LAST_NAME","EMAIL","ROLE","MOBILE_NUMBER","BRANCH_CODE","REPORTING_BRANCH","BRANCH_NAME","ADD/UPDATE"));
//			    if(headerCell.containsAll(validHeaders))
//			    {
//			    	LOGGER.info("Mandatory fields present");
//			    }
//			    else
//			    {
//			    	message1="Mandatory fields not present";
//			    }
//				
//			}	

			if (excelStatus == true) {
				// LIst of headers from excel

				if (sheetsCounter == 1) {

					List<String> headers = new ArrayList<String>();
					Iterator<Cell> cells = headerRow.cellIterator();
					while (cells.hasNext()) {
						Cell cell = (Cell) cells.next();
						RichTextString value = cell.getRichStringCellValue();
						headers.add(value.getString());
					}
					ArrayList<String> validHeaders = new ArrayList<>(
							Arrays.asList("Sr.No", "USER_ID", "FIRST_NAME", "LAST_NAME", "EMAIL", "ROLE",
									"MOBILE_NUMBER", "BRANCH_CODE", "REPORTING_BRANCH", "BRANCH_NAME", "ADD/UPDATE"));
					if (headers.containsAll(validHeaders)) {
						LOGGER.info("Mandatory fields present");
					} else {
						message1 = "Invalid File Format";
					}

					for (Row row : sheet) {
						for (int cell_number = 0; cell_number < row.getLastCellNum(); cell_number++) {

							int fcell = row.getFirstCellNum();// first cell number of excel
							int lcell = row.getLastCellNum();

							if (containsValue(row, fcell, lcell) == true) {
								Cell cell = row.getCell(cell_number, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
								// Print the cell for debugging
								LOGGER.info("Row : " + row.getRowNum() + " Cell : " + cell_number + " value –> "
										+ cell.toString());
							}

						}
					}
					if (message1.isEmpty()) {

						while (rowIterator.hasNext()) {

							Row row = rowIterator.next();
							String data = "";
							Iterator<Cell> cellIterator = row.cellIterator();
							int fcell = row.getFirstCellNum();// first cell number of excel
							int lcell = row.getLastCellNum();

							if (containsValue(row, fcell, lcell) == true) {
								if (j != 0) {
									data = "";
									while (cellIterator.hasNext()) {
										Cell cell = cellIterator.next();
										CellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
										cellStyle.setWrapText(true);
										cell.setCellStyle(cellStyle);
										LOGGER.info("cell : " + cell);
										String value = "";
										String val = "";
										String valueStr = "";
//						if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
//					       {
//					         continue;
//					       }else {
										switch (cell.getCellType()) {
										case Cell.CELL_TYPE_ERROR:
											value = "";
											break;
										case Cell.CELL_TYPE_BLANK:
											value = "";
											break;
										case Cell.CELL_TYPE_NUMERIC:
											cell.setCellType(cell.CELL_TYPE_STRING);
											value = String.valueOf(cell.getStringCellValue().trim());
											break;
										case Cell.CELL_TYPE_STRING:
											value = String.valueOf(cell.getStringCellValue().trim());
											// value = val.replace(",", "");
											break;
										}
										valueStr = value.replaceAll("-", " ");
										data = data.concat(valueStr.trim()).concat("-");
										// }
									}
									LOGGER.info("data" + data);
									map.put(j, data);
								} else {
									data1 = "";
									while (cellIterator.hasNext()) {
										Cell cell = cellIterator.next();
										CellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
										cellStyle.setWrapText(true);
										cell.setCellStyle(cellStyle);
										String value = "";
										switch (cell.getCellType()) {
										case Cell.CELL_TYPE_ERROR:
											value = "";
											break;
										case Cell.CELL_TYPE_BLANK:
											value = "";
											break;
										case Cell.CELL_TYPE_NUMERIC:
											cell.setCellType(cell.CELL_TYPE_STRING);
											value = String.valueOf(cell.getStringCellValue());
											break;
										case Cell.CELL_TYPE_STRING:
											value = String.valueOf(cell.getStringCellValue());
											break;
										}
										data1 = data1.concat(value.trim()).concat("-");
									}
									LOGGER.info("data1" + data1);
									map1.put(j, data1);

								}
								j++;

							}
						}
						for (Integer key : map1.keySet()) {
							String value = map1.get(key);
							String[] custData = value.trim().split("-");
							int custInt = custData.length;
							LOGGER.info("custInt :" + custInt);
							if (null != String.valueOf(custData[1])) {
								if (!String.valueOf(custData[1]).equalsIgnoreCase("USER_ID")) {
									message = "Invalid File Format";
								}
							} else {
								message1 = "Invalid File Format";
							}

							if (null != String.valueOf(custData[2])) {
								if (!String.valueOf(custData[2]).equalsIgnoreCase("FIRST_NAME")) {
									message = "Invalid File Format";
								}
							} else {
								message1 = "Invalid File Format";
							}

							if (null != String.valueOf(custData[3])) {
								if (!String.valueOf(custData[3]).equalsIgnoreCase("LAST_NAME")) {
									message = "Invalid File Format";
								}
							} else {
								message1 = "Invalid File Format";
							}

							if (null != String.valueOf(custData[4])) {
								if (!String.valueOf(custData[4]).equalsIgnoreCase("EMAIL")) {
									message = "Invalid Column";
								}
							} else {
								message1 = "Invalid File Format";
							}

							if (null != String.valueOf(custData[5])) {
								if (!String.valueOf(custData[5]).equalsIgnoreCase("ROLE")) {
									message = "Invalid File Format";
								}
							} else {
								message1 = "Invalid File Format";
							}

							if (null != String.valueOf(custData[6])) {
								if (!String.valueOf(custData[6]).equalsIgnoreCase("MOBILE_NUMBER")) {
									message = "Invalid File Format";
								}
							} else {
								message1 = "Invalid File Format";
							}

							if (null != String.valueOf(custData[7])) {
								if (!String.valueOf(custData[7]).equalsIgnoreCase("BRANCH_CODE")) {
									message = "Invalid File Format";
								}
							} else {
								message1 = "Invalid File Format";
							}

							if (null != String.valueOf(custData[8])) {
								if (!String.valueOf(custData[8]).equalsIgnoreCase("REPORTING_BRANCH")) {
									message = "Invalid File Format";
								}
							} else {
								message1 = "Invalid File Format";
							}

							if (null != String.valueOf(custData[9])) {
								if (!String.valueOf(custData[9]).equalsIgnoreCase("BRANCH_NAME")) {
									message = "Invalid File Format";
								}
							} else {
								message1 = "Invalid File Format";
							}

							if (null != String.valueOf(custData[10])) {
								if (!String.valueOf(custData[10]).equalsIgnoreCase("ADD/UPDATE")) {
									message = "Invalid File Format";
								}
							} else {
								message1 = "Invalid File Format";
							}

						}
					}
				} else {
					message1 = "Invalid File Format";
				}
			} else {
				message1 = "The file is empty!";
			}

			if (message1.isEmpty()) {

				if (message.isEmpty()) {

					for (Integer key : map.keySet()) {
						StringJoiner stringJoiner = new StringJoiner(", ");
						String value = map.get(key);
						String[] custData = value.trim().split("-");
						String reason = "";
						// LOGGER.info("custData LENTH " + custData.length);
						BulkUsersCreationEntity bk = new BulkUsersCreationEntity();
						Date date = new Date();
						// if (custData.length >= 11) {
						List<String> validExcel = new ArrayList<>(
								Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9"));
						String cust = String.valueOf(custData.length);
						if (!validExcel.contains(cust)) {
							if (custData.length == 11) {
								String actio = String.valueOf(custData[10]);
								if (actio.equalsIgnoreCase("Insert")) {
									if (!ObjectUtils.isEmpty(custData[1])) {
										ResponseMessageBean count = checkUser(String.valueOf(custData[1]).toLowerCase(),
												"");
										if (count.getResponseCode().equalsIgnoreCase("200")) {

											if (String.valueOf(custData[1]).length() <= 20) {
												bk.setUserId(String.valueOf(custData[1]).toLowerCase());
												// LOGGER.info("User Id : " +
												// String.valueOf(custData[1]).toLowerCase());
											} else {
												stringJoiner.add("Minimum 20 characters required");
												bk.setRemark(stringJoiner.toString());
											}

										} else {
											bk.setUserId(String.valueOf(custData[1]).toLowerCase());
											stringJoiner.add("User Already Exist");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										stringJoiner.add("Invalid User Id");
										bk.setRemark(stringJoiner.toString());
									}

									if (!ObjectUtils.isEmpty(custData[2])) {
										if (checkCharacterStringContains(String.valueOf(custData[2])) == true) {
											bk.setFirstName(Utils.toCamelCase(String.valueOf(custData[2])));
											LOGGER.info(Utils.toCamelCase(String.valueOf(custData[2])));
											if (custData[2].length() > 30) {
												stringJoiner.add("First name length should be max 30 character");
												bk.setRemark(stringJoiner.toString());
											}
										} else {
											bk.setFirstName(Utils.toCamelCase(String.valueOf(custData[2])));
											stringJoiner.add("Invalid First Name");
											bk.setRemark(stringJoiner.toString());
										}

									} else {
										stringJoiner.add("First Name Not Found");
										bk.setRemark(stringJoiner.toString());
									}

									if (!ObjectUtils.isEmpty(custData[3])) {
										if (checkCharacterStringContains(String.valueOf(custData[3])) == true) {
											bk.setLastName(Utils.toCamelCase(String.valueOf(custData[3])));
											LOGGER.info(Utils.toCamelCase(String.valueOf(custData[3])));
											if (custData[3].length() > 50) {
												stringJoiner.add("Last name length should be max 50 character");
												bk.setRemark(stringJoiner.toString());
											}
										} else {
											bk.setLastName(Utils.toCamelCase(String.valueOf(custData[3])));
											stringJoiner.add("Invalid Last Name");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										// reason = reason.concat("Last Name Not Found").concat(",");
										// bk.setRemark("Last Name Not Found");
									}

									if (!ObjectUtils.isEmpty(custData[4])) {
										if (checkEmailValidation(String.valueOf(custData[4])) == true) {
											bk.setEmail(String.valueOf(custData[4]));
											LOGGER.info(String.valueOf(custData[4]));
											int emailCount = userMasterRepository
													.userEmailExistOrNot(String.valueOf(custData[4]));
											if (emailCount != 0) {
												stringJoiner.add("Email Already Exist");
												bk.setRemark(stringJoiner.toString());
											}

										} else {
											bk.setEmail(String.valueOf(custData[4]));
											stringJoiner.add("Invalid Email Format");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										stringJoiner.add("Email Not Found");
										bk.setRemark(stringJoiner.toString());
									}

									if (!ObjectUtils.isEmpty(custData[5])) {
										if (checkCharacterStringContains(Utils.trimAdvanced(String.valueOf(custData[5]))) == true) {
											if (userRoleId == ApplicationConstants.HOMAKER_ID) {
												LOGGER.info("ROLE: "+String.valueOf(custData[5]));
												if ("Maker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.MAKER_ID);
												} else if ("Checker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.CHECKER_ID);
												} else if ("ZoMaker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.ZOMAKER_ID);
												} else if ("ZoChecker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.ZOCHECKER_ID);
												} else if ("HoMaker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.HOMAKER_ID);
												} else if ("HoChecker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.HOCHECKER_ID);
												}else {
													stringJoiner.add(
															"Invalid Role Id");
													bk.setRemark(stringJoiner.toString());
													bk.setRoles(String.valueOf(custData[5]));
												}
											} else {
												if ("Maker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.MAKER_ID);
												} else if ("Checker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.CHECKER_ID);
												} else if ("ZoMaker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.ZOMAKER_ID);
													stringJoiner.add(
															"Zonal users doesn't have authorities to create ZoMaker user");
													bk.setRemark(stringJoiner.toString());
												} else if ("ZoChecker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.ZOCHECKER_ID);
													stringJoiner.add(
															"Zonal users doesn't have authorities to create ZoChecker user");
													bk.setRemark(stringJoiner.toString());
												} else if ("HoMaker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.HOMAKER_ID);
													stringJoiner.add(
															"Zonal users doesn't have authorities to create HoMaker user");
													bk.setRemark(stringJoiner.toString());
												} else if ("HoChecker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.HOCHECKER_ID);
													stringJoiner.add(
															"Zonal users doesn't have authorities to create HoChecker user");
													bk.setRemark(stringJoiner.toString());
												}

											}

										} else {
											if ("Maker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.MAKER_ID);
												bk.setRoles(String.valueOf(custData[5]));
											} else if ("Checker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.CHECKER_ID);
												bk.setRoles(String.valueOf(custData[5]));
											} else if ("ZoMaker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.ZOMAKER_ID);
												bk.setRoles(String.valueOf(custData[5]));
											} else if ("ZoChecker"
													.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.ZOCHECKER_ID);
												bk.setRoles(String.valueOf(custData[5]));
											} else if ("HoMaker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.HOMAKER_ID);
												bk.setRoles(String.valueOf(custData[5]));
											} else if ("HoChecker"
													.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.HOCHECKER_ID);
												bk.setRoles(Utils.trimAdvanced(String.valueOf(custData[5])));
											}

											stringJoiner.add("Invalid Role");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										stringJoiner.add("Role is not Found");
										bk.setRemark(stringJoiner.toString());
									}

									if (!ObjectUtils.isEmpty(custData[6])) {
										String mob = String.valueOf(custData[6]);
										int mobLength = mob.length();
										if (mobLength == 10) {
											bk.setMobileNumber(String.valueOf(custData[6]));
											LOGGER.info(String.valueOf(custData[6]));
											int sqlMobileExist = userMasterRepository
													.userMobileExistOrNot(String.valueOf(custData[6]));
											if (sqlMobileExist != 0) {
												stringJoiner.add("Mobile Number Already Exist");
												bk.setRemark(stringJoiner.toString());
											}
											LOGGER.info(String.valueOf(custData[6]));

										} else {
											bk.setMobileNumber(String.valueOf(custData[6]));
											stringJoiner.add("Invalid Mobile Number");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										stringJoiner.add("Mobile Number Not Found");
										bk.setRemark(stringJoiner.toString());
									}

									List<String> branchCodes = new ArrayList<>();
									String cbsBranchName = null;
									LOGGER.info("custData[8]" + custData[8]);
									if (!ObjectUtils.isEmpty(custData[8])) {
										if (userRoleId == ApplicationConstants.ZOMAKER_ID) {
											if ("MAKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))
													|| "CHECKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))) {
												if (userBranchCode.equalsIgnoreCase(custData[8].toUpperCase())) {
													String repBranch = String.valueOf(custData[8].toUpperCase());
													branchCodes = cbsBranchListRepository.getBranchDetail(repBranch);
													if (!ObjectUtils.isEmpty(branchCodes)) {
														bk.setReportingBranch(
																String.valueOf(custData[8].toUpperCase()));
														LOGGER.info(String.valueOf(custData[8].toUpperCase()));
													} else {
														bk.setReportingBranch(
																String.valueOf(custData[8].toUpperCase()));
														stringJoiner.add("Invalid Reporting Branch");
														bk.setRemark(stringJoiner.toString());
													}
												} else {
													bk.setReportingBranch(String.valueOf(custData[8].toUpperCase()));
													stringJoiner.add("Invalid Reporting Branch");
													bk.setRemark(stringJoiner.toString());
												}
											} else {
												bk.setReportingBranch(String.valueOf(custData[8].toUpperCase()));
											}
										} else {

											String repBranch = String.valueOf(custData[8].toUpperCase());
											String branch = String.valueOf(custData[7].toUpperCase());
											LOGGER.info("branch :" + branch);
											String strRepBranch = null;
											if (!branch.isEmpty()) {
												strRepBranch = branch.substring(0, 2);
											}
											/********************** new *******************/
											if ("HOMAKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))
													|| "HOCHECKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))) {
												branchCodes.add("H3309");
											} else if ("ZOMAKER"
													.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))
													|| "ZOCHECKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))) {
												if ("Z0".equalsIgnoreCase(strRepBranch)) {
													branchCodes = cbsBranchListRepository.getBranchDetail(repBranch);
												} else {
													branchCodes = cbsBranchListRepository.getZonalCode();
												}
												if (!ObjectUtils.isEmpty(branchCodes)) {
													bk.setReportingBranch(String.valueOf(custData[8].toUpperCase()));
													LOGGER.info(String.valueOf(custData[8].toUpperCase()));
												} else {
													bk.setReportingBranch(String.valueOf(custData[8].toUpperCase()));
													stringJoiner.add("Invalid Reporting Branch");
													bk.setRemark(stringJoiner.toString());
												}

											} else {
												branchCodes = cbsBranchListRepository.getBranchDetail(repBranch);
												if (!ObjectUtils.isEmpty(branchCodes)) {
													bk.setReportingBranch(String.valueOf(custData[8].toUpperCase()));
													LOGGER.info(String.valueOf(custData[8].toUpperCase()));
												} else {
													bk.setReportingBranch(String.valueOf(custData[8].toUpperCase()));
													stringJoiner.add("Invalid Reporting Branch");
													bk.setRemark(stringJoiner.toString());
												}
											}
										}

									} else {
										if ("HOMAKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))
												|| "HOCHECKER"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))) {
											String hoCode = "H3309";
											branchCodes.add(hoCode);
										} else {
											stringJoiner.add("Reporting Branch Not Found");
											bk.setRemark(stringJoiner.toString());
										}
									}

									LOGGER.info("custData[7]" + custData[7]);
									if (!ObjectUtils.isEmpty(custData[7])) {
										
										if (!ObjectUtils.isEmpty(branchCodes)) {
											if ("HOMAKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))
													|| "HOCHECKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))) {
												if (branchCodes.contains(Utils.trimAdvanced(String.valueOf(custData[7])))) {
													bk.setBranchCode(Utils.trimAdvanced(branchCodes.get(0).toUpperCase()));
													cbsBranchName = "Head Office";
												} else {
													bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7].toUpperCase())));
													stringJoiner.add("Invalid Branch Code");
													bk.setRemark(stringJoiner.toString());
												}

											} else if ("ZOMAKER"
													.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))
													|| "ZOCHECKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5].toUpperCase())))) {

												if (branchCodes.contains(String.valueOf(custData[7]))) {
													if (!"Z0".equalsIgnoreCase(String.valueOf(custData[7].toUpperCase())
															.substring(0, 2))) {
														cbsBranchName = administrationService
																.getCbsZonalName(String.valueOf(custData[7]));
														bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7].toUpperCase())));
													}
												}
											} else if (branchCodes.contains(Utils.trimAdvanced(String.valueOf(custData[7])))) {
												cbsBranchName = administrationService
														.getCbsBranchName(String.valueOf(custData[7]));
												bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7].toUpperCase())));
												LOGGER.info(Utils.toCamelCase(String.valueOf(custData[7])));
											} else {
												bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7].toUpperCase())));
												stringJoiner.add("Branch Code not map to the Reporting Branch");
												bk.setRemark(stringJoiner.toString());
											}

										} else {

											bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7].toUpperCase())));
										}
									} else {

										stringJoiner.add("Branch Not Found");
										bk.setRemark(stringJoiner.toString());
									}

									if (!ObjectUtils.isEmpty(custData[9])) {
//										if (checkCharacterStringWithNumericContains(
//												String.valueOf(custData[9])) == true) {
											LOGGER.info("cbsBranchName =" + cbsBranchName);
											LOGGER.info("custData[9] =" + String.valueOf(custData[9]));
											String cbsBranchNameExcl = Utils.trimAdvanced(custData[9].toString()).toUpperCase();
											if (!ObjectUtils.isEmpty(cbsBranchName)) {
												if (cbsBranchName.replaceAll("-", " ").trim().toUpperCase()
														.equalsIgnoreCase(cbsBranchNameExcl)) {
													bk.setBranchName(cbsBranchNameExcl);
													System.out
															.println(cbsBranchNameExcl);
												} else {
													bk.setBranchName(cbsBranchNameExcl);
													stringJoiner.add("Invalid Branch Name");
													bk.setRemark(stringJoiner.toString());
												}
											} else {
												bk.setBranchName(cbsBranchNameExcl);

												stringJoiner.add("Invalid Branch Name");
												bk.setRemark(stringJoiner.toString());
											}
//										} else {
//											bk.setBranchName(String.valueOf(custData[9]).trim().toUpperCase());
//
//											stringJoiner.add("Invalid Branch Name");
//											bk.setRemark(stringJoiner.toString());
//										}
									} else {
										stringJoiner.add("Branch Name Not Found");
										bk.setRemark(stringJoiner.toString());
									}
									if (!ObjectUtils.isEmpty(custData[10])) {
										if (checkCharacterStringContains(String.valueOf(custData[10])) == true) {
//											bk.setAction(String.valueOf(custData[10]).trim().toLowerCase());
											bk.setAction(Utils.trimAdvanced(custData[10].toString().toLowerCase()));
											LOGGER.info(String.valueOf(custData[10]));
										} else {
//											bk.setAction(String.valueOf(custData[10]).toLowerCase());
											bk.setAction(Utils.trimAdvanced(custData[10].toString().toLowerCase()));
											stringJoiner.add("Invalid Action");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										stringJoiner.add("Action Not Found");
										bk.setRemark(stringJoiner.toString());
									}

								} else if (actio.equalsIgnoreCase("Update")) {

									if (!ObjectUtils.isEmpty(custData[1])) {
										
//										ResponseMessageBean count = checkUser(String.valueOf(custData[1]).toLowerCase(),"");
										ResponseMessageBean count = checkUser(Utils.trimAdvanced(custData[1].toString().toLowerCase()),"");
										if (count.getResponseCode().equalsIgnoreCase("500")) {
//											userRoleIds = userMasterRepository.getUserRoleIds(String.valueOf(custData[1]).toLowerCase());
											userRoleIds = userMasterRepository.getUserRoleIds(Utils.trimAdvanced(custData[1].toString().toLowerCase()));
											if (String.valueOf(custData[1]).length() <= 20) {
//												bk.setUserId(String.valueOf(custData[1]).toLowerCase());
												bk.setUserId(Utils.trimAdvanced(custData[1].toString().toLowerCase()));
												LOGGER.info("User Id :" + String.valueOf(custData[1]));
											} else {
//												bk.setUserId(String.valueOf(custData[1]).toLowerCase());
												bk.setUserId(Utils.trimAdvanced(custData[1].toString().toLowerCase()));
												stringJoiner.add("Minimum 20 characters required");
												bk.setRemark(stringJoiner.toString());
											}
										} else {
//											bk.setUserId(String.valueOf(custData[1]).toLowerCase());
											bk.setUserId(Utils.trimAdvanced(custData[1].toString().toLowerCase()));
											stringJoiner.add("User ID Not Exist");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										stringJoiner.add("Invalid User ID");
										bk.setRemark(stringJoiner.toString());
									}

									if (!ObjectUtils.isEmpty(custData[2])) {
										stringJoiner.add("First Name modification not permitted!");
										bk.setRemark(stringJoiner.toString());
//										if (checkCharacterStringContains(String.valueOf(custData[2])) == true) {
//											bk.setFirstName(Utils.toCamelCase(String.valueOf(custData[2])));
//											LOGGER.info(Utils.toCamelCase(String.valueOf(custData[2])));
//											if (custData[2].length() > 30) {
//												stringJoiner.add("First name length should be max 30 character");
//												bk.setRemark(stringJoiner.toString());
//											}
//										} else {
//											bk.setFirstName(Utils.toCamelCase(String.valueOf(custData[2])));
//											stringJoiner.add("Invalid First Name");
//											bk.setRemark(stringJoiner.toString());
//										}
									} else {
										bk.setFirstName("");
									}

									if (!ObjectUtils.isEmpty(custData[3])) {
										stringJoiner.add("Last Name modification not permitted!");
										bk.setRemark(stringJoiner.toString());
//										if (checkCharacterStringContains(
//												Utils.toCamelCase(String.valueOf(custData[3]))) == true) {
//											bk.setLastName(Utils.toCamelCase(String.valueOf(custData[3])));
//											LOGGER.info(Utils.toCamelCase(String.valueOf(custData[3])));
//											if (custData[3].length() > 50) {
//												stringJoiner.add("Last name length should be max 50 character");
//												bk.setRemark(stringJoiner.toString());
//											}
//										} else {
//											bk.setLastName(Utils.toCamelCase(String.valueOf(custData[3])));
//											stringJoiner.add("Invalid Last Name");
//											bk.setRemark(stringJoiner.toString());
//										}
									} else {
										bk.setLastName("");
									}

									if (!ObjectUtils.isEmpty(custData[4])) {
										if (checkEmailValidation(String.valueOf(custData[4])) == true) {
											bk.setEmail(String.valueOf(custData[4]));
											LOGGER.info(String.valueOf(custData[4]));
											int sqlEmailExist = userMasterRepository
													.userEmailExistOrNot(String.valueOf(custData[4]));
											if (sqlEmailExist != 0) {
												stringJoiner.add("Email Already Exist");
												bk.setRemark(stringJoiner.toString());
											}

										} else {
											bk.setEmail(String.valueOf(custData[4]));
											stringJoiner.add("Invalid Email Format");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										bk.setEmail("");
									}

									if (!ObjectUtils.isEmpty(custData[5])) {
										if (checkCharacterStringContains(Utils.trimAdvanced(String.valueOf(custData[5]))) == true) {
											if (userRoleId == ApplicationConstants.HOMAKER_ID) {
												if ("Maker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.MAKER_ID);
												} else if ("Checker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.CHECKER_ID);
												} else if ("ZoMaker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.ZOMAKER_ID);
												} else if ("ZoChecker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.ZOCHECKER_ID);
												} else if ("HoMaker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.HOMAKER_ID);
												} else if ("HoChecker"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
													bk.setRole(ApplicationConstants.HOCHECKER_ID);
												}
											} else {
												if (Integer.valueOf(userRoleIds) == ApplicationConstants.MAKER_ID
														|| Integer.valueOf(
																userRoleIds) == ApplicationConstants.CHECKER_ID) {
													if ("Maker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
														bk.setRole(ApplicationConstants.MAKER_ID);
													} else if ("Checker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
														bk.setRole(ApplicationConstants.CHECKER_ID);
													}
												} else {
													if ("ZoMaker"
															.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
														bk.setRole(ApplicationConstants.ZOMAKER_ID);
														stringJoiner.add(
																"Zonal users doesn't have authorities to Change ZoMaker user");
														bk.setRemark(stringJoiner.toString());
													} else if ("ZoChecker"
															.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
														bk.setRole(ApplicationConstants.ZOCHECKER_ID);
														stringJoiner.add(
																"Zonal users doesn't have authorities to Change ZoChecker user");
														bk.setRemark(stringJoiner.toString());
													} else if ("HoMaker"
															.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
														bk.setRole(ApplicationConstants.HOMAKER_ID);
														stringJoiner.add(
																"Zonal users doesn't have authorities to Change HoMaker user");
														bk.setRemark(stringJoiner.toString());
													} else if ("HoChecker"
															.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
														bk.setRole(ApplicationConstants.HOCHECKER_ID);
														stringJoiner.add(
																"Zonal users doesn't have authorities to Change HoChecker user");
														bk.setRemark(stringJoiner.toString());
													} else {
														if ("Maker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
															bk.setRole(ApplicationConstants.MAKER_ID);
															stringJoiner.add(
																	"Zonal users doesn't have authorities to Change User Details");
															bk.setRemark(stringJoiner.toString());
														} else if ("Checker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
															bk.setRole(ApplicationConstants.CHECKER_ID);
															stringJoiner.add(
																	"Zonal users doesn't have authorities to Change User Details");
															bk.setRemark(stringJoiner.toString());
														}

													}

												}
											}

										} else {
											if ("Maker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.MAKER_ID);
												bk.setRoles(Utils.trimAdvanced(String.valueOf(custData[5])));
											} else if ("Checker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.CHECKER_ID);
												bk.setRoles(Utils.trimAdvanced(String.valueOf(custData[5])));
											} else if ("ZoMaker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.ZOMAKER_ID);
												bk.setRoles(Utils.trimAdvanced(String.valueOf(custData[5])));
											} else if ("ZoChecker"
													.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.ZOCHECKER_ID);
												bk.setRoles(String.valueOf(custData[5]));
											} else if ("HoMaker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.HOMAKER_ID);
												bk.setRoles(Utils.trimAdvanced(String.valueOf(custData[5])));
											} else if ("HoChecker"
													.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
												bk.setRole(ApplicationConstants.HOCHECKER_ID);
												bk.setRoles(Utils.trimAdvanced(String.valueOf(custData[5])));
											}

											stringJoiner.add("Invalid Role");
											bk.setRemark(stringJoiner.toString());
										}

									} else {
										stringJoiner.add("Role is not Found");
										bk.setRemark(stringJoiner.toString());
									}

									if (!ObjectUtils.isEmpty(custData[6])) {
										String mob = String.valueOf(custData[6]);
										int mobLength = mob.length();
										if (mobLength == 10) {
											bk.setMobileNumber(String.valueOf(custData[6]));
											int sqlMobileExist = userMasterRepository
													.userMobileExistOrNot(String.valueOf(custData[6]));
											if (sqlMobileExist != 0) {
												stringJoiner.add("Mobile Number Already Exist");
												bk.setRemark(stringJoiner.toString());
											}
											LOGGER.info(String.valueOf(custData[6]));
										} else {
											bk.setMobileNumber(String.valueOf(custData[6]));
											stringJoiner.add("Invalid Mobile Number");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										bk.setMobileNumber("");
									}

									List<String> branchCode = new ArrayList<>();
									String cbsBranchName1 = null;
									if (!ObjectUtils.isEmpty(custData[8])) {
										if (userRoleId == ApplicationConstants.ZOMAKER_ID) {
											if (Integer.valueOf(userRoleIds) == ApplicationConstants.MAKER_ID || Integer
													.valueOf(userRoleIds) == ApplicationConstants.CHECKER_ID) {
												if ("MAKER".equals(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))
														|| "CHECKER".equals(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))) {
													if (userBranchCode.equalsIgnoreCase(custData[8].toUpperCase())) {
														String repBranch = String.valueOf(custData[8].toUpperCase());
														branchCode = cbsBranchListRepository.getBranchDetail(repBranch);
														if (null != branchCode && !branchCode.isEmpty()) {
															bk.setReportingBranch(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
															System.out
																	.println(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
														} else {
															bk.setReportingBranch(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
															stringJoiner.add("Invalid Reporting Branch");
															bk.setRemark(stringJoiner.toString());
														}
													} else {
														bk.setReportingBranch(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
														stringJoiner.add("Invalid Reporting Branch");
														bk.setRemark(stringJoiner.toString());

													}
												} else {
													bk.setReportingBranch(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
												}
											} else {
												bk.setReportingBranch(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
											}
										} else {

											String repBranch = Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase());
											String branch = Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase());
											String strRepBranch = null;
											LOGGER.info("branch :" + branch);
											if (!branch.isEmpty()) {
												strRepBranch = branch.substring(0, 2);
											}
											/********************** new *******************/
//											if("HOMAKER".equalsIgnoreCase(String.valueOf(custData[5].toUpperCase())) || "HOCHECKER".equalsIgnoreCase(String.valueOf(custData[5].toUpperCase()))){
//												branchCode.add("H3309");
//											}else 
											if ("ZOMAKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))
													|| "ZOCHECKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))) {
												if ("Z0".equalsIgnoreCase(strRepBranch)) {
													branchCode = cbsBranchListRepository.getBranchDetail(repBranch);
												} else {
													branchCode = cbsBranchListRepository.getZonalCode();
												}

												if (!ObjectUtils.isEmpty(branchCode)) {
													bk.setReportingBranch(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
													LOGGER.info(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
												} else {
													bk.setReportingBranch(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
													stringJoiner.add("Invalid Reporting Branch");
													bk.setRemark(stringJoiner.toString());
												}

											} else {
												branchCode = cbsBranchListRepository.getBranchDetail(repBranch);
												if (null != branchCode && !branchCode.isEmpty()) {
													bk.setReportingBranch(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
													LOGGER.info(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
												} else {
													bk.setReportingBranch(Utils.trimAdvanced(String.valueOf(custData[8]).toUpperCase()));
													stringJoiner.add("Invalid Reporting Branch");
													bk.setRemark(stringJoiner.toString());
												}
											}

										}

									} else {
										if ("HOMAKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))
												|| "HOCHECKER"
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))) {
											branchCode.add(ApplicationConstants.HEAD_CODE);
										} else {
											bk.setReportingBranch("");
										}

									}

									LOGGER.info("custData[7]" + custData[7]);
									if (!ObjectUtils.isEmpty(custData[7])) {

										if (!ObjectUtils.isEmpty(branchCode)) {

											if ("HOMAKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))
													|| "HOCHECKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))) {
												if (branchCode.contains(String.valueOf(custData[7]))) {
													bk.setBranchCode(Utils.trimAdvanced(branchCode.get(0).toUpperCase()));
													cbsBranchName1 = ApplicationConstants.HEAD_OFFICE_NAME;
												}
											} else if ("ZOMAKER"
													.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))
													|| "ZOCHECKER".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5]).toUpperCase()))) {

												if (branchCode.contains(String.valueOf(custData[7]))) {
													if (!"Z0".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase())
															.substring(0, 2))) {
														cbsBranchName1 = administrationService
																.getCbsZonalName(Utils.trimAdvanced(String.valueOf(custData[7])));
														bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()));
													}
												}
											} else if (branchCode.contains(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()))) {
												if (userRoleId == ApplicationConstants.ZOMAKER_ID) {
													if (Integer.valueOf(userRoleIds) == ApplicationConstants.MAKER_ID
															|| Integer.valueOf(
																	userRoleIds) == ApplicationConstants.CHECKER_ID) {
														cbsBranchName1 = administrationService
																.getCbsBranchName(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()));
														bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()));
														LOGGER.info(Utils.toCamelCase(String.valueOf(custData[7])));
													} else {
														bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()));
													}
												} else {
													cbsBranchName1 = administrationService
															.getCbsBranchName(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()));
													bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()));
													LOGGER.info(Utils.toCamelCase(String.valueOf(custData[7])));

												}

											} else {
												bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()));
											}

										} else {
											bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()));
										}
									} else {
										bk.setBranchCode("");
									}

									if (!ObjectUtils.isEmpty(custData[9])) {
//										if (checkCharacterStringWithNumericContains(
//												String.valueOf(custData[9])) == true) {
											LOGGER.info("cbsBranchName1 =" + cbsBranchName1);
											LOGGER.info("custData[9] =" + String.valueOf(custData[9]));
											if (!ObjectUtils.isEmpty(cbsBranchName1)) {
												if (cbsBranchName1.replaceAll("-", " ").trim().toUpperCase()
														.equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[9]).toUpperCase()))) {
													bk.setBranchName(Utils.trimAdvanced(cbsBranchName1.toUpperCase()));
													System.out
															.println(Utils.trimAdvanced(String.valueOf(custData[9]).toUpperCase()));
												} else {
													bk.setBranchName(String.valueOf(custData[9]));
													stringJoiner.add("Invalid Branch Name");
													bk.setRemark(stringJoiner.toString());
												}
											} else {
												bk.setBranchName(String.valueOf(custData[9]));
												stringJoiner.add("Invalid Branch Name");
												bk.setRemark(stringJoiner.toString());
											}
//										} else {
//											bk.setBranchName(String.valueOf(custData[9]).trim().toUpperCase());
//											stringJoiner.add("Invalid Branch Name");
//											bk.setRemark(stringJoiner.toString());
//										}
									} else {
										bk.setBranchName("");
									}

									if (!ObjectUtils.isEmpty(custData[10])) {
										if (checkCharacterStringContains(Utils.trimAdvanced(String.valueOf(custData[10]))) == true) {
											bk.setAction(Utils.trimAdvanced(String.valueOf(custData[10])).toLowerCase());
											LOGGER.info(String.valueOf(custData[10]));
										} else {
											stringJoiner.add("Invalid Action");
											bk.setRemark(stringJoiner.toString());
										}
									} else {
										stringJoiner.add("Action Not Found");
										bk.setRemark(stringJoiner.toString());
									}

								} else {
									stringJoiner.add("Invalid Action");
									bk.setRemark(stringJoiner.toString());
								}

							} else {

								if (2 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[1])) {
										bk.setUserId(String.valueOf(custData[1]).toLowerCase());
									} else {
										bk.setUserId("");
									}
								} else {
									stringJoiner.add("Empty cell");
									bk.setRemark(stringJoiner.toString());
								}

								if (3 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[2])) {
										bk.setFirstName(String.valueOf(custData[2]).toUpperCase());
									} else {
										bk.setFirstName("");
									}
								} else {
									stringJoiner.add("Empty cell");
									bk.setRemark(stringJoiner.toString());
								}

								if (4 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[3])) {
										bk.setLastName(String.valueOf(custData[3]).toUpperCase());
									} else {
										bk.setLastName("");
									}
								} else {
									stringJoiner.add("Empty cell");
									bk.setRemark(stringJoiner.toString());
								}

								if (5 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[4])) {
										bk.setEmail(String.valueOf(custData[4]));
									} else {
										bk.setEmail("");
									}
								} else {
									stringJoiner.add("Empty cell");
									bk.setRemark(stringJoiner.toString());
								}
								
								if (6 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[5])) {
										if ("Maker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.MAKER_ID);
										} else if ("Checker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.CHECKER_ID);
										} else if ("ZoMaker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.ZOMAKER_ID);
										} else if ("ZoChecker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.ZOCHECKER_ID);
										} else if ("HoMaker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.HOMAKER_ID);
										} else if ("HoChecker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.HOCHECKER_ID);
										}
									} else {
										if ("Maker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.MAKER_ID);
											bk.setRoles(String.valueOf(custData[5]));
										} else if ("Checker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.CHECKER_ID);
											bk.setRoles(String.valueOf(custData[5]));
										} else if ("ZoMaker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.ZOMAKER_ID);
											bk.setRoles(String.valueOf(custData[5]));
										} else if ("ZoChecker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.ZOCHECKER_ID);
											bk.setRoles(String.valueOf(custData[5]));
										} else if ("HoMaker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.HOMAKER_ID);
											bk.setRoles(String.valueOf(custData[5]));
										} else if ("HoChecker".equalsIgnoreCase(Utils.trimAdvanced(String.valueOf(custData[5])))) {
											bk.setRole(ApplicationConstants.HOCHECKER_ID);
											bk.setRoles(String.valueOf(custData[5]));
										}
									}
								} else {
									stringJoiner.add("Empty cell");
									bk.setRemark(stringJoiner.toString());
								}

								if (7 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[6])) {
										bk.setMobileNumber(String.valueOf(custData[6]));
									} else {
										bk.setMobileNumber("");
									}
								} else {
									stringJoiner.add("Empty cell");
									bk.setRemark(stringJoiner.toString());
								}

								if (8 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[7])) {
										bk.setBranchCode(Utils.trimAdvanced(String.valueOf(custData[7]).toUpperCase()));
									} else {
										bk.setBranchCode("");
									}
								} else {
									stringJoiner.add("Empty cell");
									bk.setRemark(stringJoiner.toString());
								}

								if (9 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[8])) {
										bk.setReportingBranch(String.valueOf(custData[8]));
									} else {
										bk.setReportingBranch("");
									}
								} else {
									stringJoiner.add("Empty cell");
									bk.setRemark(stringJoiner.toString());
								}

								if (10 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[9])) {
										bk.setBranchName(String.valueOf(custData[9]).toUpperCase());
									} else {
										bk.setBranchName("");
									}
								} else {
									stringJoiner.add("Empty cell");
									bk.setRemark(stringJoiner.toString());
								}

								if (11 <= custData.length) {
									if (!ObjectUtils.isEmpty(custData[10])) {
										bk.setAction(String.valueOf(custData[10]).toUpperCase());
									} else {
										bk.setAction("");
									}
								} else {
									stringJoiner.add("Invalid Action");
									bk.setRemark(stringJoiner.toString());
								}

								/*
								 * stringJoiner.add("Invalid Action"); bk.setRemark(stringJoiner.toString());
								 */
							}

						} else {

							if (1 <= custData.length) {

							} else {
								stringJoiner.add("Empty cell");
								// bk.setRemark(stringJoiner.toString());
							}

							if (2 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[1])) {
									bk.setUserId(String.valueOf(custData[1]).toLowerCase());
								} else {
									bk.setUserId("");
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							if (3 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[2])) {
									bk.setFirstName(String.valueOf(custData[2]).toUpperCase());
								} else {
									bk.setFirstName("");
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							if (4 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[3])) {
									bk.setLastName(String.valueOf(custData[3]).toUpperCase());
								} else {
									bk.setLastName("");
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							if (5 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[4])) {
									bk.setEmail(String.valueOf(custData[4]));
								} else {
									bk.setEmail("");
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							if (6 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[5])) {
									if ("Maker".equalsIgnoreCase(String.valueOf(custData[5]).trim())) {
										bk.setRole(5);
									} else if ("Checker".equalsIgnoreCase(String.valueOf(custData[5]).trim())) {
										bk.setRole(6);
									}
								} else {
									if ("Maker".equalsIgnoreCase(String.valueOf(custData[5]).trim())) {
										bk.setRole(5);
										bk.setRoles(String.valueOf(custData[5]));
									} else if ("Checker".equalsIgnoreCase(String.valueOf(custData[5]).trim())) {
										bk.setRole(6);
										bk.setRoles(String.valueOf(custData[5]));
									}
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							if (7 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[6])) {
									bk.setMobileNumber(String.valueOf(custData[6]));
								} else {
									bk.setMobileNumber("");
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							if (8 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[7])) {
									bk.setBranchCode(String.valueOf(custData[7]).toUpperCase().trim());
								} else {
									bk.setBranchCode("");
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							if (9 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[8])) {
									bk.setReportingBranch(String.valueOf(custData[8]));
								} else {
									bk.setReportingBranch("");
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							if (10 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[9])) {
									bk.setBranchName(String.valueOf(custData[9]).toUpperCase());
								} else {
									bk.setBranchName("");
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							if (11 <= custData.length) {
								if (!ObjectUtils.isEmpty(custData[10])) {
									bk.setAction(String.valueOf(custData[10]));
								} else {
									bk.setAction("");
								}
							} else {
								stringJoiner.add("Empty cell");
								bk.setRemark(stringJoiner.toString());
							}

							// stringJoiner.add("User ID Not Found");
							// bk.setRemark(stringJoiner.toString());
						}
						bk.setStatus(0);
						bk.setCreatedon(date);

						if (null != id) {
							bk.setCreatedby(id);
						}

						// bk.setUpdateon(0);

						bulkUsers.add(bk);

//					}else {
//						LOGGER.info("Empty row..!");
//					}

						// allData.add(bk);
					}

					LOGGER.info(bulkUsers);
					for (BulkUsersCreationEntity bc : bulkUsers) {
						if (null != bc.getRemark()) {
							bc.setStatus(4);
							String remaks = bc.getRemark().concat(".");
							bc.setRemark(remaks);
							success.add(bc);
							bulkUsers1.add(bc);
							errorCounter++;
						} else {
							success.add(bc);
							bulkUsers1.add(bc);
							successCounter++;
						}
					}

					totalCounter = successCounter + errorCounter;
					bulkUserExcel(success, fileName, id, successCounter, errorCounter, totalCounter, userBranchCode);
					bulkUser.setBulkUsersCreation(bulkUsers1);
				} else {
					message = "Invalid File Format";
				}
			}
//			else {
//				message1 = "Invalid File Format";
//			}

			if (!message1.isEmpty() || !message.isEmpty()) {
				bulkUser.setBulkUsersCreation(bulkUsers1);
				if (!message1.isEmpty()) {
					bulkUser.setExcelRemark(message1);
				} else {
					bulkUser.setExcelRemark(message);
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return bulkUser;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<BulkUsersCreationEntity> getBulkUsersData(String id1) {
		LOGGER.info("BulkUsersCreationServImpl ->getBulkUsersData()");
		Integer id = Integer.valueOf(id1);
		List<BulkUsersCreationEntity> template = bulkUsersCreationRepository.getBulkUsersData(id);
		return template;
	}

	@Override
	public BulkUserUploadBean getBulkUsersValidateUp(List<BulkUsersCreationEntity> bulkUsersUp) {
		LOGGER.info("BulkUsersCreationServImpl ->getBulkUsersValidateUp()");
		List<BulkUsersCreationEntity> error = new ArrayList<>();
		List<BulkUsersCreationEntity> sucess = new ArrayList<>();
		List<BulkUsersCreationEntity> list = new ArrayList<>();
		BulkUserUploadBean list2 = new BulkUserUploadBean();
		Boolean userMS = false;
		int successCount = 0;
		int errorCount = 0;
		try {
			if (!ObjectUtils.isEmpty(bulkUsersUp)) {
				for (BulkUsersCreationEntity bulkuser : bulkUsersUp) {
					if (null != bulkuser.getUserId()) {
						LOGGER.info("BulkUsersCreationServImpl ->getBulkUsersValidateUp() -> UserId ::"+bulkuser.getUserId().toLowerCase());
						ResponseMessageBean count = checkUser(bulkuser.getUserId().toLowerCase(), "");

						if (count.getResponseCode().equalsIgnoreCase("500")) {
							bulkuser.setRemark("BulkUpdateSucess");
							bulkuser.setStatus(1);
							sucess.add(bulkuser);
							successCount++;
						} else {
							bulkuser.setRemark("BulkUpdateError");
							bulkuser.setStatus(4);
							error.add(bulkuser);
							errorCount++;
						}
					} else {

					}
				}

			}

			if (null != sucess && !sucess.isEmpty()) {
				userMS = editUserDetails(sucess);
				error.addAll(sucess);
			}

			if (userMS == true) {
				list.addAll(error);
			} else {
				list.addAll(error);
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}
		list2.setBulkUsersCreation(list);
		list2.setSuccessCount(successCount);
		list2.setErrorCount(errorCount);
		return list2;
	}

	@SuppressWarnings("unused")
	@Override
	public BulkUserUploadBean getBulkUsersValidateIn(List<BulkUsersCreationEntity> bulkUsersIn) {
		LOGGER.info("BulkUsersCreationServImpl ->getBulkUsersValidateIn()");
		List<BulkUsersCreationEntity> error = new ArrayList<>();
		List<BulkUsersCreationEntity> sucess = new ArrayList<>();
		List<BulkUsersCreationEntity> list = new ArrayList<>();
		BulkUserUploadBean list2 = new BulkUserUploadBean();
		int errorCounter = 0;
		int successCounter = 0;
		Boolean userMS = false;
		try {
			if (!ObjectUtils.isEmpty(bulkUsersIn)) {
				for (BulkUsersCreationEntity bulkuser : bulkUsersIn) {
					int mobLength = (bulkuser.getMobileNumber().toString()).length();
					if (null != bulkuser.getUserId() && null != bulkuser.getEmail()) {

						ResponseMessageBean count = checkUser(bulkuser.getUserId().toLowerCase(), "");

						if (count.getResponseCode().equalsIgnoreCase("200")) {
							if (null != bulkuser.getFirstName()) {
//								if (null != bulkuser.getLastName()) {

								if (null != bulkuser.getRole()) {

									if (null != bulkuser.getMobileNumber() && mobLength == 10) {

										if (null != bulkuser.getBranchCode()) {
											if (null != bulkuser.getReportingBranch()) {
												if (null != bulkuser.getBranchName()) {
													bulkuser.setRemark("BulkInsertSucess");
													bulkuser.setStatus(1);
												} else {
													bulkuser.setRemark("BulkInsertError");
													bulkuser.setStatus(4);
												}
											} else {
												if (bulkuser.getRole().equals(ApplicationConstants.HOMAKER_ID)
														|| bulkuser.getRole()
																.equals(ApplicationConstants.HOCHECKER_ID)) {
													bulkuser.setRemark("BulkInsertSucess");
													bulkuser.setStatus(1);
												} else {
													bulkuser.setRemark("BulkInsertError");
													bulkuser.setStatus(4);
												}
											}
										} else {
											bulkuser.setRemark("BulkInsertError");
											bulkuser.setStatus(4);
										}
									} else {
										bulkuser.setRemark("BulkInsertError");
										bulkuser.setStatus(4);
									}
								} else {
									bulkuser.setRemark("BulkInsertError");
									bulkuser.setStatus(4);
								}

//								} else {
//									//bulkuser.setRemark("BulkInsertError");
//									//bulkuser.setStatus(4);
//								}
							} else {
								bulkuser.setRemark("BulkInsertError");
								bulkuser.setStatus(4);
							}

						} else {
							bulkuser.setRemark("BulkInsertError");
							bulkuser.setStatus(4);
						}

					} else {
						bulkuser.setRemark("BulkInsertError");
						bulkuser.setStatus(4);

					}

					if (4 == bulkuser.getStatus()) {
						error.add(bulkuser);
						errorCounter++;
					} else {
						sucess.add(bulkuser);
						successCounter++;
					}
				}
			}

			if (null != sucess && !sucess.isEmpty()) {
				userMS = createUserMster(sucess);
				error.addAll(sucess);
			} else {
				// error.addAll(error);
			}

//			if(userMS == true) {
//				   list.addAll(error);
//			}else {
//				   list.addAll(error);
//			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);

		}

		list2.setBulkUsersCreation(error);
		list2.setSuccessCount(successCounter);
		list2.setErrorCount(errorCounter);

		return list2;

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public ResponseMessageBean checkUser(String user, String email) {
		LOGGER.info("BulkUsersCreationServImpl->checkUser----------Start :: USER ID : "+user);
		ResponseMessageBean rmb = new ResponseMessageBean();
		try {
			// String sqlEmailExist = userMasterRepository.userEmailExistOrNot(email);
			
			String sqlUserExist = userMasterRepository.userExistOrNot(user);
			// LOGGER.info(sqlEmailExist);
			LOGGER.info("sqlUserExist :: "+sqlUserExist);

			if (!(sqlUserExist.equalsIgnoreCase("0"))) {
				rmb.setResponseCode("500");
				rmb.setResponseMessage("User ID Already Exist");
//			} else if (!(sqlEmailExist.equalsIgnoreCase("0"))) {
//				rmb.setResponseCode("500");
//				rmb.setResponseMessage("Email ID Already Exist");
			} else {
				rmb.setResponseCode("200");
				rmb.setResponseMessage("Not exist");
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		LOGGER.info("BulkUsersCreationServImpl->checkUser----------End");
		return rmb;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public Boolean createUserMster(List<BulkUsersCreationEntity> sucess) {
		LOGGER.info("BulkUsersCreationServImpl-> createUserMster----------Start");
		RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
		try {
			for (BulkUsersCreationEntity list : sucess) {
				// String randomNumber = randomNumberGenerator.generateRandomString();
				// String encryptpass = EncryptorDecryptor.encryptData(randomNumber);
				// LOGGER.info("user auto genrated plain text password---->" + randomNumber);
				String encryptpass = "4EGbSiJL8tzKRxx71xCslg==";
				LOGGER.info("User auto generated sha2 password--->" + encryptpass);
				Date date = new Date();
				User userM = new User();

				userM.setUserid(list.getUserId().toLowerCase());
				userM.setPassword(encryptpass);
				userM.setStatusId(BigInteger.valueOf(3));
				userM.setCreatedBy(list.getCreatedby().toString());
				userM.setCreatedOn(date);
				userM.setUpdatedBy(list.getUpdateby().toString());
				userM.setUpdatedOn(date);
				userM.setLoginType("AD");
				userM.setThumbnail(null);
				userM.setIsdeleted('N');
				userM.setWrong_attempts(new BigDecimal(0));
				userM.setBranchCode(list.getBranchCode().trim()); // BRANCH OFFICE
				userM.setReportingBranch(list.getReportingBranch());
				userM.setBRANCH_NAME(list.getBranchName());
				User userid = userMasterRepository.save(userM);

				if (null != userid) {
					Integer id = userid.getId().intValue();
					userDetails(list, id);

				}
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		LOGGER.info("BulkUsersCreationServImpl->createUserMster----------End");
		return true;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public void userDetails(BulkUsersCreationEntity sucess, Integer id) {
		LOGGER.info("BulkUsersCreationServImpl->userDetails----------Start");
		Date date = new Date();
		UserDetails userDetails = new UserDetails();
		try {

			userDetails.setUserMasterId(id);
			userDetails.setRoleid(sucess.getRole().intValue());
			userDetails.setEmail(sucess.getEmail());
			userDetails.setMobileNumber(sucess.getMobileNumber().toString());
			userDetails.setCreatedOn(date);
			userDetails.setStatusId(BigInteger.valueOf(3));
			userDetails.setPhonenumber(sucess.getMobileNumber().toString());
			userDetails.setUpdatedBy(sucess.getUpdateby().toString());
			userDetails.setFirstName(sucess.getFirstName());
			userDetails.setLastName(sucess.getLastName());
			userDetails.setCreatedBy(BigInteger.valueOf(sucess.getCreatedby()));
			userDetails.setUpdatedOn(date);
			userDetailsRepository.save(userDetails);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		LOGGER.info("BulkUsersCreationServImpl->userDetails----------End");
	}

	@RequestMapping(value = "/editUserDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public boolean editUserDetails(@RequestBody List<BulkUsersCreationEntity> sucess) {
		LOGGER.info("BulkUsersCreationServImpl->editUserDetails----------Start");
		ResponseMessageBean response = null;
		try {
			Boolean usr = editUser(sucess);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		LOGGER.info("BulkUsersCreationServImpl->editUserDetails----------End");
		return true;
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public boolean editUser(List<BulkUsersCreationEntity> sucess) {
		LOGGER.info("BulkUsersCreationServImpl->editUser----------Satart");
		try {
			for (BulkUsersCreationEntity sb : sucess) {
				Date date = new Date();
				LOGGER.info("BulkUsersCreationServImpl->editUser---------- User Id 1 : "+sb.getUserId().toLowerCase());
				User user = userMasterRepository.userIdDetails(sb.getUserId().toLowerCase());
				LOGGER.info("User Master :: "+user.toString());
				user.setUpdatedBy(sb.getUpdateby().toString());
				user.setUpdatedOn(date);
				user.setIsdeleted('N');
				if (null != sb.getBranchCode()) {
					user.setBranchCode(sb.getBranchCode().trim());
				}
				if (null != sb.getReportingBranch()) {
					user.setReportingBranch(sb.getReportingBranch());
				}
				if (null != sb.getBranchName()) {
					user.setBRANCH_NAME(sb.getBranchName());
				}
				userMasterRepository.save(user);

				if (null != user) {
					Integer id = user.getId().intValue();
					LOGGER.info("BulkUsersCreationServImpl->toEditUserDetails---------- User Id 2 : "+id);
					editUserDetails(sb, id);

				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		LOGGER.info("BulkUsersCreationServImpl->editUser----------End");
		return true;

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void editUserDetails(BulkUsersCreationEntity userAddEditReqBean, Integer id) {
		LOGGER.info("BulkUsersCreationServImpl->editUserDetails->Start :: User Id :"+id);
		try {
			Date du = new Date();
			UserDetails userDetails1 = userDetailsRepository.findByuserMasterId(id);
			LOGGER.info("user Details :: "+userDetails1.toString());
			// UserDetails ud =
			// userDetailsRepository.findByemail(userAddEditReqBean.getEMAIL());

			if (null != userAddEditReqBean.getEmail()) {
				userDetails1.setEmail(userAddEditReqBean.getEmail());
			}

			if (null != userAddEditReqBean.getMobileNumber()) {
				userDetails1.setPhonenumber(userAddEditReqBean.getMobileNumber().toString());
				userDetails1.setMobileNumber(userAddEditReqBean.getMobileNumber().toString());
			}

			if (null != userAddEditReqBean.getFirstName()) {
				userDetails1.setFirstName(userAddEditReqBean.getFirstName());
			}

			if (null != userAddEditReqBean.getLastName()) {
				userDetails1.setLastName(userAddEditReqBean.getLastName());
			}

			if (null != userAddEditReqBean.getRole()) {
				userDetails1.setRoleid(userAddEditReqBean.getRole());
			}

			userDetails1.setUpdatedOn(du);

//			if (null != userAddEditReqBean.getBranchName()) {
//				userDetails1.setUserMasterId(id);
//			}

			userDetails1.setUpdatedBy(userAddEditReqBean.getUpdateby().toString());

			userDetailsRepository.save(userDetails1);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}

		LOGGER.info("BulkUsersCreationServImpl->editUserDetails->End :: User Id :"+id);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public Boolean getBulkUsersDataUpdate(List<BulkUsersCreationEntity> bulkUsers) {
		LOGGER.info("BulkUsersCreationServImpl->getBulkUsersDataUpdate----------Start");
		try {
			for (BulkUsersCreationEntity bulkdata : bulkUsers) {
				bulkUsersCreationRepository.getBulkUsersDataUpdate(bulkdata.getId(), bulkdata.getStatus(),
						bulkdata.getRemark());
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		LOGGER.info("BulkUsersCreationServImpl->getBulkUsersDataUpdate----------End");
		return true;

	}

//	@Override
//	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
//	public void getBulkUsers(Integer id1, List<BigDecimal> listId) {
//		LOGGER.info("BulkUsersCreationService->getBulkUsers----------Start");
//			bulkUsersCreationRepository.getBulkUsersDataApproval(id1,listId);
//
//	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String bulkUsersCreationReject(RequestParamBean requestBean) {
		LOGGER.info("BulkUsersCreationServImpl->bulkUsersCreationReject----------Start");
		try {
			bulkUsersCreationRepository.bulkUsersCreationReject(Integer.valueOf(requestBean.getId2()));
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("BulkUsersCreationService->bulkUsersCreationReject----------End");
		return "Success";
	}

	@Override
	public List<BulkUsersExcelDetailsEntity> getBulkUsersExcelDetails(RequestParamBean requestBean) {
		List<BulkUsersExcelDetailsEntity> template = new ArrayList<>();
		if (ApplicationConstants.ZOCHECKER_ID == Integer.valueOf(requestBean.getId2())) {
			template = bulkUsersExcelDetailsRepository.getBulkUsersExcelZonalDetails(requestBean.getId1());
		} else {
			template = bulkUsersExcelDetailsRepository.getBulkUsersExcelDetails();
		}
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public Boolean bulkUserExcel(List<BulkUsersCreationEntity> sucess, String excelName, Integer userId,
			int successCounter, int errorCounter, int totalCounter, String userBranchCode) {
		LOGGER.info("BulkUsersCreationServImpl-> bulkUserExcel----------Start");
		try {
			//Date date = new Date();
			BulkUsersExcelDetailsEntity bulkE = null;
			BulkUsersExcelDetailsEntity bulkExcel = new BulkUsersExcelDetailsEntity();
			if (successCounter == 0 && errorCounter != 0) {
				bulkExcel.setExcelname(excelName);
				bulkExcel.setCreatedby(userId);
				bulkExcel.setCreatedon(new Date());
				bulkExcel.setUpdateon(new Date());
				bulkExcel.setStatus(4);
				bulkExcel.setErrorCount(String.valueOf(errorCounter));
				bulkExcel.setSuccessCount(String.valueOf(successCounter));
				bulkExcel.setTotalCount(String.valueOf(totalCounter));
				bulkExcel.setBranchcode(userBranchCode);
				bulkE = bulkUsersExcelDetailsRepository.save(bulkExcel);

			} else {

				bulkExcel.setExcelname(excelName);
				bulkExcel.setCreatedby(userId);
				bulkExcel.setCreatedon(new Date());
				bulkExcel.setErrorCount(String.valueOf(errorCounter));
				bulkExcel.setSuccessCount(String.valueOf(successCounter));
				bulkExcel.setTotalCount(String.valueOf(totalCounter));
				bulkExcel.setStatus(0);
				bulkExcel.setBranchcode(userBranchCode);
				bulkE = bulkUsersExcelDetailsRepository.save(bulkExcel);
			}

			for (BulkUsersCreationEntity list : sucess) {
				list.setExcelid(bulkE.getId().intValue());
			}

			bulkUsersCreationRepository.save(sucess);
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		LOGGER.info("BulkUsersCreationServImpl->bulkUserExcel----------End");
		return true;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<BulkUsersCreationEntity> getBulkUsersDataUp(Integer id2) {
		LOGGER.info("BulkUsersCreationServImpl ->getBulkUsersValidateUp() -> Excel ID ::"+id2);

		List<BulkUsersCreationEntity> template = bulkUsersCreationRepository.getBulkUsersDataUp(id2);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<BulkUsersCreationEntity> getBulkUsersDataIp(Integer id2) {
		LOGGER.info("BulkUsersCreationServImpl ->getBulkUsersDataIp() -> Excel ID ::"+id2);
		List<BulkUsersCreationEntity> template = bulkUsersCreationRepository.getBulkUsersDataIp(id2);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public void getBulkUsersExcelDataUpdate(BigDecimal excelId, Date date, int successCount, int errorCount,
			Integer sflags) {
		String success = String.valueOf(successCount);
		String error = String.valueOf(errorCount);
		bulkUsersExcelDetailsRepository.getBulkUsersExcelDataUpdate(excelId, date, success, error, sflags);

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public Boolean getBulkUsers(List<BulkUsersCreationEntity> bulkUsersCreationB) {
		LOGGER.info("BulkUsersCreationServImpl->getBulkUsers----------Start");
		try {
			for (BulkUsersCreationEntity bulkdata : bulkUsersCreationB) {
				bulkUsersCreationRepository.save(bulkdata);
			}

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		LOGGER.info("BulkUsersCreationServImpl->getBulkUsers----------End");
		return true;

	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public void getBulkUsersExcelDataReject(String id1, Date date, String id2, String id3) {
		BigDecimal id = new BigDecimal(id2);
		bulkUsersExcelDetailsRepository.getBulkUsersExcelDataUpdate(Integer.valueOf(id1), date, id, id3);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<BulkUsersExcelDetailsEntity> getBulkUsersExcelStatus() {
		// List<BulkUsersExcelDetailsEntity> template = new ArrayList<>();
		List<BulkUsersExcelDetailsEntity> template = null;
		// List<BulkUsersExcelDetailsEntity> excelDataPending = null;
//		template = bulkUsersExcelDetailsRepository.getBulkUsersExcelStatus();
		template = bulkUsersExcelDetailsRepository.getBulkUsersExcelStatus();
//		template.addAll(excelData);
//		template.addAll(excelDataPending);

		return template;
	}

	@Override
	public Integer getBulkUsersDataMakerErrorCount(Integer excelId) {
		LOGGER.info("BulkUsersCreationServImpl->getBulkUsersDataMakerErrorCount----------Start");
		Integer errorCount = null;
		try {

			errorCount = bulkUsersCreationRepository.getBulkUsersDataMakerErrorCount(excelId);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);

		}
		LOGGER.info("BulkUsersCreationServImpl->getBulkUsersDataMakerErrorCount----------End");
		return errorCount;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<BulkUsersCreationEntity> getBulkUsersExcelErrorDetails(String id1) {
		Integer id = Integer.valueOf(id1);
		List<BulkUsersCreationEntity> template = bulkUsersCreationRepository.getBulkUsersExcelErrorDetails(id);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<BulkUsersExcelDetailsEntity> getBulkUsersExcelZonalStatus(String id1) {
		List<BulkUsersExcelDetailsEntity> template = null;
		template = bulkUsersExcelDetailsRepository.getBulkUsersExcelZonalStatus(id1);

		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<UserDetailsBean> useruserDetails(Integer id1) {
		List<UserDetailsBean> template = new ArrayList<>();
		List<Object[]> obj = null;
		obj = userMasterRepository.userDetails(id1);

		for (Object[] o : obj) {
			UserDetailsBean us = new UserDetailsBean();
			us.setUSERID(String.valueOf(o[0]));
			us.setROLE(String.valueOf(o[1]));
			us.setUSER_ID(new BigDecimal(o[2].toString()));
			us.setFIRST_NAME(String.valueOf(o[3]));
			us.setLAST_NAME(String.valueOf(o[4]));
			us.setROLE_ID(new BigDecimal(o[5].toString()));
			us.setROLETYPE(new BigDecimal(o[6].toString()));
			us.setRoleTypeName(String.valueOf(o[7]));
			us.setBranchCode(String.valueOf(o[8]).trim());
			template.add(us);
		}
		return template;
	}

	public Boolean checkCharacterStringContains(String str) {
		LOGGER.info("BulkUsersCreationServImpl->checkCharacterStringContains----------Start");
		Boolean result = null;
		try {
			result = str.matches("^[a-zA-Z,\\s]*$");

		} catch (Exception e) {
			// LOGGER.info("Exception:", e);
			// return result;
		}
		LOGGER.info("BulkUsersCreationServImpl->checkCharacterStringContains----------End");
		return result;

	}

	private boolean isSheetEmpty(XSSFSheet sheet) {
		Iterator rows = sheet.rowIterator();
		Row row = null;
		Cell cell = null;
		while (rows.hasNext()) {
			row = (Row) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext()) {
				cell = (Cell) cells.next();
				if (!cell.getStringCellValue().isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	public Boolean checkEmailValidation(String str) {
		LOGGER.info("BulkUsersCreationServImpl->checkEmailValidation----------Start");
		Boolean result = null;
		try {
			final String EMAILPATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

			Pattern pattern = Pattern.compile(EMAILPATTERN);

			Matcher matcher = pattern.matcher(str);

			result = matcher.matches();

		} catch (Exception e) {
			// LOGGER.info("Exception:", e);
			// return result;
		}
		LOGGER.info("BulkUsersCreationService->checkEmailValidation----------End");
		return result;

	}

	public boolean containsValue(Row row, int fcell, int lcell) {
		boolean flag = false;
		for (int i = fcell; i < lcell; i++) {
			if (StringUtils.isEmpty(String.valueOf(row.getCell(i))) == true
					|| StringUtils.isWhitespace(String.valueOf(row.getCell(i))) == true
					|| StringUtils.isBlank(String.valueOf(row.getCell(i))) == true
					|| String.valueOf(row.getCell(i)).length() == 0 || row.getCell(i) == null) {
			} else {
				flag = true;
			}
		}
		return flag;
	}

	public Boolean checkCharacterStringWithNumericContains(String str) {
		LOGGER.info("BulkUsersCreationServImpl->checkCharacterStringWithNumericContains----------Start");
		Boolean result = null;
		try {
			result = str.matches("^[a-zA-Z0-9,.\\s]*$");

		} catch (Exception e) {
			// LOGGER.info("Exception:", e);
			// return result;
		}
		LOGGER.info("BulkUsersCreationServImpl->checkCharacterStringWithNumericContains----------End");
		return result;

	}
	

}
