package com.itl.pns.corp.service;

import java.util.Map;



public interface ActiveDirectoryService {
	
	public Map<String, String> checkUser(String userName);
	
	public boolean validateUser(String userName,String password,String base);
	

}