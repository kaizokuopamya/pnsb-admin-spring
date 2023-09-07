package com.itl.pns.corp.service;

import java.util.List;

import com.itl.pns.corp.entity.CorpMenuEntity;

public interface CorpMenuService {

	public CorpMenuEntity getCorpMenuById(Long id);

	public List<CorpMenuEntity> corpMenuList();

}
