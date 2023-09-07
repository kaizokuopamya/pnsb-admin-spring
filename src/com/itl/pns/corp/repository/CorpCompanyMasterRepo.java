package com.itl.pns.corp.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.corp.entity.CorpCompanyMasterEntity;

@Repository
@Transactional
public interface CorpCompanyMasterRepo extends JpaRepository<CorpCompanyMasterEntity, Serializable> {

	@Query(value = "from CorpCompanyMasterEntity where cif =:cif and statusid in (:statusIdList)")
	public CorpCompanyMasterEntity findByCifAndStatusid(@Param("cif") String cif, @Param("statusIdList") List<Integer> statusIdList);
	
	@Query(value = "from CorpCompanyMasterEntity where cif =:cif and statusid in (:statusIdList)")
	public List<CorpCompanyMasterEntity> findByCifAndStatusIdList(@Param("cif") String cif, @Param("statusIdList") List<Integer> statusIdList);

	@Modifying
	@Query(value = "UPDATE CorpCompanyMasterEntity SET statusId =:statusId, updatedBy =:updatedBy, updatedOn = current_timestamp WHERE ID =:id")
	public void corpCompanyMasterUpdateById(@Param("statusId") BigDecimal statusId, @Param("updatedBy") BigDecimal createdByUpdatedBy, @Param("id") BigDecimal id);
	
	@Modifying
	@Query(value = "UPDATE CorpCompanyMasterEntity SET statusId =:statusId,og_status =:ogstatus, updatedBy =:updatedBy, updatedOn = current_timestamp WHERE ID =:id")
	public void corpCompanyMasterUpdateById(@Param("statusId") BigDecimal statusId,@Param("ogstatus") BigDecimal ogStatus, @Param("updatedBy") BigDecimal createdByUpdatedBy, @Param("id") BigDecimal id);

	@Modifying
	@Query(value = "UPDATE CorpCompanyMasterEntity SET updatedBy =:updatedBy, statusId=:statusId,og_status=:statusId, updatedOn = current_timestamp,phoneNo=:phoneNo,makerLimit=:makerLimit,checkerLimit=:checkerLimit, maxLimit=:maxLimit, adminTypes=:adminTypes, levelMaster=:levelMaster,approvalLevel=:approvalLevel  WHERE id =:id")
	public void corpCompMasterUpdateById(@Param("updatedBy") BigDecimal createdByUpdatedBy,@Param("statusId") BigDecimal corpCompanyStatusId,@Param("id")  BigDecimal oldCorpCompId, @Param("phoneNo") String phoneNo,
			@Param("makerLimit") BigDecimal makerLimit, @Param("checkerLimit") BigDecimal checkerLimit, @Param("maxLimit") BigDecimal maxLimit, @Param("adminTypes") String adminTypes,
			@Param("levelMaster") BigDecimal levelMaster, @Param("approvalLevel") String approvalLevel);
		
	
}
