package com.hznu.fa2login.modules.sys.service.encryption.cryptolib;

import com.hznu.fa2login.common.annotation.SysLog;

/**
 * Created by hb on 2015/11/20.
 */
public class PsdProcRlt
{
    private final byte nSalt = 8;
    private byte[] value = new byte[16];
    private byte[] salt = new byte[nSalt];

    public byte[] getValue()
    {
        return value;
    }

    public void setValue(byte[] newValue)
    {
        value = newValue;
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public void setSalt(byte[] newSalt)
    {
        salt = newSalt;
    }
}