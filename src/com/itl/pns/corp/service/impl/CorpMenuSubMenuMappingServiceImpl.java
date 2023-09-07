package com.itl.pns.corp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.CorpMenuSubMenuMappingDao;
import com.itl.pns.corp.entity.CorpMenuSubMenuMappingEntity;
import com.itl.pns.corp.service.CorpMenuSubMenuMappingService;

@Service
public class CorpMenuSubMenuMappingServiceImpl implements CorpMenuSubMenuMappingService {

	@Autowired
	private CorpMenuSubMenuMappingDao corpMenuSubMenuMappingDao;

	@Override
	public CorpMenuSubMenuMappingEntity getCorpMenuSubMenuMappingById(Long id) {
		return corpMenuSubMenuMappingDao.getCorpMenuSubMenuMappingById(id);
	}

	@Override
	public List<CorpMenuSubMenuMappingEntity> corpMenuSubMenuMappingList() {
		return corpMenuSubMenuMappingDao.corpMenuSubMenuMappingList();
	}

	@Override
	public List<CorpMenuSubMenuMappingEntity> findCorpMenuSubMenuByMenuIdAndStatusId(Long menuId, int statusId) {
		return corpMenuSubMenuMappingDao.findCorpMenuSubMenuByMenuIdAndStatusId(menuId, statusId);
	}

	public List<Object[]> menuSubMenuList() {
		return corpMenuSubMenuMappingDao.menuSubMenuList();
	}

}
