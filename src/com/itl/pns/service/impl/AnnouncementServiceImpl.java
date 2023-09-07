package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.dao.AnnouncementDao;
import com.itl.pns.entity.AnnouncementsEntity;
import com.itl.pns.service.AnnouncementService;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{

	@Autowired 
	AnnouncementDao announcementDao;
	
	@Override
	public List<AnnouncementsEntity> getAnnouncementsById(int id) {
		return announcementDao.getAnnouncementsById(id);
	}

	@Override
	public List<AnnouncementsEntity> getAccouncementsDetails(int statusId) {
		return announcementDao.getAccouncementsDetails(statusId);
	}

	@Override
	public boolean saveAnnouncementsDetais(AnnouncementsEntity announcementData) {
		return announcementDao.saveAnnouncementsDetais(announcementData);
	}

	@Override
	public boolean updateAnnouncementsDetais(AnnouncementsEntity announcementData) {
		return announcementDao.updateAnnouncementsDetais(announcementData);
	}

	@Override
	public List<AnnouncementsEntity> getAnnouncementType() {
		return announcementDao.getAnnouncementType();
	}

	@Override
	public ResponseMessageBean checkSeqNo(AnnouncementsEntity announcementData) {
		return announcementDao.checkSeqNo(announcementData);
	}

	@Override
	public ResponseMessageBean updateCheckSeqNo(AnnouncementsEntity announcementData) {
		return announcementDao.updateCheckSeqNo(announcementData);
	}

	@Override
	public boolean saveAnnouncementsDetaisToAdminWorkFlow(AnnouncementsEntity announcementsData, int userStatus) {
		return announcementDao.saveAnnouncementsDetaisToAdminWorkFlow(announcementsData,userStatus);
	}
	
	@Override
	public boolean updateAnnouncementsDetaisToAdminWorkFlow(AnnouncementsEntity announcementsData, int userStatus){
		return announcementDao.updateAnnouncementsDetaisToAdminWorkFlow(announcementsData,userStatus);
	}
}
