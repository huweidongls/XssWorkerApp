package com.jingna.xssworkerapp.net;

/**
 * Created by Administrator on 2019/5/21.
 */

public class NetUrl {
    public static final String BASE_URL = "http://xss.5ijiaoyu.cn/";
    //登录
    public static String loginUrl = "index.php/WorkApi/Logins/login";
    //发送验证码
    public static String codeSendUrl = "index.php/WorkApi/Logins/CodeSend";
    //用户注册接口
    public static String registerUrl = "index.php/WorkApi/Logins/register";
    //找回密码
    public static String retrievePasswordUrl = "index.php/WorkApi/Logins/RetrievePassword";
    //个人中心主页信息
    public static String userInfoUrl = "index.php/WorkApi/User/UserInfo";
}
