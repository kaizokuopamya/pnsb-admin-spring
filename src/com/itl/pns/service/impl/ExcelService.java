package com.itl.pns.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itl.pns.entity.CustomerEntity;
import com.itl.pns.repository.CustomerRepository;
import com.itl.pns.util.ExcelHelper;

@Service
public class ExcelService {
	
	static Logger LOGGER = Logger.getLogger(ExcelService.class);

	
  @Autowired
  CustomerRepository repository;



  public void save(MultipartFile file) {
    try {
      List<CustomerEntity> custData = ExcelHelper.excelToCustEntity(file.getInputStream());
      repository.save(custData);
    } catch (IOException e) {
    	LOGGER.info("Exception:", e);
      throw new RuntimeException("Fail To Store Excel Data: " + e.getMessage());
    }
  }

  public List<CustomerEntity> getAllCustomers() {
    return repository.findAll();
  }
}