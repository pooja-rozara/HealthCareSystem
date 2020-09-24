package com.cg.healthCareSystem.appointmentService.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "test")
public class TestCenter {
	@Id
	@Column(name = "test_id")
	private String testId;
	@Column(name = "test_name")
	private String testName;
	@ManyToOne
	@JoinColumn(name = "center_fk")
	private DiagnosticCenter diagnosticCenter;

	public TestCenter() {
	
	}

	public TestCenter(String testId, String testName) {
		
		this.testId = testId;
		this.testName = testName;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	@JsonBackReference
	public DiagnosticCenter getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(DiagnosticCenter diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}
	@Override  
	public boolean equals(Object obj)   
	{  
	if (obj == null)   
	return false;  
	if (obj == this)  
	return true;  
	return this.getTestId() == ((TestCenter) obj).getTestId();  
	}  

}
