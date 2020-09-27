package com.cg.healthcaresystem.centers.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "Diagnostic_centers")
public class DiagnosticCenter {

	@Column(name = "center_name")
	private String centerName;
	@Id
	@Column(name = "center_id")
	private String centerId;
	@Column(name = "center_address")
	private String centerAddress;
	@Column(name = "contact_number")
	private String contactNumber;

	public DiagnosticCenter(String centerName, String centerId, String centerAddress, String contactNumber) {
		super();
		this.centerName = centerName;
		this.centerId = centerId;
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

	@Override
	public String toString() {
		return "DiagnosticCenter [centerName=" + centerName + ", centerId=" + centerId + ", centerAddress="
				+ centerAddress + ", contactNumber=" + contactNumber + "]";
	}

}
