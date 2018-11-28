package com.hznu.fa2login.modules.sys.beans;

/**
 * @Author: TateBrown
 * @date: 2018/11/28 20:57
 * @param:
 * @return:
 */
public class ForgetForm {
   private String username;
    private String password;
    private String newpassword;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
