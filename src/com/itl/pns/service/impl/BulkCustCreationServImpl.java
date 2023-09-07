package com.itl.pns.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.itl.pns.dao.OmniChannelDao;
import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.repository.CustomerRepository;
import com.itl.pns.service.BulkCustCreationService;
import com.itl.pns.util.EncryptDeryptUtility;
import com.itl.pns.util.EncryptorDecryptor;
import com.itl.pns.util.RandomNumberGenerator;

@Service
@Qualifier("BulkCustCreationService")
@Transactional
public class BulkCustCreationServImpl implements BulkCustCreationService {

	static Logger LOGGER = Logger.getLogger(BulkCustCreationServImpl.class);
	@Autowired
	CustomerRepository registrationRepository;

	@Autowired
	RestServiceCall rest;

	@Autowired
	OmniChannelDao omniChannelDao;

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public String createBulkCustomerFile(MultipartFile file1) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			FileInputStream file = new FileInputStream(convertMultiPartToFile(file1));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			int j = 0;
			while (rowIterator.hasNext()) {
				
				Row row = rowIterator.next();
				String data = "";
				Iterator<Cell> cellIterator = row.cellIterator();
				if (j != 0) {
					data = "";
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						String value = "";
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_NUMERIC:
							cell.setCellType(cell.CELL_TYPE_STRING);
							value = String.valueOf(cell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							value = String.valueOf(cell.getStringCellValue());
							break;
						}
						data = data.concat(value).concat("-");
					}
					data = data.concat(String.valueOf(1111)).concat("-").concat(String.valueOf(1111));
					System.out.println("data" + data);
					map.put(j, data);
				}
				j++;
			}
			for (Integer key : map.keySet()) {
				String value = map.get(key);
				String[] custData = value.split("-");
				String name = custData[0];
				String mobile = EncryptorDecryptor.encryptData(custData[1]);
				String email = EncryptorDecryptor.encryptData(custData[2]);
				String cif = custData[3];
				String mpin = custData[4];
				String tpin = custData[4];
				CustomerEntity bean = registrationRepository.findBymobile(custData[1]);
				if (ObjectUtils.isEmpty(bean)) {
					try {
						String referalno = RandomNumberGenerator.getRandomString(8);
						saveCustomer(name, mobile, email, cif, mpin, tpin, referalno);

					} catch (Exception e) {
						LOGGER.info("Exception:", e);
						return "error";
					}
				} else {
					String mobileNumber = bean.getMobile();
					return mobileNumber;
				}

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		return "success";
	}

	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public Boolean saveCustomer(String name, String mobile, String email, String cif, String mpin, String tpin,
			String referealcode) {
		Date date = new Date();
		String password = "Test@123";
		String passPhrase = mobile + "jrD@Mt6i";
		EncryptDeryptUtility encryptDeryptUtility = new EncryptDeryptUtility();

		String encpass = encryptDeryptUtility.encryptNonAndroid(password, passPhrase);
		try {
			CustomerEntity registrationDetails = new CustomerEntity();
			registrationDetails.setMpin(mpin);
			registrationDetails.setTpin(tpin);
			registrationDetails.setAppid(4);
			registrationDetails.setStatusid(3);
			registrationDetails.setCustomername(name);
			registrationDetails.setEmail(email);
			registrationDetails.setMobile(mobile);
			registrationDetails.setCif(cif);
			registrationDetails.setUsername(mobile);
			registrationDetails.setCreatedon(date);
			registrationDetails.setUpdatedon(date);
			registrationDetails.setCreatedby(0);
			registrationDetails.setUpdatedby(0);
			registrationDetails.setWrongattemptsmpin(0);
			registrationDetails.setUserpassword(encpass);
			registrationDetails.setUtilitylimit(0);
			registrationDetails.setCardlimit(0);
			registrationDetails.setInternationallimit(0);
			registrationDetails.setLocaltrflimits(0);
			registrationDetails.setMobilelastloggedon(date);
			registrationDetails.setWeblastloggenon(date);
			registrationDetails.setFrid("AR");
			registrationDetails.setSsa_active('N');
			registrationDetails.setIsmpinlocked('Y');
			registrationDetails.setIsmpinenabled('Y');
			registrationDetails.setPreferedlanguage("en_US");
			registrationDetails.setDob("29/10/1979");
			registrationDetails.setIsmobileenabled('Y');
			registrationDetails.setIstpinlocked('N');
			registrationDetails.setIsPushNotificationEnabled("N");
			registrationDetails.setReferealcode(referealcode);
			registrationRepository.saveAndFlush(registrationDetails);

		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return false;
		}
		return true;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}
