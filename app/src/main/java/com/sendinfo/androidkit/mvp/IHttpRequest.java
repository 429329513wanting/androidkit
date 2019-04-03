package com.sendinfo.androidkit.mvp;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/17
 *     desc   :
 * </pre>
 */

public interface IHttpRequest {

    void getData(RequestCallBack<BaseResponse> callBack, HttpDto httpDto);
}
