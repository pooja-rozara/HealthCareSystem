package com.cg.loginservice.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import com.cg.loginservice.dao.LoginDAO;
import com.cg.loginservice.entity.LoginCredentials;
import com.cg.loginservice.dto.LoginCredentialsDTO;
import com.cg.loginservice.dto.LoginDTO;
import com.cg.loginservice.exceptions.CustomException;
import com.cg.loginservice.id.LoginCredentialsId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

	Logger log=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LoginDAO loginDao;

	@Override
	public LoginDTO AuthenticateUser(String userName, String pass, String role) {
		if(userName.length()==0){
			log.error("Username Empty!", CustomException.class);
			throw new CustomException("Username cannot be Empty!");	
		}
		if(pass.length()==0){
			log.error("Password Empty!", CustomException.class);
			throw new CustomException("Password cannot be Empty!");
		}
		if(pass.length()<6 || pass.charAt(0)>'Z' || pass.charAt(0)<'A'){
			log.error("Password Not Following Policy!", CustomException.class);
			throw new CustomException("Password not following Policy. Greater than 6 characters, Starting with Capital Letter.");
		}
		if(role.length()==0) {
			log.error("Role Empty!", CustomException.class);
			throw new CustomException("Role cannot be empty!");
		}
		LoginCredentials user=loginDao.validateUser(userName, pass, role);
		if(user!=null){
			log.info("User Validated! - "+user.toString());
			LoginDTO result = new LoginDTO(user);
			user.setLastLoginDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			return result;
		}
		else{
			log.error("User Validation Failed!", CustomException.class);
			throw new CustomException("Credentials Invalid!");
		}
			
	}

	@Override
	public LoginDTO getUser(String userName, String role) {
		if(userName.length()==0) {
			log.error("Username Empty!", CustomException.class);
			throw new CustomException("Username cannot be Empty!");
		}
		if(role.length()==0) {
			log.error("Role Empty!", CustomException.class);
			throw new CustomException("Role cannot be empty!");
		}
		Optional<LoginCredentials> temp = loginDao.findById(new LoginCredentialsId(userName, role));
		if(temp.isEmpty()) throw new CustomException("User not found!");
		log.info("User Details returned for "+ userName+"!");
		return new LoginDTO(temp.get());
	}

	@Override
	public LoginDTO changeAuthStatus(String userName, String role) {
		if(userName.length()==0) {
			log.error("Username Empty!", CustomException.class);
			throw new CustomException("Username cannot be Empty!");
		}
		if(role.length()==0) {
			log.error("Role Empty!", CustomException.class);
			throw new CustomException("Role cannot be empty!");
		}
		Optional<LoginCredentials> cred = loginDao.findById(new LoginCredentialsId(userName, role));
		if(cred.isEmpty()) throw new CustomException("User Not Found!");
		LoginCredentials temp = cred.get();
		temp.setAuthConsent(!temp.getAuthConsent());
		log.info("AuthConsent Changed for "+ userName+"!");
		return new LoginDTO(loginDao.save(temp));
	}

	@Override
	public boolean changePassword(String userName, String role, String newPassword) {
		if(userName.length()==0) {
			log.error("Username Empty!", CustomException.class);
			throw new CustomException("Username cannot be Empty!");
		}
		if(role.length()==0) {
			log.error("Role Empty!", CustomException.class);
			throw new CustomException("Role cannot be empty!");
		}
		if(newPassword.length()==0){
			log.error("New Password Empty!", CustomException.class);
			throw new CustomException("New Password cannot be Empty!");
		}
		if(newPassword.length()<6 || newPassword.charAt(0)>'Z' || newPassword.charAt(0)<'A'){
			log.error("New Password Not Following Policy!", CustomException.class);
			throw new CustomException("New Password not following Policy. Greater than 6 characters, Starting with Capital Letter.");
		}
		Optional<LoginCredentials> cred = loginDao.findById(new LoginCredentialsId(userName, role));
		if(cred.isEmpty()) throw new CustomException("User Not Found!");
		LoginCredentials temp = cred.get();
		temp.setPassword(newPassword);
		log.info("Password Changed for "+ userName+"!");
		loginDao.save(temp);
		return true;
	}

	@Override
	public LoginDTO addCredentials(LoginCredentialsDTO credentials) {
		if(credentials.getUserName().length()==0) {
			log.error("Username Empty!", CustomException.class);
			throw new CustomException("Username cannot be Empty!");
		}
		if(credentials.getPassword().length()==0) {
			log.error("Password Empty!", CustomException.class);
			throw new CustomException("Password cannot be Empty!");
		}
		if(credentials.getPassword().length()<6 || credentials.getPassword().charAt(0)>'Z' || credentials.getPassword().charAt(0)<'A') {
			log.error("New Password Not Following Policy!", CustomException.class);
			throw new CustomException("Password not following Policy. Greater than 6 characters, Starting with Capital Letter.");
		}
		if(credentials.getRole().length()==0) {
			log.error("Role Empty!", CustomException.class);
			throw new CustomException("Role cannot be empty!");
		}
		if(credentials.getAuthConsent()!=true && credentials.getAuthConsent()!=false) {
			log.error("AuthConsent Empty!", CustomException.class); 
			throw new CustomException("AuthConsent cannot be Empty");
		}
		if(!loginDao.existsById(new LoginCredentialsId(credentials.getUserName(), credentials.getRole()))) {	
			log.info("Adding credentials: "+credentials.getUserName()+" - " +credentials.getRole());
			credentials.setLastLoginDate(Date.from(LocalDate.of(2000,1,1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			return new LoginDTO(loginDao.save(new LoginCredentials(credentials)));
		}else {
			log.error("User Exists!", CustomException.class);
			throw new CustomException("User Already Exists!");
		}
	}

	@Override
	public boolean deleteCredentials(String userName, String role) {
		if(userName.length()==0) {
			log.error("Username Empty!", CustomException.class);
			throw new CustomException("Username cannot be Empty!");
		}
		if(role.length()==0) {
			log.error("Role Empty!", CustomException.class);
			throw new CustomException("Role cannot be empty!");
		}
		if(loginDao.existsById(new LoginCredentialsId(userName, role))) {	
			log.info("Adding credentials"+userName);
			loginDao.deleteById(new LoginCredentialsId(userName, role));
			return true;
		}
		else {
			log.error("User not Found!", CustomException.class);
			return false;
		}

	}

}
