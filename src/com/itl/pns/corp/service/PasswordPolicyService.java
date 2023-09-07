package com.itl.pns.corp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itl.pns.corp.entity.ForcePasswordPolicyEntity;

@Service
public interface PasswordPolicyService {

	public ForcePasswordPolicyEntity getForcePassword(String id);

	public List<ForcePasswordPolicyEntity> forcePasswordPolicyList();

	public ForcePasswordPolicyEntity saveForcePassword(ForcePasswordPolicyEntity forcePasswordPolicyEntity);

}
