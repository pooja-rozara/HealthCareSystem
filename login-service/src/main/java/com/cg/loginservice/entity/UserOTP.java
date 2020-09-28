package com.cg.loginservice.entity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user2fa")
public class UserOTP {
    @Id
    private String userNameCommaRole;
    private String secretKey;
    private int validationCode;
    private String scratchCodes;

    public String getUserNameCommaRole() {
        return userNameCommaRole;
    }
    public void setUserNameCommaRole(String userNameCommaRole) {
        this.userNameCommaRole = userNameCommaRole;
    }
    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    public int getValidationCode() {
        return validationCode;
    }
    public void setValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }
    public List<Integer> getScratchCodes() {
        return Arrays.asList(scratchCodes.split(",")).stream().map(a -> Integer.parseInt(a)).collect(Collectors.toList());
    }
    public void setScratchCodes(List<Integer> scratchCodes) {
        this.scratchCodes = scratchCodes.stream().map(a->a+"").reduce((a,b) -> a+","+b).get();
    }
    
    public UserOTP() {
        super();
    }

    public UserOTP(String userNameCommaRole, String secretKey, int validationCode, List<Integer> scratchCodes) {
        this.userNameCommaRole = userNameCommaRole;
        this.secretKey = secretKey;
        this.validationCode = validationCode;
        this.scratchCodes = scratchCodes.stream().map(a->a+"").reduce((a,b) -> a+","+b).get();
    }

}
