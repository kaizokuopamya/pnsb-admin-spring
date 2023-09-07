package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itl.pns.entity.Ease5TermsConditionEntity;

public interface Ease5TermsConditionRepository extends JpaRepository<Ease5TermsConditionEntity, Serializable> {
	@Query(value = " FROM Ease5TermsConditionEntity WHERE statusId IN (3,0) and appId =:appId ORDER BY id DESC")
	List<Ease5TermsConditionEntity> getEase5TermsConditionListData(@Param("appId") BigDecimal appId);

	@Modifying
	@Query(value = " UPDATE Ease5TermsConditionEntity SET statusId =:status WHERE Id =:id ")
	void ease5TermsConditionDelt(@Param("id") Integer id1, @Param("status") BigDecimal id2);

	@Query(value = " FROM Ease5TermsConditionEntity WHERE statusId = '3' and Id =:id")
	List<Ease5TermsConditionEntity> getEase5TermsConditionId(@Param("id") Integer id1);

	@Modifying
	@Query(value = "UPDATE Ease5TermsConditionEntity cm SET cm.header =:header,cm.termsCondition1 =:termsCondition1,cm.termsCondition2 =:termsCondition2,cm.termsCondition3 =:termsCondition3,cm.termsCondition4 =:termsCondition4,cm.termsCondition5 =:termsCondition5,cm.termsCondition6 =:termsCondition6,cm.termsCondition7 =:termsCondition7,cm.termsCondition8 =:termsCondition8,cm.statusId =:statusid WHERE cm.id=:id1")
	public void update(@Param("header") String header, @Param("termsCondition1") String termsCondition1,
			@Param("termsCondition2") String termsCondition2, @Param("termsCondition3") String termsCondition3,
			@Param("termsCondition4") String termsCondition4, @Param("termsCondition5") String termsCondition5,
			@Param("termsCondition6") String termsCondition6, @Param("termsCondition7") String termsCondition7,
			@Param("termsCondition8") String termsCondition8, @Param("statusid") BigDecimal statusid,
			@Param("id1") Integer id);
}
