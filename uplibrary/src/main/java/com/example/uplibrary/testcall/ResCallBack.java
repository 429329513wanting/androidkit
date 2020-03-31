package com.example.uplibrary.testcall;

import com.example.uplibrary.bean.ResponseVo;

public interface ResCallBack {

    void onSuccess(String data);
    void onSuccess(ResponseVo obj);
    void onFail();
}
