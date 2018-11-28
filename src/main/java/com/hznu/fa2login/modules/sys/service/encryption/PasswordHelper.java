package com.hznu.fa2login.modules.sys.service.encryption;

import com.hznu.fa2login.common.utils.Utils;
import com.hznu.fa2login.modules.sys.entity.User;
import com.hznu.fa2login.modules.sys.service.encryption.cryptolib.CryptoApp;
import com.hznu.fa2login.modules.sys.service.encryption.cryptolib.PsdProcRlt;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by TAO on 2015/10/3.
 *
 * UserServiceImpl中createUser会用到
 *
 * modified
 * @author wheelchen
 * @date 2018-04-07
 */
@Service
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${config.password.algorithmName}")
    private String algorithmName;
    @Value("${config.password.hashIterations}")
    private int hashIterations;

    public RandomNumberGenerator getRandomNumberGenerator() {
        return randomNumberGenerator;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public void encryptPassword(User user) {
        try {
            PsdProcRlt r;
            //对原始密码进行加密处理,处理后得到salt及密文
            r = CryptoApp.PwdProcess(user.getPassword().getBytes());
            //设置salt值
            user.setSalt(Utils.bytesToHexString(r.getSalt()));
            //设置new password
            user.setPassword(Utils.bytesToHexString(r.getValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        user.setSalt(randomNumberGenerator.nextBytes().toHex());
//
//        String newPassword = new SimpleHash(
//                algorithmName,
//                user.getPassword(),
//                ByteSource.Util.bytes(user.getCredentialsSalt()),
//                hashIterations).toHex();
//
//        user.setPassword(newPassword);

    }
}