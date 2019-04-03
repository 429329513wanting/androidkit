package com.sendinfo.androidkit.mvp;

import io.reactivex.Observable;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/17
 *     desc   : 用于回调界面
 * </pre>
 */

public interface RequestCallBack<T> {


    void requestSuccess(String s,HttpDto httpDto);

    void requestSuccess(Observable<String>observable, HttpDto httpDto);

    void requestFail(String msg,HttpDto httpDto);

    boolean isViewExist();

}
