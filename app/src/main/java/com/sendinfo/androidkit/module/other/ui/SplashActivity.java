package com.sendinfo.androidkit.module.other.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.module.MainActivity;
import com.sendinfo.androidkit.module.mecenter.ui.LoginActivity;
import com.sendinfo.androidkit.util.Constraint;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (StringUtils.isEmpty(SPUtils.getInstance().getString(Constraint.IGNORE_WELCOM))){

            ActivityUtils.startActivity(WelcomActivity.class);

        }else {

            if (StringUtils.isEmpty(SPUtils.getInstance().getString(Constraint.IS_LOGIN))||
                    SPUtils.getInstance().getString(Constraint.IS_LOGIN).equals("0")){

                ActivityUtils.startActivity(LoginActivity.class);

            }else {

                ActivityUtils.startActivity(MainActivity.class);


            }
        }
        finish();

    }
}
