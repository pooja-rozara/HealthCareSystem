package com.cg.healthcaresystem.testservice.service;

import java.util.List;

import com.cg.healthcaresystem.testservice.DTO.TestsDTO;
import com.cg.healthcaresystem.testservice.entity.Tests;


public interface TestsService {

	Tests addTest(Tests test);

	boolean deleteTest(TestsDTO testdto);

	Tests findTestById(String testId);

	List<Tests> findAllTests();

	Tests findTestByName(String testName);

	Tests updateTest(Tests test);

	List<Tests> findTestByCenterId(String centerId);
	
}
