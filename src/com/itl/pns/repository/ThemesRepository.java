package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itl.pns.entity.ThemesEntity;

public interface ThemesRepository extends JpaRepository<ThemesEntity, Serializable>{

	@Query(value = " FROM ThemesEntity WHERE id=:id and statusid = 3")
	List<ThemesEntity> getThemes(@Param("id") BigDecimal id);

	@Modifying
	@Query(value = " UPDATE ThemesEntity SET fromDate=:Date1,toDate=:Date2,updatedby=:updatedby,updatedon=:updatedon,themeMenuOptions=:themeMenuOptions,statusid=:statusId WHERE id=:id and statusid = 3")
	void updateThemes(@Param("id") BigDecimal id,@Param("Date1")  Date fromDate,@Param("Date2") Date toDate,@Param("updatedby") BigDecimal updatedby,@Param("updatedon") Date updatedon, @Param("themeMenuOptions") String themeMenuOptions, @Param("statusId") BigDecimal statusId);

	@Query(value = "SELECT COUNT(*) FROM ThemesEntity WHERE themeName=:themeName and statusid = 3")
	String checkThemes(@Param("themeName") String id1);

}
