package com.cg.healthcaresystem.testservice.DTO;

import java.math.BigInteger;
import java.util.List;

public class DiagnosticCenterDTO {

	private String centerName;

	private String centerId;

	private List<String> listofTests;

	private List<BigInteger> appointmentList;

	private String centerAddress;

	private String contactNumber;

	public DiagnosticCenterDTO(String centerName, String centerId, List<String> listofTests,
			List<BigInteger> appointmentList, String centerAddress, String contactNumber) {
		super();
		this.centerName = centerName;
		this.centerId = centerId;
		this.listofTests = listofTests;
		this.appointmentList = appointmentList;
		this.centerAddress = centerAddress;
		this.contactNumber = contactNumber;
	}

	public DiagnosticCenterDTO() {

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

	public List<String> getListofTests() {
		return listofTests;
	}

	public void setListofTests(List<String> listofTests) {
		this.listofTests = listofTests;
	}

	public List<BigInteger> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(List<BigInteger> appointmentList) {
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
