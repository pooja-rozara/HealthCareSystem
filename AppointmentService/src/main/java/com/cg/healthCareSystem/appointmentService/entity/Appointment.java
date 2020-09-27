package com.cg.healthCareSystem.appointmentService.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cg.healthCareSystem.appointmentService.dto.AppointmentDto;


@Entity
@Table(name = "appointments")
public class Appointment {

	@Column(name="user_id")
	private String userId;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "appointment_id")
	private BigInteger appointmentId;

	@Column(name="test_id")
	private String testId;

	@Column(name = "date_time")
	private LocalDateTime dateTime;

	@Column(name = "status")
	private int status;

	@Column(name="diagnostic_id")
	private String diagnosticCenterId;

	public Appointment() {
		super();

	}
	public Appointment(AppointmentDto appointmentDto)
	{
		this.appointmentId=appointmentDto.getAppointmentId();
		this.dateTime=appointmentDto.getDateTime();
		this.userId=appointmentDto.getUserId();
		this.testId=appointmentDto.getTestId();
		this.status=appointmentDto.getStatus();
		this.diagnosticCenterId=appointmentDto.getDiagnosticCenterId();
	}
	public Appointment(String userId, String testId, LocalDateTime dateTime, int status, String diagnosticCenterId) {
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
		Appointment appointment=(Appointment) obj;
		return appointment.getAppointmentId()==this.appointmentId;
	}
	

}
