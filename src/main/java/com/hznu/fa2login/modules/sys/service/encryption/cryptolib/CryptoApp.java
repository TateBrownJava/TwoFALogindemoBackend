package com.hznu.fa2login.modules.sys.service.encryption.cryptolib;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;

import static com.hznu.fa2login.modules.sys.service.encryption.cryptolib.SM4.ECB;


/**
 * Created by hb on 2015/11/20.
 */
public class CryptoApp {
    private final static byte nSalt = 8;

    /*-------------user password process------------
    this process returns a unit containing code stored in DB and the associate salt value
    1. generate a nSalt-bytes salt
    2. splice the password and salt into a single unit
    3. hash the unit with SM3 and save the first 128 bits as encrypt key
    4. encrypt 0 with the above key using SM4
     */
    public static PsdProcRlt PwdProcess(byte[] pwd) throws Exception {
        if (pwd.length == 0)
            throw new Exception("Password string empty exception!");

        if (pwd == null)
            throw new Exception("Password null exception!");

        PsdProcRlt rlt = new PsdProcRlt();
        Random rn = new Random();

        //generate a 2-bytes salt
        rn.nextBytes(rlt.getSalt());

        //splice
        byte[] temp = new byte[pwd.length + nSalt];

        System.arraycopy(pwd, 0, temp, 0, pwd.length);
        System.arraycopy(rlt.getSalt(), 0, temp, pwd.length, nSalt);

        //hash
        byte[] key = new byte[16];
        System.arraycopy(SM3.hash(temp), 0, key, 0, 16);

        SM4 s = new SM4();

        s.Init(ECB, key, null);
        s.Ecb_Encrypt(rlt.getValue());

        return rlt;
    }

    /*------translate password and salt into the code stored in DB-------*/
    public static byte[] PwdTransValue(byte[] pwd, byte[] salt) throws Exception {
        if (pwd.length == 0)
            throw new Exception("Password string empty exception!");

        if (pwd == null)
            throw new Exception("Password null exception!");

        if (salt.length != nSalt)
            throw new Exception("Salt length invalid exception!");

        //splice
        byte[] temp = new byte[pwd.length + nSalt];

        System.arraycopy(pwd, 0, temp, 0, pwd.length);
        System.arraycopy(salt, 0, temp, pwd.length, nSalt);

        //hash
        byte[] key = new byte[16];
        System.arraycopy(SM3.hash(temp), 0, key, 0, 16);

        SM4 s = new SM4();
        byte[] value = new byte[16];

        s.Init(ECB, key, null);
        s.Ecb_Encrypt(value);

        return value;
    }

    /**
     * author: WheelChen
     * date: 2017/7/17
     * function: 加密不限长度的明文数据
     *
     * @param ptext 字符串明文
     * @param key
     * @param iv
     * @return
     */
    public static byte[] SM4Enc(String ptext, byte[] key, byte[] iv) throws Exception {
        if (ptext == null || ptext.equals("")) {
            throw new Exception("加密数据为空");
        }
        //---------- 处理数据 --------

//        StringBuilder ptextBuilder = new StringBuilder(ptext);
//        int length = ptextBuilder.toString().getBytes().length;
//        if (length > 16)
//            for (int i = length % 16; i < 16; i+=2) {
//                ptextBuilder.append(0x20);
//            }
//        else {
//            for (int i = 0; i < 16 - length; i+=2) {
//                ptextBuilder.append(0x20);
//            }
//        }
//        ptext = ptextBuilder.toString();

        int length = ptext.getBytes("UTF-8").length;
        //计算需要扩展的长度
        int plushLength = 0;
        if (length % 16 != 0) {
            plushLength = 16 - length % 16;
        } else if (length < 16) {
            plushLength = 16 - length;
        }
        //生成新的byte串并将多余的空间以0x20填充
        byte[] bytesPtext = Arrays.copyOf(ptext.getBytes("UTF-8"), length + plushLength);
        for (int i = 0; i < plushLength; i++) {
            bytesPtext[i + length] = (byte) 0x20;
        }
        return SM4EncBatch(bytesPtext, key, iv);
    }

    /**
     * author: WheelChen
     * date: 2017/7/18
     * function: 加密不限长度明文
     *
     * @param ptext
     * @param key
     * @param iv
     * @return {byte[]}密文
     */
    public static byte[] SM4EncBatch(byte[] ptext, byte[] key, byte[] iv) {
        byte[] ctext = new byte[ptext.length];
        int pLength = ptext.length;
        int k = 0;
        while (k + 16 <= pLength) {
            //一个block(16个字节)待加密数据
            byte[] cellPlain = Arrays.copyOfRange(ptext, k, k + 16);
            byte[] cellCiper = CryptoApp.SM4Enc16(cellPlain, key, iv);
            System.arraycopy(cellCiper, 0, ctext, k, 16);
            k += 16;
        }
        return ctext;
    }

    /**
     * author: WheelChen
     * date: 2017/7/17
     * function: 加密16字节明文
     *
     * @param ptext 加密一个分区（16字节）的数据
     * @param key   16位key
     * @param iv    16位初始向量
     * @return {byte[]} 16位byte密文
     */
    public static byte[] SM4Enc16(byte[] ptext, byte[] key, byte[] iv) {
        SM4 s = new SM4();
        s.Init(ECB, key, iv);
        s.Encrypt(ptext);
        return ptext;
    }

    /**
     * author: WheelChen
     * date: 2017/7/18
     * function:解密不限长度密文
     *
     * @param ctext 密文
     * @param key   16位key
     * @param iv    16位初始向量
     * @return 字符串
     */
    public static String SM4Dec(byte[] ctext, byte[] key, byte[] iv) throws UnsupportedEncodingException {
        byte[] ptext;
        ptext = CryptoApp.SM4DecBatch(ctext, key, iv);
        int reaLength;
        for (reaLength = 0; reaLength < ptext.length; reaLength++) {
            if (ptext[reaLength] == (byte) 0x20) break;
        }
//        return Util.hexStr2Str(Util.bytesToHexString(Arrays.copyOf(ptext, reaLength)));
        return new String(Arrays.copyOf(ptext, reaLength), "UTF-8");
    }

    /**
     * author: WheelChen
     * date: 2017/7/18
     * function: 解密不限长度的密文
     *
     * @param ctext 密文
     * @param key   16位key
     * @param iv    16位初始向量
     * @return
     */
    public static byte[] SM4DecBatch(byte[] ctext, byte[] key, byte[] iv) {
        byte[] ptext = new byte[ctext.length];
        int cLength = ctext.length;
        int k = 0;
        while (k + 16 <= cLength) {
            byte[] cellCiper = Arrays.copyOfRange(ctext, k, k + 16);
            byte[] cellPlain = CryptoApp.SM4Dec16(cellCiper, key, iv);
            System.arraycopy(cellPlain, 0, ptext, k, 16);
            k += 16;
        }
        return ptext;
    }

    /**
     * author: WheelChen
     * date: 2017/7/17
     * function: 解密16字节
     *
     * @param ctext
     * @return
     */
    public static byte[] SM4Dec16(byte[] ctext, byte[] key, byte[] iv) {
        SM4 s = new SM4();
        s.Init(ECB, key, iv);
        s.Decrypt(ctext);
        return ctext;
    }
}

