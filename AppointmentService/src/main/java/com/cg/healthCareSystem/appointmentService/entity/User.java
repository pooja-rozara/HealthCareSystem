package com.cg.healthCareSystem.appointmentService.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class User {
	@Column(name = "user_id")
	@Id
	private String userId;
	@Column(name = "user_password")
	private String userPassword;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "contact_number")
	private BigInteger contactNumber;
	@Column(name = "user_role")
	private String userRole;
	@Column(name = "email_id")
	private String emailId;
	
	public User() {
		
	}
	public User(String userId, String userPassword, String userName,
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
