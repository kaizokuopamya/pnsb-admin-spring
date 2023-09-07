package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpTempSalProcessEntity;

@Repository
public interface CorpTempSalProcessRepository  extends JpaRepository<CorpTempSalProcessEntity, Serializable> {
	
	List<CorpTempSalProcessEntity> findByPayeeAccountNumber(String payeeAccountNumber);
	
	List<CorpTempSalProcessEntity> findAllByOrderByIdDesc();

}
