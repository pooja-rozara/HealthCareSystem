package com.cg.healthCareSystem.appointmentService.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.healthCareSystem.appointmentService.dao.AppointmentRepository;
import com.cg.healthCareSystem.appointmentService.entity.Appointment;
import com.cg.healthCareSystem.appointmentService.entity.DiagnosticCenter;
import com.cg.healthCareSystem.appointmentService.entity.TestCenter;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AppointmentServiceImplTest {

	@InjectMocks
	private AppointmentServiceImpl appointmentService;
	
	@Mock
	private AppointmentRepository appointmentRepository;
	
	Appointment appointment=new Appointment();
	Appointment anotherAppointment=new Appointment();
	TestCenter test=new TestCenter();
	TestCenter firstTest=new TestCenter();
	TestCenter secondTest=new TestCenter();
	DiagnosticCenter diagnosticCenter=new DiagnosticCenter();
	BigInteger appointmentId=new BigInteger("1");
	
	@BeforeClass
	public void objectSetUp()
	{
		MockitoAnnotations.initMocks(this);
		
		//populating testCenter objects
		test.setTestId("15361dghgs");
		test.setTestName("Blood Test");
		test.setDiagnosticCenter(diagnosticCenter);
		firstTest.setTestId("153AGF");
		firstTest.setTestName("Blood Sugar Test");
		firstTest.setDiagnosticCenter(diagnosticCenter);
		secondTest.setTestId("153bhj");
		secondTest.setTestName("Blood Glucose Test");
		secondTest.setDiagnosticCenter(diagnosticCenter);
		
		
		//populating diagnosticCenter object
		diagnosticCenter.setCenterId("123abc");
		diagnosticCenter.setCenterName("Ram Diagnostic Center");
		diagnosticCenter.setCenterAddress("Landran, Punjab");
		diagnosticCenter.setContactNumber("9876504321");
		diagnosticCenter.setListOfTests(new ArrayList<TestCenter>(Arrays.asList(test,firstTest,secondTest)));
		
		
		//populating appointment Objects
		anotherAppointment.setAppointmentId(new BigInteger("2"));
		anotherAppointment.setDateTime(LocalDateTime.of(LocalDate.of(2020, 9, 26), LocalTime.of(11, 00)));
		anotherAppointment.setStatus(1);
		anotherAppointment.setDiagnosticCenter(diagnosticCenter);
		anotherAppointment.setTest(secondTest);
		
		appointment.setAppointmentId(appointmentId);
		appointment.setDateTime(LocalDateTime.of(LocalDate.of(2020, 9, 27), LocalTime.of(10, 00)));
		appointment.setStatus(0);
		appointment.setDiagnosticCenter(diagnosticCenter);
		appointment.setTest(firstTest);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void checkStatusTest()
	{
		
		Mockito.when(appointmentRepository.fetchStatusByAppointmentId(appointmentId)).thenReturn(0);
		System.out.println("testing "+appointmentId);
		assertThat(appointmentService.checkAppointmentStatus(appointmentId).equalsIgnoreCase("Cancelled"));
	}

}
