package com.itl.pns.corp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.CorpMenuSubMenuMappingDao;
import com.itl.pns.corp.entity.CorpMenuSubMenuMappingEntity;
import com.itl.pns.corp.repository.CorpMenuSubMenuMappingRepository;

@Service
public class CorpMenuSubMenuMappingDaoImpl implements CorpMenuSubMenuMappingDao {

	@Autowired
	private CorpMenuSubMenuMappingRepository corpMenuSubMenuMappingRepository;

	@Override
	public CorpMenuSubMenuMappingEntity getCorpMenuSubMenuMappingById(Long id) {
		return corpMenuSubMenuMappingRepository.findOne(id);
	}

	@Override
	public List<CorpMenuSubMenuMappingEntity> corpMenuSubMenuMappingList() {
		return corpMenuSubMenuMappingRepository.findAll();
	}

	@Override
	public List<CorpMenuSubMenuMappingEntity> findCorpMenuSubMenuByMenuIdAndStatusId(Long menuId, int statusId) {
		return corpMenuSubMenuMappingRepository.findCorpMenuSubMenuByMenuIdAndStatusId(menuId, statusId);
	}

	public List<Object[]> menuSubMenuList() {
		return corpMenuSubMenuMappingRepository.menuSubMenuList();
	}
}
