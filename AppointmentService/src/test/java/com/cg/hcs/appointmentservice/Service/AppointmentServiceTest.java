package com.cg.hcs.appointmentservice.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cg.hcs.appointmentservice.entity.Appointment;
import com.cg.hcs.appointmentservice.exception.NoValueFoundException;
import com.cg.hcs.appointmentservice.exception.NotPossibleException;
import com.cg.hcs.appointmentservice.service.AppointmentService;
import com.cg.hcs.appointmentservice.service.AppointmentServiceImpl;

@DataJpaTest
@RunWith(SpringRunner.class)
class AppointmentServiceTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private AppointmentService appointmentService;

	Appointment appointment;

	@TestConfiguration
	static class AppointmentServiceImplTestContextConfiguration {

		@Bean
		public AppointmentService appointmentService() {
			return new AppointmentServiceImpl();
		}
	}

	@BeforeEach
	public void configure() {
		appointment = new Appointment("123Abc", "123def", LocalDateTime.parse("2020-09-29T12:00:00"), 0, "123ghi");
		entityManager.persist(appointment);
		entityManager.flush();
	}

	@Test
	void checkAppointmentStatusByAppointmentId() {

		String status = appointmentService.checkAppointmentStatus(appointment.getAppointmentId());
		System.out.println(status);
		assertEquals("Pending", status);
	}

	@Test
	void checkAppointmentStatusByAppointmentIdWhenAppoitmentDoesNotExist() {
		assertThrows(NoValueFoundException.class,
				() -> appointmentService.checkAppointmentStatus(BigInteger.valueOf(32434655)));
	}

//	@Test
//	public void fetchingApppointmentByUserId() {
//		List<Appointment> testingAppointments = new ArrayList<Appointment>();
//		testingAppointments.add(appointment);
//		assertEquals(testingAppointments.stream().map(a -> new AppointmentDto(a)).collect(Collectors.toList()),
//				 appointmentService.fetchAppointmentsByUserId(appointment.getUserId()));
//	}
//
//	@Test
//	public void fetchingApppointmentByUserIdWhenUserDoesNotExist() {
//		assertThrows(NoValueFoundException.class,
//				() -> appointmentService.fetchAppointmentsByUserId(appointment.getUserId()));
//	}

//	@Test
//	public void fetchingApppointmentByUserIdWhenAppointmentDoesNotExist() {
//		// input value which have uiserId but no appointment with that
//		assertThrows(NoValueFoundException.class, () -> appointmentService.fetchAppointmentsByUserId("123wed"));
//	}

//	@Test
//	public void fetchingApppointmentByDiagnosticCenterId() {
//		
//		List<Appointment> testingAppointments = new ArrayList<Appointment>();
//		testingAppointments.add(appointment);
//		assertEquals(testingAppointments.stream().map(a -> new AppointmentDto(a)).collect(Collectors.toList()),
//				appointmentService.fetchAppointmentsByUserId(appointment.getDiagnosticCenterId()));
//	}
//
//	@Test
//	public void fetchingApppointmentByDiagnosticCenterIdWhenDiagnosticCenterDoesNotExist() {
//		assertThrows(NoValueFoundException.class,
//				() -> appointmentService.fetchAppointmentsByUserId(appointment.getDiagnosticCenterId()));
//	}
//
//	@Test
//	public void fetchingApppointmentByDiagnosticCenterIdWhenAppointmentDoesNotExist() {
//		// enter correct value of diagnostic center with no appointment
//		assertThrows(NoValueFoundException.class, () -> appointmentService.fetchAppointmentsByUserId("dsgvf"));
//	}

	@Test
	void approvingAppointmentWithAppointmentId() {
		assertEquals(true, appointmentService.approveAppointment(appointment.getAppointmentId()));
	}

	@Test
	void approvingAppointmentWithInavlidAppointmentId() {
	
		assertThrows(NoValueFoundException.class,
				() -> appointmentService.approveAppointment(BigInteger.valueOf(345678)));
	}

	@Test
	void approvingAppointmentWithAppointmentIdWhichIsAlreadyApproved() {
		appointment.setStatus(1);
		entityManager.persistAndFlush(appointment);
		assertThrows(NotPossibleException.class,
				() -> appointmentService.approveAppointment(appointment.getAppointmentId()));
	}

	@Test
	void approvingAppointmentWithAppointmentIdWhichIsAlreadyCancelled() {
		appointment.setStatus(-1);
		entityManager.persistAndFlush(appointment);
		assertThrows(NotPossibleException.class,
				() -> appointmentService.approveAppointment(appointment.getAppointmentId()));
	}

	@Test
	void approvingAppointmentWithAppointmentIdWhoseDateIsAlreadyMissed() {
		Appointment appointment = new Appointment("123Abc", "123def", LocalDateTime.parse("2020-08-29T12:00:00"), 1,
				"123ghi");
		entityManager.persist(appointment);
		entityManager.flush();
		assertThrows(NotPossibleException.class,
				() -> appointmentService.approveAppointment(appointment.getAppointmentId()));
	}

	@Test
	void validatingDate() {
		LocalDateTime dateTime = LocalDateTime.parse("2020-09-29T12:00:00");
		assertEquals(true, appointmentService.validateDate(dateTime));
	}
	@Test
	void validatingDateBeforeToday() {
		LocalDateTime dateTime = LocalDateTime.parse("2020-08-29T12:00:00");
		assertThrows(NotPossibleException.class, () -> appointmentService.validateDate(dateTime));
	}
	@Test
	void validatingDateAfter30DaysFromToday() {
		LocalDateTime dateTime = LocalDateTime.parse("2020-11-29T12:00:00");
		assertThrows(NotPossibleException.class, () -> appointmentService.validateDate(dateTime));
	}
	@Test
	void validatingDateOfSunday() {
		LocalDateTime dateTime = LocalDateTime.parse("2020-10-04T12:00:00");
		assertThrows(NotPossibleException.class, () -> appointmentService.validateDate(dateTime));
	}
	//@Test
	//public void getAvailableSlots() {
//		LocalDateTime dateTime = LocalDateTime.parse("2020-09-29T12:00:00");
//		String testId="123Abc";
//		
	//}

}
