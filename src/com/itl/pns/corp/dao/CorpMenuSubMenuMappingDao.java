package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.corp.entity.CorpMenuSubMenuMappingEntity;

public interface CorpMenuSubMenuMappingDao {

	public CorpMenuSubMenuMappingEntity getCorpMenuSubMenuMappingById(Long id);

	public List<CorpMenuSubMenuMappingEntity> corpMenuSubMenuMappingList();

	public List<CorpMenuSubMenuMappingEntity> findCorpMenuSubMenuByMenuIdAndStatusId(Long menuId, int statusId);
	
	public List<Object[]> menuSubMenuList();

}
