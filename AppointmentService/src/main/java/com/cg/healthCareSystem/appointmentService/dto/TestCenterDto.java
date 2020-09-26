package com.cg.healthCareSystem.appointmentService.dto;

public class TestCenterDto {
	private String testId;
	private String testName;
	private DiagnosticCenterDto diagnosticCenter;

	public TestCenterDto() {
	
	}

	public TestCenterDto(String testId, String testName) {
		
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

	public DiagnosticCenterDto getDiagnosticCenter() {
		return diagnosticCenter;
	}

	public void setDiagnosticCenter(DiagnosticCenterDto diagnosticCenter) {
		this.diagnosticCenter = diagnosticCenter;
	}
	public boolean equals(Object obj)   
	{  
	if (obj == null)   
	return false;  
	if (obj == this)  
	return true;  
	return this.getTestId() == ((TestCenterDto) obj).getTestId();  
	}  

}
