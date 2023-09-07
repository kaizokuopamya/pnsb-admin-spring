package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Serializable> {

	Role findByCodeAndNameAndStatusId(String code, String displayName,BigDecimal statusId);

	Role findByid(BigDecimal id);

		
}
