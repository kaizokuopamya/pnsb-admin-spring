package com.itl.pns.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itl.pns.corp.entity.ForcePasswordPolicyEntity;
import com.itl.pns.corp.service.PasswordPolicyService;
import com.itl.pns.dao.PasswordPolicyDao;

@Service
public class PasswordPolicyServiceImpl implements PasswordPolicyService {

	@Autowired
	private PasswordPolicyDao passwordPolicyDao;

	@Override
	public ForcePasswordPolicyEntity getForcePassword(String id) {
		return passwordPolicyDao.getForcePassword(id);
	}

	@Override
	public List<ForcePasswordPolicyEntity> forcePasswordPolicyList() {
		return passwordPolicyDao.forcePasswordPolicyList();
	}

	@Override
	public ForcePasswordPolicyEntity saveForcePassword(ForcePasswordPolicyEntity forcePasswordPolicyEntity) {
		return passwordPolicyDao.saveForcePassword(forcePasswordPolicyEntity);
	}

}
