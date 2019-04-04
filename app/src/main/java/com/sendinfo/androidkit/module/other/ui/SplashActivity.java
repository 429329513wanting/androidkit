package com.sendinfo.androidkit.module.other.ui;

import android.animation.Animator;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.module.MainActivity;
import com.sendinfo.androidkit.module.mecenter.ui.LoginActivity;
import com.sendinfo.androidkit.util.Constraint;

public class SplashActivity extends Activity {

    private LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animationView = findViewById(R.id.splash_lottie);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (StringUtils.isEmpty(SPUtils.getInstance().getString(Constraint.IGNORE_WELCOM))){

                    ActivityUtils.startActivity(WelcomActivity.class);

                }else {

                    if (!SPUtils.getInstance().getString(Constraint.IS_LOGIN).equals("1")){

                        ActivityUtils.startActivity(LoginActivity.class);

                    }else {

                        ActivityUtils.startActivity(MainActivity.class);
                    }
                }
                finish();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
}
