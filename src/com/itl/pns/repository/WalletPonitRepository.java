package com.itl.pns.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itl.pns.entity.WalletPoints;

public interface WalletPonitRepository  extends JpaRepository<WalletPoints, Integer>{


	List<WalletPoints> findAllByOrderByCreatedonDesc();
	

	WalletPoints findByConfigtype(String configtype);

	

}