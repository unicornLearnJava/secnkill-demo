package com.bjpowernode.secnkill.utils;

import org.apache.commons.codec.digest.DigestUtils;

//存储数据用到两层MD5加密 一种是在前端加密 另一种在数据库加密
public class MD5Util {

    private static final String salt = "abcdefgasds";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    //前端加密
    public static String inuPutPassToFromPass(String pass) {
        String src = salt.charAt(0) + salt.charAt(5) + pass + salt.charAt(6) + salt.charAt(7);
        return md5(src);
    }

    //存进数据库之前加密
    public static String fromPassToDB(String frompass, String salt) {
        String src = salt.charAt(0) + salt.charAt(3) + frompass + salt.charAt(4) + salt.charAt(5);
        return md5(src);
    }

    //两种合并,得到最后加密的数据
    public static String inPutPassToDb(String inputpass, String salt) {
        String src = inuPutPassToFromPass(inputpass);
        String src2 = fromPassToDB(src, "121212121");
        return src2;
    }

    public static void main(String[] args) {
        //123456
        String s1 = fromPassToDB("75b9c091404e2af8e95117656b891492", "121212");
        //789123
        String s2 = fromPassToDB("5c26c20f7b3e07f452b207ebf901892c", "121212");
        //567234
        String s3 = fromPassToDB("bd81d6487702278d763eefce7540e155", "121212");
        System.out.println(s1);



    }

}
