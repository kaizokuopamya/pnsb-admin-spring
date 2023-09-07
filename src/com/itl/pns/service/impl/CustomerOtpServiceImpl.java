package com.itl.pns.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.itl.pns.corp.service.CustomerOtpService;
import com.itl.pns.dao.CustomerOtpDao;

public class CustomerOtpServiceImpl implements CustomerOtpService {

	@Autowired
	private CustomerOtpDao customerOtpDao;

	@Override
	public void getAllCustomerOtp() {
		customerOtpDao.getAllCustomerOtp();

	}

	@Override
	public void getCustomerOtpByCustomerId(String customerOtp) {
		customerOtpDao.getCustomerOtpByCustomerId(customerOtp);

	}

}
