package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.entity.LanguageJson;


@Repository
@Transactional(value = "jpaTransactionManager",propagation = Propagation.REQUIRES_NEW)
public interface LanguageJsonRepository extends  JpaRepository<LanguageJson, Serializable>{
	
	LanguageJson findByid(BigDecimal id);

}
