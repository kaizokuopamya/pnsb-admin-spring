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
import com.itl.pns.entity.Ease5MoreSubIconsEntity;
import com.itl.pns.repository.Ease5MoreIconsRepository;
import com.itl.pns.repository.Ease5MoreSubIconsRepository;
import com.itl.pns.service.Ease5MoreSubIconsService;

@Service
@Qualifier("Ease5MoreSubIconsService")
@Transactional
public class Ease5MoreSubIconsServiceImpl implements Ease5MoreSubIconsService {

	static Logger LOGGER = Logger.getLogger(Ease5MoreIconsServiceImpl.class);

	
	@Autowired
	Ease5MoreSubIconsRepository ease5MoreSubIconsRepository;
	
	@Autowired
	private CorpServiceRequestService corpserviceRequestService;

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<Ease5MoreSubIconsEntity> getEaseMoreSubIconsList(String id1) {
		BigDecimal appId = new BigDecimal(id1);
		List<Ease5MoreSubIconsEntity> template = ease5MoreSubIconsRepository.getEaseMoreSubIconsList(appId);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String ease5MoreSubIconsDelt(String id1,String id2) {
		LOGGER.info("Ease5MoreSubIconsServiceImpl->ease5MoreIconsDelt----------Start");
		try {
			ease5MoreSubIconsRepository.ease5MoreSubIconsDelt(Integer.valueOf(id1),new BigDecimal(id2));
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5MoreSubIconsServiceImpl->ease5MoreIconsDelt----------End");
		return "Success";
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String addEase5MoreSubIconsData(Ease5MoreSubIconsEntity ease5MoreSubIconsList) {
		LOGGER.info("Ease5MoreSubIconsServiceImpl->addEase5MoreIconsData----------Start");
		try {
			if(ObjectUtils.isNotEmpty(ease5MoreSubIconsList)) {
				//for(Ease5MoreSubIconsEntity ease : ease5MoreSubIconsList) {
					if(ease5MoreSubIconsList.getIsActiveAll().equalsIgnoreCase("activeAll")){
						List<CorpServiceRequestEntity> corpserviceRequestEntity = corpserviceRequestService
								.getChannelDetailsActiveService();
						for(CorpServiceRequestEntity corpServ: corpserviceRequestEntity) {
							Ease5MoreSubIconsEntity ease1 = new Ease5MoreSubIconsEntity();
							ease1.setAppredirectionlink(ease5MoreSubIconsList.getAppredirectionlink());
							ease1.setCreatedby(ease5MoreSubIconsList.getCreatedby());
							ease1.setCreatedon(new Date());
							ease1.setAppid(corpServ.getId());
							ease1.setImagelink(ease5MoreSubIconsList.getImagelink());
							ease1.setMain_icon_id(ease5MoreSubIconsList.getMain_icon_id());
							ease1.setOptionname(ease5MoreSubIconsList.getOptionname());
							ease1.setOptiontype(ease5MoreSubIconsList.getOptiontype());
							ease1.setPagename(ease5MoreSubIconsList.getPagename());
							ease1.setRedirectiontype(ease5MoreSubIconsList.getRedirectiontype());
							ease1.setRedirectionurl(ease5MoreSubIconsList.getRedirectionurl());
							ease1.setSeqnumber(ease5MoreSubIconsList.getSeqnumber());
							ease1.setStatusid(new BigDecimal(3));
					       ease5MoreSubIconsRepository.save(ease1);
						}
					}else {
						ease5MoreSubIconsList.setCreatedon(new Date());
						ease5MoreSubIconsList.setStatusid(new BigDecimal(3));
						ease5MoreSubIconsRepository.save(ease5MoreSubIconsList);
					}
				//}
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5MoreSubIconsServiceImpl->addEase5MoreIconsData----------End");
		return "Success";
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<Ease5MoreSubIconsEntity> getEaseMoreSubIconsId(String id1) {
		List<Ease5MoreSubIconsEntity> template = ease5MoreSubIconsRepository.getEaseMoreSubIconsId(Integer.valueOf(id1));
		return template;
	}
	
	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String editEase5MoreSubIconsData(Ease5MoreSubIconsEntity ease5MoreSubIconsList) {
		LOGGER.info("Ease5MoreSubIconsServiceImpl->editEase5MoreSubIconsData----------Start");
		try {
			if(ObjectUtils.isNotEmpty(ease5MoreSubIconsList)) {
				ease5MoreSubIconsRepository.update(ease5MoreSubIconsList.getImagelink(),ease5MoreSubIconsList.getRedirectionurl(),ease5MoreSubIconsList.getStatusid(),ease5MoreSubIconsList.getId(),ease5MoreSubIconsList.getAppredirectionlink());

			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5MoreSubIconsServiceImpl->editEase5MoreSubIconsData----------End");
		return "Success";
	}


}
