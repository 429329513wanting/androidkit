package com.example.uplibrary;

public class Constraint {

    public static String BASE_URL = "http://192.168.66.229:9901";
    //public static String BASE_URL = "http://220.191.224.192:8085";

    //签到列表
    public static String SIGNLIST = BASE_URL+"/api/app/getOndutySignByUser.htm";
    //登录
    public static String LOGIN = BASE_URL+"/api/user/login.htm";


    //获取APP参数
    public static String GET_APP_INFO = BASE_URL+"/api/zzj/getAppInfo";

    //升级查询接口
    public static String QUERY_ORDERS = BASE_URL+"/api/zzj/queryOrder";

    //交易预保存（未支付）
    public static String SAVE_PREORDER = BASE_URL+"/api/zzj/savePreOrder";

    //交易确认（支付）
    public static String CONFIRM_PREORDER = BASE_URL+"/api/zzj/confirmPreOrder";

    //异常订单
    public static String QUERY_TRANS = BASE_URL+"/api/zzj/queryTrans";



    public static String UP_TYPE = "up_type";
    public static String APP_ID = "app_id";
    public static String SECRET = "secret";

}
