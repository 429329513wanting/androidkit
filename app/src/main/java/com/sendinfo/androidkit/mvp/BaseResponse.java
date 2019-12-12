package com.sendinfo.androidkit.mvp;

import java.io.Serializable;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/17
 *     desc   :
 * </pre>
 */

public class BaseResponse implements Serializable{


    private Object result;
    private String msg;
    private String code;
    public  boolean success;
    public  Object user;
    public  String token;



    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }







}
