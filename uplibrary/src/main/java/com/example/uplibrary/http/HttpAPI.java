package com.example.uplibrary.http;

import android.app.Activity;

import com.example.uplibrary.Constraint;
import com.example.uplibrary.bean.AppInfoVo;
import com.example.uplibrary.bean.ResponseVo;

import java.util.Map;

public class HttpAPI {


    public static void getAPPInfo(Map<String, String> params, Activity activity, final UICallBack callBack) {


        HttpPostUtil httpPostUtil = new HttpPostUtil(Constraint.GET_APP_INFO, params, activity, false, new ResultCallBack() {
            @Override
            public void onSuccess(String result) {

                ResponseVo responseVo = JsonTool.fromJson(result, ResponseVo.class);
                AppInfoVo appInfoVo = JsonTool.jsonToObject(JsonTool.toJson(responseVo.getDataInfo()),AppInfoVo.class);
                callBack.result(appInfoVo);
            }

            @Override
            public void onFail(String msg) {

                callBack.fail(msg);

            }
        });
        httpPostUtil.execRequest();

    }

    public static void sign(Map<String, String> params, Activity activity, final UICallBack callBack) {


        HttpPostUtil httpPostUtil = new HttpPostUtil(Constraint.SIGN, params, activity, false, new ResultCallBack() {
            @Override
            public void onSuccess(String result) {

                callBack.result(result);
            }

            @Override
            public void onFail(String msg) {

                callBack.fail(msg);

            }
        });
        httpPostUtil.execRequest();

    }

    public static void queryOrders(Map<String, String> params, Activity activity, final UICallBack callBack) {


        HttpPostUtil httpPostUtil = new HttpPostUtil(Constraint.QUERY_ORDERS, params, activity, new ResultCallBack() {
            @Override
            public void onSuccess(String result) {

                callBack.result(result);
            }

            @Override
            public void onFail(String msg) {

                callBack.fail(msg);

            }
        });
        httpPostUtil.execRequest();

    }


    public static void login(Map<String, String> params, Activity activity, final UICallBack callBack) {


        HttpGetUtil getUtil = new HttpGetUtil(Constraint.LOGIN, params, activity, new ResultCallBack() {
            @Override
            public void onSuccess(String result) {

                callBack.result(result);

            }

            @Override
            public void onFail(String msg) {

            }
        });
        getUtil.execRequest();
    }
}