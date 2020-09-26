package com.cg.hcs.UserService.dto;

import java.math.BigInteger;



public class UserDto {
	private String userId;
	private String userPassword;
	private String userName;
	private BigInteger contactNumber;
	private String userRole;
	private String emailId;
	
	public UserDto() {
		
	}
	public UserDto(String userId, String userPassword, String userName,
			BigInteger contactNumber, String userRole, String emailId) {
		super();
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.contactNumber = contactNumber;
		this.userRole = userRole;
		this.emailId = emailId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigInteger getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(BigInteger contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}
