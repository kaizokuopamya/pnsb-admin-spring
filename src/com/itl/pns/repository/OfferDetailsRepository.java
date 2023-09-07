package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.OfferDetailsEntity;

@Repository
public interface OfferDetailsRepository extends JpaRepository<OfferDetailsEntity, Serializable> {

	@Query("select distinct serviceType from OfferDetailsEntity")
	public List<String> findDistinctByServiceType();

}
