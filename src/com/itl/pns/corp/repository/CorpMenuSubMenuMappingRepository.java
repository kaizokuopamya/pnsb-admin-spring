package com.itl.pns.corp.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpMenuSubMenuMappingEntity;

@Repository
public interface CorpMenuSubMenuMappingRepository extends JpaRepository<CorpMenuSubMenuMappingEntity, Serializable> {

	public List<CorpMenuSubMenuMappingEntity> findCorpMenuSubMenuByMenuIdAndStatusId(Long menuId, int statusId);

	@Query(value = "select cm.id as menu_Id, cm.menuName as menu_name, NVL(cs.id,0) as subMenu_Id, NVL(cs.MENUNAME,'NA') as submenu_Name from CORP_MENU_SUBMENU_MAP cms "
			+ "left join corp_menu cm on cm.id=cms.MENU_ID " + "left join corp_submenu cs on cs.id=cms.SUBMENU_ID "
			+ "where cm.statusid=3 and cms.status_id=3", nativeQuery = true)
	public List<Object[]> menuSubMenuList();

//	public CorpMenuSubMenuMappingEntity findCorpMenuSubMenuByMenuIdAndSubMenuIdAndStatusId(Long menuId, Long subMenuId,
//			Long statusId);

}
