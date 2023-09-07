package com.itl.pns.corp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.CorpSubMenuDao;
import com.itl.pns.corp.entity.CorpSubMenuEntity;
import com.itl.pns.corp.repository.CorpSubMenuRepository;

@Service
public class CorpSubMenuDaoImpl implements CorpSubMenuDao {

	@Autowired
	private CorpSubMenuRepository corpSubMenuRepository;

	@Override
	public CorpSubMenuEntity getCorpSubMenuById(Long id) {
		return corpSubMenuRepository.findOne(id);
	}

	@Override
	public List<CorpSubMenuEntity> corpSubMenuList() {
		return corpSubMenuRepository.findAll();
	}

}
