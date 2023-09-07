package com.itl.pns.repository;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itl.pns.entity.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Serializable> {

	UserDetails findByemail(String email);

	UserDetails findByuserMasterId(Integer usermasterid);

	List<UserDetails> findByroleid(String roleid);

}
