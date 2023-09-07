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

import com.itl.pns.corp.entity.CorpAccMapEntity;

@Repository
@Transactional
public interface CorpAccMapRepository extends JpaRepository<CorpAccMapEntity, Serializable> {

	public List<CorpAccMapEntity> findByCorpIdAndStatusId(BigDecimal corpId, BigDecimal statusid);

	@Modifying
	@Query(value = "update CorpAccMapEntity cm set cm.corpId=:oldCorpId, cm.updatedon = current_timestamp where cm.corpId=:newCorpId and cm.statusId=3")
	public void update(@Param("newCorpId") BigDecimal newCorpId, @Param("oldCorpId") BigDecimal oldCorpId);

	
	@Modifying
	@Query(value = "update CorpAccMapEntity cm set cm.statusId=10,cm.updatedon = current_timestamp where cm.corpId=:corpId and cm.statusId=:statusId")
	public void delete(@Param("corpId") BigDecimal newCorpId, @Param("statusId") BigDecimal statusId);

	@Modifying
	@Query(value = "update CorpAccMapEntity cm set cm.corpId=:oldCorpId,cm.updatedon = current_timestamp where cm.corpId=:newCorpId and cm.accountNo=:accountNo and cm.statusId=3")
	public void updateByAccountNo( @Param("oldCorpId") BigDecimal oldCorpId,@Param("newCorpId") BigDecimal newCorpId,
			@Param("accountNo") BigDecimal accountNo);
}
