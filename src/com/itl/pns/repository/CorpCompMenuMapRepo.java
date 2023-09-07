package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpCompanyMenuMappingEntity;

@Repository
public interface CorpCompMenuMapRepo extends JpaRepository<CorpCompanyMenuMappingEntity, Serializable> {

	List<CorpCompanyMenuMappingEntity> findByCompanyId(BigInteger compId);

	List<CorpCompanyMenuMappingEntity> findByCompanyIdAndStatusId(BigInteger companyId, int i);

	@Modifying
	void deleteByCompanyId(BigInteger companyId);

}
