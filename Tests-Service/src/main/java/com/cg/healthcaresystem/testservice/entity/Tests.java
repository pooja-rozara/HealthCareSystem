package com.cg.healthcaresystem.testservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "test")
public class Tests {
	@Id
	@Column(name = "test_id")
	private String testId;
	@Column(name = "test_name")
	private String testName;
	@Column(name = "diagnostic_center_id")
	private String diagnosticCenter;

	public Tests() {
		super();

	}

	public Tests(String testId, String testName) {
		super();
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

	public String getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(String diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}

}
