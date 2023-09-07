package com.itl.pns.dao;

import java.math.BigInteger;
import java.util.List;

import com.itl.pns.bean.ChangePasswordBean;
import com.itl.pns.bean.UserDetailsBean;
import com.itl.pns.entity.User;

/**
 * 
 * @author Sareeka Bangar
 * @since 14-6-2017
 * @version 1.0
 */
/**
 * The LoginDao is an interface which consist of abstract methods to be override
 * in LoginDaoImpl class
 * 
 */
public interface LoginDao {

	public UserDetailsBean getUserID(String userid);

	public boolean changePassword(ChangePasswordBean chanegebean);

	public boolean forgetPassword(ChangePasswordBean chanegebean);

	public boolean logout(String token);

	public boolean logoutByUserId(String userid);

	boolean updateStatus(String userid);

	public boolean updateStatusActive(String userid);

	public User ckeckLogin(User user);

	List<UserDetailsBean> getUserLoginTypes();

	public int saveToAdminUserSession(UserDetailsBean user);

	public int updateAdminSessionStatus(BigInteger userId);

}
