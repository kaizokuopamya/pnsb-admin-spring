package com.itl.pns.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itl.pns.entity.CustomizeSubmenuEntity;
public interface CustomizeSubMenuRepository extends JpaRepository<CustomizeSubmenuEntity, Integer> {

	CustomizeSubmenuEntity findByid(int id);
	
	List<CustomizeSubmenuEntity> findAll();

	CustomizeSubmenuEntity findByidAndIsActive(int submenuId, int i);

	List<CustomizeSubmenuEntity> findByIsActive(int i);

	List<CustomizeSubmenuEntity> findAllByOrderByMenudesc();
	
	@Query("from CustomizeSubmenuEntity m where m.customizeMenuid.id=? and m.isActive=?")
	List<CustomizeSubmenuEntity> findByCustomizeMenuidAndIsActive(int menuId, int statusId);
	
	
	@Query("from CustomizeSubmenuEntity m where m.customizeMenuid.id=?")
	List<CustomizeSubmenuEntity> findByCustomizeMenuid(BigInteger menuid);
	
	@Query("from CustomizeSubmenuEntity m where m.menuLink=? and m.isActive=3")
	List<CustomizeSubmenuEntity> findSubmenuByMneuLink(String menuLink);


}

