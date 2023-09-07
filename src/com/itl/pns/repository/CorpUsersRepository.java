package com.itl.pns.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpUserNewEntity;

@Repository
public interface CorpUsersRepository extends JpaRepository<CorpUserNewEntity, BigDecimal> {

	List<CorpUserNewEntity> findAllByCreatedonBetween(Timestamp fromdate, Timestamp todate);

}
