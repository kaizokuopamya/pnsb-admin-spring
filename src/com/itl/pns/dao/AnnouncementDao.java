package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.entity.AnnouncementsEntity;

public interface AnnouncementDao {
	
	
    public List<AnnouncementsEntity> getAnnouncementsById(int id);
	
	public List<AnnouncementsEntity>getAccouncementsDetails(int statusId);
	
	public boolean saveAnnouncementsDetais(AnnouncementsEntity announcementData);
	
	public boolean updateAnnouncementsDetais(AnnouncementsEntity announcementData);
	
	public List<AnnouncementsEntity>getAnnouncementType();
	
    public ResponseMessageBean checkSeqNo(AnnouncementsEntity announcementData);
	
	public ResponseMessageBean updateCheckSeqNo(AnnouncementsEntity announcementData);
	
	public boolean saveAnnouncementsDetaisToAdminWorkFlow(AnnouncementsEntity announcementsData, int useuserStatusrId);
	
	public boolean updateAnnouncementsDetaisToAdminWorkFlow(AnnouncementsEntity announcementsData, int userStatus);
	
}
