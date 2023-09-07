package com.itl.pns.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itl.pns.entity.SecurityQuestionMaster;

public interface SecurityQuestionsRepository extends JpaRepository<SecurityQuestionMaster, Serializable>{ 

}
