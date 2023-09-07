package com.itl.pns.repository;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.itl.pns.corp.entity.CorpUserEntityTmp;

@Repository
public interface CorpUserEntityTmpRepository extends JpaRepository<CorpUserEntityTmp, Serializable> {

	@Procedure("validate_hierarchy")
	Map<String, Object> validateHierarchy(int corpCompId);

}
