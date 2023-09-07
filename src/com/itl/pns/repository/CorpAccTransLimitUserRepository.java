package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itl.pns.corp.entity.CorpAcctTransLimitUsersEntity;

public interface CorpAccTransLimitUserRepository extends JpaRepository<CorpAcctTransLimitUsersEntity, Serializable> {
	
	List<CorpAcctTransLimitUsersEntity> findByCatmId(BigInteger catmId);
	
	 
	@Modifying
	@Query(value=" update CORP_ACCT_TRANS_LIMIT_USERS set STATUSID=0 where CATM_ID=? and id=?",nativeQuery=true)
	void deleteByCatmId(BigInteger i, BigInteger id);

	

}
