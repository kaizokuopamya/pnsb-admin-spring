package com.itl.pns.service;

import java.util.List;

import com.itl.pns.entity.Ease5QuickAccessEntity;

public interface Ease5QuickAccessService {

	List<Ease5QuickAccessEntity> getEase5QuickAccessList(String id1);

	String ease5QuickAccessDelt(String id1, String id2);

	String addEase5QuickAccess(Ease5QuickAccessEntity ease5QuickAccessList);

	List<Ease5QuickAccessEntity> getEase5QuickAccessId(String id1);

	String editEase5QuickAccessData(Ease5QuickAccessEntity ease5QuickAccessList);




}
