package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpActivitySettingMasterEntity;

@Repository
public interface CorpActivitySettingMasterRepository  extends JpaRepository<CorpActivitySettingMasterEntity, Serializable> {
	
	List<CorpActivitySettingMasterEntity> findByCompanyId(BigInteger companyId);
	
	 
	@Modifying
	@Query(value=" delete from CORPACTIVITYSETTINGMASTER where  COMPANYID=? and id=?",nativeQuery=true)
	void deleteByCompid(BigInteger i, BigDecimal id);

}
