package com.hznu.fa2login.modules.sys.service.encryption.cryptolib;


/**
 * Created by hb on 2015/11/20.
 */
public class SM3
{
    private static final int H0 = 0x7380166f;
    private static final int H1 = 0x4914b2b9;
    private static final int H2 = 0x172442d7;
    private static final int H3 = 0xda8a0600;
    private static final int H4 = 0xa96f30bc;
    private static final int H5 = 0x163138aa;
    private static final int H6 = 0xe38dee4d;
    private static final int H7 = 0xb0fb0e4e;

    private static final byte PAD = (byte)0x80;
    private static final byte ZERO = 0;

    private static long[] length = new long[2];//length[0]存放消息长度（bit数）的低32位值，length[1]存放高32位值，消息允许最大长度为2^64bits
    private static int[] H = new int[8];//存放每次迭代后的值，最后一次迭代后H数组中的值即为消息的hash值
    private static int[] W = new int[68];//存放每个分组（512bits）的扩展字（W0, ..., W67）
    private static int[] W1 = new int[64];//存放每个分组（512bits）的扩展字（W'0, ..., W'63）

    static byte[] hash(byte[] str) throws Exception
    {
        byte[] hashvalue = new byte[32];
        int i;

        if(str.length == 0)
        {
            throw new Exception("exception from SM3 algorithm: null string can't be hashed!");
        }
        Init();

        for(i=0; i<str.length; i++)
            Process(str[i]);

        Hash(hashvalue);

        return hashvalue;
    }
    /*
    SM3_process函数和SM3_transform函数一起完成一次压缩，
    其中SM3_process函数先完成由B(i)到W和W'的扩展，然后调用
    SM3_transform函数实现压缩；SM3_transform函数实现了
    SM3算法中的压缩函数CF。
    */
    private static void Init()
    {
        int i;

        length[0] = length[1] = 0L;

        H[0] = H0;
        H[1] = H1;
        H[2] = H2;
        H[3] = H3;
        H[4] = H4;
        H[5] = H5;
        H[6] = H6;
        H[7] = H7;

        for(i=0; i<68; i++)
            W[i] = 0;

        for(i=0; i<64; i++)
            W1[i] = 0;
    }
    private static void Process(byte byteval)
    {
        //参考miracl库的SHA-256代码（shs256_process函数），设计非常巧妙！:)
        //该函数先完成W[0...15]的赋值，当处理数据长度为512b时进行压缩
        int cnt;

        cnt=(int)((length[0]/32)%16); //32代表字长，16表示16个字中的顺序，用于确定W[i]中的位置

        W[cnt]<<=8;
        W[cnt]|=(int)(byteval&0xFF);

        length[0]+=8;   //一个字符8个bit

        if (length[0]==0x100000000L)
        {
            length[1]++;
            length[0]=0;
        }
        if ((length[0]%512)==0)
            Transform();
    }

    //该函数处理最后一个块
    private static void Hash(byte[] hash)
    {
        long len0, len1;
        int i;

        len0 = length[0];
        len1 = length[1];

        Process(PAD);
        while((length[0] % 512) != 448)
            Process(ZERO);

        W[14] = (int)(len1 & 0xFFFFFFFFL);
        W[15] = (int)(len0 & 0xFFFFFFFFL);

        Transform();

        for(i=0; i<32; i++)
            hash[i] = (byte)((H[i/4]>>>(8*(3-i%4))) & 0xff);

        Init();
    }

    private static void Transform()
    {
        int A, B, C, D, E, F, G, HH; //变量HH在C语言版本中的名字为H，因为Java库中该变量名和类中的数组H名字冲突，故改为HH
        int SS1, SS2, TT1, TT2;
        int j;

        int r7j13, r15j3, P1temp;

        //由W0, ..., W15扩展得到W[16..67]和W'[0..63]
        //扩展W[16..67]
        for(j=16; j<68; j++)
        {
            //W[j] = P1(W[j-16]^W[j-9]^ROTL15(W[j-3]))^ROTL7(W[j-13])^W[j-6];

            //求ROTL15(W[j-3])
            r15j3 = (((W[j-3])<<15) | ((W[j-3])>>>17));
            //求ROTL7(W[j-13]
            r7j13 = (((W[j-13])<<7) | ((W[j-13])>>>25));
            //求W[j-16]^W[j-9]^ROTL15(W[j-3])
            P1temp = W[j-16] ^ W[j-9] ^ r15j3;
            //P1(X):=(X)^ROTL15((X))^ROTL23((X))
            W[j] = ((P1temp) ^ (((P1temp)<<15) | ((P1temp)>>>17)) ^ (((P1temp)<<23) | ((P1temp)>>>9))) ^ r7j13 ^ W[j-6];
        }

        //扩展W'[0..63]
        for(j=0; j<64; j++)
            W1[j] = W[j]^W[j+4];

        //压缩函数CF
        //1. ABCDEFGH  <-- V(i)
        A = H[0];
        B = H[1];
        C = H[2];
        D = H[3];
        E = H[4];
        F = H[5];
        G = H[6];
        HH = H[7];

        int r12A, Tj, rnTjj, Temp;
        for(j=0; j<64; j++)
        {
            //SS1 = ROTL7(ROTL12(A) + E + ROTLn(T(j), j));
            //计算ROTL12(A)
            r12A = (((A)<<12) | ((A)>>>20));
            //计算T(j)
            Tj = (j<16 ? 0x79cc4519 : 0x7a879d8a);
            //计算ROTLn(T(j), j)
            rnTjj = (((Tj)<<j) | ((Tj)>>>(32-j)));

            //计算SS1
            Temp = r12A + E + rnTjj;
            SS1 = (((Temp)<<7) | ((Temp)>>>25));

            //SS2 = SS1^ROTL12(A);
            SS2 = SS1 ^ r12A;

            //TT1 = FF(j, A, B, C) + D + SS2 + W1[j];
            TT1 = (j<16 ? A^B^C : (A&B)|(A&C)|(B&C)) + D + SS2 + W1[j];

            //TT2 = GG(j, E, F, G) + H + SS1 + W[j];
            TT2 = (j<16 ? E^F^G : (E&F)|(~E&G)) + HH + SS1 + W[j];

            D = C;

            //C = ROTL9(B);
            C = (((B)<<9) | ((B)>>>23));

            B = A;

            A = TT1;

            HH = G;

            //G = ROTL19(F);
            G = (((F)<<19) | ((F)>>>13));

            F = E;

            //E = P0(TT2);
            int t1 = (((TT2)<<9) | ((TT2)>>>23));
            int t2 = (((TT2)<<17) | ((TT2)>>>15));
            E = TT2 ^ t1 ^ t2;
        }

        H[0] ^= A;
        H[1] ^= B;
        H[2] ^= C;
        H[3] ^= D;
        H[4] ^= E;
        H[5] ^= F;
        H[6] ^= G;
        H[7] ^= HH;
    }
}