package com.itl.pns.corp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.CorpSubMenuDao;
import com.itl.pns.corp.entity.CorpSubMenuEntity;
import com.itl.pns.corp.service.CorpSubMenuService;

@Service
public class CorpSubMenuServiceImpl implements CorpSubMenuService {

	@Autowired
	private CorpSubMenuDao corpSubmenuDao;

	@Override
	public CorpSubMenuEntity getCorpSubMenuById(Long id) {
		return corpSubmenuDao.getCorpSubMenuById(id);
	}

	@Override
	public List<CorpSubMenuEntity> corpSubMenuList() {
		return corpSubmenuDao.corpSubMenuList();
	}

}
