package com.bjpowernode.secnkill.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//校检手机号，使用正则表达式
public class VaildatorUtil {
    private static final Pattern moblie_pattern=Pattern.compile("[1]([3-9])[0-9]{9}$");
    public static boolean isMoblie(String mobolie){
        if (StringUtils.isEmpty(mobolie)){
            return false;
        }
        Matcher matcher = moblie_pattern.matcher(mobolie);
        return matcher.matches();
    }
}
