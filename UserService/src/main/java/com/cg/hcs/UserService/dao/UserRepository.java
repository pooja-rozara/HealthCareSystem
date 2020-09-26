package com.cg.hcs.UserService.dao;

import org.springframework.data.repository.CrudRepository;

import com.cg.hcs.UserService.dto.UserDto;

public interface UserRepository extends CrudRepository<UserDto, String> {

}
