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
    //接单设置(回显)
    public static String orderAcceptanceSettingsUrl = "index.php/WorkApi/User/OrderAcceptanceSettings";
    //接单设置(保存)
    public static String orderAcceptanceSettingsSaveUrl = "index.php/WorkApi/User/OrderAcceptanceSettingsSave";
    //选择擅长服务分类列表
    public static String workerTypeListUrl = "index.php/WorkApi/User/WorkerTypeList";
    //保存擅长服务
    public static String saveWorkerTypeListUrl = "index.php/WorkApi/User/SaveWorkerTypeList";
    //定位接口
    public static String locationUrl = "index.php/api/Index/Home/Location";
    //已开通城市列表
    public static String openCityListUrl = "index.php/api/Index/Home/OpenCityList";
    //保存长据地
    public static String habitualresidenceUrl = "index.php/WorkApi/User/habitualresidence";
    //首页订单
    public static String indexOrderUrl = "index.php/WorkApi/User/IndexOrder";
    //修改首页听单状态
    public static String updateWorkerRadioUrl = "index.php/WorkApi/User/UpdateWorkerRadio";
    //工人接单
    public static String ReceiptUrl = "index.php/WorkApi/User/Receipt";
    //订单详情
    public static String order_ContentUrl = "index.php/WorkApi/User/Order_Content";
    //上传坐标
    public static String uploadCoordinatesUrl = "index.php/WorkApi/User/UploadCoordinates";
    //服务开始(设置)
    public static String service_startUrl = "index.php/WorkApi/User/service_start";
    //服务结束(设置)
    public static String service_endUrl = "index.php/WorkApi/User/service_end";
}
