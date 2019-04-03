package com.sendinfo.androidkit.module.mecenter.presenter;

import com.sendinfo.androidkit.bean.LoginVo;
import com.sendinfo.androidkit.module.mecenter.contract.LoginContract;
import com.sendinfo.androidkit.mvp.BaseModel;
import com.sendinfo.androidkit.mvp.BaseResponse;
import com.sendinfo.androidkit.mvp.HttpDto;
import com.sendinfo.androidkit.mvp.IPresenterImpl;
import com.sendinfo.androidkit.mvp.IView;
import com.sendinfo.androidkit.util.JsonUtil.JsonUtil;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2019/04/03
 *     desc   :
 * </pre>
 */

public class LoginPresenter extends IPresenterImpl<LoginContract.View, BaseResponse>
        implements LoginContract.Presenter {
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void requestSuccess(String s, HttpDto httpDto) {
        super.requestSuccess(s, httpDto);

        BaseResponse response = JsonUtil.getObject(s,BaseResponse.class);
        if (!response.getCode().equals("200")){

            mView.showDialog(SweetAlertDialog.WARNING_TYPE,"提示",response.getMsg());
            return;
        }

        LoginVo loginVo = JsonUtil.getObjectFromObject(response.getResult(),LoginVo.class);

        mView.loginSuccess(loginVo);
    }
}
