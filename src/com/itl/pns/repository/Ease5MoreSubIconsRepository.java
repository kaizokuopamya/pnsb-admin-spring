package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.itl.pns.entity.Ease5MoreSubIconsEntity;

@Repository
public interface Ease5MoreSubIconsRepository extends JpaRepository<Ease5MoreSubIconsEntity, Serializable>{
	
	@Query(value = " FROM Ease5MoreSubIconsEntity WHERE statusid in (3,0) and main_icon_id =:id1 ORDER BY seqnumber DESC")
	List<Ease5MoreSubIconsEntity> getEaseMoreSubIconsList(@Param("id1") BigDecimal id);

	@Modifying
	@Query(value = " UPDATE Ease5MoreSubIconsEntity SET statusid =:status WHERE id =:id ")
	void ease5MoreSubIconsDelt(@Param("id") Integer id1,@Param("status") BigDecimal id2);

	@Query(value = " FROM Ease5MoreSubIconsEntity WHERE statusid = '3' and id =:id")
	List<Ease5MoreSubIconsEntity> getEaseMoreSubIconsId(@Param("id") Integer id1);

	@Modifying
	@Query(value = "UPDATE Ease5MoreSubIconsEntity cm SET cm.imagelink=:imagelink,cm.redirectionurl =:redirectionurl,cm.statusid =:statusid,cm.appredirectionlink =:appredirectionlink  WHERE cm.id=:id")
	public void update(@Param("imagelink") String imagelink,@Param("redirectionurl") String redirectionurl,@Param("statusid") BigDecimal statusid,@Param("id") Integer id,@Param("appredirectionlink") String appredirectionlink);

}
