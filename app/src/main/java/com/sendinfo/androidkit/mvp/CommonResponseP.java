package com.sendinfo.androidkit.mvp;

import com.sendinfo.androidkit.util.JsonUtil.JsonUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2018/05/18
 *     desc   :
 * </pre>
 */

public class CommonResponseP extends IPresenterImpl<ICommonResponseView,BaseResponse> implements IPresenter{
    public CommonResponseP(ICommonResponseView view) {
        super(view);
    }

    @Override
    public void requestSuccess(BaseResponse data, HttpDto httpDto) {

        mView.Success(data,httpDto);


    }

    @Override
    public void requestFail(String msg, HttpDto httpDto) {
        super.requestFail(msg, httpDto);
    }
}
