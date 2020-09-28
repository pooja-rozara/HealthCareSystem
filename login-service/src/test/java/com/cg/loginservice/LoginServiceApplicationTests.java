package com.cg.loginservice;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.cg.loginservice.authentication.CredentialRepository;
import com.cg.loginservice.dao.LoginDAO;
import com.cg.loginservice.dao.UserOTPDAO;
import com.cg.loginservice.entity.LoginCredentials;
import com.cg.loginservice.dto.LoginCredentialsDTO;
import com.cg.loginservice.dto.LoginDTO;
import com.cg.loginservice.exceptions.CustomException;
import com.cg.loginservice.service.LoginService;
import com.cg.loginservice.service.LoginServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
class LoginServiceApplicationTests {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	LoginDAO loginDAO;

	@Autowired
	UserOTPDAO userTOTPDAO;

	@Autowired
	LoginService loginService;

	@Autowired
	CredentialRepository credentialRepository;
	
	@TestConfiguration
	static class EmployeeServiceImplTestContextConfiguration {

		@Bean
		public LoginService loginService() {
			return new LoginServiceImpl();
		}

		@Bean
		public CredentialRepository credentialRepository() {
			return new CredentialRepository();
		}
	}

	@Test
	public void whenAuthenticateByUsernameAndPasswordAndRole_thenReturnLoginDTO() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		temp.setLastLoginDate(Date.from(LocalDate.of(2000,1,1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		entityManager.persist(temp);
		entityManager.flush();
		LoginDTO found = loginService.AuthenticateUser("user", "Pass123", "admin");
		assertEquals(new LoginDTO(temp), found);
	}

	@Test
	public void whenAuthenticateByWrongUsernameAndPasswordAndRole_thenThrowException() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();
		assertTrue(assertThrows(
			CustomException.class,()
			 -> loginService.AuthenticateUser("user1", "Pass123", "admin")).getMessage().equals("Credentials Invalid!"));
	}
	
	@Test
	public void whenAuthenticateByBlankUsernameAndPasswordAndRole_thenThrowException() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();
		assertTrue(assertThrows(
			CustomException.class,()
			 -> loginService.AuthenticateUser("", "Pass123", "admin")).getMessage().equals("Username cannot be Empty!"));
	}

	@Test
	public void whenAuthenticateByUsernameAndEmptyPasswordAndRole_thenThrowException() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();
		assertTrue(assertThrows(
			CustomException.class,()
			 -> loginService.AuthenticateUser("user1", "", "admin")).getMessage().equals("Password cannot be Empty!"));
	}

	@Test
	public void whenAuthenticateByUsernameAndIncorrectPasswordAndRole_thenThrowException() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();
		assertTrue(assertThrows(
			CustomException.class,()
			 -> loginService.AuthenticateUser("user1", "Pass", "admin")).getMessage().equals
			 ("Password not following Policy. Greater than 6 characters, Starting with Capital Letter."));
	}

	@Test
	public void whenAuthenticateByUsernameAndPasswordAndEmptyRole_thenThrowException() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();
		assertTrue(assertThrows(
			CustomException.class,()
			 -> loginService.AuthenticateUser("user1", "Pass123", "")).getMessage().equals("Role cannot be empty!"));
	}

	@Test
	public void whenGetUserByUsernameAndRole_thenReturnLoginCredentials() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();
		LoginDTO found = loginService.getUser("user", "admin");
		assertEquals(new LoginDTO(temp), found);
	}

	@Test
	public void whenGetByWrongUsernameAndRole_thenThrowException() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();

		assertTrue(
			assertThrows(
				CustomException.class, ()
				 -> loginService.getUser("user1", "admin")).getMessage().equals("User not found!"));
	}

	@Test
	public void whenChangeAuthStatusByUsernameAndRole_thenReturnChangedLoginCredentials() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();

		assertFalse(loginService.changeAuthStatus("user", "admin").getAuthConsent());
	}

	@Test
	public void whenChangeAuthStatusByWrongUsernameAndRole_thenThrowException() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();

		assertTrue(
			assertThrows(
				CustomException.class, ()
				 -> loginService.changeAuthStatus("user1", "admin").getAuthConsent()).getMessage().equals("User Not Found!"));
	}

	@Test
	public void whenChangePasswordByUsernameAndRoleAndNewPassword_thenReturnTrue() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();

		assertTrue(loginService.changePassword("user", "admin", "Newpass"));
	}

	@Test
	public void whenChangePasswordByWrongUsernameAndRoleAndNewPassword_thenThrowException() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();

		assertTrue(
			assertThrows(
				CustomException.class, ()
				 -> loginService.changePassword("user1", "admin", "Newpass")).getMessage().equals("User Not Found!"));
	}

	@Test
	public void whenAddCredentialsByLoginCredentialsDTO_thenReturnLoginDTO() {
		LoginDTO newUser = loginService.addCredentials(new LoginCredentialsDTO("user", "Password", "admin", true));
		assertTrue(newUser.getUserName().equals("user") && newUser.getRole().equals("admin") && newUser.getAuthConsent() && 
		newUser.getLastLoginDate().compareTo(Date.from(LocalDate.of(2000,1,1).atStartOfDay(ZoneId.systemDefault()).toInstant()))==0);
	}

	@Test
	public void whenAddCredentialsByRepeatedLoginCredentialsDTO_thenThrowException() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();
		
		assertTrue(
			assertThrows(
				CustomException.class, ()
				 -> loginService.addCredentials(new LoginCredentialsDTO("user", "Password", "admin", true))).getMessage().equals("User Already Exists!"));
	}

	@Test
	public void whenDeleteCredentialsByUserNameAndRole_thenReturnTrue() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();

		assertTrue(loginService.deleteCredentials("user", "admin"));
	}

	@Test
	public void whenDeleteCredentialsByUserNameAndRoleNotInDatabase_thenReturnFalse() {
		LoginCredentials temp = new LoginCredentials("user", "Pass123", "admin", true);
		entityManager.persist(temp);
		entityManager.flush();

		assertFalse(loginService.deleteCredentials("user1", "admin"));
	}
}
