package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.CustomerSurveyEntity;
import com.itl.pns.entity.SurveyAnsMasterEntity;
import com.itl.pns.entity.SurveyMasterEntity;
import com.itl.pns.entity.SurveyQueMasterEntity;

public interface SurveyService {

public List<SurveyMasterEntity> getActiveSurveyDetails();
	
	public List<SurveyQueMasterEntity> getSurveyQue();
	
	public List<SurveyAnsMasterEntity> getSurveyAns();
	
	public boolean saveCustAnsOfSurvey(CustomerSurveyEntity custAnsData);
	
	List<CustomerSurveyEntity> getCustSurveyDetails();
	
	public boolean addSurveMasterDetails(List<SurveyMasterEntity> surveyMasterData);
	
	public boolean addSurveyQue(SurveyQueMasterEntity surveyQue);
	
	public boolean addSurveyAns(SurveyAnsMasterEntity surveyAns);

	List<SurveyMasterEntity> getSurveyMasterDetailsById(int id);
	
	List<SurveyQueMasterEntity> getSurveyQueBySurveyId(int surveyId);
	
	List<SurveyAnsMasterEntity>getSurveyAnsByQueSUrveyId(SurveyAnsMasterEntity surveyAns);
	
	public boolean updateSurveMasterDetails(SurveyMasterEntity surveyMasterData);
	
	public boolean updateSurveyQue(SurveyQueMasterEntity surveyQue);
	
	public boolean updateSurveyAns(SurveyAnsMasterEntity surveyAns);

	public	boolean  deleteSurvey(int id);
	
	public boolean deleteSurveyQue(int id);

	public boolean deleteSurveyAns(int id);

	public ResponseMessageBean chechSurvey(List<SurveyMasterEntity> surveyData);
	

}

