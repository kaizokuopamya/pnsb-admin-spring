package com.itl.pns.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpCompanyMasterNewEntity;

@Repository
public interface CorpCompanyMasterRepository extends JpaRepository<CorpCompanyMasterNewEntity, BigDecimal> {

	List<CorpCompanyMasterNewEntity> findAllByCreatedOnBetween(Timestamp fromdate, Timestamp todate);

}
