package com.itl.pns.entity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USER_LOGIN_DETAILS")
public class UserLoginDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
	@SequenceGenerator(name = "id_Sequence", sequenceName = "USER_LOGIN_DETAILS_SEQ", allocationSize = 1)
	@Column(name = "ID")
	private BigInteger id;

	@Column(name = "USER_ID")
	private BigInteger userId;

	@Column(name = "CREATED_ON")
	private Timestamp createdOn;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "IS_PASSWORD_VERIFIED")
	private char isPasswordVerified;

	@Column(name = "IS_LOGIN")
	private char isLogin;

	public char getIsPasswordVerified() {
		return isPasswordVerified;
	}

	public void setIsPasswordVerified(char isPasswordVerified) {
		this.isPasswordVerified = isPasswordVerified;
	}

	public char getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(char isLogin) {
		this.isLogin = isLogin;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
