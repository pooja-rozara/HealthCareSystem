package com.cg.loginservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCodeDTO {
    private Integer code;
    private String userNameCommaRole;
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getUserNameCommaRole() {
        return userNameCommaRole;
    }
    public void setUserNameCommaRole(String username) {
        this.userNameCommaRole = username;
    }
    public ValidateCodeDTO(){
        super();
    }
    public ValidateCodeDTO(Integer code, String username){
        this.code = code;
        this.userNameCommaRole = username;
    }
}