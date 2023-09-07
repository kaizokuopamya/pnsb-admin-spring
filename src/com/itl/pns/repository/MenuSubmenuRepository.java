package com.itl.pns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itl.pns.entity.MenuSubmenuEntity;

public interface MenuSubmenuRepository  extends JpaRepository<MenuSubmenuEntity, Integer>{



	@Query("from MenuSubmenuEntity m where m.menuId.id=?")
	List<MenuSubmenuEntity> findByMenuId(int menuId);
    List<MenuSubmenuEntity> findByMenuIdAndStatusId(int menuId,int status);

	List<MenuSubmenuEntity> findByroleid(int roleId);
	
	@Modifying
	@Query(" delete from MenuSubmenuEntity m where  m.roleid=? and m.menuId.id=?")
	void deleteByRoleidAndMenuId(int roleid, int i);
	
	
	@Query(" from MenuSubmenuEntity m where  m.roleid=? and m.menuId.id=? and m.statusId=3")
	List<MenuSubmenuEntity> findByRoleidAndMenuId(int roleid, int i);
	
	@Query(" select m from MenuSubmenuEntity m where  m.menuId=?  order by m.id desc")
	List<MenuSubmenuEntity> findByMenuIdOrderByMenuName(int menuId);



	
}
