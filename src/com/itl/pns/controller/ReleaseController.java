package com.itl.pns.controller;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.ReleaseInfoEntity;
import com.itl.pns.repository.ReleaseInfoRepository;

@RestController
@RequestMapping("Release")
public class ReleaseController {
	
	private static final Logger logger = LogManager.getLogger(AdministrationController.class);
	
	@Autowired
	private ReleaseInfoRepository releaseRepository;

	@PostMapping(value = "/getVersion")
	public ResponseEntity<ResponseMessageBean> getVersion() {
		ResponseMessageBean response = new ResponseMessageBean();
		String version = "";
		List<ReleaseInfoEntity> releaseInfoList = releaseRepository.findAllByOrderByIdDesc();
		if (!ObjectUtils.isEmpty(releaseInfoList)) {
			version = releaseInfoList.get(0).getVersion();
		}
		response.setResponseCode("200");
		response.setResult(version);
		logger.info("Version: "+version);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
