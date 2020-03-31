package com.sendinfo.androidkit.mvp;

import com.blankj.utilcode.util.LogUtils;
import com.lzy.okgo.model.Response;
import com.sendinfo.androidkit.util.JsonUtil.JsonUtil;


import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/17
 *     desc   :
 * </pre>
 */

public class HttpRequestImpl implements IHttpRequest {

    @Override
    public void getData(final RequestCallBack<BaseResponse> callBack, final HttpDto httpDto) {

        httpDto.print();
        httpDto.getRequest()
                .execute(new BaseCallBack(callBack,httpDto){
                    @Override
                    public void onSuccess(Response<String> response) {

                        Observable<BaseResponse> observable = Observable.just(response.body())
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.newThread())
                                .map((String s) ->{

                                        if (s.startsWith("\"") && s.endsWith("\"")){
                                            s = s.substring(1, s.length() - 1).trim();
                                            s = s.replace("\\","");
                                        }
                                        LogUtils.json("请求服务器响应:" + "=================\n\n"+ JsonUtil.jsonPrintFormat(s));

                                        BaseResponse result = JsonUtil.getObject(s,BaseResponse.class);
                                        return result;
                                });

                        //回到主线程进行回调
                        callBack.requestSuccess(observable,httpDto);
                    }
                });
    }
}
