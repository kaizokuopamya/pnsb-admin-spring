package com.itl.pns.corp.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpMenuEntity;

@Repository
public interface CorpMenuRepository extends JpaRepository<CorpMenuEntity, Serializable> {

}
