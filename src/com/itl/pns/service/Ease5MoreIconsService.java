package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.Ease5MoreIconsEntity;

public interface Ease5MoreIconsService {

	List<Ease5MoreIconsEntity> getEaseMoreIconsList(String id);

	String ease5MoreIconsDelt(String id1,String id2);

	String addEase5MoreIconsData(Ease5MoreIconsEntity ease5MoreIconsList);

	List<Ease5MoreIconsEntity> getEaseMoreIconsId(String id1);

	String editEase5MoreIconsData(Ease5MoreIconsEntity ease5MoreIconsList);
}
