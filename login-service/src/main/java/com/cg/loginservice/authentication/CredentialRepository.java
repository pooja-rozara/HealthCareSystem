package com.cg.loginservice.authentication;

import java.util.List;
import java.util.Optional;

import com.cg.loginservice.dao.LoginDAO;
import com.cg.loginservice.dao.UserOTPDAO;
import com.cg.loginservice.entity.LoginCredentials;
import com.cg.loginservice.dto.UserOTPDTO;
import com.cg.loginservice.entity.UserOTP;
import com.cg.loginservice.exceptions.CustomException;
import com.cg.loginservice.id.LoginCredentialsId;
import com.warrenstrange.googleauth.ICredentialRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CredentialRepository implements ICredentialRepository {

    Logger log=LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    UserOTPDAO userOTPDAO;

    @Autowired
    LoginDAO loginDAO;

    @Override
    public String getSecretKey(String userNameCommaRole) {
        String[] userDetails = userNameCommaRole.split(",");
        if(userDetails[0].length()==0) {
            log.error("Username Empty!", CustomException.class);
            throw new CustomException("Username cannot be empty!");
        }
        if(userDetails[1].length()==0) {
            log.error("Role Empty!", CustomException.class);
            throw new CustomException("Role cannot be empty!");
        }
        Optional<LoginCredentials> user = loginDAO.findById(new LoginCredentialsId(userDetails[0], userDetails[1]));
        if(user.isEmpty()) {
            log.error("User not Found!", CustomException.class);
            throw new CustomException("User is not present in the Repository. Cannot verify 2FA code. Please Register.");
        }
        if(!user.get().getAuthConsent()) throw new CustomException("User did not opt for 2FA. Connot verify 2FA code.");
        
        Optional<UserOTP> temp = userOTPDAO.findById(userNameCommaRole);
        if(temp.isEmpty()) throw new CustomException("User has not generated 2FA authentication.");
        return temp.get().getSecretKey();
    }

    @Override
    public void saveUserCredentials(String userNameCommaRole,
                                    String secretKey,
                                    int validationCode,
                                    List<Integer> scratchCodes) {
        String[] userDetails = userNameCommaRole.split(",");
        if(userDetails[0].length()==0) {
            log.error("Username Empty!", CustomException.class);
            throw new CustomException("Username cannot be empty!");
        }
        if(userDetails[1].length()==0) {
            log.error("Role Empty!", CustomException.class);
            throw new CustomException("Role cannot be empty!");
        }
        Optional<LoginCredentials> user = loginDAO.findById(new LoginCredentialsId(userDetails[0], userDetails[1]));
        if(user.isEmpty()) {
            log.error("User Not found", CustomException.class);
            throw new CustomException("User is not present in the Repository. Cannot generate 2FA QR Code. Please Register.");
        }
        if(!user.get().getAuthConsent()) {
            log.error("User has no 2FA!", CustomException.class);
            throw new CustomException("User did not opt for 2FA. Connot generate QR Code.");
        }
        Optional<UserOTP> temp = userOTPDAO.findById(userNameCommaRole);
        if(temp.isPresent()) {
            log.error("User has 2FA!", CustomException.class);
            throw new CustomException("User has already created 2FA authentication.");
        }
        log.info("User 2FA Created: "+userDetails[0]+" - "+userDetails[1]);
        userOTPDAO.save(new UserOTP(userNameCommaRole, secretKey, validationCode, scratchCodes));
    }

    public UserOTPDTO getUser(String userNameCommaRole) {
        String[] userDetails = userNameCommaRole.split(",");
        if(userDetails[0].length()==0) {
            log.error("Username Empty!", CustomException.class);
            throw new CustomException("Username cannot be empty!");
        }
        if(userDetails[1].length()==0) {
            log.error("Role Empty!", CustomException.class);
            throw new CustomException("Role cannot be empty!");
        }
        Optional<LoginCredentials> user = loginDAO.findById(new LoginCredentialsId(userDetails[0], userDetails[1]));
        if(user.isEmpty()) {
            log.error("User Not found", CustomException.class);
            throw new CustomException("User is not present in the Repository. Cannot generate 2FA QR Code. Please Register.");
        }
        if(!user.get().getAuthConsent()) {
            log.error("User has no 2FA!", CustomException.class);
            throw new CustomException("User did not opt for 2FA. Connot generate QR Code.");
        }
        
        Optional<UserOTP> temp = userOTPDAO.findById(userNameCommaRole);
        if(temp.isEmpty()) {
            log.error("User not generated 2FA!", CustomException.class);
            throw new CustomException("User not generated 2FA!");
        }
        UserOTP userAuth = temp.get();
        log.info("User Returned: "+userDetails[0]+" - "+userDetails[1]);
        return new UserOTPDTO(userNameCommaRole, userAuth.getSecretKey(), userAuth.getValidationCode(), userAuth.getScratchCodes());
    }

    public boolean removeUser(String userNameCommaRole) {
        String[] userDetails = userNameCommaRole.split(",");
        if(userDetails[0].length()==0) {
            log.error("Username Empty!", CustomException.class);
            throw new CustomException("Username cannot be empty!");
        }
        if(userDetails[1].length()==0) {
            log.error("Role Empty!", CustomException.class);
            throw new CustomException("Role cannot be empty!");
        }
        Optional<UserOTP> user = userOTPDAO.findById(userNameCommaRole);
        if(user.isEmpty()) {
            log.error("User("+userDetails[0]+"-"+userDetails[1]+") 2FA not found. Cannot be deleted.", CustomException.class);
            return false;
        }
        userOTPDAO.deleteById(userNameCommaRole);
        log.info("User("+userDetails[0]+"-"+userDetails[1]+") 2FA deleted.");
        return true;
    }
    
}
