package com.itl.pns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itl.pns.entity.SubMenuEntity;


public interface SubMenuRepository extends JpaRepository<SubMenuEntity, Integer> {

	SubMenuEntity findByid(int id);
	
	List<SubMenuEntity> findAll();

	SubMenuEntity findByidAndStatusId(int submenuId, int i);

	List<SubMenuEntity> findByStatusId(int i);

	List<SubMenuEntity> findAllByOrderByMenudesc();
	
	@Query("from SubMenuEntity m where m.mainMenuid.id=? and m.statusId=?")
	List<SubMenuEntity> findByMainMenuidAndStatusId(int menuId, int statusId);
	
	
	@Query("from SubMenuEntity m where m.mainMenuid.id=?")
	List<SubMenuEntity> findByMainMenuid(int menuid);
	
	@Query("from SubMenuEntity m where m.menuLink=? and m.statusId=3")
	List<SubMenuEntity> findSubmenuByMneuLink(String menuLink);





}
