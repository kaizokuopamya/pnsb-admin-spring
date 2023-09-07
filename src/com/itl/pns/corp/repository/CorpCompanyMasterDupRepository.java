package com.itl.pns.corp.repository;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpCompanyMasterDupEntity;

@Repository
public interface CorpCompanyMasterDupRepository extends JpaRepository<CorpCompanyMasterDupEntity, Serializable> {

	public CorpCompanyMasterDupEntity findByCifAndBranchCode(String cif, String branchCode);

	@Modifying
	@Query("update CorpCompanyMasterDupEntity c set c.makerValidated=:makerValidated, c.checkerValidated=:checkerValidated where c.id=:id ")
	public void updateCorpCompanyMasterDup(@Param("makerValidated") Integer makerValidated,
			@Param("checkerValidated") Integer checkerValidated, @Param("id") BigDecimal id);

}
