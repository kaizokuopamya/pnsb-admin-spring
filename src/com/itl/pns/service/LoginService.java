package com.itl.pns.service;

import java.util.List;

import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.ResponseMessageBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.entity.User;

public interface LoginService {

	public boolean changePassword(ChangePasswordBean chanegebean);

	public boolean forgetPassword(ChangePasswordBean chanegebean);

	public UserDetailsBean getUserID(String userid);

	boolean logout(String token);

	public boolean logoutByUserId(String userid);

	public boolean updateStatus(String userid);

	public boolean updateStatusActive(String userid);

//	public ResponseMessageBean ckeckLogin(User user);
	public User ckeckLogin(User user);
	
	List<UserDetailsBean> getUserLoginTypes();




}
