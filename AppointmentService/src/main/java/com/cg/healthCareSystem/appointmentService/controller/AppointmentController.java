package com.cg.healthCareSystem.appointmentService.controller;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		List<Appointment> appointmentList = appointmentService
				.fetchAppointmentsByDiagnosticCenterId(diagnosticCenterId);

		return appointmentList;
	}

	@PutMapping("/approve")
	public void approveAppointment(@RequestParam("Id") BigInteger appointmentId) {
		appointmentService.approveAppointment(appointmentId);
	}

	@GetMapping("/ValidateDate")
	public boolean validateDate(@RequestParam String dateTime) {
		
		
		return appointmentService.validateDate(LocalDateTime.parse(dateTime));
	}

	@PutMapping("/cancel")
	public String cancelAppointment(@RequestParam("Id") BigInteger appointmentId) {
		return appointmentService.cancelAppointment(appointmentId);
	}

	@GetMapping("/availableSlots")
	List<LocalTime> getAvailableSlots(@RequestBody Map<String,String> input) {
			
		String testId= input.get("test");
		String dateTime= input.get("dateTime");
		
		return appointmentService.getAvailableSlots(testId, LocalDateTime.parse(dateTime));
	}
	
	@PostMapping("/makeAppointment")
	Appointment makeAppointment(@RequestBody Map<String,Object> input)
	{
		String userId= (String) input.get("user");
		String testId= (String) input.get("test");
		String dateTime= (String) input.get("dateTime");
		String diagnosticCenterId= (String) input.get("diagnosticCenter");
		
		return appointmentService.makeAppointment(userId, diagnosticCenterId, testId, LocalDateTime.parse(dateTime));
		
	}

}
