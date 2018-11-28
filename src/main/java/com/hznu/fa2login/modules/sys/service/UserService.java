package com.hznu.fa2login.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.hznu.fa2login.modules.sys.entity.User;

/**
 * @Author: TateBrown
 * @date: 2018/11/27 20:41
 * @param:
 * @return:
 */
public interface UserService extends IService<User> {
    Boolean validatePassword(User user, String password);
}
