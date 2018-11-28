package com.hznu.fa2login.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hznu.fa2login.common.exception.OAException;
import com.hznu.fa2login.common.utils.Utils;
import com.hznu.fa2login.modules.sys.dao.UserDao;
import com.hznu.fa2login.modules.sys.entity.User;
import com.hznu.fa2login.modules.sys.service.UserService;
import com.hznu.fa2login.modules.sys.service.encryption.cryptolib.CryptoApp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: TateBrown
 * @date: 2018/11/27 20:43
 * @param:
 * @return:
 */
@Transactional(rollbackFor = {Exception.class})
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

    /**
     * @Description:验证密码是否正确
     * @param:
     * @return:
     * @author: TateBrown
     * @Date: 2018/11/27
     */
    @Override
    public Boolean validatePassword(User user, String password) {
        String oldPassword = null;
        try {
            oldPassword = Utils.bytesToHexString(CryptoApp.PwdTransValue(password.getBytes(), Utils.hexStringToBytes(user.getSalt())));
        } catch (Exception e) {
            e.printStackTrace();
            /**
             * 加密密码发生异常时也认为密码不正确
             */
            throw new OAException("用户名/密码不正确");
        }
        return oldPassword.equals(user.getPassword());

    }
}
