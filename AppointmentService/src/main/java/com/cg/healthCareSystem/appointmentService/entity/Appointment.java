package com.cg.healthCareSystem.appointmentService.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "appointments")
public class Appointment {
	@OneToOne(cascade = CascadeType.ALL) 
	@JoinColumn(name = "user_fk")
	private User user;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "appointment_id")
	private BigInteger appointmentId;
	@OneToOne(cascade = CascadeType.ALL) 
	@JoinColumn(name = "test_fk")
	private TestCenter test;
	@Column(name = "date_time")
	private LocalDateTime dateTime;
	@Column(name = "status")
	private int status;
	@ManyToOne
	@JoinColumn(name = "center_fk")
	private DiagnosticCenter diagnosticCenter;
	public Appointment() {
		super();
		
	}
	public Appointment(User user, TestCenter test, LocalDateTime dateTime,DiagnosticCenter diagnosticCenter, int status) {
		super();
		this.user = user;
		this.test = test;
		this.dateTime = dateTime;
		this.diagnosticCenter = diagnosticCenter;
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BigInteger getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(BigInteger appointmentId) {
		this.appointmentId = appointmentId;
	}
	public TestCenter getTest() {
		return test;
	}
	public void setTest(TestCenter test) {
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
	@JsonManagedReference
	public DiagnosticCenter getDiagnosticCenter() {
		return diagnosticCenter;
	}
	public void setDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}
	
}
