package com.example.uplibrary.proxy;

import com.blankj.utilcode.util.LogUtils;

public class SuperStartManager implements IStartAction {

    private IStartAction action;
    public SuperStartManager(IStartAction startAction){

        this.action = startAction;
    }
    @Override
    public void doWork() {

        LogUtils.d("通知明星演出");
        this.action.doWork();
    }
}
