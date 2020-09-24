package com.cg.healthCareSystem.appointmentService.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.healthCareSystem.appointmentService.entity.Appointment;
import com.cg.healthCareSystem.appointmentService.service.AppointmentService;

@RestController
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/status")
	public String getStatus(@RequestParam("Id") BigInteger appointmentId) {
		return appointmentService.checkAppointmentStatus(appointmentId);
	}

	@GetMapping("/appointmentsByUserId")
	public List<Appointment> getAppointmentByUserId(@RequestParam("Id") String userId) {
		List<Appointment> appointmentList = appointmentService.fetchAppointmentsByUserId(userId);

		return appointmentList;
	}
	
	@GetMapping("/appointmentsByDiagnosticCenterId")
	public List<Appointment> getAppointmentsByDiagnosticCenterId(@RequestParam("Id") String diagnosticCenterId) {
		List<Appointment> appointmentList = appointmentService.fetchAppointmentsByDiagnosticCenterId(diagnosticCenterId);

		return appointmentList;
	}
	
	@GetMapping("/approve")
	public void approveAppointment(@RequestParam("Id") BigInteger appointmentId) {
		appointmentService.approveAppointment(appointmentId);
	}
	
	@GetMapping("/ValidateDate")
	public boolean validateDate(@RequestParam LocalDateTime dateTime) {
		return appointmentService.validateDate(dateTime);
	}
	
	
}
