package com.sendinfo.androidkit.module.mecenter.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.j256.ormlite.logger.Log;
import com.sendinfo.androidkit.bean.LoginVo;
import com.sendinfo.androidkit.module.mecenter.contract.LoginContract;
import com.sendinfo.androidkit.mvp.BaseModel;
import com.sendinfo.androidkit.mvp.BaseResponse;
import com.sendinfo.androidkit.mvp.HttpDto;
import com.sendinfo.androidkit.mvp.IPresenterImpl;
import com.sendinfo.androidkit.mvp.IView;
import com.sendinfo.androidkit.util.JsonUtil.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        BaseResponse response = null;
        try {

            response = JsonUtil.getObject(s,BaseResponse.class);

        }catch (Exception e){

            mView.showSweetDialog(SweetAlertDialog.WARNING_TYPE,"提示","解析异常");
            return;

        }
        if (!response.success){

            mView.showSweetDialog(SweetAlertDialog.WARNING_TYPE,"提示",response.getMsg());
            return;
        }

        try {

            JSONObject jsonob = new JSONObject(JsonUtil.getJsonString(response.user));
            LogUtils.d(jsonob);
            JSONArray jsarr = jsonob.getJSONArray("scenicCode");
            LogUtils.d(jsarr.get(0));
            JSONObject operator = jsonob.getJSONObject("operator");
            LogUtils.d(operator.getString("realName"));


        }catch (JSONException e){

            e.printStackTrace();
        }

        LoginVo loginVo = JsonUtil.getObjectFromObject(response.user,LoginVo.class);

        mView.loginSuccess(loginVo);
    }
}
