package com.itl.pns.corp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.dao.CorpMenuDao;
import com.itl.pns.corp.entity.CorpMenuEntity;
import com.itl.pns.corp.repository.CorpMenuRepository;

//@Repository
//@Transactional
@Service
public class CorpMenuDaoImpl implements CorpMenuDao {

	@Autowired
	private CorpMenuRepository corpMenuRepository;

	@Override
	public CorpMenuEntity getCorpMenuById(Long id) {
		return corpMenuRepository.findOne(id);

	}

	@Override
	public List<CorpMenuEntity> corpMenuList() {
		return corpMenuRepository.findAll();
	}

}
