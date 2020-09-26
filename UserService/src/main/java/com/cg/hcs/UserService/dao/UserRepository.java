package com.cg.hcs.UserService.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.hcs.UserService.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
