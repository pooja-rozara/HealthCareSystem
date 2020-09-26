package com.cg.hcs.UserService.dao;

import org.springframework.data.repository.CrudRepository;

import com.cg.hcs.UserService.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

}
