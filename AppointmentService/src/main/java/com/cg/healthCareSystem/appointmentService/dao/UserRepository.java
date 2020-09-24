package com.cg.healthCareSystem.appointmentService.dao;

import org.springframework.data.repository.CrudRepository;

import com.cg.healthCareSystem.appointmentService.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

}
