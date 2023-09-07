package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.NotificationsEntity;

@Repository
public interface NotificationEntityRepository extends JpaRepository<NotificationsEntity, Serializable> {

	@Query(value = "Select COUNT(*) as totalCount from notifications where notification_type =? and customerid =? and createdon > sysdate - interval '5' minute", nativeQuery = true)
	public int notificationCount(BigDecimal notificationType, String customerId, int timeInterval);
	
	@Query(value = " FROM NotificationsEntity WHERE notification_type = 3 and statusId = '0'")
	public List<NotificationsEntity> getEmailList();
	
	@Query(value = " FROM NotificationsEntity WHERE notification_type = 2 and statusId = '0'")
	public List<NotificationsEntity> getMobileList();
	
	@Query(value = " FROM NotificationsEntity WHERE notification_type = 5 and statusId = '0'")
	public List<NotificationsEntity> getPushList();

	@Modifying
	@Query(value = " UPDATE NOTIFICATIONS SET statusId ='3' WHERE id =:Id and notification_type = 3",nativeQuery=true)
	public void updateEmailId(@Param("Id") BigDecimal id);
	
	@Modifying
	@Query(value = " UPDATE NOTIFICATIONS SET statusId ='3' WHERE id =:Id and notification_type = 2",nativeQuery=true)
	public void updateMobId(@Param("Id") BigDecimal id);
	
	@Modifying
	@Query(value = " UPDATE NOTIFICATIONS SET statusId ='3' WHERE id =:Id and notification_type = 5",nativeQuery=true)
	public void updatePushId(@Param("Id") BigDecimal id);
	
	@Query(value = " FROM NotificationsEntity WHERE id=:Id and notification_type = 3 and statusId = '0'")
	public NotificationsEntity getEmailDetailsById(@Param("Id") BigDecimal id);
	
	@Query(value = " FROM NotificationsEntity WHERE id=:Id and notification_type = 2 and statusId = '0'")
	public NotificationsEntity getSmsDetailsById(@Param("Id") BigDecimal id);
	
	@Query(value = " FROM NotificationsEntity WHERE id=:Id and notification_type = 5 and statusId = '0'")
	public NotificationsEntity getPushDetailsById(@Param("Id") BigDecimal id);
}
