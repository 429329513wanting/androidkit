package com.sendinfo.androidkit.module.mecenter.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.example.uplibrary.UpMainActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPActivity;
import com.sendinfo.androidkit.bean.LoginVo;
import com.sendinfo.androidkit.module.mecenter.contract.LoginContract;
import com.sendinfo.androidkit.module.mecenter.presenter.LoginPresenter;
import com.sendinfo.androidkit.mvp.BaseModel;
import com.sendinfo.androidkit.mvp.HttpDto;
import com.sendinfo.androidkit.util.Constraint;
import com.sendinfo.androidkit.util.TextTool;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMVPActivity<LoginContract.Presenter>
        implements LoginContract.View {

    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.tool_tv)
    Button toolTv;

    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView() {

        myTopNavBar.setVisibility(View.GONE);
        mPresenter = new LoginPresenter(this);
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        TextTool.getBuilder("测试标题").setForegroundColor(ContextCompat.getColor(this,R.color.bg_orange_n))
                .setItalic()
                .append("小标题")
                .setXProportion(4)
                .setForegroundColor(ContextCompat.getColor(this,R.color.main_orange_stroke_color))
                .into(toolTv);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.login_btn)
    public void viewClick(View view) {

        Beta.checkUpgrade(true, false);

        HttpDto httpDto = new HttpDto(Constraint.LOGIN).setTryAgain(true);
        BaseModel baseModel = new BaseModel();
        baseModel.username = "13867551710";
        baseModel.password = EncryptUtils.encryptMD5ToString("123456").toLowerCase();
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
        SPUtils.getInstance().put(Constraint.IS_LOGIN, "1");
        ActivityUtils.startActivity(UpMainActivity.class);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
