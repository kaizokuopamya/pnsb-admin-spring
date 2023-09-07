package com.itl.pns.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.Donation;


@Repository
public interface DonationRepository extends JpaRepository<Donation, Serializable>{ 

}
