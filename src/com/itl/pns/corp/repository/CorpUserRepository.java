package com.itl.pns.corp.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.corp.entity.CorpUserEntity;

@Repository
@Transactional
public interface CorpUserRepository extends JpaRepository<CorpUserEntity, Serializable> {

	@Query(value = "from CorpUserEntity where corp_comp_id = :corpCompId and statusid = :statusid")
	public List<CorpUserEntity> findByCorpCompIdAndStatusid(@Param("corpCompId") BigDecimal corpCompId,
			@Param("statusid") BigDecimal statusid);

	public List<CorpUserEntity> findByCorpCompIdAndStatusidOrderById(BigDecimal corpCompId, BigDecimal statusid);

	@Query(value = "from CorpUserEntity where corp_comp_id = :corpCompId and user_name = :userName")
	public CorpUserEntity findByCorpCompIdAndUserName(@Param("corpCompId") BigDecimal corpCompId,
			@Param("userName") String userName);

	@Query(value = "from CorpUserEntity where corp_comp_id = :corpCompId and user_name = :userName and statusid = :statusid")
	public CorpUserEntity findByCorpCompIdAndUserNameAndStatusid(@Param("corpCompId") BigDecimal corpCompId,
			@Param("userName") String userName, @Param("statusid") BigDecimal statusid);

	// TODO uncomment code for enable delete functionality
	@Query(value = "from CorpUserEntity where corp_comp_id = :corpCompId and statusid in :statusid and og_status NOT in(102) order by corpRoleId")
	public List<CorpUserEntity> findByCorpCompIdAndStatusidIN(@Param("corpCompId") BigDecimal corpCompId,
			@Param("statusid") List<BigDecimal> statusid);

	public List<CorpUserEntity> findByCorpCompIdAndStatusidNotIn(BigDecimal corpCompId, BigDecimal statusid);

	@Query(value = "from CorpUserEntity where corp_comp_id = :corpCompId and user_name = :userName and statusid <>:statusid")
	public CorpUserEntity findByCorpCompIdAnduserNameAndStatusidNotIn(@Param("corpCompId") BigDecimal corpCompId,
			@Param("userName") String userName, @Param("statusid") BigDecimal statusid);

	@Modifying
	@Query(value = "update CorpUserEntity cu set cu.statusid = 10, cu.updatedOn = current_timestamp, cu.updatedby = :updatedBy where cu.corpCompId = :corpCompId and cu.user_name=:userName and cu.statusid =:statusId")
	public void deleteByStatusId(@Param("corpCompId") BigDecimal corpCompId, @Param("userName") String userName,
			@Param("updatedBy") BigDecimal updatedBy, @Param("statusId") BigDecimal statusId);

	@Modifying
	@Query(value = "update CorpUserEntity cu set cu.statusid = 10, cu.updatedOn = current_timestamp, cu.updatedby = :updatedBy where cu.corpCompId = :corpCompId and cu.user_name=:userName")
	public void delete(@Param("corpCompId") BigDecimal corpCompId, @Param("userName") String userName,
			@Param("updatedBy") BigDecimal updatedBy);

	@Modifying
	@Query(value = "UPDATE CorpUserEntity cu SET cu.statusid = 10, cu.ogstatus = 10 WHERE cu.corpCompId in :corpCompId and cu.id in :corpUserId")
	public void deleteByCompanyIdAndUserId(@Param("corpCompId") List<BigDecimal> companyId,
			@Param("corpUserId") List<BigDecimal> userId);

	@Modifying
	@Query(value = "UPDATE CorpUserEntity cu SET cu.statusid =:statusId,cu.remark=:remark, cu.user_pwd =:userPwd,updatedby =:updatedby,cu.updatedOn = current_timestamp WHERE cu.id =:corpCompId")
	public void corpUserUpdateByUserId(@Param("statusId") BigDecimal statusId, @Param("remark") String remark,
			@Param("userPwd") String user_pwd, @Param("updatedby") BigDecimal createdByUpdatedBy,
			@Param("corpCompId") BigDecimal id);

	@Modifying
	@Query(value = "UPDATE CorpUserEntity cu SET cu.statusid =:statusId, cu.wrongAttemptSoftToken =:wrongAttemptSoftToken, cu.remark =:remarks, cu.user_pwd =:userPwd, cu.updatedby =:updatedBy,cu.ogstatus =:og_status, cu.updatedOn = current_timestamp WHERE ID =:id")
	public void corpUserUpdateByUserId(@Param("statusId") BigDecimal statusId,
			@Param("wrongAttemptSoftToken") BigDecimal wrongAttemptSoftToken, @Param("remarks") String remark,
			@Param("userPwd") String user_pwd, @Param("updatedBy") BigDecimal createdByUpdatedBy,
			@Param("og_status") BigDecimal ogstatus, @Param("id") BigDecimal id);

	@Modifying
	@Query(value = "UPDATE CorpUserEntity cu SET  cu.transMaxLimit=:transMaxLimit, cu.email_id=:email_id, cu.personal_Phone=:personal_Phone, "
			+ "cu.work_phone=:personal_Phone, cu.remark=:remark, cu.rights=:rights, cu.pkiStatus=:pkiStatus, cu.updatedby=:updatedby,  cu.dob=:dob, "
			+ "cu.ogstatus=:ogstatus, cu.user_pwd=:user_pwd, cu.parentId=:parentId,cu.parentUserName=:parentUserName, cu.updatedOn=current_timestamp,"
			+ " cu.pancardNumber=:pancardNumber,cu.statusid=:statusid, cu.corpCompId =:oldCorpCompId WHERE cu.corpCompId =:corpCompId and cu.id=:corpUserId")
	public void updateUsers(@Param("transMaxLimit") BigDecimal transMaxLimit, @Param("email_id") String email_id,
			@Param("personal_Phone") String personal_Phone, @Param("remark") String remark,
			@Param("rights") String rights, @Param("pkiStatus") String pkiStatus,
			@Param("updatedby") BigDecimal updatedby, @Param("dob") String dob, @Param("ogstatus") BigDecimal ogstatus,
			@Param("user_pwd") String user_pwd, @Param("parentId") BigDecimal parentId,
			@Param("parentUserName") String parentUserName, @Param("pancardNumber") String pancardNumber,
			@Param("statusid") BigDecimal statusid, @Param("oldCorpCompId") BigDecimal oldCorpCompId,
			@Param("corpCompId") BigDecimal corpCompId, @Param("corpUserId") BigDecimal corpUserId);

}
