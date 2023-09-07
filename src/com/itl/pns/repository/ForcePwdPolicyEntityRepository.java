package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.ForcePasswordPolicyEntity;

@Repository
public interface ForcePwdPolicyEntityRepository extends JpaRepository<ForcePasswordPolicyEntity, Serializable> {

	public List<ForcePasswordPolicyEntity> findAllByStatusId(Integer statusId);

}
