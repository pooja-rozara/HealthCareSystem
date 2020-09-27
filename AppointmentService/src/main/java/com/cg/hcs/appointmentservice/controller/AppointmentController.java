package com.cg.hcs.appointmentservice.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.hcs.appointmentservice.dto.AppointmentDto;
import com.cg.hcs.appointmentservice.service.AppointmentService;


@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	
	

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping("/status")
	public String getStatus(@RequestParam("Id") BigInteger appointmentId) {
		return appointmentService.checkAppointmentStatus(appointmentId);
	}

	@GetMapping("/appointments-by-UserId")
	public List<AppointmentDto> getAppointmentByUserId(@RequestParam("Id") String userId) {
		
		return  appointmentService.fetchAppointmentsByUserId(userId);
	}

	@GetMapping("/appointments-by-DiagnosticCenterId")
	public List<AppointmentDto> getAppointmentsByDiagnosticCenterId(@RequestParam("Id") String diagnosticCenterId) {
		
		return appointmentService.fetchAppointmentsByDiagnosticCenterId(diagnosticCenterId);
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
	public String cancelAppointment( @RequestParam("Id") BigInteger appointmentId) {
		return appointmentService.cancelAppointment(appointmentId);
	}

	@GetMapping("/availableSlots")
	public List<LocalTime> getAvailableSlots(@RequestBody Map<String,String> input) {
			
		String testId= input.get("test");
		String dateTime= input.get("dateTime");
		
		return appointmentService.getAvailableSlots(testId, LocalDateTime.parse(dateTime));
	}
	
	@PostMapping("/makeAppointment")
	public AppointmentDto makeAppointment(@RequestBody AppointmentDto appointmentDto)
	{
		return appointmentService.makeAppointment(appointmentDto);
	}

	@GetMapping("/check-appointment-by-appointmentId")
	public boolean checkAppointmentExists(BigInteger appointmentId)
	{
		return appointmentService.checkAppointmentByAppointmentId(appointmentId);
	}
	@GetMapping("/search-appointment-by-appointmentId")
	public AppointmentDto searchAppointment(BigInteger appointmentId)
	{
		return appointmentService.searchAppointmentByAppointmentId(appointmentId);
	}
	@GetMapping("/pendingAppointmentOfDiagnosticCenter")
	public boolean checkPendingAppointmentsForDiagnosticCenter(@RequestParam("Id") String diagnosticCenterId)
	{
		return appointmentService.getPendingAppointmentsForDiagnosticCenter(diagnosticCenterId);
	}
	@GetMapping("/pendingAppointmentOfTestCenter")
	public boolean checkPendingAppointmentsForTestCenter(@RequestParam("Id") String testCenterId)
	{
		return appointmentService.getPendingAppointmentsForTestCenter(testCenterId);
	}
}
