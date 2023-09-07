package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itl.pns.entity.CustomizeMenus;

public interface CustomizeMenuRepository extends JpaRepository<CustomizeMenus, Serializable>{

	
	@Query("select distinct(c.moduleName) from CustomizeMenus c")
	List<CustomizeMenus> findAllDistinct();

}
