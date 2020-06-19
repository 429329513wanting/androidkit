package com.sendinfo.androidkit.mvp;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/18
 *     desc   :
 * </pre>
 */

public interface ICommonView extends IView {

    void Success(BaseResponse response,HttpDto httpDto);
}
