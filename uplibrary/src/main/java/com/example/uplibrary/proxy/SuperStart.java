package com.example.uplibrary.proxy;

import com.blankj.utilcode.util.LogUtils;

public class SuperStart implements IStartAction{
    @Override
    public void doWork() {

        LogUtils.d("演出开始");
    }
}
