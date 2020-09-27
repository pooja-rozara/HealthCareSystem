package com.cg.healthCareSystem.appointmentService.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.healthCareSystem.appointmentService.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,BigInteger > {

	@Query("Select a.status from Appointment a where a.appointmentId=?1")
	int fetchStatusByAppointmentId(BigInteger appointmentId);

	@Query("Select a from Appointment a where a.userId=?1")
	List<Appointment> fetchAppointmentsByUserId(String userId);

	@Query("Select a from Appointment a where a.diagnosticCenterId=?1")
	List<Appointment> fetchAppointmentsByDiagnosticCenterId(String diagnosticCenterId);

	@Query("Select a.dateTime from Appointment a where a.appointmentId=?1")
	LocalDateTime fetchDateTimeByAppointmentId(BigInteger appointmentId);
	
	@Query("Select a from Appointment a where a.appointmentId=?1 and a.status=0")
	List<Appointment> checkPendingAppointmentForDiagnosticCenter(String diagnosticCenterId);


}
