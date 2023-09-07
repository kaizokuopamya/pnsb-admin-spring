package com.itl.pns.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.MessageMasterEntity;

@Repository
public interface MessageMasterRepository extends JpaRepository<MessageMasterEntity, Long> {

	@Modifying
	@Query(value = "update MessageMasterEntity m set m.smsMessage=:smsMessage, m.subject =:subject, m.emailMessage =:emailMessage, m.pushMessage =:pushMessage, m.inAppMessage =:inAppMessage, m.otherMessag1 =:otherMessag1, m.otherMessag2 =:otherMessag2, m.otherMessag3 =:otherMessag3, m.otherMessag4 =:otherMessag4, m.statusId =:statusId where m.id=:id")
	public void update(@Param("smsMessage") String smsMessage, @Param("subject") String subject,
			@Param("emailMessage") String emailMessage, @Param("pushMessage") String pushMessage,
			@Param("inAppMessage") String inAppMessage, @Param("otherMessag1") String otherMessag1,
			@Param("otherMessag2") String otherMessag2, @Param("otherMessag3") String otherMessag3,
			@Param("otherMessag4") String otherMessag4, @Param("statusId") BigDecimal statusId,
			@Param("id") BigDecimal id);

}
