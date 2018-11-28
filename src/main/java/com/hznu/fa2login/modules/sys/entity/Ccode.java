package com.hznu.fa2login.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: TateBrown
 * @date: 2018/11/28 19:20
 * @param:
 * @return:
 */
public class Ccode implements Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(value="user_id",type= IdType.INPUT)
    private Integer userId;

    private String cacode;

    @TableField("exptime")
    private Date exptime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getExptime() {
        return exptime;
    }

    public void setCacode(String cacode) {
        this.cacode = cacode;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCacode() {
        return cacode;
    }

    public void setExptime(Date exptime) {
        this.exptime = exptime;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
