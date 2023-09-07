package com.itl.pns.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.MerchantEntity;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantEntity, BigDecimal> {

}
