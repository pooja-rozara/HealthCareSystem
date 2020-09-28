package com.cg.healthcaresystem.testservice.DTO;

import javax.validation.constraints.NotNull;

public class TestsDTO {

	private String testId;
	private String testName;
	@NotNull
	private String diagnosticCenter;

	public TestsDTO() {
		super();

	}

	public TestsDTO(String testId, String testName) {
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

	@Override
	public String toString() {
		return "TestsDTO [testId=" + testId + ", testName=" + testName + ", diagnosticCenter=" + diagnosticCenter + "]";
	}

}
