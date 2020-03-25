package com.example.uplibrary.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uplibrary.R;
import com.example.uplibrary.bean.FaceVo;

import java.util.List;

public class FaceAdapter extends BaseQuickAdapter<FaceVo, BaseViewHolder> {

    public FaceAdapter(int layout, List<FaceVo>datas){

        super(layout,datas);


    }

    @Override
    protected void convert(BaseViewHolder helper, FaceVo item) {


        helper.addOnClickListener(R.id.bg_ll);

    }
}
