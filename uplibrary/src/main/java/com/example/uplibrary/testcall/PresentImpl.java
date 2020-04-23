package com.example.uplibrary.testcall;

import com.blankj.utilcode.util.LogUtils;
import com.example.uplibrary.bean.ResponseVo;

public class PresentImpl implements ResCallBack {

    public void getData(){

        HttpRequest request = new HttpRequest();
        request.sendRequest(this);
    }



    @Override
    public void onSuccess(String data) {

        LogUtils.d("PresentImpl call");
        ResponseVo vo = new ResponseVo();
        vo.setMessage(data);
        onSuccess(vo);
    }

    @Override
    public void onSuccess(ResponseVo obj) {


    }

    @Override
    public void onFail() {


    }
}
