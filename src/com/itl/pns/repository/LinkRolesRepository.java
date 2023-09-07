package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.LinkRolesEntity;


@Repository
public interface LinkRolesRepository extends JpaRepository<LinkRolesEntity, Serializable>{
	
	List<LinkRolesEntity> findByroleId(int id);

}
