package com.example.uplibrary.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uplibrary.R;

import java.util.List;

public class NewsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public NewsAdapter(int layout, List<String>datas){
        super(layout,datas);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tit_tv,"新闻"+item);
    }
}
