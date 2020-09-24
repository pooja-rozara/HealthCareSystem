package com.cg.healthCareSystem.appointmentService.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.healthCareSystem.appointmentService.entity.Appointment;
import com.cg.healthCareSystem.appointmentService.entity.DiagnosticCenter;
import com.cg.healthCareSystem.appointmentService.entity.User;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment,BigInteger > {

	@Query("Select a.status from Appointment a where a.appointmentId=?1")
	int fetchStatusByAppointmentId(BigInteger appointmentId);

	@Query("Select a from Appointment a where a.user=?1")
	List<Appointment> fetchAppointmentsByUserId(User user);

	@Query("Select a from Appointment a where a.diagnosticCenter=?1")
	List<Appointment> fetchAppointmentsByDiagnosticCenterId(DiagnosticCenter diagnosticCenter);

	@Query("Select a.dateTime from Appointment a where a.appointmentId=?1")
	LocalDateTime fetchDateTimeByAppointmentId(BigInteger appointmentId);

	@Query("Update Appointment set status=?1 where appointmentId=?2")
	boolean setStatus(int status,BigInteger appointmentId);

}
