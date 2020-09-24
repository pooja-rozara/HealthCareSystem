package com.cg.healthCareSystem.appointmentService.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.healthCareSystem.appointmentService.entity.TestCenter;

@Repository
public interface TestRepository extends CrudRepository<TestCenter, String> {

}
