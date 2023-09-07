package com.itl.pns.corp.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.corp.entity.CorpMenuMapEntity;

@Repository
@Transactional
public interface CorpMenuMapRepository extends JpaRepository<CorpMenuMapEntity, Serializable> {

	public List<CorpMenuMapEntity> findByCorpIdAndStatusId(BigDecimal corpId, BigDecimal statusid);

	@Modifying
	@Query(value = "update CorpMenuMapEntity cm set cm.corpId=:oldCorpId,cm.updatedon = current_timestamp where cm.corpId=:newCorpId and cm.statusId=3")
	public void update(@Param("newCorpId") BigDecimal newCorpId, @Param("oldCorpId") BigDecimal oldCorpId);

	@Modifying
	@Query(value = "update CorpMenuMapEntity cm set cm.statusId=10, cm.updatedon = current_timestamp where cm.corpId=:corpId and cm.statusId=:statusId")
	public void delete(@Param("corpId") BigDecimal corpId, @Param("statusId") BigDecimal statusId);

	@Modifying
	@Query(value = "update CorpMenuMapEntity cm set cm.corpId=:oldCorpId,cm.updatedon = current_timestamp where cm.corpId=:newCorpId and cm.corpMenuId=:corpMenuId and cm.statusId=3")
	public void updateByMenuId(@Param("oldCorpId") BigDecimal oldCorpId,@Param("newCorpId") BigDecimal newCorpId, @Param("corpMenuId") BigDecimal menuId);

	@Modifying
	@Query(value = "update CORP_MENU_MAP SET StatusId = 10 WHERE id in (select cm.ID from CORP_MENU_MAP cm inner join corp_menu cmm on cmm.id =cm.corpMenuId  where cm.corpId=:corpId and cm.statusid = 3 and cmm.menuname = 'PFMS')",nativeQuery=true)
	public void corpCompMenuUpdateById(@Param("corpId") BigDecimal corpId);
}
