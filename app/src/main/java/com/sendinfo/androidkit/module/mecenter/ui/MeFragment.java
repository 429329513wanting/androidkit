package com.sendinfo.androidkit.module.mecenter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPActivity;
import com.sendinfo.androidkit.base.BaseMVPFragment;
import com.sendinfo.androidkit.util.Constraint;

import butterknife.OnClick;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2019/04/03
 *     desc   :
 * </pre>
 */

public class MeFragment extends BaseMVPFragment {
    @Override
    protected void initArgs(Bundle bundle) {

    }

    @Override
    protected void initView(Bundle bundle) {

        setContentView(R.layout.fragment_me);
    }

    @Override
    protected void initData() {

    }
    @OnClick(R.id.logout_btn)
    public void viewClick(View view){


        SPUtils.getInstance().put(Constraint.IS_LOGIN,"0");
        Intent intent = new Intent(getContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityUtils.startActivity(intent);
    }
}
