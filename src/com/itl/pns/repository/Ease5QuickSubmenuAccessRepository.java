package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.Ease5QuickSubMenuAccessEntity;

@Repository
public interface Ease5QuickSubmenuAccessRepository extends JpaRepository<Ease5QuickSubMenuAccessEntity, Serializable> {

	@Query(value = " FROM Ease5QuickSubMenuAccessEntity WHERE statusId IN (3,0) and quickMainMenuId =:quickMainMenuId ORDER BY seqNumber DESC")
	List<Ease5QuickSubMenuAccessEntity> getEase5QuickAccessSubMenuListData(@Param("quickMainMenuId") BigDecimal quickMainMenuId);

	@Modifying
	@Query(value = " UPDATE Ease5QuickSubMenuAccessEntity SET statusId =:status WHERE id =:id ")
	void ease5QuickSubMenuAccessDelt(@Param("id") Integer id1, @Param("status") BigDecimal id2);

	@Query(value = " FROM Ease5QuickSubMenuAccessEntity WHERE statusId = '3' and id =:id")
	List<Ease5QuickSubMenuAccessEntity> getEase5QuickSubMenuAccessId(@Param("id") Integer id1);

	@Modifying
	@Query(value = "UPDATE Ease5QuickSubMenuAccessEntity cm SET cm.imageLink=:imageLink, cm.redirectionUrl =:redirectionUrl,cm.statusId =:statusId,cm.appRedirectionLink =:appRedirectionLink WHERE cm.id=:id")
	public void update(@Param("imageLink") String imagelink, @Param("redirectionUrl") String redirectionurl,
			@Param("statusId") BigDecimal statusid, @Param("id") Integer id,
			@Param("appRedirectionLink") String appredirectionlink);

}
