package com.itl.pns.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itl.pns.entity.UserLoginDetails;

public interface UserLoginDetailsRepository extends JpaRepository<UserLoginDetails, Serializable> {

	public UserLoginDetails findByUserIdAndIsLogin(BigInteger userid, char isLogin);

	public List<UserLoginDetails> findByUserIdAndIsPasswordVerified(BigInteger userid, char isPasswordVerified);

}
