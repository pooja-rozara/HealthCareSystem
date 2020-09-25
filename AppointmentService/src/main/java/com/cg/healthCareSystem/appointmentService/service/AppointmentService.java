package com.cg.healthCareSystem.appointmentService.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.cg.healthCareSystem.appointmentService.entity.Appointment;
import com.cg.healthCareSystem.appointmentService.entity.DiagnosticCenter;
import com.cg.healthCareSystem.appointmentService.entity.TestCenter;
import com.cg.healthCareSystem.appointmentService.entity.User;

public interface AppointmentService {

	String checkAppointmentStatus(BigInteger appointmentId);

	List<Appointment> fetchAppointmentsByUserId(String userId) ;


	Appointment makeAppointment(String userId, String diagnosticCenterId, String testCenterId,
			LocalDateTime dateTime);

	List<Appointment> fetchAppointmentsByDiagnosticCenterId(String diagnosticCenterId);

	boolean approveAppointment(BigInteger appointmentId);
	
	boolean validateDate(LocalDateTime dateTime);

	List<LocalTime> getAvailableSlots(String testId, LocalDateTime time);
	
	String cancelAppointment(BigInteger appointmentId);

}
