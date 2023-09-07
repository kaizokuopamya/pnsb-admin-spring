package com.itl.pns.dao;

import java.util.List;

import com.itl.pns.corp.entity.ForcePasswordPolicyEntity;

public interface PasswordPolicyDao {

	public ForcePasswordPolicyEntity getForcePassword(String id);

	public List<ForcePasswordPolicyEntity> forcePasswordPolicyList();

	public ForcePasswordPolicyEntity saveForcePassword(ForcePasswordPolicyEntity forcePasswordPolicyEntity);
}
