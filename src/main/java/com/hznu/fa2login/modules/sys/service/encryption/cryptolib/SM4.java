package com.hznu.fa2login.modules.sys.service.encryption.cryptolib;

/**
 * Created by hb on 2015/11/20.
 */
public class SM4
{
    private static final byte[] SBOX =
            {
                    (byte)0xd6, (byte)0x90, (byte)0xe9, (byte)0xfe, (byte)0xcc, (byte)0xe1, (byte)0x3d, (byte)0xb7,
                    (byte)0x16, (byte)0xb6, (byte)0x14, (byte)0xc2, (byte)0x28, (byte)0xfb, (byte)0x2c, (byte)0x05,
                    (byte)0x2b, (byte)0x67, (byte)0x9a, (byte)0x76, (byte)0x2a, (byte)0xbe, (byte)0x04, (byte)0xc3,
                    (byte)0xaa, (byte)0x44, (byte)0x13, (byte)0x26, (byte)0x49, (byte)0x86, (byte)0x06, (byte)0x99,
                    (byte)0x9c, (byte)0x42, (byte)0x50, (byte)0xf4, (byte)0x91, (byte)0xef, (byte)0x98, (byte)0x7a,
                    (byte)0x33, (byte)0x54, (byte)0x0b, (byte)0x43, (byte)0xed, (byte)0xcf, (byte)0xac, (byte)0x62,
                    (byte)0xe4, (byte)0xb3, (byte)0x1c, (byte)0xa9, (byte)0xc9, (byte)0x08, (byte)0xe8, (byte)0x95,
                    (byte)0x80, (byte)0xdf, (byte)0x94, (byte)0xfa, (byte)0x75, (byte)0x8f, (byte)0x3f, (byte)0xa6,
                    (byte)0x47, (byte)0x07, (byte)0xa7, (byte)0xfc, (byte)0xf3, (byte)0x73, (byte)0x17, (byte)0xba,
                    (byte)0x83, (byte)0x59, (byte)0x3c, (byte)0x19, (byte)0xe6, (byte)0x85, (byte)0x4f, (byte)0xa8,
                    (byte)0x68, (byte)0x6b, (byte)0x81, (byte)0xb2, (byte)0x71, (byte)0x64, (byte)0xda, (byte)0x8b,
                    (byte)0xf8, (byte)0xeb, (byte)0x0f, (byte)0x4b, (byte)0x70, (byte)0x56, (byte)0x9d, (byte)0x35,
                    (byte)0x1e, (byte)0x24, (byte)0x0e, (byte)0x5e, (byte)0x63, (byte)0x58, (byte)0xd1, (byte)0xa2,
                    (byte)0x25, (byte)0x22, (byte)0x7c, (byte)0x3b, (byte)0x01, (byte)0x21, (byte)0x78, (byte)0x87,
                    (byte)0xd4, (byte)0x00, (byte)0x46, (byte)0x57, (byte)0x9f, (byte)0xd3, (byte)0x27, (byte)0x52,
                    (byte)0x4c, (byte)0x36, (byte)0x02, (byte)0xe7, (byte)0xa0, (byte)0xc4, (byte)0xc8, (byte)0x9e,
                    (byte)0xea, (byte)0xbf, (byte)0x8a, (byte)0xd2, (byte)0x40, (byte)0xc7, (byte)0x38, (byte)0xb5,
                    (byte)0xa3, (byte)0xf7, (byte)0xf2, (byte)0xce, (byte)0xf9, (byte)0x61, (byte)0x15, (byte)0xa1,
                    (byte)0xe0, (byte)0xae, (byte)0x5d, (byte)0xa4, (byte)0x9b, (byte)0x34, (byte)0x1a, (byte)0x55,
                    (byte)0xad, (byte)0x93, (byte)0x32, (byte)0x30, (byte)0xf5, (byte)0x8c, (byte)0xb1, (byte)0xe3,
                    (byte)0x1d, (byte)0xf6, (byte)0xe2, (byte)0x2e, (byte)0x82, (byte)0x66, (byte)0xca, (byte)0x60,
                    (byte)0xc0, (byte)0x29, (byte)0x23, (byte)0xab, (byte)0x0d, (byte)0x53, (byte)0x4e, (byte)0x6f,
                    (byte)0xd5, (byte)0xdb, (byte)0x37, (byte)0x45, (byte)0xde, (byte)0xfd, (byte)0x8e, (byte)0x2f,
                    (byte)0x03, (byte)0xff, (byte)0x6a, (byte)0x72, (byte)0x6d, (byte)0x6c, (byte)0x5b, (byte)0x51,
                    (byte)0x8d, (byte)0x1b, (byte)0xaf, (byte)0x92, (byte)0xbb, (byte)0xdd, (byte)0xbc, (byte)0x7f,
                    (byte)0x11, (byte)0xd9, (byte)0x5c, (byte)0x41, (byte)0x1f, (byte)0x10, (byte)0x5a, (byte)0xd8,
                    (byte)0x0a, (byte)0xc1, (byte)0x31, (byte)0x88, (byte)0xa5, (byte)0xcd, (byte)0x7b, (byte)0xbd,
                    (byte)0x2d, (byte)0x74, (byte)0xd0, (byte)0x12, (byte)0xb8, (byte)0xe5, (byte)0xb4, (byte)0xb0,
                    (byte)0x89, (byte)0x69, (byte)0x97, (byte)0x4a, (byte)0x0c, (byte)0x96, (byte)0x77, (byte)0x7e,
                    (byte)0x65, (byte)0xb9, (byte)0xf1, (byte)0x09, (byte)0xc5, (byte)0x6e, (byte)0xc6, (byte)0x84,
                    (byte)0x18, (byte)0xf0, (byte)0x7d, (byte)0xec, (byte)0x3a, (byte)0xdc, (byte)0x4d, (byte)0x20,
                    (byte)0x79, (byte)0xee, (byte)0x5f, (byte)0x3e, (byte)0xd7, (byte)0xcb, (byte)0x39, (byte)0x48
            };
    private final static int[] FK = {0xA3B1BAC6, 0x56AA3350, 0x677D9197, 0xB27022DC};
    private final static int[] CK =
            {
                    0x00070e15, 0x1c232a31, 0x383f464d, 0x545b6269,
                    0x70777e85, 0x8c939aa1, 0xa8afb6bd, 0xc4cbd2d9,
                    0xe0e7eef5, 0xfc030a11, 0x181f262d, 0x343b4249,
                    0x50575e65, 0x6c737a81, 0x888f969d, 0xa4abb2b9,
                    0xc0c7ced5, 0xdce3eaf1, 0xf8ff060d, 0x141b2229,
                    0x30373e45, 0x4c535a61, 0x686f767d, 0x848b9299,
                    0xa0a7aeb5, 0xbcc3cad1, 0xd8dfe6ed, 0xf4fb0209,
                    0x10171e25, 0x2c333a41, 0x484f565d, 0x646b7279
            };

