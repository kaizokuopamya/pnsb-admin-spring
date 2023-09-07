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
import com.itl.pns.entity.Ease5TermsConditionEntity;
import com.itl.pns.repository.Ease5TermsConditionRepository;
import com.itl.pns.service.Ease5TermsConditionService;

@Service
@Qualifier("Ease5TermsConditionService")
@Transactional
public class Ease5TermsConditionServiceImpl implements Ease5TermsConditionService {
	static Logger LOGGER = Logger.getLogger(Ease5TermsConditionServiceImpl.class);

	@Autowired
	Ease5TermsConditionRepository ease5TermsConditionRepository;

	@Autowired
	private CorpServiceRequestService corpserviceRequestService;

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<Ease5TermsConditionEntity> getEase5TermsConditionList(String id1) {
		BigDecimal appId = new BigDecimal(id1);
		List<Ease5TermsConditionEntity> template = ease5TermsConditionRepository.getEase5TermsConditionListData(appId);
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String ease5TermsConditionDelt(String id1, String id2) {
		LOGGER.info("Ease5TermsConditionServiceImpl->ease5TermsConditionDelt----------Start");
		try {
			ease5TermsConditionRepository.ease5TermsConditionDelt(Integer.valueOf(id1), new BigDecimal(id2));
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5TermsConditionServiceImpl->ease5TermsConditionDelt----------End");
		return "Success";
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String addEase5TermsCondition(Ease5TermsConditionEntity ease5TermsCondition) {
		LOGGER.info("Ease5TermsConditionServiceImpl->addEase5TermsCondition----------Start");
		try {
			if (ObjectUtils.isNotEmpty(ease5TermsCondition)) {
				if ("activeAll".equalsIgnoreCase(ease5TermsCondition.getIsActiveAll())) {
					List<CorpServiceRequestEntity> corpserviceRequestEntity = corpserviceRequestService
							.getChannelDetailsActiveService();
					for (CorpServiceRequestEntity corpServ : corpserviceRequestEntity) {
						Ease5TermsConditionEntity ease1 = new Ease5TermsConditionEntity();
						ease1.setHeader(ease5TermsCondition.getHeader());
						int length = ease1.getTermsCondition().length();												
						if (length > 0)
							if(length<=4000)
								ease1.setTermsCondition1(ease5TermsCondition.getTermsCondition().substring(0, length));
							else
								ease1.setTermsCondition1(ease5TermsCondition.getTermsCondition().substring(0, 4000));
						if (length >= 4000)
							if(length<=8000)
								ease1.setTermsCondition2(ease1.getTermsCondition().substring(4000, length));
							else
								ease1.setTermsCondition2(ease1.getTermsCondition().substring(4000, 8000));
						if (length >= 8000)
							if(length<=12000)
								ease1.setTermsCondition3(ease1.getTermsCondition().substring(8000, length));
							else
								ease1.setTermsCondition3(ease1.getTermsCondition().substring(8000, 12000));
						if (length >= 12000)
							if(length<=16000)
								ease1.setTermsCondition4(ease1.getTermsCondition().substring(12000, length));
							else
								ease1.setTermsCondition4(ease1.getTermsCondition().substring(12000, 16000));
						if (length >= 16000)
							if(length<=20000)
								ease1.setTermsCondition5(ease1.getTermsCondition().substring(16000, length));
							else
								ease1.setTermsCondition5(ease1.getTermsCondition().substring(20000, length));
						if (length >= 20000)
							if(length<=24000)
								ease1.setTermsCondition6(ease1.getTermsCondition().substring(20000, length));
							else
								ease1.setTermsCondition6(ease1.getTermsCondition().substring(20000, 24000));
						if (length >= 24000)
							if(length<=28000)
								ease1.setTermsCondition7(ease1.getTermsCondition().substring(24000, length));
							else
								ease1.setTermsCondition7(ease1.getTermsCondition().substring(28000, length));
						if (length >= 28000)					
							ease1.setTermsCondition8(ease1.getTermsCondition().substring(28000, length));	
						
						ease1.setRedirectionType(ease5TermsCondition.getRedirectionType());
						ease1.setStatusId(new BigDecimal(3));
						ease1.setCreatedBy(ease5TermsCondition.getCreatedBy());
						ease1.setCreatedOn(new Date());
						ease1.setAppId(corpServ.getId());
						ease5TermsConditionRepository.save(ease1);
					}

				} else {
					ease5TermsCondition.setCreatedOn(new Date());
					ease5TermsCondition.setStatusId(new BigDecimal(3));
					ease5TermsConditionRepository.save(ease5TermsCondition);
				}
				// }
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5TermsConditionServiceImpl->addEase5TermsCondition----------End");
		return "Success";
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public List<Ease5TermsConditionEntity> getEase5TermsConditionId(String id1) {
		List<Ease5TermsConditionEntity> template = ease5TermsConditionRepository
				.getEase5TermsConditionId(Integer.valueOf(id1));
		return template;
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRED)
	public String editEase5TermsConditionData(Ease5TermsConditionEntity ease5TermsCondition) {
		LOGGER.info("Ease5TermsConditionServiceImpl->editEase5TermsConditionData----------Start");
		try {
			if (ObjectUtils.isNotEmpty(ease5TermsCondition)) {
				String t1 = "";
				String t2 = "";
				String t3 = "";
				String t4 = "";
				String t5 = "";
				String t6 = "";
				String t7 = "";
				String t8 = "";
				int length = ease5TermsCondition.getTermsCondition().length();
				if (length > 0)
					if(length<=4000)
						t1 = ease5TermsCondition.getTermsCondition().substring(0, length);
					else
						t1 = ease5TermsCondition.getTermsCondition().substring(0, 4000);
				if (length >= 4000)
					if(length<=8000)
						t2 = ease5TermsCondition.getTermsCondition().substring(4000, length);
					else
						t2 = ease5TermsCondition.getTermsCondition().substring(4000, 8000);
				if (length >= 8000)
					if(length<=12000)
						t3 = ease5TermsCondition.getTermsCondition().substring(8000, length);
					else
						t3 = ease5TermsCondition.getTermsCondition().substring(8000, 12000);
				if (length >= 12000)
					if(length<=16000)
						t4 = ease5TermsCondition.getTermsCondition().substring(12000, length);
					else
						t4 = ease5TermsCondition.getTermsCondition().substring(12000, 16000);
				if (length >= 16000)
					if(length<=20000)
						t5 = ease5TermsCondition.getTermsCondition().substring(16000, length);
					else
						t5 = ease5TermsCondition.getTermsCondition().substring(16000, 20000);
				if (length >= 20000)
					if(length<=24000)
						t6 = ease5TermsCondition.getTermsCondition().substring(20000, length);
					else
						t6 = ease5TermsCondition.getTermsCondition().substring(20000, 24000);
				if (length >= 24000)
					if(length<=28000)
						t7 = ease5TermsCondition.getTermsCondition().substring(24000, length);
					else
						t7 = ease5TermsCondition.getTermsCondition().substring(28000, length);
				if (length >= 28000)					
						t8 = ease5TermsCondition.getTermsCondition().substring(28000, length);								
				ease5TermsConditionRepository.update(ease5TermsCondition.getHeader(), t1, t2, t3, t4, t5, t6, t7, t8,
						ease5TermsCondition.getStatusId(), ease5TermsCondition.getId());
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
			return "error";
		}
		LOGGER.info("Ease5TermsConditionServiceImpl->editEase5TermsConditionData----------End");
		return "Success";
	}

}
