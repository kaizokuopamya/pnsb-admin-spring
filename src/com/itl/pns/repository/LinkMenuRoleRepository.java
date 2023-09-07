package com.itl.pns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itl.pns.entity.LinkMenuRoles;


public interface LinkMenuRoleRepository extends JpaRepository<LinkMenuRoles, Integer>{

	List<LinkMenuRoles> findByroleidAndStatus(int roleId, int i);

	List<LinkMenuRoles> findByroleid(int roleid);

	void deleteByroleid(int roleid);

}
