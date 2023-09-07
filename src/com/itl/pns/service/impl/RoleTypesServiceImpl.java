package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.dao.RoleTypesDao;
import com.itl.pns.entity.RoleTypesEntity;
import com.itl.pns.service.RoleTypesService;

@Service
@Qualifier("RoleTypesService")
@Transactional
public class RoleTypesServiceImpl implements RoleTypesService {
	
	@Autowired
	RoleTypesDao roleTypes;

	@Override
	public List<RoleTypesEntity> getRoleTypeList() {
		return roleTypes.getRoleTypeList();
	}

	@Override
	public List<RoleTypesEntity> getRoleTypesById(int roleTypeId) {
		return roleTypes.getRoleTypeById(roleTypeId);
	}

}
