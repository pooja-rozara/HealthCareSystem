package com.cg.hcs.appointmenservice.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.cg.hcs.appointmenservice.dto.AppointmentDto;

public interface AppointmentService {

	String checkAppointmentStatus(BigInteger appointmentId);

	List<AppointmentDto> fetchAppointmentsByUserId(String userId);

	AppointmentDto makeAppointment(AppointmentDto appointment);

	List<AppointmentDto> fetchAppointmentsByDiagnosticCenterId(String diagnosticCenterId);

	boolean approveAppointment(BigInteger appointmentId);

	boolean validateDate(LocalDateTime dateTime);

	List<LocalTime> getAvailableSlots(String testId, LocalDateTime time);

	String cancelAppointment(BigInteger appointmentId);

	boolean checkAppointmentByAppointmentId(BigInteger appointmentId);

	AppointmentDto searchAppointmentByAppointmentId(BigInteger appointmentId);

	boolean getPendingAppointmentsForDiagnosticCenter(String diagnosticCenterId);
}
