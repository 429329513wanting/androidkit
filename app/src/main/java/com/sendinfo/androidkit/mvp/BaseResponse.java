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

    private Object Data;
    private String Result;
    private String Code;


    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }




}
