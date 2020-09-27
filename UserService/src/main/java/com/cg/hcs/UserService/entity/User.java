package com.cg.hcs.UserService.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cg.hcs.UserService.dto.UserDto;



@Entity
@Table(name = "users")
public class User {
	@Column(name = "user_name")
	@Id
	private String userName;
	@Column(name = "name")
	private String name;
	@Column(name = "phone_no")
	private String phoneNo;
	@Column(name = "role")
	private String role;
	@Column(name = "email")
	private String email;
	@Column(name="security_question")
	private String securityQuestion;
	@Column(name="security_answer")
	private String securityAnswer;

	public User() {

	}
	public User(UserDto user) {
		this.email=user.getEmail();
		this.name=user.getName();
		this.phoneNo=user.getPhoneNo();
		this.role=user.getRole();
		this.securityAnswer=user.getSecurityAnswer();
		this.securityQuestion=user.getSecurityQuestion();
		this.userName=user.getUserName();

	}

	public User(String userName, String name, String phoneNo, String role, String email, String securityQuestion,
			String securityAnswer) {
		super();
		this.userName = userName;
		this.name = name;
		this.phoneNo = phoneNo;
		this.role = role;
		this.email = email;
		this.securityQuestion = securityQuestion;
		this.securityAnswer = securityAnswer;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecurityQuestion() {
		return securityQuestion;
	}

	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
}
