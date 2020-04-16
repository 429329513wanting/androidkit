package com.example.uplibrary.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class BanerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BanerAdapter(int layout, List<String>datas){
        super(layout,datas);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
