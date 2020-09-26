package com.cg.healthCareSystem.appointmentService.dto;

import java.util.List;


public class DiagnosticCenterDto {
	private String centerName;
	private String centerId;
	private List<TestCenterDto> listOfTests;
	private List<AppointmentDto> appointmentList;
	private String centerAddress;
	private String contactNumber;

	public DiagnosticCenterDto(String centerName, String centerId, List<TestCenterDto> listOfTests,
			List<AppointmentDto> appointmentList, String centerAddress, String contactNumber) {
		
		this.centerName = centerName;
		this.centerId = centerId;
		this.listOfTests = listOfTests;
		this.appointmentList = appointmentList;
		this.centerAddress = centerAddress;
		this.contactNumber = contactNumber;
	}

	public DiagnosticCenterDto() {

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

	public List<TestCenterDto> getListOfTests() {
		return listOfTests;
	}

	public void setListOfTests(List<TestCenterDto> listOfTests) {
		this.listOfTests = listOfTests;
	}
	
	public List<AppointmentDto> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(List<AppointmentDto> appointmentList) {
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
