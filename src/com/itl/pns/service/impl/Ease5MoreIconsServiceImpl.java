package com.itl.pns.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.corp.entity.CorpServiceRequestEntity;
import com.itl.pns.corp.service.CorpServiceRequestService;
import com.itl.pns.entity.Ease5MoreIconsEntity;
import com.itl.pns.repository.Ease5MoreIconsRepository;
import com.itl.pns.service.Ease5MoreIconsService;

@Service
@Qualifier("Ease5MoreIconsService")
@Transactional
public class Ease5MoreIconsServiceImpl implements Ease5MoreIconsService {
	static Logger LOGGER = Logger.getLogger(Ease5MoreIconsServiceImpl.class);

	
	@Autowired
	Ease5MoreIconsRepository ease5MoreIconsRepository;
	
	@Autowired
	private CorpServiceRequestService corpserviceRequestService;

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<Ease5MoreIconsEntity> getEaseMoreIconsList(String id1) {
		BigDecimal appId = new BigDecimal(id1);
		List<Ease5MoreIconsEntity> template = ease5MoreIconsRepository.getEase5MoreIconsListData(appId);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String ease5MoreIconsDelt(String id1,String id2) {
		LOGGER.info("Ease5MoreIconsService->ease5MoreIconsDelt----------Start");
		try {
			ease5MoreIconsRepository.ease5MoreIconsDelt(Integer.valueOf(id1),new BigDecimal(id2));
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5MoreIconsService->ease5MoreIconsDelt----------End");
		return "Success";
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String addEase5MoreIconsData(Ease5MoreIconsEntity ease5MoreIconsList) {
		LOGGER.info("Ease5MoreIconsService->addEase5MoreIconsData----------Start");
		try {
			if(ObjectUtils.isNotEmpty(ease5MoreIconsList)) {
				//for(Ease5MoreIconsEntity ease : ease5MoreIconsList) {
					if(ease5MoreIconsList.getIsActiveAll().equalsIgnoreCase("activeAll")){
						List<CorpServiceRequestEntity> corpserviceRequestEntity = corpserviceRequestService
								.getChannelDetailsActiveService();
						for(CorpServiceRequestEntity corpServ: corpserviceRequestEntity) {
							Ease5MoreIconsEntity ease1 = new Ease5MoreIconsEntity();
							ease1.setAppredirectionlink(ease5MoreIconsList.getAppredirectionlink());
							ease1.setCreatedby(ease5MoreIconsList.getCreatedby());
							ease1.setCreatedon(new Date());
							ease1.setAppid(corpServ.getId());
							ease1.setImagelink(ease5MoreIconsList.getImagelink());
							ease1.setModulename(ease5MoreIconsList.getModulename());
							ease1.setOptionname(ease5MoreIconsList.getOptionname());
							ease1.setOptiontype(ease5MoreIconsList.getOptiontype());
							ease1.setPagename(ease5MoreIconsList.getPagename());
							ease1.setRedirectiontype(ease5MoreIconsList.getRedirectiontype());
							ease1.setRedirectionurl(ease5MoreIconsList.getRedirectionurl());
							ease1.setSeqnumber(ease5MoreIconsList.getSeqnumber());
							ease1.setStatusid(new BigDecimal(3));
							ease5MoreIconsRepository.save(ease1);
						}
						
					}else {
						ease5MoreIconsList.setCreatedon(new Date());
						ease5MoreIconsList.setStatusid(new BigDecimal(3));
				        ease5MoreIconsRepository.save(ease5MoreIconsList);
					}
				//}
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5MoreIconsService->addEase5MoreIconsData----------End");
		return "Success";
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<Ease5MoreIconsEntity> getEaseMoreIconsId(String id1) {
		List<Ease5MoreIconsEntity> template = ease5MoreIconsRepository.getEaseMoreIconsId(Integer.valueOf(id1));
		return template;
	}
	
	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String editEase5MoreIconsData(Ease5MoreIconsEntity ease5MoreIconsList) {
		LOGGER.info("Ease5MoreIconsService->editEase5MoreIconsData----------Start");
		try {
			if(ObjectUtils.isNotEmpty(ease5MoreIconsList)) {
			
				ease5MoreIconsRepository.update(ease5MoreIconsList.getImagelink(),ease5MoreIconsList.getRedirectionurl(),ease5MoreIconsList.getStatusid(),ease5MoreIconsList.getId(),ease5MoreIconsList.getAppredirectionlink());
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5MoreIconsService->editEase5MoreIconsData----------End");
		return "Success";
	}

}
