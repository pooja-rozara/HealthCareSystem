package com.cg.hcs.UserService.service;

import com.cg.hcs.UserService.entity.User;

public interface UserService {
	
	User fetchUserByUserId(String userId);

	boolean checkUser(String userId);
	
	

}
