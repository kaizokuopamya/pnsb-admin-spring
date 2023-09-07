package com.itl.pns.service;

import java.util.List;
import com.itl.pns.entity.RoleTypesEntity;

public interface RoleTypesService {

	List<RoleTypesEntity> getRoleTypeList();

	List<RoleTypesEntity> getRoleTypesById(int roleTypeId);

}
