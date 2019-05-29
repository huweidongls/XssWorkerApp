package com.jingna.xssworkerapp.util;

/**
 * Created by Administrator on 2019/5/21.
 */

public class TokenUtils {

    public static String getToken(String url){
        String token = StringUtils.stringToMD5(url);
        String tokens = StringUtils.stringToMD5(token);
        return tokens;
    }

}
