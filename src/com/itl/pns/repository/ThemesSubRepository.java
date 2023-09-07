package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itl.pns.entity.ThemesSubEntity;

public interface ThemesSubRepository extends JpaRepository<ThemesSubEntity, Serializable>{

	@Query(value = " FROM ThemesSubEntity WHERE themes_id=:id and statusid = 3")
	List<ThemesSubEntity> getThemesthemesSub(@Param("id") BigDecimal id);
	
	@Modifying
	@Query(value = " UPDATE ThemesSubEntity SET textcolor=:textcolor,barbackgroundcolor=:barbackgroundcolor,updatedby=:updatedby,updatedon=:updatedon,statusid=:statusid WHERE id=:id and statusid = 3")
	void updateThemesSub(@Param("id") BigDecimal id, @Param("textcolor") String textcolor,@Param("barbackgroundcolor") String barbackgroundcolor,@Param("updatedby") BigDecimal updatedby,
			@Param("updatedon") Date updatedon,  @Param("statusid") BigDecimal statusId);

}
