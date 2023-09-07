package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itl.pns.entity.ReleaseInfoEntity;

public interface ReleaseInfoRepository extends JpaRepository<ReleaseInfoEntity, Serializable> {

	public List<ReleaseInfoEntity> findAllByOrderByIdDesc();
}
