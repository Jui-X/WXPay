package com.mandarinbites.pay.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * @param: none
 * @description:
 * @author: KingJ
 * @create: 2019-12-17 13:52
 **/
public class MD5Util {
    public static String getMD5Str(String strValue) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String newStr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return newStr;
    }
}
