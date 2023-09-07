package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.Ease5MoreSubIconsEntity;

public interface Ease5MoreSubIconsService {
	
	List<Ease5MoreSubIconsEntity> getEaseMoreSubIconsList(String id);

	String ease5MoreSubIconsDelt(String id1,String id2);

	String addEase5MoreSubIconsData(Ease5MoreSubIconsEntity ease5MoreIconsList);

	List<Ease5MoreSubIconsEntity> getEaseMoreSubIconsId(String id1);

	String editEase5MoreSubIconsData(Ease5MoreSubIconsEntity ease5MoreIconsList);

}
