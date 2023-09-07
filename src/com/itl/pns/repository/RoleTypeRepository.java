package com.itl.pns.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.RoleTypesEntity;

@Repository
public interface RoleTypeRepository extends JpaRepository<RoleTypesEntity, Integer> {

}
