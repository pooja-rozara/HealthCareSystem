package com.cg.hcs.UserService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hcs.UserService.dto.UserDto;
import com.cg.hcs.UserService.entity.User;
import com.cg.hcs.UserService.service.UserService;

@RestController
public class UserServiceController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getUser")
	ResponseEntity<UserDto> fetchUserByUserId(@RequestParam("Id") String userId)
	{
		User user=userService.fetchUserByUserId(userId);
		return new ResponseEntity<>(new UserDto(user),HttpStatus.OK);
	}
	@GetMapping("/check")
	boolean checkUserByUserId(@RequestParam("Id") String userId)
	{
		
		return userService.checkUser(userId);
	}
	
	

}
