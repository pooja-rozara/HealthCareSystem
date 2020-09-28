package com.cg.healthcaresystem.testservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.healthcaresystem.testservice.entity.Tests;

public interface TestsDao extends JpaRepository<Tests, String> {

	@Query(value = "Select * from test where test_name =?1 ", nativeQuery = true)
	Tests findByName(String testName);

	@Query(value = "Select * from test where diagnostic_center_id =?1 ", nativeQuery = true)
	List<Tests> findTestByCenterId(String centerId);

}
