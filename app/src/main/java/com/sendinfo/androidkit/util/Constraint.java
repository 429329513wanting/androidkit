package com.sendinfo.androidkit.util;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/23
 *     desc   :
 * </pre>
 */

public class Constraint {


    public static final String AibeeUrl = "sendinfo.aibee.cn";

    public static final String SdkKey = "2da06b0849e84fb2";
    public static final String SdkSecret = "ZXYHp7GKanN7YY6kfj7jT9bLyZWf4Yxa";

    public static final String ApiKey = "3htkHXEu9MfcMb2JsQLlIxGDLKyZyaV8";
    public static final String ApiSecret = "JvfD4SIzL7nm9od9fmosLkVakUoq1tqd";

    public static final String FACE_SERVER = "https://###/";
    public static final String SERVER = "http://@@@/";

    //aibee
    //单个人买票
    public static final String ADD_FACE = FACE_SERVER+"tickets/v1/add";
    //查询人脸
    public static final String QUERY_FACE = FACE_SERVER+"tickets/v1/getPhoto";
    //删除人脸
    public static final String REMOVE_FACE = FACE_SERVER+"tickets/v1/remove";


    //登录
    public static final String LOGIN = SERVER+"api/CheckLogin";

    //查询票接口
    public static final String QUERY_TICKET = SERVER+"api/QueryTicketInfo";

    //检票
    public static final String CHECKT_TICKET = SERVER+"api/CheckTicket";

    public static final String ADDFACEINFO = SERVER+"api/AddFaceinfo";

    //查询景点
    public static final String PARKLIST = SERVER+"api/GetParkInfo";

    //查询年卡
    public static final String QUERY_YEARCARD = SERVER+"api/QueryICcardInfo";

    //查询faceID
    public static final String FACE_IDS = SERVER+"api/BarcodeFaceId";

    //年卡检票
    public static final String YEAR_CARD_CHECKUP = SERVER+"api/CheckICcardInfo";


    ///////////////普陀山业务

    //查票
    public static final String PUTUO_QUERYTICKET = SERVER+"api/face/Face/GetTicketPic";

    //上传人脸地址
    public static final String PUTUO_UPFACE = SERVER+"api/face/Face/AddFace";



    //配置属性
    public static final String BUSINESS_IP = "BUSINESS_IP";
    public static final String BUSINESS_PORT = "BUSINESS_PORT";
    public static final String FACE_IP = "FACE_IP";
    public static final String FACE_PORT = "FACE_PORT";
    public static final String PARK_CODE = "PARK_CODE";
    public static final String QUERY_PARK_CODE = "QUERY_PARK_CODE";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String GATE_NO = "GATE_NO";
    public static final String GATE_IP = "GATE_IP";
    public static final String CARD_TYPE = "CARD_TYPE";




}
