package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.Ease5QuickAccessEntity;

@Repository
public interface Ease5QuickAccessRepository extends JpaRepository<Ease5QuickAccessEntity, Serializable>{

	@Query(value = " FROM Ease5QuickAccessEntity WHERE statusid IN (3,0) and appid =:id1 ORDER BY seqnumber DESC")
	List<Ease5QuickAccessEntity> getEase5QuickAccessListData(@Param("id1") BigDecimal appId);

	@Modifying
	@Query(value = " UPDATE Ease5QuickAccessEntity SET statusid =:status WHERE id =:id ")
	void ease5QuickAccessDelt(@Param("id") Integer id1,@Param("status") BigDecimal id2);

	@Query(value = " FROM Ease5QuickAccessEntity WHERE statusid = '3' and id =:id")
	List<Ease5QuickAccessEntity> getEase5QuickAccessId(@Param("id") Integer id1);

	@Modifying
	@Query(value = "UPDATE Ease5QuickAccessEntity cm SET cm.imagelink=:imagelink,cm.redirectionurl =:redirectionurl,cm.statusid =:statusid,cm.appredirectionlink =:appredirectionlink WHERE cm.id=:id1")
	public void update(@Param("imagelink") String imagelink,@Param("redirectionurl") String redirectionurl,@Param("statusid") BigDecimal statusid,@Param("id1") Integer id,@Param("appredirectionlink") String appredirectionlink);

}
