package com.example.uplibrary.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class GoodsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public GoodsAdapter(int layout, List<String>datas){
        super(layout,datas);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
