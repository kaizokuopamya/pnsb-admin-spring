package com.itl.pns.controller;

import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.TinMultilevelCallBackLogs;
import com.itl.pns.service.FinancialCountMobService;
import com.itl.pns.util.EncryptorDecryptor;

@RequestMapping("/tinCallLogs")
@RestController
public class TinMultilvlCallBackLogsController {
	
	private static final Logger logger = LogManager.getLogger(TinMultilevelCallBackLogs.class);

	@Autowired
	FinancialCountMobService financialCountMobService;
	
	@PostMapping("/gettinMultilevelCallLogs")
	public ResponseEntity<ResponseMessageBean> gettinMultilevelCallLogs(
			@RequestBody TinMultilevelCallBackLogs tinCallLogs) {

		ResponseMessageBean res = new ResponseMessageBean();
		try {
			List<TinMultilevelCallBackLogs> service = financialCountMobService
					.gettinMultilevelCallLogs(tinCallLogs);
			
			logger.info(" inside gettinMultilevelCallLogs ");
				
				if (!ObjectUtils.isEmpty(service)) {
					res.setResponseCode("200");
					res.setResult(service);
				} else {
					res.setResponseCode("202");
					res.setResponseMessage("No Records Found");
				}
		} catch (Exception e) {
			logger.error("Exception occured in TinMultilevelCallLogs", e);

		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
