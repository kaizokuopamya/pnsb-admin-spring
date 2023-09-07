package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itl.pns.entity.BulkUsersExcelDetailsEntity;

public interface BulkUsersExcelDetailsRepository  extends JpaRepository<BulkUsersExcelDetailsEntity, Serializable> {

	@Query(value = " FROM BulkUsersExcelDetailsEntity WHERE status = '0' ORDER BY createdon DESC")
	List<BulkUsersExcelDetailsEntity> getBulkUsersExcelDetails();

	@Modifying
	@Query(value = "UPDATE BulkUsersExcelDetailsEntity SET status =:status,updateon =:date1,successCount =:successCoun,errorCount =:errorCoun WHERE id =:excelId ")
	void getBulkUsersExcelDataUpdate(@Param("excelId") BigDecimal excelId, @Param("date1") Date date, @Param("successCoun") String successCount, @Param("errorCoun") String errorCount, @Param("status") Integer sflags);

	@Modifying
	@Query(value = "UPDATE BulkUsersExcelDetailsEntity SET status = 6, updateon =:date1, remark =:remark, updateby =:id1 WHERE id =:id2 ")
	void getBulkUsersExcelDataUpdate(@Param("id1") Integer valueOf,@Param("date1") Date date,@Param("id2") BigDecimal id, @Param("remark") String id3);

	
//	@Query(value = " FROM BulkUsersExcelDetailsEntity WHERE TO_CHAR( updateon, 'DD-mon-YY') = TO_CHAR( sysdate, 'DD-mon-YY') and status <> '0' order by createdon")
//	List<BulkUsersExcelDetailsEntity> getBulkUsersExcelStatus();

//	@Query(value = " FROM BulkUsersExcelDetailsEntity WHERE status = '0' order by createdon")
//	List<BulkUsersExcelDetailsEntity> getBulkUsersExcelPending();

	@Modifying
	@Query(value = "SELECT b.ID,b.EXCEL_NAME,b.CREATEDBY,b.UPDATEBY,b.CREATEDON,b.UPDATEON,b.STATUS,b.REMARK,b.SUCCESS_COUNT,b.ERROR_COUNT,b.TOTAL_COUNT,b.BRANCHCODE FROM "
			+ "(SELECT ID,EXCEL_NAME,CREATEDBY,UPDATEBY,CREATEDON,UPDATEON,STATUS,REMARK,SUCCESS_COUNT,ERROR_COUNT,TOTAL_COUNT,BRANCHCODE"
			+ " FROM BULK_USERS_EXCEL_DETAILS"
			+ " WHERE TO_CHAR( updateon, 'DD-mon-YY') = TO_CHAR( sysdate, 'DD-mon-YY')"
			+ " and status <> '0'"
			+ " union all"
			+ " SELECT ID,EXCEL_NAME,CREATEDBY,UPDATEBY,CREATEDON,UPDATEON,STATUS,REMARK,SUCCESS_COUNT,ERROR_COUNT,TOTAL_COUNT,BRANCHCODE"
			+ " FROM BULK_USERS_EXCEL_DETAILS  WHERE status = '0')b ORDER BY b.CREATEDON DESC",nativeQuery=true)
	List<BulkUsersExcelDetailsEntity> getBulkUsersExcelStatus();

	@Modifying
	@Query(value = "SELECT b.ID,b.EXCEL_NAME,b.CREATEDBY,b.UPDATEBY,b.CREATEDON,b.UPDATEON,b.STATUS,b.REMARK,b.SUCCESS_COUNT,b.ERROR_COUNT,b.TOTAL_COUNT,b.BRANCHCODE FROM "
			+ "(SELECT ID,EXCEL_NAME,CREATEDBY,UPDATEBY,CREATEDON,UPDATEON,STATUS,REMARK,SUCCESS_COUNT,ERROR_COUNT,TOTAL_COUNT,BRANCHCODE"
			+ " FROM BULK_USERS_EXCEL_DETAILS"
			+ " WHERE TO_CHAR( updateon, 'DD-mon-YY') = TO_CHAR( sysdate, 'DD-mon-YY')"
			+ " AND status <> '0' AND BRANCHCODE=:id1"
			+ " union all"
			+ " SELECT ID,EXCEL_NAME,CREATEDBY,UPDATEBY,CREATEDON,UPDATEON,STATUS,REMARK,SUCCESS_COUNT,ERROR_COUNT,TOTAL_COUNT,BRANCHCODE"
			+ " FROM BULK_USERS_EXCEL_DETAILS  WHERE BRANCHCODE=:id1 AND status = '0')b ORDER BY b.CREATEDON DESC",nativeQuery=true)
	List<BulkUsersExcelDetailsEntity> getBulkUsersExcelZonalStatus(@Param("id1")  String id1);

	@Query(value = " FROM BulkUsersExcelDetailsEntity WHERE BRANCHCODE=:id1 AND status = '0' ORDER BY createdon DESC")
	List<BulkUsersExcelDetailsEntity> getBulkUsersExcelZonalDetails(@Param("id1") String id1);
}
