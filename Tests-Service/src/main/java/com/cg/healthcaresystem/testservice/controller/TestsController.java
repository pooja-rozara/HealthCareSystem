package com.cg.healthcaresystem.testservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cg.healthcaresystem.testservice.DTO.DiagnosticCenterDTO;
import com.cg.healthcaresystem.testservice.DTO.TestsDTO;
import com.cg.healthcaresystem.testservice.entity.Tests;
import com.cg.healthcaresystem.testservice.exception.InvalidArgumentException;
import com.cg.healthcaresystem.testservice.exception.InvalidTestIdException;
import com.cg.healthcaresystem.testservice.service.TestsService;

@RestController
@RequestMapping("/tests")
public class TestsController {

	@Autowired
	private TestsService testsService;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger log = LoggerFactory.getLogger(TestsController.class);

	@PostMapping("/add/{centerId}")
	public ResponseEntity<TestsDTO> addTest(@PathVariable String centerId, @RequestBody TestsDTO testsDto) {
		DiagnosticCenterDTO diagnosticCenterDTO = getCenterById(centerId);
		if (diagnosticCenterDTO != null) {
			Tests tests = convertFromDTO(testsDto);
			tests.setDiagnosticCenter(centerId);
			tests = testsService.addTest(tests);
			TestsDTO testresponseDto = convertToDTO(tests);
			return new ResponseEntity<>(testresponseDto, HttpStatus.OK);
		} else {
			throw new InvalidArgumentException("Center does not exits");
		}

	}

	@DeleteMapping("/remove/{centerId}")
	public ResponseEntity<Boolean> removeTest(@PathVariable String centerId, @RequestBody TestsDTO testsDto) {
		DiagnosticCenterDTO diagnosticCenterDTO = getCenterById(centerId);
		if (diagnosticCenterDTO != null) {
			Boolean result = testsService.deleteTest(testsDto);
			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
		} else {
			throw new InvalidArgumentException("Center does not exits");
		}

	}

	@GetMapping("/alltests")
	public ResponseEntity<List<TestsDTO>> getAllTests() {
		List<Tests> tests = testsService.findAllTests();
		List<TestsDTO> testDTOList = new ArrayList<>();
		for (Tests test : tests) {
			testDTOList.add(convertToDTO(test));
		}
		return new ResponseEntity<>(testDTOList, HttpStatus.OK);

	}

	@GetMapping("/alltests/{centerId}")
	public ResponseEntity<List<TestsDTO>> getTestByCenterId(@PathVariable String centerId) {
		List<Tests> tests = testsService.findTestByCenterId(centerId);
		List<TestsDTO> testDTOList = new ArrayList<>();
		for (Tests test : tests) {
			testDTOList.add(convertToDTO(test));
		}
		return new ResponseEntity<>(testDTOList, HttpStatus.OK);

	}

	@GetMapping("/search_test_name/{testName}")
	public ResponseEntity<TestsDTO> getTestByName(@PathVariable String testName) {
		Tests tests = testsService.findTestByName(testName);
		TestsDTO testDtoObj = convertToDTO(tests);
		return new ResponseEntity<>(testDtoObj, HttpStatus.OK);

	}

	@GetMapping("/search_test_id/{testId}")
	public ResponseEntity<TestsDTO> getTestById(@PathVariable String testId) {
		Tests tests = testsService.findTestById(testId);
		TestsDTO testDto = convertToDTO(tests);
		return new ResponseEntity<>(testDto, HttpStatus.OK);

	}

	public DiagnosticCenterDTO getCenterById(String diagnosticCenterId) {
		String url = "http://DIAGNOSTIC-CENTER-SERVICE/diagnosticcenters/search_center_by_id/" + diagnosticCenterId;
		ResponseEntity<DiagnosticCenterDTO> response = restTemplate.getForEntity(url, DiagnosticCenterDTO.class);
		DiagnosticCenterDTO center = response.getBody();
		return center;
	}

	public Tests convertFromDTO(TestsDTO testsDto) {
		Tests tests = new Tests();
		tests.setTestId(testsDto.getTestId());
		tests.setTestName(testsDto.getTestName());
		tests.setDiagnosticCenter(testsDto.getDiagnosticCenter());
		return tests;
	}

	public TestsDTO convertToDTO(Tests tests) {
		TestsDTO testDto = new TestsDTO();
		testDto.setTestId(tests.getTestId());
		testDto.setTestName(tests.getTestName());
		testDto.setDiagnosticCenter(tests.getDiagnosticCenter());
		return testDto;

	}

	@ExceptionHandler(InvalidTestIdException.class)
	public String handleCenterIdNotFound(InvalidTestIdException ex) {
		log.error("handleCenterIdNotFound()", ex);
		String msg = ex.getMessage();
		return msg;
	}

	@ExceptionHandler(InvalidArgumentException.class)
	public String handleCenterNotFound(InvalidArgumentException ex) {
		log.error("handleCenterNotFound()", ex);
		String msg = ex.getMessage();
		return msg;
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleAll(Throwable ex) {
		log.error("Something went wrong", ex);
		String msg = ex.getMessage();
		return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
