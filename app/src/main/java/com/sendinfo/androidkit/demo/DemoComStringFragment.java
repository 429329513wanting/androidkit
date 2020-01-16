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

import com.blankj.utilcode.util.ActivityUtils;
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

import java.sql.Time;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class DemoComStringFragment extends BaseMVPFragment<CommonStringP> implements ICommonStringView {

    public  Handler mHandler;


    @BindView(R.id.upload_tv)
    TextView upload_tv;

    @Override
    protected void initArgs(Bundle bundle) {

        learnThreadPoor();
    }

    private void learnThreadPoor() {

        //缓存线程池，不超出用空闲的，超处工作范围就新建线程
        ExecutorService cachePoor = Executors.newCachedThreadPool();
        for (int i=0;i<10;i++){

            final  int index = i;

            try {

                Thread.sleep(index*1000);


            }catch (InterruptedException e){

                e.printStackTrace();
            }

            cachePoor.execute(new Runnable() {
                @Override
                public void run() {

                    LogUtils.d(index+"");
                    LogUtils.d(Thread.currentThread().getId());

                }
            });
        }
        //定长线程池,超出工作任务,放到队列里
        ExecutorService fixPoor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int n =0;n < 10;n++){
            final int sn = n;
            fixPoor.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        LogUtils.d("fixpoor"+sn);
                        Thread.sleep(2000);

                    }catch (InterruptedException e){

                        e.printStackTrace();
                    }

                }
            });
        }


        //周期性线程池
        //延迟3秒
        ScheduledExecutorService sche = Executors.newScheduledThreadPool(5);
        sche.schedule(new Runnable() {
            @Override
            public void run() {

            }
        },3000, TimeUnit.SECONDS);

        ScheduledExecutorService recycSche = Executors.newScheduledThreadPool(5);
        recycSche.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                LogUtils.d("recycSche");
            }
        },0,1, TimeUnit.SECONDS);



        //单线程池
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
    @OnClick({R.id.upload_tv,R.id.handle_thread_tv,R.id.accept_tv,R.id.webview_tv})
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
            //handler,处理和发送消息
            // looper,分发消息
            // messagequeue 接收handler发送过来的的
            //如果子线程要用handler,需要手动创建looper
            new Thread(new Runnable() {

                @Override
                public void run() {

                    Looper.prepare();

                    mHandler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {


                            LogUtils.d(Looper.myLooper());
                            LogUtils.d(msg.what+"");
                            super.handleMessage(msg);
                        }
                    };

                    Looper.loop();

                    //quit，终止循环

                }
            }).start();





        }else if(view.getId() == R.id.accept_tv){

            mHandler.sendEmptyMessage(2);

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

        }else if(view.getId() == R.id.webview_tv){

            ActivityUtils.startActivity(WebViewActivity.class);
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
