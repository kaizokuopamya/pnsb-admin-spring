package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.corp.entity.CorpMenuEntity;

public interface CorpMenuDao {

	public CorpMenuEntity getCorpMenuById(Long id);

	public List<CorpMenuEntity> corpMenuList();

}
