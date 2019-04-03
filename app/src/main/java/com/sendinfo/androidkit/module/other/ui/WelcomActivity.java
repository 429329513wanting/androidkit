package com.sendinfo.androidkit.module.other.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPActivity;
import com.sendinfo.androidkit.module.MainActivity;
import com.sendinfo.androidkit.module.mecenter.ui.LoginActivity;
import com.sendinfo.androidkit.util.Constraint;
import com.sendinfo.androidkit.widget.welcomPage.PageFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WelcomActivity extends BaseMVPActivity {

    private PageFrameLayout contentFrameLayout;

    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView() {

        myTopNavBar.setVisibility(View.GONE);
        contentFrameLayout = (PageFrameLayout) findViewById(R.id.contentFrameLayout);
        // 设置资源文件和选中圆点
        contentFrameLayout.setUpViews(new int[]{
                R.layout.page_tab1,
                R.layout.page_tab2,
                R.layout.page_tab3,
                R.layout.page_tab4
        }, R.mipmap.banner_on, R.mipmap.banner_off);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcom;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMain(String msg){

        SPUtils.getInstance().put(Constraint.IGNORE_WELCOM,"1");
        if (!SPUtils.getInstance().getString(Constraint.IS_LOGIN).equals("1")){

            ActivityUtils.startActivity(LoginActivity.class);

        }else {

            ActivityUtils.startActivity(MainActivity.class);

        }
        finish();
    }
}
