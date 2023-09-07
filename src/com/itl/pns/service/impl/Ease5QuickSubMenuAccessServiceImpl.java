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
import com.itl.pns.entity.Ease5QuickSubMenuAccessEntity;
import com.itl.pns.repository.Ease5QuickSubmenuAccessRepository;
import com.itl.pns.service.Ease5QuickSubMenuAccessService;

@Service
@Qualifier("Ease5QuickSubMenuAccessService")
@Transactional
public class Ease5QuickSubMenuAccessServiceImpl implements Ease5QuickSubMenuAccessService {
	static Logger LOGGER = Logger.getLogger(Ease5QuickSubMenuAccessServiceImpl.class);

	@Autowired
	Ease5QuickSubmenuAccessRepository ease5QuickSubMenuAccessRepository;

	@Autowired
	private CorpServiceRequestService corpserviceRequestService;

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<Ease5QuickSubMenuAccessEntity> getEase5QuickSubMenuAccessList(BigDecimal quickMainMenuId) {
		List<Ease5QuickSubMenuAccessEntity> template = ease5QuickSubMenuAccessRepository
				.getEase5QuickAccessSubMenuListData(quickMainMenuId);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String ease5QuickSubMenuAccessDelt(String id1, String id2) {
		LOGGER.info("Ease5QuickAccessServiceImpl->ease5QuickAccessDelt----------Start");
		try {
			ease5QuickSubMenuAccessRepository.ease5QuickSubMenuAccessDelt(Integer.valueOf(id1), new BigDecimal(id2));
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5QuickAccessServiceImpl->ease5QuickAccessDelt----------End");
		return "Success";
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String addEase5QuickSubMenuAccess(Ease5QuickSubMenuAccessEntity ease5QuickSubMenuEntity) {
		LOGGER.info("Ease5QuickAccessServiceImpl->addEase5QuickAccess----------Start");
		try {
			if (ObjectUtils.isNotEmpty(ease5QuickSubMenuEntity)) {
				// for(Ease5MoreIconsEntity ease : ease5MoreIconsList) {
				if (ease5QuickSubMenuEntity.getIsActiveAll().equalsIgnoreCase("activeAll")) {
					List<CorpServiceRequestEntity> corpserviceRequestEntity = corpserviceRequestService
							.getChannelDetailsActiveService();
					for (CorpServiceRequestEntity corpServ : corpserviceRequestEntity) {
						Ease5QuickSubMenuAccessEntity ease1 = new Ease5QuickSubMenuAccessEntity();
						ease1.setQuickMainMenuId(ease5QuickSubMenuEntity.getQuickMainMenuId());
						ease1.setOptionName(ease5QuickSubMenuEntity.getOptionName());
						ease1.setPageName(ease5QuickSubMenuEntity.getPageName());
						ease1.setAppRedirectionLink(ease5QuickSubMenuEntity.getAppRedirectionLink());
						ease1.setImageLink(ease5QuickSubMenuEntity.getImageLink());
						ease1.setOptionType(ease5QuickSubMenuEntity.getOptionType());
						ease1.setRedirectionType(ease5QuickSubMenuEntity.getRedirectionType());
						ease1.setRedirectionUrl(ease5QuickSubMenuEntity.getRedirectionUrl());
						ease1.setStatusId(new BigDecimal(3));
						ease1.setSeqNumber(ease5QuickSubMenuEntity.getSeqNumber());
						ease1.setAppId(corpServ.getId());
						ease1.setCreatedBy(ease5QuickSubMenuEntity.getCreatedBy());
						ease1.setCreatedOn(new Date());

						ease5QuickSubMenuAccessRepository.save(ease1);
					}

				} else {
					ease5QuickSubMenuEntity.setCreatedOn(new Date());
					ease5QuickSubMenuEntity.setStatusId(new BigDecimal(3));
					ease5QuickSubMenuAccessRepository.save(ease5QuickSubMenuEntity);
				}
				// }
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
	public List<Ease5QuickSubMenuAccessEntity> getEase5QuickSubMenuAccessId(String id1) {
		List<Ease5QuickSubMenuAccessEntity> template = ease5QuickSubMenuAccessRepository
				.getEase5QuickSubMenuAccessId(Integer.valueOf(id1));
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String editEase5QuickSubMenuAccessData(Ease5QuickSubMenuAccessEntity ease5QuickSubMenuEntity) {

		LOGGER.info("Ease5QuickAccessServiceImpl->editEase5QuickAccessData----------Start");
		try {
			if (ObjectUtils.isNotEmpty(ease5QuickSubMenuEntity)) {

				ease5QuickSubMenuAccessRepository.update(ease5QuickSubMenuEntity.getImageLink(),
						ease5QuickSubMenuEntity.getRedirectionUrl(), ease5QuickSubMenuEntity.getStatusId(),
						ease5QuickSubMenuEntity.getId(), ease5QuickSubMenuEntity.getAppRedirectionLink());
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5QuickAccessServiceImpl->editEase5QuickAccessData----------End");
		return "Success";
	}

}
