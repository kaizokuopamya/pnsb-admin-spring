package com.itl.pns.corp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.CorpMenuDao;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.service.CorpMenuService;

@Service
public class CorpMenuServiceImpl implements CorpMenuService {

	@Autowired
	private CorpMenuDao corpMenuDao;

	@Override
	public CorpMenuEntity getCorpMenuById(Long id) {
		return corpMenuDao.getCorpMenuById(id);

	}

	@Override
	public List<CorpMenuEntity> corpMenuList() {
		return corpMenuDao.corpMenuList();
	}

}
