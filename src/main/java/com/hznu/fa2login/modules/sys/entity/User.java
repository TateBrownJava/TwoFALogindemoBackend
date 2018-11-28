package com.hznu.fa2login.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hznu.fa2login.common.validator.group.AddGroup;
import com.hznu.fa2login.common.validator.group.UpdateGroup;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author: TateBrown
 * @date: 2018/11/27 20:07
 * @param:
 * @return:
 */
@TableName("user")
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @Length(message = "用户名长度应为4~15位",min = 4,max = 15,groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Length(message = "密码长度应为6~18位",min = 6,max = 18,groups = {AddGroup.class, UpdateGroup.class})
    private String password;

    /**
     * 盐值
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;

    /**
     * 手机号码
     */
    @Pattern(message = "手机号格式有误",regexp = "^1[3|4|5|8][0-9]\\d{4,8}$",groups = {AddGroup.class,UpdateGroup.class})
    private String mobilephone;

    private String email;

    private String key;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
