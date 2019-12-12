package com.sendinfo.androidkit.module.home.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.bean.LoginVo;

import java.util.List;

/**
 * <pre>
 *     author : ghwang
 *     e-mail : 429329513@qq.com
 *     time   : 2019/04/04
 *     desc   :
 * </pre>
 */

public class TestAdapter extends BaseQuickAdapter<LoginVo, BaseViewHolder> {


    public TestAdapter(int layoutResId, @Nullable List<LoginVo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LoginVo item) {

        helper.setText(R.id.name_tv,item.getUser());
    }
}
