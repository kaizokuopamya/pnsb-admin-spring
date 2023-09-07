package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.Ease5TermsConditionEntity;

public interface Ease5TermsConditionService {

	List<Ease5TermsConditionEntity> getEase5TermsConditionList(String id1);

	String ease5TermsConditionDelt(String id1, String id2);

	String addEase5TermsCondition(Ease5TermsConditionEntity ease5TermsConditionEntity);

	List<Ease5TermsConditionEntity> getEase5TermsConditionId(String id1);

	String editEase5TermsConditionData(Ease5TermsConditionEntity ease5TermsConditionEntity);

}
