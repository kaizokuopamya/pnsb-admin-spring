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

import com.itl.pns.corp.entity.CorpUserAccMapEntity;

@Repository
@Transactional
public interface CorpUsersAccMapRepository extends JpaRepository<CorpUserAccMapEntity, Serializable> {

	public List<CorpUserAccMapEntity> findByCorpCompIdAndStatusIdAndCorpUserId(BigDecimal corpCompId,
			BigDecimal statusid, BigDecimal oldCorpUserId);

	public List<CorpUserAccMapEntity> findByCorpCompIdAndStatusId(BigDecimal corpCompId, BigDecimal statusid);

	@Modifying
	@Query(value = "update CorpUserAccMapEntity cum set cum.corpCompId=:oldCorpCompId where cum.corpCompId=:newCorpCompId and cum.corpUserId=:newUserId and cum.statusId=3")
	public void updateCorpUserAcc(@Param("oldCorpCompId") BigDecimal oldCorpCompId,
			@Param("newCorpCompId") BigDecimal newCorpCompId, @Param("newUserId") BigDecimal newUserId);

	@Modifying
	@Query(value = "update CorpUserAccMapEntity cum set cum.corpCompId=:oldCorpCompId, cum.corpUserId=:oldUserId where cum.corpCompId=:newCorpCompId and cum.corpUserId=:newUserId and cum.statusId=3")
	public void update(@Param("oldCorpCompId") BigDecimal oldCorpCompId, @Param("oldUserId") BigDecimal oldUserId,
			@Param("newCorpCompId") BigDecimal newCorpCompId, @Param("newUserId") BigDecimal newUserId);

	@Modifying
	@Query(value = "update CorpUserAccMapEntity cum set cum.statusId=10 where cum.corpCompId=:oldCorpCompId and cum.corpUserId=:oldUserId and cum.statusId=:statusId")
	public void delete(@Param("oldCorpCompId") BigDecimal oldCorpCompId, @Param("oldUserId") BigDecimal oldUserId,
			@Param("statusId") BigDecimal statusId);

	@Modifying
	@Query(value = "update CorpUserAccMapEntity cum set cum.statusId = 10 WHERE cum.corpCompId in :corpCompId and cum.corpUserId in :corpUserId")
	public void deleteByCompanyIdAndUserId(@Param("corpCompId") List<BigDecimal> companyId,
			@Param("corpUserId") List<BigDecimal> userId);
}
