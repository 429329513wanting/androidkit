package com.sendinfo.androidkit.module.home.ui;

import android.os.Bundle;

import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPFragment;
import com.sendinfo.androidkit.mvp.BaseModel;
import com.sendinfo.androidkit.mvp.BaseResponse;
import com.sendinfo.androidkit.mvp.CommonP;
import com.sendinfo.androidkit.mvp.HttpDto;
import com.sendinfo.androidkit.mvp.ICommonView;
import com.sendinfo.androidkit.util.Constraint;

import java.util.HashMap;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2019/04/03
 *     desc   :
 * </pre>
 */

public class HomeFragment extends BaseMVPFragment<CommonP> implements ICommonView {
    @Override
    protected void initArgs(Bundle bundle) {

    }

    @Override
    protected void initView(Bundle bundle) {

        setContentView(R.layout.fragment_home);
        mPresenter = new CommonP(this);
    }

    @Override
    protected void initData() {


    }

    @Override
    public void Success(BaseResponse response) {


    }
}
