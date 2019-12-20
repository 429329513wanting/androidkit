package com.sendinfo.androidkit.demo;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.sendinfo.androidkit.R;
import com.sendinfo.androidkit.base.BaseMVPFragment;
import com.sendinfo.androidkit.mvp.BaseModel;
import com.sendinfo.androidkit.mvp.CommonStringP;
import com.sendinfo.androidkit.mvp.HttpDto;
import com.sendinfo.androidkit.mvp.ICommonStringView;
import com.sendinfo.androidkit.util.BackgroundExeUtil;
import com.sendinfo.androidkit.util.Constraint;
import com.sendinfo.androidkit.util.JsonUtil.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.upload_tv,R.id.handle_thread_tv,R.id.accept_tv})
    public void viewClick(View view){

        if (view.getId() == R.id.upload_tv){

            HttpDto dto = new HttpDto(Constraint.UPLOAD_EVENT+"?token="+ SPUtils.getInstance().getString(Constraint.TOKEN));
            BaseModel baseModel = new BaseModel();
            baseModel.imageType = "1,3,4";
            baseModel.projectCode = "05750001";
            dto.setBodyJson(JsonUtil.getJsonString(baseModel));
            mPresenter.getData(dto);

        }else if (view.getId() == R.id.handle_thread_tv){

            //1.
            HandlerThread handlerThread = new HandlerThread("handlerThread");
            handlerThread.start();

            //2.
            Handler handler = new Handler(handlerThread.getLooper()){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    boolean isMain = Looper.myLooper() == Looper.getMainLooper();

                    LogUtils.d(isMain?"mainThread":Thread.currentThread().getName()+"==="+msg.obj);
                }
            };

            //3子线程里发消失
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = "from sub Thread";
                    handler.sendMessage(msg);
                }
            }).start();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = "from UI Thread";
                    handler.sendMessage(msg);
                    handlerThread.quitSafely();

                }
            });



        }else if(view.getId() == R.id.accept_tv){

            if (!BackgroundExeUtil.isIgnoringBatteryOptimizations(getActivity())){

                showSweetDialog(SweetAlertDialog.WARNING_TYPE, "提示",
                        "是否加入白名单",
                        "确定",
                        "取消",
                        new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        //BackgroundExeUtil.requestIgnoreBatteryOptimizations(getContext());
                        sweetAlertDialog.dismiss();
                        BackgroundExeUtil.goMeizuSetting(getActivity());

                    }
                },
                        new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        sweetAlertDialog.dismiss();

                    }
                });

            }

        }


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
