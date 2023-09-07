package com.itl.pns.service;

import java.math.BigDecimal;
import java.util.List;

import com.itl.pns.entity.Ease5QuickSubMenuAccessEntity;

public interface Ease5QuickSubMenuAccessService {

	List<Ease5QuickSubMenuAccessEntity> getEase5QuickSubMenuAccessList(BigDecimal quickMainMenuId);

	String ease5QuickSubMenuAccessDelt(String id1, String id2);

	String addEase5QuickSubMenuAccess(Ease5QuickSubMenuAccessEntity ease5QuickSubMenuEntity);

	List<Ease5QuickSubMenuAccessEntity> getEase5QuickSubMenuAccessId(String id1);

	String editEase5QuickSubMenuAccessData(Ease5QuickSubMenuAccessEntity ease5QuickSubMenuEntity);

}
