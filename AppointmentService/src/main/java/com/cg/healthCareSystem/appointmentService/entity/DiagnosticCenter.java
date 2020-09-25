package com.cg.healthCareSystem.appointmentService.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Diagnostic_centers")
public class DiagnosticCenter {
	@Column(name = "center_name")
	private String centerName;
	@Id
	@Column(name = "center_id")
	private String centerId;
	@OneToMany(targetEntity = TestCenter.class, cascade = CascadeType.ALL, mappedBy = "diagnosticCenter")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TestCenter> listOfTests;
	@OneToMany(targetEntity = Appointment.class, cascade = CascadeType.ALL, mappedBy = "diagnosticCenter",  fetch = FetchType.EAGER) 
	private List<Appointment> appointmentList;
	@Column(name = "center_address")
	private String centerAddress;
	@Column(name = "contact_number")
	private String contactNumber;

	public DiagnosticCenter(String centerName, String centerId, List<TestCenter> listOfTests,
			List<Appointment> appointmentList, String centerAddress, String contactNumber) {
		
		this.centerName = centerName;
		this.centerId = centerId;
		this.listOfTests = listOfTests;
		this.appointmentList = appointmentList;
		this.centerAddress = centerAddress;
		this.contactNumber = contactNumber;
	}

	public DiagnosticCenter() {

	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	@JsonBackReference
	public List<TestCenter> getListOfTests() {
		return listOfTests;
	}

	public void setListOfTests(List<TestCenter> listOfTests) {
		this.listOfTests = listOfTests;
	}
	
	@JsonBackReference
	public List<Appointment> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(List<Appointment> appointmentList) {
		this.appointmentList = appointmentList;
	}

	public String getCenterAddress() {
		return centerAddress;
	}

	public void setCenterAddress(String centerAddress) {
		this.centerAddress = centerAddress;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