    public final static int ECB = 0;
    public final static int CBC = 1;

    private int mode;
    private int[] rk = new int[32];
    private byte[] f = new byte[16];

    void Reset(int encmode, byte[] iv)
    {
        int i;

        mode = encmode;

        for(i=0; i<16; i++)
            f[i] = 0;

        if(mode != ECB && iv != null)
            for(i=0; i<16; i++)
                f[i] = iv[i];
    }
    /*------------SM4算法初始化函数---------------
            该函数实现标准中的“秘钥扩展算法”
    参数：
    s: sm4结构体对象；
    mode: 加密模式，目前支持ECB和CBC；
    key: 128位加密密钥；
    iv: 128位初始向量
    */
    public boolean Init(int encmode, byte[] key, byte[] iv)
    {
        int[] MK = new int[4];
        int[] K = new int[36];
        int A, B, i;

        Reset(encmode, iv);
	    /*---------------用128位加密密钥扩展得到32个32位轮密钥-----------------*/
        //从128位加密密钥计算得到MK0，MK1，MK2和MK3
        for(i=0; i<4; i++)
            MK[i] = ((int)key[4*i]<<24)|
                    (((int)key[4*i+1]<<16) & 0xFF0000) |
                    (((int)key[4*i+2]<<8) & 0xFF00) |
                    (((int)key[4*i+3]) & 0xFF);

        for(i=0; i<4; i++)
            K[i] = MK[i] ^ FK[i];

        // rk[i] = K[i+4] = K[i]^T'(K[i+1]^K[i+2]^K[i+3]^CK[i])
        for(i=0; i<32; i++)
        {
            A = K[i+1]^K[i+2]^K[i+3]^CK[i];

            B = SBOX[(A>>>24)]<<24;
            //计算B = tao(A)
            B = (int)(SBOX[A & 0xFF]) & 0xFF |
                    (((int)(SBOX[(A>>8) & 0xFF]))<<8) & 0xFF00 |
                    (((int)(SBOX[(A>>16) & 0xFF]))<<16) & 0xFF0000 |
                    ((int)(SBOX[(A>>24) & 0xFF]))<<24;

            K[i+4] = K[i]^B^(((B)<<13) | ((B)>>>19))^(((B)<<23) | ((B)>>>9));
            rk[i] = K[i+4];
        }
        return true;
    }
    /*----------ECB模式下的SM4加密算法---------

        其他加密模式都在此模式基础上实现

    参数：
    s：sm4结构体对象；
    buff：128位待加密数据，加密后的数据也存放在该对象中；
    */
    void Ecb_Encrypt(byte[] buff)
    {
        int[] X = new int[36];
        int[] Y = new int[4];
        int[] p;
        int A, B;
        int i;

        //初始化X0，X1，X2，X3
        for(i=0; i<4; i++)
            X[i] = ((int)buff[4*i]<<24)|
                    ((int)buff[4*i+1]<<16) & 0xFF0000|
                    ((int)buff[4*i+2]<<8)  & 0xFF00|
                    ((int)buff[4*i+3]) & 0xFF;

        p = rk;

        //计算X4, ..., X35，
        //X[i+4] = F(X[i], X[i+1], X[i+2], X[i+3], rk[i]) = (X[i]^T(X[i+1]^X[i+2]^X[i+3]^p[i]))
        for(i=0; i<32; i++)
        {
            A = X[i+1]^X[i+2]^X[i+3]^p[i];

            //tao运算
            B = (int)(SBOX[A & 0xFF]) & 0xFF |
                    (((int)(SBOX[(A>>8) & 0xFF]))<<8) & 0xFF00 |
                    (((int)(SBOX[(A>>16) & 0xFF]))<<16) & 0xFF0000 |
                    ((int)(SBOX[(A>>24) & 0xFF]))<<24;

            X[i+4] = X[i]^B^
                    (((B)<<2) | ((B)>>>30))^
                    (((B)<<10) | ((B)>>>22))^
                    (((B)<<18) | ((B)>>>14))^
                    (((B)<<24) | ((B)>>>8));
        }

        Y[0] = X[35];
        Y[1] = X[34];
        Y[2] = X[33];
        Y[3] = X[32];

        for(i=0; i<4; i++)
        {
            buff[4*i] = (byte)(Y[i]>>>24);
            buff[4*i+1] = (byte)(Y[i]>>>16);
            buff[4*i+2] = (byte)(Y[i]>>>8);
            buff[4*i+3] = (byte)Y[i];
        }
    }
    /*----------ECB模式下的SM4解密算法---------

        其他解密模式都在此模式基础上实现

    参数：
    s：sm4结构体对象；
    buff：128位待解密数据，解密后的数据也存放在该对象中；
    */
    void Ecb_Decrypt(byte[] buff)
    {
        int[] X = new int[36];
        int[] Y = new int[4];
        int[] p;
        int A, B;
        int i;

        //初始化X0，X1，X2，X3
        for(i=0; i<4; i++)
            X[i] = ((int)buff[4*i]<<24)|
                    ((int)buff[4*i+1]<<16) & 0xFF0000|
                    ((int)buff[4*i+2]<<8) & 0xFF00|
                    ((int)buff[4*i+3]) & 0xFF;

        p = rk;

        //计算X4, ..., X35，
        //X[i+4] = F(X[i], X[i+1], X[i+2], X[i+3], rk[i]) = (X[i]^T(X[i+1]^X[i+2]^X[i+3]^p[i]))
        for(i=0; i<32; i++)
        {
            A = X[i+1]^X[i+2]^X[i+3]^p[31-i];

            //tao运算
            B = (int)(SBOX[A & 0xFF]) & 0xFF |
                    (((int)(SBOX[(A>>8) & 0xFF]))<<8) & 0xFF00 |
                    (((int)(SBOX[(A>>16) & 0xFF]))<<16) & 0xFF0000 |
                    ((int)(SBOX[(A>>24) & 0xFF]))<<24;

            X[i+4] = X[i]^B^
                    (((B)<<2) | ((B)>>>30))^
                    (((B)<<10) | ((B)>>>22))^
                    (((B)<<18) | ((B)>>>14))^
                    (((B)<<24) | ((B)>>>8));
        }

        Y[0] = X[35];
        Y[1] = X[34];
        Y[2] = X[33];
        Y[3] = X[32];

        for(i=0; i<4; i++)
        {
            buff[4*i] = (byte)(Y[i]>>>24);
            buff[4*i+1] = (byte)(Y[i]>>>16);
            buff[4*i+2] = (byte)(Y[i]>>>8);
            buff[4*i+3] = (byte)Y[i];
        }
    }
    /*----------------SM4加密算法-------------------

    目前支持的加密模式：ECB，CBC

    参数：
    s: 加密结构体；
    buff：待加密数据；
    */
    public int Encrypt(byte[] buff)
    {
        int i;

        switch(mode)
        {
            case ECB:
                Ecb_Encrypt(buff);
                return 0;

            case CBC:
                for(i=0; i<16; i++)
                    buff[i] ^= f[i];

                Ecb_Encrypt(buff);

                for(i=0; i<16; i++)
                    f[i] = buff[i];

                return 0;

            default:
                return 0;
        }
    }
    /*----------------SM4解密算法-------------------
    目前支持的加密模式：ECB，CBC

    参数：
    s: 加密结构体；
    buff：待解密数据；
    */
    public int Decrypt(byte[] buff)
    {
        int i;
        byte[] t = new byte[16];

        switch(mode)
        {
            case ECB:
                Ecb_Decrypt(buff);
                return 0;

            case CBC:
                for(i=0; i<16; i++)
                {
                    t[i] = f[i];
                    f[i] = buff[i];
                }

                Ecb_Decrypt(buff);

                for(i=0; i<16; i++)
                {
                    buff[i] ^= t[i];
                    t[i] = 0;
                }

                return 0;

            default:
                return 0;
        }
    }

}

