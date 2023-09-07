package com.itl.pns.service.impl;

import java.math.BigDecimal;
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
import com.itl.pns.entity.Ease5QuickAccessEntity;
import com.itl.pns.repository.Ease5QuickAccessRepository;
import com.itl.pns.service.Ease5QuickAccessService;

@Service
@Qualifier("Ease5QuickAccessService")
@Transactional
public class Ease5QuickAccessServiceImpl implements Ease5QuickAccessService {
	static Logger LOGGER = Logger.getLogger(Ease5QuickAccessServiceImpl.class);


	@Autowired
	Ease5QuickAccessRepository ease5QuickAccessRepository;
	
	@Autowired
	private CorpServiceRequestService corpserviceRequestService;
	
	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<Ease5QuickAccessEntity> getEase5QuickAccessList(String id1) {
		BigDecimal appId = new BigDecimal(id1);
		List<Ease5QuickAccessEntity> template = ease5QuickAccessRepository.getEase5QuickAccessListData(appId);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String ease5QuickAccessDelt(String id1, String id2) {
		LOGGER.info("Ease5QuickAccessServiceImpl->ease5QuickAccessDelt----------Start");
		try {
			ease5QuickAccessRepository.ease5QuickAccessDelt(Integer.valueOf(id1),new BigDecimal(id2));
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5QuickAccessServiceImpl->ease5QuickAccessDelt----------End");
		return "Success";
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String addEase5QuickAccess(Ease5QuickAccessEntity ease5QuickAccessList) {
		LOGGER.info("Ease5QuickAccessServiceImpl->addEase5QuickAccess----------Start");
		try {
			if(ObjectUtils.isNotEmpty(ease5QuickAccessList)) {
				//for(Ease5MoreIconsEntity ease : ease5MoreIconsList) {
					if(ease5QuickAccessList.getIsActiveAll().equalsIgnoreCase("activeAll")){
						List<CorpServiceRequestEntity> corpserviceRequestEntity = corpserviceRequestService
								.getChannelDetailsActiveService();
						for(CorpServiceRequestEntity corpServ: corpserviceRequestEntity) {
							Ease5QuickAccessEntity ease1 = new Ease5QuickAccessEntity();
							ease1.setAppredirectionlink(ease5QuickAccessList.getAppredirectionlink());
							ease1.setCreatedby(ease5QuickAccessList.getCreatedby());
							ease1.setCreatedon(new Date());
							ease1.setAppid(corpServ.getId());
							ease1.setImagelink(ease5QuickAccessList.getImagelink());
							ease1.setModulename(ease5QuickAccessList.getModulename());
							ease1.setOptionname(ease5QuickAccessList.getOptionname());
							ease1.setOptiontype(ease5QuickAccessList.getOptiontype());
							ease1.setPagename(ease5QuickAccessList.getPagename());
							ease1.setRedirectiontype(ease5QuickAccessList.getRedirectiontype());
							ease1.setRedirectionurl(ease5QuickAccessList.getRedirectionurl());
							ease1.setSeqnumber(ease5QuickAccessList.getSeqnumber());
							ease1.setStatusid(new BigDecimal(3));
							ease5QuickAccessRepository.save(ease1);
						}
						
					}else {
						ease5QuickAccessList.setCreatedon(new Date());
						ease5QuickAccessList.setStatusid(new BigDecimal(3));
						ease5QuickAccessRepository.save(ease5QuickAccessList);
					}
				//}
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5QuickAccessServiceImpl->addEase5QuickAccess----------End");
		return "Success";
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<Ease5QuickAccessEntity> getEase5QuickAccessId(String id1) {
		List<Ease5QuickAccessEntity> template = ease5QuickAccessRepository.getEase5QuickAccessId(Integer.valueOf(id1));
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String editEase5QuickAccessData(Ease5QuickAccessEntity ease5QuickAccessList) {
		LOGGER.info("ease5QuickAccessList "+ease5QuickAccessList);
		LOGGER.info("Ease5QuickAccessServiceImpl->editEase5QuickAccessData----------Start");
		try {
			if(ObjectUtils.isNotEmpty(ease5QuickAccessList)) {
				ease5QuickAccessRepository.update(ease5QuickAccessList.getImagelink(),ease5QuickAccessList.getRedirectionurl(),ease5QuickAccessList.getStatusid(),ease5QuickAccessList.getId(),ease5QuickAccessList.getAppredirectionlink());
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5QuickAccessServiceImpl->editEase5QuickAccessData----------End");
		return "Success";
	}

}
