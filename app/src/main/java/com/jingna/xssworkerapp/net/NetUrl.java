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
    //个人资料信息获取
    public static String personalDataUrl = "index.php/WorkApi/User/PersonalData";
    //上传头像
    public static String uploadTheImgUrl = "index.php/WorkApi/User/UploadTheImg";
    //更改电话号码
    public static String updateUserPhoneUrl = "index.php/WorkApi/User/UpdateUserPhone";
    //实名认证
    public static String real_Name_AuthenticationUrl = "index.php/WorkApi/User/Real_Name_Authentication";
    //工作经历列表
    public static String workExperienceListUrl = "index.php/WorkApi/User/workExperienceList";
    //添加工作经历
    public static String workExperienceAddUrl = "index.php/WorkApi/User/workExperienceAdd";
    //修改工作经历信息获取
    public static String workExperienceInfoUrl = "index.php/WorkApi/User/workExperienceInfo";
    //保存修改的工作经历
    public static String workExperienceSaveUrl = "index.php/WorkApi/User/workExperienceSave";
    //删除工作经历
    public static String delete_WorkExperienceUrl = "index.php/WorkApi/User/delete_WorkExperience";
}
