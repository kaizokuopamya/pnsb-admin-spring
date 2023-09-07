package com.itl.pns.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.CorpUserBean;
import com.itl.pns.bean.DateBean;
import com.itl.pns.bean.FinancialCountMobBean;
import com.itl.pns.bean.Payaggregatorbean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.SolePropritorRegistrationBean;
import com.itl.pns.bean.TimestampDateBean;
import com.itl.pns.bean.TransactionBean;
import com.itl.pns.corp.entity.CorpTransactionsEntity;
import com.itl.pns.service.FinancialCountMobService;
import com.itl.pns.service.TransactionService;
import com.itl.pns.util.EncryptorDecryptor;

@RestController
@RequestMapping("transaction")
public class TransactionController {

	private static final Logger logger = LogManager.getLogger(TransactionController.class);

	@Autowired
	private Environment environment;

	@Autowired
	FinancialCountMobService financialCountMobService;

	@Autowired
	TransactionService transactionlogservice;

	@RequestMapping(value = "/getTransactions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTransactions(@RequestBody DateBean datebean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<TransactionBean> transactionList = transactionlogservice.getTransactions(datebean);

			if (!ObjectUtils.isEmpty(transactionList)) {
				for (TransactionBean transaction : transactionList) {
					if (null != transaction.getCUSTOMERNAME()) {
						transaction.setCUSTOMERNAME(EncryptorDecryptor.decryptData(transaction.getCUSTOMERNAME()));
					}
					if (null != transaction.getSENDERCUST()) {
						transaction.setSENDERCUST(EncryptorDecryptor.decryptData(transaction.getSENDERCUST()));
					}
					res.setResponseCode("200");
					res.setResult(transactionList);
				}
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getCorpTransactions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getCorpTransactions(@RequestBody DateBean datebean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpTransactionsEntity> transactionList = transactionlogservice.getCorpTransactions(datebean);
			if (!ObjectUtils.isEmpty(transactionList)) {
				res.setResponseCode("200");
				res.setResult(transactionList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// 1. made for mobile retail

	/*
	 * private XSSFSheet createHeaderForExcelColwise(XSSFSheet sheet, int rownum,
	 * Row row, Cell cell) { if (rownum == 0) { row = sheet.createRow(rownum); cell
	 * = row.createCell(0); cell.setCellValue("COUNTDAILY");
	 * 
	 * cell = row.createCell(1); cell.setCellValue("SHORTNAME");
	 * 
	 * cell = row.createCell(2); cell.setCellValue("STATUS");
	 * 
	 * } return sheet; }
	 * 
	 * private File writeDataToExcelFileColwise(List<FinancialCountMobBean>
	 * transactionCountList) { File file = null; String file_name =
	 * environment.getRequiredProperty("message.conversation.path") + "Registration"
	 * + ".xlsx"; file = new File(file_name); XSSFWorkbook workbook = null;
	 * XSSFSheet sheet = null; workbook = new XSSFWorkbook(); sheet =
	 * workbook.createSheet("FirstSheet"); sheet = workbook.getSheetAt(0); Row row =
	 * null; Cell cell = null; int rownum = sheet.getPhysicalNumberOfRows(); sheet =
	 * createHeaderForExcelColwise(sheet, rownum++, row, cell); for
	 * (FinancialCountMobBean bean : transactionCountList) {
	 * 
	 * row = sheet.createRow(rownum++); cell = row.createCell(0);
	 * cell.setCellValue(bean.getCOUNTDAILY());
	 * 
	 * cell = row.createCell(1); cell.setCellValue(bean.getSHORTNAME());
	 * 
	 * cell = row.createCell(2); cell.setCellValue(bean.getSTATUS());
	 * 
	 * }
	 * 
	 * try (FileOutputStream out = new FileOutputStream(file)) {
	 * workbook.write(out);
	 * logger.info("Converstaton Details written successfully on disk."); } catch
	 * (Exception e) { e.printStackTrace();
	 * logger.info("getting error while writing data to the Excel file"); } return
	 * file; }
	 */
	@RequestMapping(value = "/getTotalFinancialTransaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTotalFinancialTransaction(@RequestBody DateBean datebean
	/* HttpServletResponse response */)/* throws IOException */ {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<FinancialCountMobBean> transactionCountList = financialCountMobService
					.getTotalFinancialTransaction(datebean);

			if (!ObjectUtils.isEmpty(transactionCountList)) {

				res.setResponseCode("200");
				res.setResult(transactionCountList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}

			/*
			 * File file = writeDataToExcelFileColwise(transactionCountList);
			 * response.setContentType("application/octet-stream");
			 * response.setHeader("Content-Disposition", String.format("inline; filename=\""
			 * + file.getName() + "\"")); response.setContentLength((int) file.length());
			 * InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			 * FileCopyUtils.copy(inputStream, response.getOutputStream());
			 */

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTotalCountFromCustomers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTotalCountFromCustomers(
			@RequestBody FinancialCountMobBean financialCountMobBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<FinancialCountMobBean> transactionCountList = financialCountMobService
					.getTotalCountFromCustomers(financialCountMobBean);

			if (!ObjectUtils.isEmpty(transactionCountList)) {

				res.setResponseCode("200");
				res.setResult(transactionCountList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// 2.made for mobile retail

	@RequestMapping(value = "/getTotalNonfinancialTransaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTotalNonfinancialTransaction(@RequestBody DateBean datebean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<FinancialCountMobBean> transactionCountList = financialCountMobService
					.getTotalNonfinancialTransaction(datebean);
			if (!ObjectUtils.isEmpty(transactionCountList)) {
				res.setResponseCode("200");
				res.setResult(transactionCountList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTotalNonfinancialTransactionExclude", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTotalNonfinancialTransactionExclude(@RequestBody DateBean datebean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<FinancialCountMobBean> transactionCountList = financialCountMobService
					.getTotalNonfinancialTransactionExclude(datebean);
			if (!ObjectUtils.isEmpty(transactionCountList)) {
				res.setResponseCode("200");
				res.setResult(transactionCountList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// solepropriterregdetail.xls

	@RequestMapping(value = "/getSolePropritorRegistrationDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getSolePropritorRegistrationDetails(@RequestBody DateBean datebean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<SolePropritorRegistrationBean> transactionList = financialCountMobService
					.getSolePropritorRegistrationDetails(datebean);

			for (SolePropritorRegistrationBean transaction : transactionList) {

				if (null != transaction.getCOMPANYCODE() && transaction.getCOMPANYCODE().contains("=")) {
					transaction.setCOMPANYCODE(EncryptorDecryptor.decryptData(transaction.getCOMPANYCODE()));
				}

				if (null != transaction.getCOMPANYNAME() && transaction.getCOMPANYNAME().contains("=")) {
					transaction.setCOMPANYNAME(EncryptorDecryptor.decryptData(transaction.getCOMPANYNAME()));
				}

				if (null != transaction.getCIF() && transaction.getCIF().contains("=")) {
					transaction.setCIF(EncryptorDecryptor.decryptData(transaction.getCIF()));
				}
				if (null != transaction.getEMAIL_ID() && transaction.getEMAIL_ID().contains("=")) {
					transaction.setEMAIL_ID(EncryptorDecryptor.decryptData(transaction.getEMAIL_ID()));
				}

				if (!ObjectUtils.isEmpty(transactionList)) {
					res.setResponseCode("200");
					res.setResult(transactionList);
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// solepropriterregcount.xlsx

	@RequestMapping(value = "/getSolePropritorRegistrationCount", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getSolePropritorRegistrationCount(@RequestBody DateBean datebean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<CorpUserBean> transactionList = financialCountMobService.getSolePropritorRegistrationCount(datebean);

			if (!ObjectUtils.isEmpty(transactionList)) {
				res.setResponseCode("200");
				res.setResult(transactionList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTransactionsType", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTransactionsType() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<String> trasactionType = new ArrayList<>();
			trasactionType.add("Domestic");
			trasactionType.add("International");

			if (!ObjectUtils.isEmpty(trasactionType)) {
				res.setResponseCode("200");
				res.setResult(trasactionType);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTransactionsDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTransactionsDetails(@RequestBody DateBean datebean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<TransactionBean> transactionList = transactionlogservice.getTransactionsDetails(datebean);

			for (TransactionBean transaction : transactionList) {

				if (null != transaction.getCUSTOMERNAME() && transaction.getCUSTOMERNAME().contains("=")) {
					transaction.setCUSTOMERNAME(EncryptorDecryptor.decryptData(transaction.getCUSTOMERNAME()));
				}

				if (null != transaction.getCIF() && transaction.getCIF().contains("=")) {
					transaction.setCIF(EncryptorDecryptor.decryptData(transaction.getCIF()));
				}
				if (!ObjectUtils.isEmpty(transactionList)) {

					res.setResponseCode("200");
					res.setResult(transactionList);
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	// sneha code //

	@RequestMapping(value = "/getTransactionsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTransactionsById(
			@RequestBody FinancialCountMobBean financialCountMobBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {

			List<FinancialCountMobBean> internetbank = financialCountMobService
					.getTransactionsById(financialCountMobBean);

			if (!ObjectUtils.isEmpty(internetbank)) {
				res.setResponseCode("200");
				res.setResult(internetbank);
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

	@RequestMapping(value = "/getFundTransferDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getFundTransferDetails(
			@RequestBody FinancialCountMobBean financialCountMobBean) {
		ResponseMessageBean res = new ResponseMessageBean();
		try {

			List<FinancialCountMobBean> FundTransferDetails = financialCountMobService
					.getFundTransferDetails(financialCountMobBean);

			if (!ObjectUtils.isEmpty(FundTransferDetails)) {
				res.setResponseCode("200");
				res.setResult(FundTransferDetails);
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

	@RequestMapping(value = "/getRibandRmob", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getRibandRmob() {
		logger.info("in MessageReportController -> getChannelDetails()");
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<FinancialCountMobBean> messageReport = financialCountMobService.getRibandRmob();
			if (null != messageReport) {
				res.setResponseCode("200");
				res.setResult(messageReport);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getTotalCustRegWithRibRmob", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTotalCustRegWithRibRmob(
			@RequestBody FinancialCountMobBean financialCountMobBean) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<FinancialCountMobBean> transactionCountList = financialCountMobService
					.getTotalCustRegWithRibRmob(financialCountMobBean);

			if (!ObjectUtils.isEmpty(transactionCountList)) {

				res.setResponseCode("200");
				res.setResult(transactionCountList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getTotalActiveCustWithRibRmob", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTotalActiveCustWithRibRmob(
			@RequestBody FinancialCountMobBean financialCountMobBean) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<FinancialCountMobBean> transactionCountList = financialCountMobService
					.getTotalActiveCustWithRibRmob(financialCountMobBean);

			if (!ObjectUtils.isEmpty(transactionCountList)) {

				res.setResponseCode("200");
				res.setResult(transactionCountList);
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getPaymentaggDataByMerchantname", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getPaymentaggDataByMerchantname(
			@RequestBody Payaggregatorbean payaggregatorbean) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<Payaggregatorbean> payagg = financialCountMobService
					.getPaymentaggDataByMerchantname(payaggregatorbean);
			System.out.println("getPaymentaggDataByMerchantname inside");
			for (Payaggregatorbean payg : payagg) {

				if (null != payg.getCUSTOMERNAME() && payg.getCUSTOMERNAME().contains("=")) {
					payg.setCUSTOMERNAME(EncryptorDecryptor.decryptData(payg.getCUSTOMERNAME()));
				}

				if (null != payg.getACCOUNTNO() && payg.getACCOUNTNO().contains("=")) {
					payg.setACCOUNTNO(EncryptorDecryptor.decryptData(payg.getACCOUNTNO()));
				}

				if (null != payg.getMOBILE() && payg.getMOBILE().contains("=")) {
					payg.setMOBILE(EncryptorDecryptor.decryptData(payg.getMOBILE()));
				}

				System.out.println("getPaymentaggDataByMerchantname inside");
				if (!ObjectUtils.isEmpty(payagg)) {

					res.setResponseCode("200");
					res.setResult(payagg);
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMerchantNameDropdown", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getMerchantNameDropdown() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {
			logger.info("in MessageReportController -> getMerchantNameDropdown()");
			System.out.println("getMerchantNameDropdown inside");
			List<Payaggregatorbean> merchantname = financialCountMobService.getMerchantNameDropdown();
			if (null != merchantname) {
				res.setResponseCode("200");
				res.setResult(merchantname);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@RequestMapping(value = "/getTableNameDropdown", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseMessageBean> getTableNameDropdown() {
		ResponseMessageBean res = new ResponseMessageBean();
		try {

			List<TimestampDateBean> tables = financialCountMobService.getTableNameDropdown();
			if (null != tables) {
				res.setResponseCode("200");
				res.setResult(tables);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode("202");
				res.setResponseMessage("No Records Found");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

}
