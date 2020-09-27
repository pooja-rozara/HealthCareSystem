package com.cg.healthCareSystem.appointmentService.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.healthCareSystem.appointmentService.entity.Appointment;
import com.cg.healthCareSystem.appointmentService.service.AppointmentService;
import com.cg.healthCareSystem.appointmentService.service.AppointmentServiceImpl;

@DataJpaTest
@RunWith(SpringRunner.class)
public class appointmentRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
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
		int status=appointmentRepository.fetchStatusByAppointmentId(appointment.getAppointmentId());
		assertEquals(appointment.getStatus(), status);
	}

}
