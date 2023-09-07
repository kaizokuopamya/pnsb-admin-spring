package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.entity.RoleTypesEntity;

public interface RoleTypesDao {

	List<RoleTypesEntity> getRoleTypeList();

	List<RoleTypesEntity> getRoleTypeById(int roleTypeId);

}
