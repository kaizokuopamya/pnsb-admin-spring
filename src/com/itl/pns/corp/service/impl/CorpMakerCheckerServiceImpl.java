package com.itl.pns.corp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.corp.dao.CorpMakerCheckerDao;
import com.itl.pns.corp.entity.CorpActivitySettingMasterEntity;
import com.itl.pns.corp.entity.CorpTempSalProcessEntity;
import com.itl.pns.corp.entity.DesignationHierarchyMasterEntity;
import com.itl.pns.corp.service.CorpMakerCheckerService;
import com.itl.pns.entity.ActivitySettingMasterEntity;
import com.itl.pns.repository.CorpActivitySettingMasterRepository;

@Service
@Component
public class CorpMakerCheckerServiceImpl implements CorpMakerCheckerService {

	static Logger LOGGER = Logger.getLogger(CorpMakerCheckerServiceImpl.class);

	@Autowired
	CorpMakerCheckerDao corpMakerCheckerDao;

	@Autowired
	CorpActivitySettingMasterRepository corpActivityRepository;

	@Override
	public List<ActivitySettingMasterEntity> getAllActivitySettingForCorp() {

		return corpMakerCheckerDao.getAllActivitySettingForCorp();
	}

	@Override
	public List<CorpActivitySettingMasterEntity> getCorpActivitiesAndMapping(int companyId) {
		return corpMakerCheckerDao.getCorpActivitiesAndMapping(companyId);
	}

	@Override
	@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
	public void saveCorpActivities(List<CorpActivitySettingMasterEntity> corpActivityData) {
		try {
			boolean success = true;
			if (corpActivityData != null && !corpActivityData.isEmpty()) {
				List<CorpActivitySettingMasterEntity> corpActiDataNew = corpActivityRepository
						.findByCompanyId(corpActivityData.get(0).getCompanyId().toBigInteger());
				if (!corpActiDataNew.isEmpty())// If Exist
				{
					for (CorpActivitySettingMasterEntity corpActiData : corpActiDataNew) {

						System.out.println("----" + corpActiDataNew.get(0).getCompanyId());
						System.out.println("Exists data");
						corpActivityRepository.deleteByCompid(corpActiData.getCompanyId().toBigInteger(), (corpActiData.getId()));

					}
				}
			}
			if (success) {

				corpActivityRepository.save(corpActivityData);
			}
		} catch (Exception e) {
			LOGGER.info("Exception:", e);
		}

	}

	@Override
	public boolean bulkSalaryUpload(List<CorpTempSalProcessEntity> corpSalData) {
		return corpMakerCheckerDao.bulkSalaryUpload(corpSalData);
	}

	@Override
	public boolean addDesignationHierarchy(DesignationHierarchyMasterEntity designationData) {
		return corpMakerCheckerDao.addDesignationHierarchy(designationData);
	}

	@Override
	public boolean updateDesignationHierarchy(DesignationHierarchyMasterEntity designationData) {
		return corpMakerCheckerDao.updateDesignationHierarchy(designationData);
	}

	@Override
	public List<DesignationHierarchyMasterEntity> getAllDesignationHierarchy() {
		return corpMakerCheckerDao.getAllDesignationHierarchy();
	}

	@Override
	public List<DesignationHierarchyMasterEntity> getDesignationHierarchyById(int id) {
		return corpMakerCheckerDao.getDesignationHierarchyById(id);
	}

	@Override
	public List<DesignationHierarchyMasterEntity> getDesignationHierarchyByCompId(int compId) {
		return corpMakerCheckerDao.getDesignationHierarchyByCompId(compId);
	}

	@Override
	public boolean saveCorpUserLevels(List<CorpActivitySettingMasterEntity> corpActivityData) {
		return corpMakerCheckerDao.saveCorpUserLevels(corpActivityData);
	}

	@Override
	public boolean updateCorpUserLevels(List<CorpActivitySettingMasterEntity> corpActivityData) {
		return corpMakerCheckerDao.updateCorpUserLevels(corpActivityData);
	}

	@Override
	public List<DesignationHierarchyMasterEntity> getAuthTypeByCompIdAndDesignationId(
			DesignationHierarchyMasterEntity corpReq) {
		return corpMakerCheckerDao.getAuthTypeByCompIdAndDesignationId(corpReq);
	}

}
