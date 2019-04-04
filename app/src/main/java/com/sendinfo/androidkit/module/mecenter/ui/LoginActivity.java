package com.sendinfo.androidkit.module.mecenter.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPActivity;
import com.sendinfo.androidkit.base.BaseMVPFragment;
import com.sendinfo.androidkit.bean.LoginVo;
import com.sendinfo.androidkit.module.MainActivity;
import com.sendinfo.androidkit.module.mecenter.contract.LoginContract;
import com.sendinfo.androidkit.module.mecenter.presenter.LoginPresenter;
import com.sendinfo.androidkit.mvp.BaseModel;
import com.sendinfo.androidkit.mvp.HttpDto;
import com.sendinfo.androidkit.util.Constraint;

import butterknife.OnClick;

public class LoginActivity extends BaseMVPActivity<LoginContract.Presenter>
        implements LoginContract.View {

    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView() {

        myTopNavBar.setVisibility(View.GONE);
        mPresenter = new LoginPresenter(this);
        ImmersionBar.with(this).statusBarDarkFont(true).init();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @OnClick(R.id.login_btn)
    public void viewClick(View view){

        HttpDto httpDto = new HttpDto(Constraint.LOGIN);
        BaseModel baseModel = new BaseModel();
        baseModel.username = "15257958840";
        baseModel.checkCode = "123456";
        httpDto.setBaseModel(baseModel);
        mPresenter.getData(httpDto);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void loginSuccess(LoginVo loginVo) {

        ImmersionBar.with(this).statusBarDarkFont(false).init();
        SPUtils.getInstance().put(Constraint.IS_LOGIN,"1");
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}
