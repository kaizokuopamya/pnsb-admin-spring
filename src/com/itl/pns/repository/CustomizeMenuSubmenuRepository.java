package com.itl.pns.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itl.pns.entity.CustomizeMenuSubMenuEntity;

public interface CustomizeMenuSubmenuRepository extends JpaRepository<CustomizeMenuSubMenuEntity, Integer> {

	/*
	@Query("from CustomizeMenuSubMenuEntity m where m.menuId.id=?")
	List<CustomizeMenuSubMenuEntity> findByMenuId(int menuId);
	
    List<CustomizeMenuSubMenuEntity> findByMenuIdAndStatusId(int menuId,int status);

	List<CustomizeMenuSubMenuEntity> findByroleid(int roleId);
	*/
	@Modifying
	@Query(" delete from CustomizeMenuSubMenuEntity m where  m.bankingType=? and m.customizeMenuId.id=?")
	void deleteByBankingTypeAndMenuId(String bankingType, BigInteger i);
	
	
	@Query(" from CustomizeMenuSubMenuEntity m where  m.bankingType=? and m.customizeMenuId.id=? and customizeSubmenuId.isActive=3 ")
	List<CustomizeMenuSubMenuEntity> findByBankingTypeAndMenuId(String bankingType, BigInteger i);
	/*
	@Query(" select m from CustomizeMenuSubMenuEntity m where  m.menuId=?  order by m.id desc")
	List<CustomizeMenuSubMenuEntity> findByMenuIdOrderByMenuName(int menuId);*/
}
