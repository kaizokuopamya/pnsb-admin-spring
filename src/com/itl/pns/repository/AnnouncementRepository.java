package com.itl.pns.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.AnnouncementsEntity;


@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementsEntity, Serializable>{

}
