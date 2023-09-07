package com.itl.pns.corp.dao;

import java.util.List;

import com.itl.pns.corp.entity.CorpSubMenuEntity;

public interface CorpSubMenuDao {

	public CorpSubMenuEntity getCorpSubMenuById(Long id);

	public List<CorpSubMenuEntity> corpSubMenuList();

}
