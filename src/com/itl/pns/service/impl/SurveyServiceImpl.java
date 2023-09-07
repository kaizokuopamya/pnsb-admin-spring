package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.SurveyDao;
import com.itl.pns.entity.CustomerSurveyEntity;
import com.itl.pns.entity.SurveyAnsMasterEntity;
import com.itl.pns.entity.SurveyMasterEntity;
import com.itl.pns.entity.SurveyQueMasterEntity;
import com.itl.pns.service.SurveyService;

@Service
@Qualifier("SurveyService")
@Transactional
public class SurveyServiceImpl implements SurveyService{

	@Autowired
	SurveyDao surveyDao;

	@Override
	public List<SurveyMasterEntity> getActiveSurveyDetails() {
		return surveyDao.getActiveSurveyDetails();
	}

	@Override
	public List<SurveyQueMasterEntity> getSurveyQue() {
		return surveyDao.getSurveyQue();
	}

	@Override
	public List<SurveyAnsMasterEntity> getSurveyAns() {
		return surveyDao.getSurveyAns();
	}

	@Override
	public boolean saveCustAnsOfSurvey(CustomerSurveyEntity custAnsData) {
		return surveyDao.saveCustAnsOfSurvey(custAnsData);
	}
	

	@Override
	public List<CustomerSurveyEntity> getCustSurveyDetails() {
		return surveyDao.getCustSurveyDetails();
	}

	@Override
	public boolean addSurveMasterDetails(List<SurveyMasterEntity> surveyMasterData) {
		return surveyDao.addSurveMasterDetails(surveyMasterData);
	}

	
	@Override
	public boolean addSurveyQue(SurveyQueMasterEntity surveyQue) {
		return surveyDao.addSurveyQue(surveyQue);
	}

	@Override
	public boolean addSurveyAns(SurveyAnsMasterEntity surveyAns) {
		return surveyDao.addSurveyAns(surveyAns);
	}

	@Override
	public List<SurveyMasterEntity> getSurveyMasterDetailsById(int id) {
		return surveyDao.getSurveyMasterDetailsById(id);
	}

	@Override
	public List<SurveyQueMasterEntity> getSurveyQueBySurveyId(int surveyId) {
		return surveyDao.getSurveyQueBySurveyId(surveyId);
	}

	@Override
	public List<SurveyAnsMasterEntity> getSurveyAnsByQueSUrveyId(SurveyAnsMasterEntity surveyAns) {
		return surveyDao.getSurveyAnsByQueSUrveyId(surveyAns);
	}

	@Override
	public boolean updateSurveMasterDetails(SurveyMasterEntity surveyMasterData) {
		return surveyDao.updateSurveMasterDetails(surveyMasterData);
	}

	@Override
	public boolean updateSurveyQue(SurveyQueMasterEntity surveyQue) {
		return surveyDao.updateSurveyQue(surveyQue);
	}

	@Override
	public boolean updateSurveyAns(SurveyAnsMasterEntity surveyAns) {
		return surveyDao.updateSurveyAns(surveyAns);
	}

	@Override
	public boolean deleteSurvey(int surveyId) {
		return surveyDao.deleteSurvey(surveyId);
	}

	@Override
	public boolean deleteSurveyQue(int surveyId) {
		return surveyDao.deleteSurveyQue(surveyId);
	}

	@Override
	public boolean deleteSurveyAns(int surveyId) {
		return surveyDao.deleteSurveyAns(surveyId);
	}

	@Override
	public ResponseMessageBean chechSurvey(List<SurveyMasterEntity> surveyData) {
		return surveyDao.chechSurvey(surveyData);
	}
	

}
