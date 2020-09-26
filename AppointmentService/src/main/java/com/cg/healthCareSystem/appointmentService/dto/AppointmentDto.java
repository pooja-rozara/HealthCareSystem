package com.cg.healthCareSystem.appointmentService.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;



public class AppointmentDto {
	private UserDto user;
	private BigInteger appointmentId;
	private TestCenterDto test;
	private LocalDateTime dateTime;
	private int status;
	private DiagnosticCenterDto diagnosticCenter;
	public AppointmentDto() {
		super();
		
	}
	public AppointmentDto(UserDto user, TestCenterDto test, LocalDateTime dateTime,DiagnosticCenterDto diagnosticCenter, int status) {
		super();
		this.user = user;
		this.test = test;
		this.dateTime = dateTime;
		this.diagnosticCenter = diagnosticCenter;
		this.status = status;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public BigInteger getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(BigInteger appointmentId) {
		this.appointmentId = appointmentId;
	}
	public TestCenterDto getTest() {
		return test;
	}
	public void setTest(TestCenterDto test) {
		this.test = test;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public DiagnosticCenterDto getDiagnosticCenter() {
		return diagnosticCenter;
	}
	public void setDiagnosticCenter(DiagnosticCenterDto diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}
	
}
