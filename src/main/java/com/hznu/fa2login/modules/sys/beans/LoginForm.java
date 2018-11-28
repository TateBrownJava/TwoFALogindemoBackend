package com.hznu.fa2login.modules.sys.beans;

/**
 * @Author: TateBrown
 * @date: 2018/11/27 23:42
 * @param:
 * @return:
 */
public class LoginForm {
private String username;

private String password;

private String code;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
