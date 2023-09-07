package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.itl.pns.entity.BulkUsersCreationEntity;

@Repository
public interface BulkUsersCreationRepository extends JpaRepository<BulkUsersCreationEntity, Serializable> {

	@Query(value = " FROM BulkUsersCreationEntity WHERE excelid =:id and status = '0'")
	List<BulkUsersCreationEntity> getBulkUsersData(@Param("id") Integer id);

	@Modifying
	@Query(value = " UPDATE BULK_USERS_DETAILS SET UPDATEBY =:id1, STATUS ='7' WHERE ID in (:listId) and STATUS = '0'",nativeQuery=true)
	void getBulkUsersDataApproval(@Param("id1") Integer id1, @Param("listId") List<BigDecimal> listId);

	@Modifying
	@Query(value = " UPDATE BulkUsersCreationEntity SET remark =:remark, status =:status WHERE id =:id1 and status = '7'")
	void getBulkUsersDataUpdate(@Param("id1") BigDecimal id, @Param("status") Integer status, @Param("remark") String remark);
    
	@Modifying
	@Query(value = " UPDATE BulkUsersCreationEntity SET status ='6', remark ='Rejected' WHERE excelid =:id and status = '0'")
	void bulkUsersCreationReject(@Param("id") Integer integer);

	@Query(value = " FROM BulkUsersCreationEntity WHERE excelid =:id and status = '7' and action = 'update' ")
	List<BulkUsersCreationEntity> getBulkUsersDataUp(@Param("id") Integer id2);

	@Query(value = " FROM BulkUsersCreationEntity WHERE excelid =:id and status = '7' and action = 'insert' ")
	List<BulkUsersCreationEntity> getBulkUsersDataIp(@Param("id") Integer id2);

	@Query(value = "Select count(*) FROM BulkUsersCreationEntity WHERE excelid =:id and status = '4'")
	Integer getBulkUsersDataMakerErrorCount(@Param("id") Integer excelId);

	@Query(value = " FROM BulkUsersCreationEntity WHERE excelid =:id and status = '4'")
	List<BulkUsersCreationEntity> getBulkUsersExcelErrorDetails(@Param("id") Integer id);

}
