package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.itl.pns.entity.LanguageMasterEntity;

@Repository
@Transactional(value = "jpaTransactionManager", propagation = Propagation.REQUIRES_NEW)
public interface LanguageMasterRepository extends JpaRepository<LanguageMasterEntity, Serializable> {

	@Query(value = "select lm.preferedLanguageCode from LanguageMasterEntity lm where statusid=3 ORDER BY preferedLanguageCode")
	public List<Object> languageCodeList();

}
