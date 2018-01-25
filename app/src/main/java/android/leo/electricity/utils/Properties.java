package android.leo.electricity.utils;

/**
 * Created by Leo on 2017/9/7.
 */

public class Properties {
    //类共通的类名字符串对象名
    public static String WEB_IP = "58.49.13.252";// ip
    public static String WEB_PORT = "8082";//port
    public static String PROJECT_NAME = "palmpower";//项目名
    public static String LOGIN_ACTION = "login";//登录
    public static String REGISTER_ACTION = "register";//注册
    public static String GETSECURITYCODE_ACTION = "getSecurityCode";//获取验证
    public static String LOCKUSER_ACTION = "lockUser";//绑定户号
    public static String QUERYCUSTOMERID_ACTION = "queryCustomerID";//快速查询客户编号
    public static String UEWUSERAPPLY_ACTION = "newUserApply";//用电申请
    public static String GETBOUNDCUSTOMERS_ACTION = "getBoundCustomers";//获取APP用户绑定的户号
    public static String GETDEPARTMENT_ACTION = "getDepartMent";//获取管理单位
    public static String GETPOWERAREA_ACTION = "getPowerArea";//获取服务网点
    public static String GETPOWERCUTINFO_ACTION = "getPowerCutInfo";//获取停电信息
    public static String QUERYELECTRIC_ACTION = "queryElectric";//电费查询
    public static String QUERYCHARGE_ACTION = "queryCharge";//电费记录
    public static String NEWUSERAPPLY_ACTION = "newUserApply";//用电申请
    public static String GETSECURITYCODEOFRESETPSD_ACTION = "getSecurityCodeOfResetPsd";//更改密码短信验证
    public static String UPDATEPASSWORD_ACTION = "updatePassword";//更新密码
    public static String UNLOCKUSER_ACTION = "unlockUser";//解绑用户
    public static String QUERYBALABCE_ACTION = "queryBalance";//欠费信息

    public static String USER_LINK = "ws/AppUser/userService";
    public static String POWER_LINK = "ws/Power/powerService";
}
