package com.hznu.fa2login.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hznu.fa2login.common.utils.GoogleAuthenticator;
import com.hznu.fa2login.common.validator.Assert;
import com.hznu.fa2login.modules.sys.beans.LoginForm;
import com.hznu.fa2login.modules.sys.entity.User;
import com.hznu.fa2login.modules.sys.service.UserService;
import com.hznu.fa2login.myBeans.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: TateBrown
 * @date: 2018/11/27 23:10
 * @param:
 * @return:
 */
@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody LoginForm loginForm){
        if(loginForm.getUsername()==null){
            return R.error("用户名不能为空");
        }
        if(loginForm.getPassword()==null){
            return R.error("密码不能为空");
        }
        if(loginForm.getCode()==null){
            return R.error("口令不能为空");
        }
         User user=userService.selectOne(new EntityWrapper<User>().eq("username",loginForm.getUsername()));
        if (user == null) {
            return R.error("账号或密码不正确");
        }
        Assert.isNull(user.getUsername(), "用户名不能为空");

        Assert.isNull(user.getPassword(), "密码不能为空");

        Assert.isNull(user.getKey(),"key不能为空");

        //密码错误
        if (!userService.validatePassword(user, loginForm.getPassword())) {
            return R.error("账号或密码不正确");
        }
        if(GoogleAuthenticator.authcode(loginForm.getCode(),user.getKey())){
            return R.ok("登录成功");
        }else{
            return R.error("谷歌认证验证码错误");
        }
    }
}
