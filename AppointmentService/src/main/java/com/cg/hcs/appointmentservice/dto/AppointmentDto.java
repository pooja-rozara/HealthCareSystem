package com.cg.hcs.appointmentservice.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.cg.hcs.appointmentservice.entity.Appointment;



public class AppointmentDto {
	
	private String userId;
	private BigInteger appointmentId;
	private String testId;
	private LocalDateTime dateTime;
	private int status;
	private String diagnosticCenterId;
	public AppointmentDto() {
		super();
		
	}
	public AppointmentDto(Appointment appointment) {
		this.appointmentId=appointment.getAppointmentId();
		this.dateTime=appointment.getDateTime();
		this.userId=appointment.getUserId();
		this.testId=appointment.getTestId();
		this.status=appointment.getStatus();
		this.diagnosticCenterId=appointment.getDiagnosticCenterId();
		
	}
	public AppointmentDto(String userId, String testId, LocalDateTime dateTime, int status, String diagnosticCenterId) {
		super();
		this.userId = userId;
		this.testId = testId;
		this.dateTime = dateTime;
		this.status = status;
		this.diagnosticCenterId = diagnosticCenterId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BigInteger getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(BigInteger appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
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
	public String getDiagnosticCenterId() {
		return diagnosticCenterId;
	}
	public void setDiagnosticCenterId(String diagnosticCenterId) {
		this.diagnosticCenterId = diagnosticCenterId;
	}
	@Override
	public boolean equals(Object obj) {
		AppointmentDto appointment=(AppointmentDto) obj;
		return appointment.getAppointmentId()==this.appointmentId;
	}
	
}
