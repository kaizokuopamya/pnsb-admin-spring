package com.itl.pns.corp.service;

import java.util.List;

import com.itl.pns.corp.entity.CorpSubMenuEntity;

public interface CorpSubMenuService {

	public CorpSubMenuEntity getCorpSubMenuById(Long id);

	public List<CorpSubMenuEntity> corpSubMenuList();

}
