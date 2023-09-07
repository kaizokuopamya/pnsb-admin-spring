package com.itl.pns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itl.pns.entity.ActivityNotificationMasterEntity;

public interface ActivityNotificationRepository extends JpaRepository<ActivityNotificationMasterEntity, Long> {

	List<ActivityNotificationMasterEntity>findAll();
	

	@Modifying
	@Query(value=" select a.id,a.activitycode, a.displayname ,an.activityid,an.sms,an.email,an.push ,an.id as actinotiid,an.createdon  "
			+ " from ACTIVITY_NOTIFICATION_MASTER an inner join activitymaster a on an.activityid=a.id ",nativeQuery=true)
	 List<Object[]> findAllActivityNotification();
	 

		@Query(" select m from ActivityNotificationMasterEntity m where  m.activityid.id=? ")
		List<ActivityNotificationMasterEntity> findByActivityid(Long actId);
		
		@Modifying
		@Query(value=" select an.id,a.activitycode, a.displayname ,an.activityid,an.sms,an.email,an.push ,an.createdon "
				+ "		 from ACTIVITY_NOTIFICATION_MASTER an inner join activitymaster a on an.activityid=a.id where an.id=?1 ",nativeQuery=true)
		 List<Object[]> findByid(Long id);
	
	
}
