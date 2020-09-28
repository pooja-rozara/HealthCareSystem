package com.cg.healthcaresystem.testservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.healthcaresystem.testservice.DTO.TestsDTO;
import com.cg.healthcaresystem.testservice.dao.TestsDao;
import com.cg.healthcaresystem.testservice.entity.Tests;
import com.cg.healthcaresystem.testservice.exception.InvalidArgumentException;
import com.cg.healthcaresystem.testservice.exception.InvalidTestIdException;

@Service
public class TestsServiceImpl implements TestsService{
	
	@Autowired
	private TestsDao testsDao;

	@Override
	public Tests addTest(Tests test) {
		if (test == null) {
			throw new InvalidArgumentException("Test cannot be null");
		}
		else {
			UUID uuid = UUID.randomUUID();
			test.setTestId(uuid.toString().substring(0, 13).replace("-", ""));
			System.out.println(test.toString());
		    test= testsDao.save(test);
			return test;
		}
	}

	@Override
	public boolean deleteTest(TestsDTO testDto) {
		String testId = testDto.getTestId();
		if (testId == null) {
			throw new InvalidArgumentException("Test Id cannot be null");
		}
		Optional<Tests> test = testsDao.findById(testId);
		System.out.println(testId);
		if (test.isPresent()) {
			testsDao.deleteById(testId);
			return true;
		} else {
			throw new InvalidTestIdException("Sorry, No test found with the given Id");
		}
	}

	@Override
	public Tests findTestById(String testId) {
		Optional<Tests> test = testsDao.findById(testId);
		if (test.isPresent()) {
			return  test.get();

		} else {
			throw new InvalidTestIdException("Sorry, No test found with the given Id");
		}
	}

	@Override
	public List<Tests> findAllTests() {
		return testsDao.findAll();
	}

	@Override
	public Tests findTestByName(String testName) {
		return  testsDao.findByName(testName);
	}

	@Override
	public Tests updateTest(Tests test) {
		if (test == null) {
			throw new InvalidArgumentException("Please provide Test Details");
		}
		Optional<Tests> tests = testsDao.findById(test.getTestId());
		if (!tests.isPresent()) {
			throw new InvalidArgumentException("Warning! Test with the given Id not present. Will add a Test center");
		} else {
			testsDao.save(test);
			return test;
		}
	}

	@Override
	public List<Tests> findTestByCenterId(String centerId) {
		 return testsDao.findTestByCenterId(centerId);
	}


}
