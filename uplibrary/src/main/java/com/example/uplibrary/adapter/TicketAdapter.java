package com.example.uplibrary.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.uplibrary.R;
import com.example.uplibrary.bean.FaceTicketVo;

import java.util.ArrayList;

public class TicketAdapter extends BaseQuickAdapter<FaceTicketVo,BaseViewHolder> {

    public TicketAdapter(int layoutId, ArrayList<FaceTicketVo>datas){

        super(layoutId,datas);
    }
    @Override
    protected void convert(BaseViewHolder helper, FaceTicketVo item) {

        if (item.checked.equals("1")){

            helper.setImageResource(R.id.check_img,R.drawable.unchecked);

        }else {

            helper.setImageResource(R.id.check_img,R.drawable.checked);


        }
        helper.addOnClickListener(R.id.bg_ll);

    }


}
