package com.cg.healthCareSystem.appointmentService.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.healthCareSystem.appointmentService.dto.AppointmentDto;
import com.cg.healthCareSystem.appointmentService.entity.Appointment;
import com.cg.healthCareSystem.appointmentService.exception.NoValueFoundException;
import com.cg.healthCareSystem.appointmentService.exception.NotPossibleException;
import com.cg.healthCareSystem.appointmentService.service.AppointmentService;
import com.cg.healthCareSystem.appointmentService.service.AppointmentServiceImpl;

@DataJpaTest
@RunWith(SpringRunner.class)
class AppointmentServiceTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@TestConfiguration
	static class AppointmentServiceImplTestContextConfiguration{
		
		@Bean
		public AppointmentService appointmentService()
		{
			return new AppointmentServiceImpl();
		}
	}
	
	@Test
	public void fetchingStatusByAppointmentId()
	{
		Appointment appointment=new Appointment("123Abc","123def",LocalDateTime.parse("2020-09-29T12:00:00"),0,"123ghi");
		entityManager.persist(appointment);
		entityManager.flush();
		String status=appointmentService.checkAppointmentStatus(appointment.getAppointmentId());
		System.out.println(status);
		assertEquals("Pending", status);
	}
	@Test
	public void fetchingStatusByAppointmentIdWhenAppoitmentDoesNotExist()
	{
		Appointment appointment=new Appointment("123Abc","123def",LocalDateTime.parse("2020-09-29T12:00:00"),0,"123ghi");
		entityManager.persist(appointment);
		entityManager.flush();
		assertThrows(NoValueFoundException.class,()-> appointmentService.checkAppointmentStatus(new BigInteger("32434655")));
	}
	@Test
	public void fetchingApppointmentByUserId()
	{
		Appointment appointment=new Appointment("123Abc","123def",LocalDateTime.parse("2020-09-29T12:00:00"),0,"123ghi");
		entityManager.persist(appointment);
		entityManager.flush();
		Appointment secondAppointment=new Appointment("123Abc","123dTf",LocalDateTime.parse("2020-09-29T12:00:00"),0,"125ghi");
		entityManager.persist(secondAppointment);
		entityManager.flush();
		List<AppointmentDto> appointments=appointmentService.fetchAppointmentsByUserId(appointment.getUserId());
		List<Appointment> testingAppointments=new ArrayList<Appointment>();
		testingAppointments.add(appointment);
		testingAppointments.add(secondAppointment);
		assertEquals(testingAppointments.stream().map(a->new AppointmentDto(a)).collect(Collectors.toList()), appointments);
	}
	@Test
	public void fetchingApppointmentByUserIdWhenUserDoesNotExist()
	{
		Appointment appointment=new Appointment("123Abc","123def",LocalDateTime.parse("2020-09-29T12:00:00"),0,"123ghi");
		entityManager.persist(appointment);
		entityManager.flush();
		assertThrows(NoValueFoundException.class,()-> appointmentService.fetchAppointmentsByUserId(appointment.getUserId()));
	}
	@Test
	public void fetchingApppointmentByDiagnosticCenterId()
	{
		Appointment appointment=new Appointment("123Abc","123def",LocalDateTime.parse("2020-09-29T12:00:00"),0,"123ghi");
		entityManager.persist(appointment);
		entityManager.flush();
		Appointment secondAppointment=new Appointment("123Abc","123dTf",LocalDateTime.parse("2020-09-29T12:00:00"),0,"125ghi");
		entityManager.persist(secondAppointment);
		entityManager.flush();
		List<AppointmentDto> appointments=appointmentService.fetchAppointmentsByUserId(appointment.getDiagnosticCenterId());
		List<Appointment> testingAppointments=new ArrayList<Appointment>();
		testingAppointments.add(appointment);
		testingAppointments.add(secondAppointment);
		assertEquals(testingAppointments.stream().map(a->new AppointmentDto(a)).collect(Collectors.toList()), appointments);
	}
	@Test
	public void fetchingApppointmentByDiagnosticCenterIdWhenDiagnosticCenterDoesNotExist()
	{
		Appointment appointment=new Appointment("123Abc","123def",LocalDateTime.parse("2020-09-29T12:00:00"),0,"123ghi");
		entityManager.persist(appointment);
		entityManager.flush();
		assertThrows(NoValueFoundException.class,()-> appointmentService.fetchAppointmentsByUserId(appointment.getDiagnosticCenterId()));
	}
	@Test
	public void fetchingDateTimeByAppointmentId()
	{
		Appointment appointment=new Appointment("123Abc","123def",LocalDateTime.parse("2020-09-29T12:00:00"),0,"123ghi");
		entityManager.persist(appointment);
		entityManager.flush();
		boolean appointments=appointmentService.validateDate(appointment.getDateTime());
		
		assertEquals(true,appointments);
	}
		

}
