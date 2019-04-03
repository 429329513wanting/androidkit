package com.sendinfo.androidkit.mvp;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/17
 *     desc   :
 * </pre>
 */

public interface IPresenter {

    void onResume();
    void onDestroy();
    void getData(HttpDto httpDto);
}
