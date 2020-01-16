package com.sendinfo.androidkit.mvp;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;



/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/17
 *     desc   : 用于转换服务器返回数据 为了方便直接解析成字符串
 * </pre>
 */

public class BaseCallBack extends AbsCallback<String> {

    private RequestCallBack<BaseResponse> requestCallBack;
    private HttpDto httpDto;

    public BaseCallBack(RequestCallBack<BaseResponse> callBack,HttpDto httpDto){

        this.requestCallBack = callBack;
        this.httpDto = httpDto;
    }

    /**
     * 开始发请求
     * @param request
     */
    @Override
    public void onStart(Request<String, ? extends Request> request) {

        super.onStart(request);
    }

    @Override
    public void onSuccess(Response<String> response) {


    }

    @Override
    public void onError(Response<String> response) {

        requestCallBack.requestFail("网络异常,请检查网络后再试",httpDto);
        super.onError(response);

    }

    @Override
    public String convertResponse(okhttp3.Response response) throws Throwable {

        return response.body().string();
        //在这里如果正常返回就会走onSuccess，在这里抛出异常就会回调onError包括解析错误

        //throw new IllegalStateException("SSSS");
    }
}
