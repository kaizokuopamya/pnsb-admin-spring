package com.itl.pns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.AccessRights;

@Repository
public interface AccessRightsRepository extends JpaRepository<AccessRights, Integer>{

}
