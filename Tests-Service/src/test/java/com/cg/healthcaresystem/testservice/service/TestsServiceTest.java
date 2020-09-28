package com.cg.healthcaresystem.testservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.healthcaresystem.testservice.dao.TestsDao;
import com.cg.healthcaresystem.testservice.entity.Tests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsServiceTest {
	@InjectMocks
	private TestsServiceImpl testsService;

	@MockBean
	private TestsDao testsDao;

	private Tests tests;

	@BeforeEach
	void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		tests = new Tests();
		tests.setTestId("qshxjs89832");
		tests.setTestName("Blood test");
	}

	@Test
	public void testAddTests() {

		Mockito.when(testsDao.save(tests)).thenReturn(tests);
		assertThat(testsService.addTest(tests)).isEqualTo(tests);

	}

	@Test
	public void testfindTestByName() {

		Mockito.when(testsDao.findByName("Blood test")).thenReturn(tests);
		assertThat(testsDao.findByName("Blood test")).isEqualTo(tests);

	}

	@Test
	public void testfindTestById() {

		Mockito.when(testsDao.findById("qshxjs89832")).thenReturn(Optional.of(tests));
		assertThat(testsService.findTestById("qshxjs89832")).isEqualTo(tests);

	}

	@Test
	public void testfindAllTests() {
		Tests tests1 = new Tests();
		tests1.setTestId("qshxjs89832");
		tests1.setTestName("Blood test");
		Tests tests2 = new Tests();
		tests2.setTestId("lqzax4562");
		tests2.setTestName("X-RAY");
		List<Tests> testList = new ArrayList<>();
		testList.add(tests1);
		testList.add(tests2);
		Mockito.when(testsDao.findAll()).thenReturn(testList);
		assertThat(testsService.findAllTests()).isEqualTo(testList);

	}

	@Test
	public void testdeleteTest() {
		Mockito.when(testsDao.findById("qshxjs89832")).thenReturn(Optional.of(tests));
		Mockito.when(testsDao.existsById("qshxjs89832")).thenReturn(false);
		assertFalse(testsDao.existsById(tests.getTestId()));

	}

//	@Test
//	public void testupdateCenter() {
//
//		Mockito.when(diagnosticCenterDao.findById("1")).thenReturn(Optional.of(diagnosticCenter));
//		diagnosticCenter.setCenterAddress("5 BN road");
//		Mockito.when(diagnosticCenterDao.save(diagnosticCenter)).thenReturn(diagnosticCenter);
//
//		DiagnosticCenter center = new DiagnosticCenter();
//		center.setCenterId("1");
//		center.setCenterAddress("5 BN road");
//		assertThat(diagnosticCenterService.updateCenter(center)).isEqualTo(diagnosticCenter);
//
//	}

}