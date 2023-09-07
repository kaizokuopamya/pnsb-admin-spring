package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.itl.pns.entity.ThemeNameEntity;

public interface ThemeNamesRepository extends JpaRepository<ThemeNameEntity, Serializable>{
	
	List<ThemeNameEntity> findByid(int id);
	
	List<ThemeNameEntity> findAll();

}
