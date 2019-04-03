package com.sendinfo.androidkit.module;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPActivity;
import com.sendinfo.androidkit.base.MyApplication;
import com.sendinfo.androidkit.module.home.ui.HomeFragment;
import com.sendinfo.androidkit.module.mecenter.ui.MeFragment;
import com.sendinfo.androidkit.mvp.BaseResponse;
import com.sendinfo.androidkit.mvp.CommonP;
import com.sendinfo.androidkit.mvp.ICommonView;


import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends BaseMVPActivity<CommonP> implements ICommonView {

    @BindView(R.id.bottom_rg)
    RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private Fragment mCurrentFragment;

    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView() {

        myTopNavBar.setVisibility(View.GONE);
        mPresenter = new CommonP(this);
        fragmentManager = getSupportFragmentManager();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Class<? extends Fragment> clz = null;
                if (checkedId == R.id.home_rg){

                    clz = HomeFragment.class;

                }else if (checkedId == R.id.me_rg){

                    clz = MeFragment.class;
                }

                showOrHideFragment(R.id.container,clz);
            }
        });

        radioGroup.check(R.id.home_rg);
        showOrHideFragment(R.id.container,HomeFragment.class);

    }
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }



    @Override
    public void Success(BaseResponse response) {

    }



    private void showOrHideFragment(int contentId, Class<?extends Fragment> clz) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (mCurrentFragment != null &&
                mCurrentFragment.getClass().getSimpleName().equals(clz.getSimpleName())){

            return;
        }

        Fragment fragment = null;

        try {

            Fragment oldFragment = fragmentManager.findFragmentByTag(clz.getSimpleName());
            if (oldFragment != null){

                transaction.show(oldFragment);
                transaction.hide(mCurrentFragment);
                mCurrentFragment = oldFragment;

            }else {


                fragment = clz.newInstance();
                transaction.add(contentId,fragment,clz.getSimpleName());

                if (mCurrentFragment != null){

                    transaction.hide(mCurrentFragment);
                }

                mCurrentFragment = fragment;

            }

            transaction.commit();

        }catch (InstantiationException e){

            e.printStackTrace();

        }catch (IllegalAccessException e){

            e.printStackTrace();
        }


    }

    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 3000) {

            //ToastUtils.showLong("再按一次退出");
            mExitTime = System.currentTimeMillis();

        } else {

            showDialog(SweetAlertDialog.WARNING_TYPE,
                    "提示",
                    "确定退出?",
                    "确定",
                    "取消",
                    dialog ->
                    {
                        finish();
                         MyApplication.instance.exitApp();
                    },
                    dialog -> dialog.dismiss());
        }
    }
}
