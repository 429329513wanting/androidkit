package com.sendinfo.androidkit.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPFragment;
import com.sendinfo.androidkit.mvp.BaseModel;
import com.sendinfo.androidkit.mvp.CommonStringP;
import com.sendinfo.androidkit.mvp.HttpDto;
import com.sendinfo.androidkit.mvp.ICommonStringView;
import com.sendinfo.androidkit.util.Constraint;
import com.sendinfo.androidkit.util.JsonUtil.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;


public class DemoComStringFragment extends BaseMVPFragment<CommonStringP> implements ICommonStringView {

    @BindView(R.id.upload_tv)
    TextView upload_tv;

    @Override
    protected void initArgs(Bundle bundle) {

    }

    @Override
    protected void initView(Bundle bundle) {

        setContentView(R.layout.fragment_demo_comm_str);
        mPresenter = new CommonStringP(this);
    }

    @Override
    protected void initData() {

        //发送请求
        HttpDto dto = new HttpDto(Constraint.IMGS);
        BaseModel baseModel = new BaseModel();
        baseModel.imageType = "1,3,4";
        baseModel.projectCode = "05750001";
        dto.setBaseModel(baseModel);
        dto.setMethod(HttpDto.GET);
        mPresenter.getData(dto);

    }
    @OnClick(R.id.upload_tv)
    public void viewClick(View view){

        HttpDto dto = new HttpDto(Constraint.UPLOAD_EVENT+"?token="+ SPUtils.getInstance().getString(Constraint.TOKEN));
        BaseModel baseModel = new BaseModel();
        baseModel.imageType = "1,3,4";
        baseModel.projectCode = "05750001";
        dto.setBodyJson(JsonUtil.getJsonString(baseModel));
        mPresenter.getData(dto);

    }

    @Override
    public void onSucess(String data, HttpDto httpDto) {

        try {

            if (httpDto.getUrl().endsWith("getImageUrls")){

                JSONObject jbRoot = new JSONObject(data);
                LogUtils.d(jbRoot.getBoolean("success"));

            }else if (httpDto.getUrl().contains("api/event/commit.htm")){

                if (data.startsWith("{") && data.endsWith("}")){

                    LogUtils.d("normal json");


                }else {

                    showToast(data);
                }
            }


        }catch (JSONException e){

            e.printStackTrace();
        }
    }
}
