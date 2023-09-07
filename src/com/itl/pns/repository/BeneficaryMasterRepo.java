package com.itl.pns.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itl.pns.entity.BeneficaryMasterEntity;
import com.itl.pns.entity.Donation;

public interface BeneficaryMasterRepo extends JpaRepository<BeneficaryMasterEntity, Serializable>{ 

}
