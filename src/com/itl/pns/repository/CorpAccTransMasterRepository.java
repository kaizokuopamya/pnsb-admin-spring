package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itl.pns.corp.entity.CorpAcctTransMasterEntity;

public interface CorpAccTransMasterRepository  extends JpaRepository<CorpAcctTransMasterEntity, Serializable> {

	List<CorpAcctTransMasterEntity> findByTranLimitIdAndAccountNumber(BigInteger transLimitId,String accountNumber);
	
	 
	@Modifying
	@Query(value=" update  CORP_ACCT_TRANS_MASTER set STATUSID = 0 where TRAN_LIMIT_ID=? and id=?",nativeQuery=true)
	void deleteByTransLimitid(BigInteger i, BigInteger id);

	
}
