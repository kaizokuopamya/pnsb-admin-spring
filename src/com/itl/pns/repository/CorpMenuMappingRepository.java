package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpMenuMapping;


@Repository
public interface CorpMenuMappingRepository extends JpaRepository<CorpMenuMapping, Serializable> {

	List<CorpMenuMapping> findByRoleidAndCorporatecompid(BigInteger roleId,
			BigInteger companyId);

	 
	@Modifying
	@Query(value=" delete from CORP_MENU_MAPPING where roleid=? and corporatecompid=? and id=?",nativeQuery=true)
	void deleteByRoleidAndCorporatecompid(BigInteger roleid, BigInteger i, BigInteger id);

	List<CorpMenuMapping> findByRoleidAndCorporatecompidAndStatusId(BigInteger roleId,
			BigInteger companyId, BigInteger status);
	
}