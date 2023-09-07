package com.itl.pns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.PaymentagreegatorsEntity;

@Repository
public interface PaymentagreegatorRepository extends JpaRepository<PaymentagreegatorsEntity, Long> {

	/*
	 * List<PaymentagreegatorsEntity> findById(long ID);
	 * 
	 * List<PaymentagreegatorsEntity> findByMerchantname(String merchantname);
	 * 
	 * List<PaymentagreegatorsEntity> findByAppType(String appType);
	 * 
	 * List<PaymentagreegatorsEntity> findByPaymentRefNo(String paymentRefNo);
	 * 
	 * List<PaymentagreegatorsEntity> findByBankTransactionId(String
	 * bankTransactionId);
	 */

}
