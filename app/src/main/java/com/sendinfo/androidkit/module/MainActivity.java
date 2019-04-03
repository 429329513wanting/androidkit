package com.sendinfo.androidkit.module;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPActivity;
import com.sendinfo.androidkit.mvp.BaseResponse;
import com.sendinfo.androidkit.mvp.CommonP;
import com.sendinfo.androidkit.mvp.HttpDto;
import com.sendinfo.androidkit.mvp.ICommonView;
import com.sendinfo.androidkit.util.Constraint;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMVPActivity<CommonP> implements ICommonView {

    @BindView(R.id.click_btn)
    Button button;
    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView() {

        mPresenter = new CommonP(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.click_btn)
    public void viewClick(View view){

        HttpDto dto = new HttpDto(Constraint.ADD_FACE);
        mPresenter.getData(dto);

    }

    @Override
    public void Success(BaseResponse response) {

    }
}
