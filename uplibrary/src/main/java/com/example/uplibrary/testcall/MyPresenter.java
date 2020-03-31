package com.example.uplibrary.testcall;

import com.blankj.utilcode.util.LogUtils;
import com.example.uplibrary.bean.ResponseVo;

public class MyPresenter extends PresentImpl {

    @Override
    public void onSuccess(ResponseVo data) {

        LogUtils.d("MyPresenter call");

        LogUtils.d(data.getMessage());
    }

    @Override
    public void onFail() {
        super.onFail();
    }
}
