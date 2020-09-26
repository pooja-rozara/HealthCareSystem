package com.cg.hcs.UserService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.hcs.UserService.dao.UserRepository;
import com.cg.hcs.UserService.entity.User;
import com.cg.hcs.UserService.exception.NoValueFoundException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User fecthUserByUserId(String userId) {
		Optional<User> user= userRepository.findById(userId);
		if(user.isEmpty()) {
			throw new NoValueFoundException("No User present with the given Id");
		}
		return user.get();
	}

}
