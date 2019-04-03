package com.sendinfo.androidkit.module.mecenter.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPActivity;
import com.sendinfo.androidkit.base.BaseMVPFragment;
import com.sendinfo.androidkit.module.MainActivity;
import com.sendinfo.androidkit.util.Constraint;

import butterknife.OnClick;

public class LoginActivity extends BaseMVPActivity {

    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView() {

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

        SPUtils.getInstance().put(Constraint.IS_LOGIN,"1");
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }
}
